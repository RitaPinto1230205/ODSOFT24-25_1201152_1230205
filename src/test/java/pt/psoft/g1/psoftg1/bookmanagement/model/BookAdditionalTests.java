package pt.psoft.g1.psoftg1.bookmanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private final String validIsbn = "9782826012092";
    private final String validTitle = "Encantos de contar";
    private final Author validAuthor1 = new Author("João Alberto", "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.", null);
    private final Author validAuthor2 = new Author("Maria José", "A Maria José nasceu em Viseu e só come laranjas às segundas feiras.", null);
    private final Genre validGenre = new Genre("Fantasia");
    private ArrayList<Author> authors = new ArrayList<>();

    @BeforeEach
    void setUp(){
        authors.clear();
    }

    @Test
    void ensureIsbnNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(null, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureTitleNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, null, null, validGenre, authors, null));
    }

    @Test
    void ensureGenreNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null,null, authors, null));
    }

    @Test
    void ensureAuthorsNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, null, null));
    }

    @Test
    void ensureAuthorsNotEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureBookCreatedWithMultipleAuthors() {
        authors.add(validAuthor1);
        authors.add(validAuthor2);
        assertDoesNotThrow(() -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }


    //NEW TESTS


    @Test
    void testDuplicateAuthorsAllowed() {
        authors.add(validAuthor1);
        authors.add(validAuthor1);
        assertDoesNotThrow(() -> new Book(validIsbn, validTitle, null, validGenre, authors, null), 
                           "Book creation should allow duplicate authors");
    }

    @Test
    void testApplyPatchWithCorrectVersion() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);
        long currentVersion = book.getVersion();

        UpdateBookRequest request = new UpdateBookRequest("New Title", null, null, null, null);
        book.applyPatch(currentVersion, request);

        assertEquals("New Title", book.getTitle(), "Patch update should apply the new title when version is correct");
    }

    @Test
    void testApplyPatchWithStaleVersion() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);
        long staleVersion = book.getVersion() + 1;

        UpdateBookRequest request = new UpdateBookRequest("New Title", null, null, null, null);
        assertThrows(StaleObjectStateException.class, () -> book.applyPatch(staleVersion, request),
                     "Patch update should fail with a stale version");
    }

    @Test
    void testRemovePhotoWithCorrectVersion() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, "photoURI.jpg");
        long currentVersion = book.getVersion();

        book.removePhoto(currentVersion);
        assertNull(book.getPhoto(), "Photo should be removed when the correct version is provided");
    }

    @Test
    void testRemovePhotoWithStaleVersion() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, "photoURI.jpg");
        long staleVersion = book.getVersion() + 1;

        assertThrows(ConflictException.class, () -> book.removePhoto(staleVersion), 
                     "Photo removal should fail with a stale version");
    }

    @Test
    void testMaxLengthTitle() {
        authors.add(validAuthor1);
        StringBuilder longTitle = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            longTitle.append("a");
        }
        assertDoesNotThrow(() -> new Book(validIsbn, longTitle.toString(), null, validGenre, authors, null), 
                           "Title at maximum length should be accepted");
    }

    @Test
    void testExceedingMaxLengthTitle() {
        authors.add(validAuthor1);
        StringBuilder longTitle = new StringBuilder();
        for (int i = 0; i < 129; i++) {
            longTitle.append("a");
        }
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, longTitle.toString(), null, validGenre, authors, null), 
                     "Title exceeding maximum length should throw an exception");
    }

    @Test
    void testChangeGenreUsingPatch() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);
        long currentVersion = book.getVersion();
        Genre newGenre = new Genre("Science Fiction");

        UpdateBookRequest request = new UpdateBookRequest(null, null, newGenre.getGenre(), null, null);

        book.applyPatch(currentVersion, request);

        assertEquals("Science Fiction", book.getGenre(), 
                     "Genre should be updated to 'Science Fiction' when patch is applied");
    }
}


