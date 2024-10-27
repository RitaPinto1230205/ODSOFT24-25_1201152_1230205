package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {
    @Test
    void ensureValidMobilePhoneNumberIsAccepted() {
        assertDoesNotThrow(() -> new PhoneNumber("912345678"));
    }

    @Test
    void ensureValidFixedPhoneNumberIsAccepted() {
        assertDoesNotThrow(() -> new PhoneNumber("212345678"));
    }

    @Test
    void ensureInvalidPhoneNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("12345678")); // Too short
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("00123456789")); // Too long
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("abcdefghij")); // Non-numeric
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("512345678")); // Invalid start digit
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("91234567")); // Too short by one digit
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("21234567")); // Too short by one digit
    }

    @Test
    void ensureCorrectStringRepresentation() {
        PhoneNumber phoneNumber = new PhoneNumber("912345678");
        assertEquals("912345678", phoneNumber.toString());

        PhoneNumber anotherPhoneNumber = new PhoneNumber("212345678");
        assertEquals("212345678", anotherPhoneNumber.toString());
    }

    // Mutation tests
     @ParameterizedTest
    @ValueSource(strings = {
            "912345678.",
            "91234567,8",
            "91 234 567",
            "+912345678",
            "(912345678)",
            "912-345-678"
    })
    void ensurePhoneNumbersWithSpecialCharactersAreRejected(String invalidNumber) {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(invalidNumber));
    }

    @Test
    void ensureEqualsAndHashCodeContract() {
        PhoneNumber number1 = new PhoneNumber("912345678");
        PhoneNumber number2 = new PhoneNumber("912345678");
        PhoneNumber number3 = new PhoneNumber("212345678");

        // Test equality
        assertNotEquals(number1, number2);
        assertNotEquals(number1, number3);

        // Test hashCode
        assertNotEquals(number1.hashCode(), number2.hashCode());
    }

    @Test
    void ensureDefaultConstructorCreatesValidObject() {
        PhoneNumber number = new PhoneNumber();
        assertNotNull(number);
    }

    // Tests for boundary conditions
    @ParameterizedTest
    @ValueSource(strings = {
            "900000000",  // Minimum valid mobile number
            "999999999",  // Maximum valid mobile number
            "200000000",  // Minimum valid fixed number
            "299999999"   // Maximum valid fixed number
    })
    void ensureBoundaryPhoneNumbersAreAccepted(String boundaryNumber) {
        assertDoesNotThrow(() -> new PhoneNumber(boundaryNumber));
    }

    @Test
    void ensureObjectCreationWithoutNumberThrowsException() {
        assertThrows(NullPointerException.class, () -> new PhoneNumber(null));
    }

    // Performance test for large number of validations
    @Test
    void ensurePerformanceWithMultipleValidations() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            assertDoesNotThrow(() -> new PhoneNumber("912345678"));
        }
        long endTime = System.currentTimeMillis();
        assertTrue((endTime - startTime) < 1000, "Phone number validation should be fast");
    }

    // Test for object comparison
    @Test
    void ensurePhoneNumberComparison() {
        PhoneNumber number1 = new PhoneNumber("912345678");
        Object notAPhoneNumber = "912345678";

        assertNotEquals(number1, notAPhoneNumber);
        assertNotEquals(number1, null);
    }
}
