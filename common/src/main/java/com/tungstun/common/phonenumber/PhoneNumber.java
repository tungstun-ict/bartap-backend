package com.tungstun.common.phonenumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.persistence.Embeddable;

import static com.google.i18n.phonenumbers.NumberParseException.ErrorType.NOT_A_NUMBER;

@Embeddable
public class PhoneNumber {
    private static final String VALIDATION_ERROR_MESSAGE = "Invalid phone number. " +
            "Phone number must be in the E164 or international format. " +
            "i.e. +3161234568 with any other variation of ' ', '-' or '.' in between the numbers";

    private String value;

    private static String getValidatedPhoneNumber(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber nr = phoneUtil.parse(number, null);
            if (!phoneUtil.isValidNumber(nr)) {
                throw new NumberParseException(NOT_A_NUMBER, "");
            }
            return phoneUtil.format(nr, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException ignored) {
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE);
        }
    }

    public PhoneNumber() {
    }

    public PhoneNumber(String value) {
        this.value = getValidatedPhoneNumber(value);

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
