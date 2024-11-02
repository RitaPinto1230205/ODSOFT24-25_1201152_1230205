package pt.psoft.g1.psoftg1.shared.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    @Test
    void ensureNameMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Name(null));
    }

    @Test
    void ensureNameMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Name(""));
    }

    @Test
    void ensureNameMustOnlyBeAlphanumeric() {
        assertThrows(IllegalArgumentException.class, () -> new Name("Ricardo!"));
    }


    /**
     * Text from <a href="https://www.lipsum.com/">Lorem Ipsum</a> generator.
     */
    @Test
    void ensureNameMustNotBeOversize() {
        assertThrows(IllegalArgumentException.class, () -> new Name("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut fermentum venenatis augue, a congue turpis eleifend ut. Etiam fringilla ex nulla, id quis."));
    }

    @Test
    void ensureNameIsSet() {
        final var name = new Name("Some name");
        assertEquals("Some name", name.toString());
    }

    @Test
    void ensureNameIsChanged() {
        final var name = new Name("Some name");
        name.setName("Some other name");
        assertEquals("Some other name", name.toString());
    }

    //New unit tests using AAA
    @Test
    void ensureNameCannotBeChangedToInvalid() {
        // Arrange
        String initialName = "ValidName";
        String invalidNewName = "Invalid!";
        Name name = new Name(initialName);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(invalidNewName));
    }

    @Test
    void ensureNameCannotBeChangedToBlank() {
        // Arrange
        String initialName = "ValidName";
        String blankNewName = " ";
        Name name = new Name(initialName);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(blankNewName));
    }

    @Test
    void ensureNameCannotBeChangedToNull() {
        // Arrange
        String initialName = "ValidName";
        String nullNewName = null;
        Name name = new Name(initialName);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(nullNewName));
    }

    @Test
    void ensureNameCanBeInitializedWithValidInput() {
        // Arrange
        String validName = "ValidName123";

        // Act
        Name name = new Name(validName);

        // Assert
        assertEquals(validName, name.toString());
    }

    @Test
    void ensureValidNameCanBeChanged() {
        // Arrange
        Name name = new Name("InitialName");

        // Act
        name.setName("UpdatedName");

        // Assert
        assertEquals("UpdatedName", name.toString());
    }

    @Test
    void ensureNameWithNumericCharactersIsValid() {
        String validNameWithNumbers = "Name123";
        Name name = new Name(validNameWithNumbers);
        assertEquals(validNameWithNumbers, name.toString());
    }

    @Test
    void ensureSpacesAroundNameAreNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> new Name("   "));
    }

    @Test
    void ensureValidNameWithSpaceIsAccepted() {
        // Arrange
        String validNameWithSpace = "John Doe";

        // Act
        Name name = new Name(validNameWithSpace);

        // Assert
        assertEquals(validNameWithSpace, name.toString());
    }

    @Test
    void ensureValidNameWithHyphenIsAccepted() {
        // Arrange
        String validNameWithHyphen = "Anne-Marie";

        // Act
        Name name = new Name(validNameWithHyphen);

        // Assert
        assertEquals(validNameWithHyphen, name.toString());
    }

    @Test
    void ensureNameWithMultipleInvalidCharactersThrowsException() {
        // Arrange
        String invalidName = "Invalid@Name#";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    void ensureEmptyStringThrowsExceptionOnSetName() {
        // Arrange
        Name name = new Name("ValidName");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(" "));
    }

    @Test
    void ensureMinimumLengthIsValid() {
        // Arrange
        String singleCharacterName = "A";

        // Act
        Name name = new Name(singleCharacterName);

        // Assert
        assertEquals(singleCharacterName, name.toString());
    }

    @Test
    void ensureMaximumLengthIsAccepted() {
        // Arrange
        String maxLengthName = "A".repeat(150);

        // Act
        Name name = new Name(maxLengthName);

        // Assert
        assertEquals(maxLengthName, name.toString());
    }
}