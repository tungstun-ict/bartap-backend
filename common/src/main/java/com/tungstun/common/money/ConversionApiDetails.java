package com.tungstun.common.money;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
* Details used for the API calls to update the stored conversion rates
* */
@Configuration
public class ConversionApiDetails {
    private static final String BASE_URL = "https://api.apilayer.com/exchangerates_data/latest";
    private static final String VERSION = "latest";

    @Value("{com.tungstun.bartap.currency.conversion.api-key}")
    private String apiKey;
    @Value("{com.tungstun.bartap.currency.conversion.base:EUR}")
    private String baseCurrency;
    @Value("${com.tungstun.bartap.currency.conversion.apiUpdateDelay:86400000}")
    private Integer updateDelayInMilliseconds;

    /**
     * URL used for the conversion rate api.<br>
     * See ap <a href="https://exchangeratesapi.io/documentation/">documentation</a>.
     * */
    public URL getUrl() throws MalformedURLException {
        return new URL(String.format("%s/%s?apikey=%s&base=%s", BASE_URL, VERSION, apiKey, baseCurrency));
    }

    /**
     * Delay to be used between calls to update the conversion rates.<br>
     * */
    public Integer getUpdateDelay() {
        return updateDelayInMilliseconds;
    }
}
