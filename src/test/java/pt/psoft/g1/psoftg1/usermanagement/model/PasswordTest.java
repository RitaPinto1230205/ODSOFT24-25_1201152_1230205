package pt.psoft.g1.psoftg1.usermanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void testValidPassword() {
        assertDoesNotThrow(() -> new Password("Valid1Password!"));
    }

    @Test
    public void testInvalidPasswordTooShort() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Password("Short1"));
        assertEquals("Given Password is not valid. It must contain at least 8 characters, 1 upper case letter, 1 lower case letter and 1 number or special character.", exception.getMessage());
    }

    @Test
    public void testInvalidPasswordNoUppercase() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Password("nouppercase1!"));
        assertEquals("Given Password is not valid. It must contain at least 8 characters, 1 upper case letter, 1 lower case letter and 1 number or special character.", exception.getMessage());
    }
    //BlackBox

    public class PasswordBlackBoxTest {

        @Test
        public void testValidPasswordInput() {
            assertDoesNotThrow(() -> new Password("ValidPassword1!"));
        }

        @Test
        public void testInvalidPasswordInput() {
            assertThrows(IllegalArgumentException.class, () -> new Password("invalid"));
        }
    }

}
