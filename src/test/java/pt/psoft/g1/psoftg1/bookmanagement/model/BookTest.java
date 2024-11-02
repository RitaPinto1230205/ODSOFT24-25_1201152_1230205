package pt.psoft.g1.psoftg1.bookmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;
import java.util.Collections;

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
    void testValidBookCreation() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, "A wonderful book.", validGenre, authors, null);
        assertNotNull(book);
        assertEquals(validIsbn, book.getIsbn());
        assertEquals(validTitle, book.getTitle().toString());
        assertEquals(validGenre, book.getGenre());
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void testInvalidIsbnFormat() {
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book("invalid_isbn", validTitle, null, validGenre, authors, null));
    }

    @Test
    void testApplyPatchUpdatesFields() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);
        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated description.");
        request.setGenreObj(new Genre("Science Fiction"));
        request.setAuthorObjList(Collections.singletonList(validAuthor2));
        book.applyPatch(book.getVersion(), request);
        assertEquals("Updated Title", book.getTitle().toString());
        assertEquals("Updated description.", book.getDescription());
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void testInvalidIsbn() {
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book("invalid-isbn", validTitle, null, validGenre, authors, null),
                "Invalid ISBN should throw an exception.");
    }

    @Test
    void testGetTitle() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);
        assertEquals(validTitle, book.getTitle().toString(), "Title should be correctly retrieved.");
    }

    @Test
    void testAddAndRemoveAuthors() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, authors, null);

        // Check initial authors count
        assertEquals(1, book.getAuthors().size(), "Initial authors count should be 1.");

        // Adding another author
        book.getAuthors().add(validAuthor2);
        assertEquals(2, book.getAuthors().size(), "Authors count should be 2 after adding another author.");

        // Remove an author
        book.getAuthors().remove(validAuthor1);
        assertEquals(1, book.getAuthors().size(), "Authors count should be 1 after removing an author.");
    }

    @Test
    void testImmutableAuthorsListAfterCreation() {
        authors.add(validAuthor1);
        Book book = new Book(validIsbn, validTitle, null, validGenre, new ArrayList<>(authors), null); // Use a copy to prevent external modification
        authors.add(validAuthor2); // Modify the original authors list
        assertEquals(1, book.getAuthors().size(), "Book should still have 1 author after external modification.");
    }

    @Test
    void testValidDescriptionLength() {
        authors.add(validAuthor1);
        StringBuilder validDescription = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            validDescription.append("a");
        }
        Book book = new Book(validIsbn, validTitle, validDescription.toString(), validGenre, authors, null);
        assertNotNull(book);
        assertEquals(validDescription.toString(), book.getDescription(), "Description should be set correctly.");
    }

    @Test
    void testISBNCannotBeEmpty() {
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book("", validTitle, null, validGenre, authors, null),
                "Empty ISBN should throw an exception.");
    }
}


