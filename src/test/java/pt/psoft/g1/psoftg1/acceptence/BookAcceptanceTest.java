package pt.psoft.g1.psoftg1.acceptence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookAcceptanceTest {

    private Genre genre;
    private List<Author> authors;
    private String isbn;
    private String title;
    private String description;

    @BeforeEach
    public void setup() {
        genre = new Genre("Fiction"); // Certifique-se de que o Genre possui o construtor adequado
        authors = List.of(new Author("Rita", "é um livro muito giro", "")); // Verifique se Author tem um construtor que aceita esses parâmetros
        isbn = "9782826012092";
        title = "Sample Book";
        description = "A description of the sample book.";
    }

    @Test
    public void testCreateBook() {
        Book book = new Book(isbn, title, description, genre, authors, null);

        assertEquals(title, book.getTitle().toString());
        assertEquals(isbn, book.getIsbn());
        assertEquals(description, book.getDescription());
        assertEquals(genre, book.getGenre());
        assertEquals(authors, book.getAuthors());
    }

    @Test
    public void testBookWithoutGenre_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Book(isbn, title, description, null, authors, null);
        });
        assertEquals("Genre cannot be null", thrown.getMessage());
    }
}
