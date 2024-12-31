package pt.psoft.g1.psoftg1.acceptance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookAcceptanceTestIT {

    @Test
    void createBookWithInvalidISBNFails() {
        // Arrange
        Author author = new Author("John Doe", "Bio", null);
        Genre genre = new Genre("Fiction");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        "invalid-isbn",
                        "Test Book",
                        "Description",
                        genre,
                        List.of(author),
                        null
                )
        );
        Assertions.assertEquals("Invalid ISBN-13 format or check digit.", exception.getMessage());
    }
}