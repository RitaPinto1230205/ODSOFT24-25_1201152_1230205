package pt.psoft.g1.psoftg1.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorAcceptanceTestIT {

    @Test
    void createAuthorWithValidData() {
        // Arrange & Act
        Author author = new Author(
                "John Doe",
                "A famous writer",
                null
        );

        // Assert
        assertNotNull(author);
        assertEquals("John Doe", author.getName());
        assertEquals("A famous writer", author.getBio());
    }


    @Test
    void createAuthorWithLongBio() {
        // Arrange
        String longBio = "A".repeat(1000);

        // Act
        Author author = new Author("John Doe", longBio, null);

        // Assert
        assertNotNull(author);
        assertEquals(longBio, author.getBio());
    }
}