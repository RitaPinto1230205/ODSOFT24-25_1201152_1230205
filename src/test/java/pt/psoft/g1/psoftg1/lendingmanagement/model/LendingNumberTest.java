package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LendingNumberTest {
    @Test
    void ensureLendingNumberNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(null));
    }
    @Test
    void ensureLendingNumberNotBlank(){
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(""));
    }
    @Test
    void ensureLendingNumberNotWrongFormat(){
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("1/2024"));
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("24/1"));
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2024-1"));
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2024\\1"));
    }
    @Test
    void ensureLendingNumberIsSetWithString() {
        final var ln = new LendingNumber("2024/1");
        assertEquals("2024/1", ln.toString());
    }

    @Test
    void ensureLendingNumberIsSetWithSequential() {
        final LendingNumber ln = new LendingNumber(1);
        assertNotNull(ln);
        assertEquals(LocalDate.now().getYear() + "/1", ln.toString());
    }

    @Test
    void ensureLendingNumberIsSetWithYearAndSequential() {
        final LendingNumber ln = new LendingNumber(2024,1);
        assertNotNull(ln);
    }

    @Test
    void ensureSequentialCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(2024,-1));
    }

    @Test
    void ensureYearCannotBeInTheFuture() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(LocalDate.now().getYear()+1,1));
    }

    //NEW TESTS

      // Boundary Value Test for Year
      @Test
      void ensureYearAtBoundaryIsValid() {
          final LendingNumber ln = new LendingNumber(1970, 1);
          assertEquals("1970/1", ln.toString(), "LendingNumber should be valid for the boundary year 1970");
      }
  
      // Test for a very large sequential number
      @Test
      void ensureLargeSequentialNumberIsValid() {
          final LendingNumber ln = new LendingNumber(2024, Integer.MAX_VALUE);
          assertEquals("2024/" + Integer.MAX_VALUE, ln.toString(), "LendingNumber should accept very large sequential values");
      }
  
      // Test for leap year (optional depending on the business logic of your system)
      @Test
      void ensureLeapYearIsValid() {
          final LendingNumber ln = new LendingNumber(2024, 1);  // 2024 is a leap year
          assertEquals("2024/1", ln.toString(), "LendingNumber should correctly handle leap years");
      }
  
      // Test for Invalid Lending Number String Parsing (Wrong Format)
      @Test
      void ensureLendingNumberParsingWithWrongFormatThrowsException() {
          assertThrows(IllegalArgumentException.class, () -> new LendingNumber("20/12345"), 
                       "LendingNumber should throw an exception for incorrect year format");
      }
  
      // Test for Negative Year (which should be invalid)
      @Test
      void ensureNegativeYearThrowsException() {
          assertThrows(IllegalArgumentException.class, () -> new LendingNumber(-2024, 1), 
                       "LendingNumber should not accept negative years");
      }

}