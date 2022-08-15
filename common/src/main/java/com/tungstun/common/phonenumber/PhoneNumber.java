package com.tungstun.common.phonenumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static com.google.i18n.phonenumbers.NumberParseException.ErrorType.NOT_A_NUMBER;

/**
 * Common PhoneNumber value object that represents a phone number in the E164 format.
 * The PhoneNumber constructor takes a String in E164 (i.e. +3161234568) or international (i.e. +31 6 1234568, etc.) number format.
 * Provided string may contain ' ', '-' and '.' characters anywhere, except in front of the  country code (i.e. +31).
 * On construction, the provided phone number is parsed and validated using Google's libphonenumber i18n library.
 */
@Embeddable
public class PhoneNumber implements Serializable {
    private static final String VALIDATION_ERROR_MESSAGE = "Invalid phone number. " +
            "Provided phone number must be in the E164 or international number format " +
            "Provided phone number may contain ' ', '-' and '.' characters anywhere, " +
            "except in front of the  country code (i.e. +31)";

    private String value;

    private static String getValidatedPhoneNumber(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber nr = phoneUtil.parse(phoneNumber, null);
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

    public PhoneNumber(String phoneNumber) {
        this.value = getValidatedPhoneNumber(phoneNumber);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
