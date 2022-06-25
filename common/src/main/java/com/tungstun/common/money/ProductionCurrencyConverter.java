package com.tungstun.common.money;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Production currency converter for use in production deploys.<br>
 * Converter loads the latest currency conversion rates and stores them for local conversion.<br>
 * Rates get updated daily by default, but can be changed in the {@code ConversionApiDetails}.
 * {@code Convert()} returns the same value in the specified currency.<br>
 * */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile("prod")
public class ProductionCurrencyConverter implements CurrencyConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ProductionCurrencyConverter.class);

    private final ConversionApiDetails apiDetails;
    private Map<String, Double> conversionRates= new HashMap<>();

    public ProductionCurrencyConverter(ConversionApiDetails apiDetails) {
        this.apiDetails = apiDetails;
        new AsyncConversionRateUpdater(0, this);
    }

    @Override
    public Money convert(Money money, Currency currency) {
        double moneyConversionRate = conversionRates.get(money.currency().code());
        double newCurrencyConversionRate = conversionRates.get(currency.code());
        BigDecimal value =  money.amount()
                .multiply(BigDecimal.valueOf(1 / moneyConversionRate))
                .multiply(BigDecimal.valueOf(newCurrencyConversionRate));
        return new Money(value, currency);
    }

    private void updateConversionRates() throws IOException {
        HttpURLConnection request = (HttpURLConnection) apiDetails.getUrl().openConnection();
        request.connect();
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        JsonObject json = root.getAsJsonObject();

        if (json.get("success").getAsBoolean()) {
            handleSuccess(json);
        } else {
            logError(json);
        }
    }

    private void logError(JsonObject json) {
        JsonObject errorBody = (JsonObject) json.get("error");
        LOG.warn("API error: code: {}. Message: {}}.",
                errorBody.get("code").getAsInt(),
                errorBody.get("info").getAsString());
    }

    private void handleSuccess(JsonObject json) {
        JsonObject conversionsJson = json.get("rates").getAsJsonObject();
        Type doubleMapType = new TypeToken<Map<String, Double>>() {
        }.getType();
        conversionRates = new Gson().fromJson(conversionsJson, doubleMapType);

        new AsyncConversionRateUpdater(apiDetails.getUpdateDelay(), this);
    }

    private record AsyncConversionRateUpdater(int interval, ProductionCurrencyConverter currencyConverter) implements Runnable {
        private AsyncConversionRateUpdater {
            new Thread(this).start();
        }

        public void run() {
            try {
                Thread.sleep(interval);
                currencyConverter.updateConversionRates();
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
