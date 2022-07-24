package com.tungstun.common.phonenumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {
    @ParameterizedTest
    @CsvSource({
            "+31612345678",
            "+31 612345678",
            "+316 12345678",
            "+31 6 12345678",
            "+31 6 1234 5678",
            "+31 6 1234 56 78",
            "+31 6 1234 5 6 7 8",
            "+3 1 6 1 2 3 4 5 6 7 8",
            "+ 3 1 6 1 2 3 4 5 6 7 8",
            "+ 31612345678",
            "+31 612 345 678",
            "+31 612-345-678",
            "+31 612.345.678",
            "+31 612.345.678.",
            "+31 612.345.678.",
            "+.3.1 ..612...345...678.",
            "+-3-1 --612---345---678-",
    })
    void validE164OrInternationalFormatWithCountryCodeAndWithDifferentSeparatorCombinations(String num) {
        assertDoesNotThrow(() -> new PhoneNumber(num));
    }

    @ParameterizedTest
    @CsvSource({
            "+31",
            "+3161234567",
            "+316123456789",
            "+3161234567891233245",
    })
    void tooLongAndShortNumbersInE164OrInternationalFormat_Throws(String num) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber(num)
        );
    }

    @Test
    void phoneNumberNull_Throws() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber(null)
        );
    }

    @Test
    void phoneNumberWithMoreThanMaxOfPhoneNUmberUtil() {
        String number = "+3145678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890" +
                "123456789012345678901234567890123456789012345678901"; //251 characters
        assertThrows( //Should fail anyway, because of the isValid check
                IllegalArgumentException.class,
                () -> new PhoneNumber(number)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "12",
            "123",
            "1234",
            "12345",
            "123456",
            "1234567",
            "12345678",
            "123456789",
            "12345678901",
            "123456789012",
            "1234567890123",
            "12345678901234",
            "123456789012345",
            "1234567890123456",
            "12345678901234567",
            "123456789012345678",
            "1234567890123456789",
            "12345678901234567890",
    })
    void tooLongAndShortNumbersWithoutCountryCode_Throws(String num) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber(num)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890",
            "0987654321",
            "1212121212",
            "3434343434",
            "5656565656",
            "7878787878",
            "9090909090",
            "1234 567 890",
            "1234-567-890",
            "1234.567.890",
            "1234 567-890",
            "1234 567.890",
            "1234-567 890",
            "1234-567.890",
            "1234.567 890",
            "1234.567-890",
            "123 456 7890",
            "123-456-7890",
            "123.456.7890",
            "123 456.7890",
            "123 456-7890",
            "123-456 7890",
            "123-456.7890",
            "123.456 7890",
            "123.456-7890",
    })
    void otherwiseValidNumbersWithoutCountryCode_Throws(String num) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber(num)
        );
    }
}