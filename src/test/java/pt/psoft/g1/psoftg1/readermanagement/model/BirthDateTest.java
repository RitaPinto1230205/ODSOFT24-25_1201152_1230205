package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.AccessDeniedException;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BirthDateTest {

    @Test
    void ensureBirthDateCanBeCreatedWithValidDate() {
        assertDoesNotThrow(() -> new BirthDate(2000, 1, 1));
    }

    @Test
    void ensureBirthDateCanBeCreatedWithValidStringDate() {
        assertDoesNotThrow(() -> new BirthDate("2000-01-01"));
    }

    @Test
    void ensureExceptionIsThrownForInvalidStringDateFormat() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new BirthDate("01-01-2000"));
        assertEquals("Provided birth date is not in a valid format. Use yyyy-MM-dd", exception.getMessage());
    }

    @Test
    void ensureCorrectStringRepresentation() {
        BirthDate birthDate = new BirthDate(2000, 1, 1);
        assertEquals("2000-1-1", birthDate.toString());
    }


    // New tests...

    @ParameterizedTest
    @CsvSource({
            "2000,13,1",  // Invalid month
            "2000,1,32",  // Invalid day
            "2000,0,1",   // Month zero
            "2000,1,0"    // Day zero
    })
    void ensureInvalidDateComponentsThrowException(int year, int month, int day) {
        assertThrows(DateTimeException.class, () -> new BirthDate(year, month, day));
    }


    @Test
    void ensureLeapYearDateIsValid() {
        assertDoesNotThrow(() -> new BirthDate(2000, 2, 29));
    }

    @Test
    void ensureNonLeapYearDateIsInvalid() {
        assertThrows(DateTimeException.class, () -> new BirthDate(2001, 2, 29));
    }


    @Test
    void ensureExactMinimumAgeIsAllowed() {
        LocalDate today = LocalDate.now();
        int minimumAge = 18; // Assuming minimum age is 18

        assertDoesNotThrow(() -> new BirthDate(today.getYear() - minimumAge,
                today.getMonthValue(),
                today.getDayOfMonth()));
    }

    @Test
    void ensureBirthDateGetterReturnsCorrectValue() {
        LocalDate expectedDate = LocalDate.of(2000, 1, 1);
        BirthDate birthDate = new BirthDate(2000, 1, 1);
        assertEquals(expectedDate, birthDate.birthDate);
    }

    // Mutation tests
    @Test
    void ensureNullStringDateThrowsException() {
        assertThrows(NullPointerException.class, () -> new BirthDate((String) null));
    }

    @Test
    void ensureEmptyStringDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate(""));
    }

    @Test
    void ensureWhitespaceStringDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate("   "));
    }

    @Test
    void ensureDateWithExtraWhitespaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate("2000 -01-01"));
    }

    @ParameterizedTest
    @CsvSource({
            "2000,1,1,2000-1-1",
            "2020,12,31,2020-12-31",
            "1990,7,15,1990-7-15"
    })
    void ensureToStringFormatIsConsistentForVariousDates(int year, int month, int day, String expected) {
        BirthDate birthDate = new BirthDate(year, month, day);
        assertEquals(expected, birthDate.toString());
    }

    @Test
    void ensureInvalidYearInStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate("two-thousand-01"));
    }


    @ParameterizedTest
    @CsvSource({
            "2000,1,1,2000-1-1",
            "2020,12,31,2020-12-31",
            "1990,7,5,1990-7-5"
    })
    void ensureStringRepresentationIsConsistent(int year, int month, int day, String expected) {
        BirthDate birthDate = new BirthDate(year, month, day);
        assertEquals(expected, birthDate.toString(), "A representação em string deve estar correta");
    }

    @Test
    void ensureValidBirthDateRange() {
        LocalDate today = LocalDate.now();
        BirthDate birthDate = new BirthDate(today.getYear() - 18, today.getMonthValue(), today.getDayOfMonth());
        assertDoesNotThrow(() -> birthDate);
        assertEquals(today.getYear() - 18, birthDate.birthDate.getYear(), "Birth date should be correctly assigned.");
    }

    @Test
    void ensureValidDateWithLeadingZerosInString() {
        assertDoesNotThrow(() -> new BirthDate("2000-01-01"), "Leading zeros should not affect date creation.");
    }

    @Test
    void ensureNonNumericStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate("abc-def-ghij"),
                "Non-numeric string should throw an IllegalArgumentException.");
    }

    @Test
    void ensureInequalityWithDifferentDate() {
        BirthDate date1 = new BirthDate(2000, 1, 1);
        BirthDate date2 = new BirthDate(2001, 1, 1);
        assertNotEquals(date1, date2, "BirthDates with different dates should not be equal.");
    }

    @Test
    void ensureToStringConsistencyWithDifferentFormats() {
        BirthDate birthDate1 = new BirthDate(2000, 1, 1);
        assertEquals("2000-1-1", birthDate1.toString(), "String representation should match expected format.");

        BirthDate birthDate2 = new BirthDate("2000-01-01");
        assertEquals("2000-1-1", birthDate2.toString(), "String representation from valid string should match expected format.");
    }
}
    

