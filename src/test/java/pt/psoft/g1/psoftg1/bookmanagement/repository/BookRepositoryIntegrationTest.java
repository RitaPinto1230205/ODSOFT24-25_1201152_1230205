package pt.psoft.g1.psoftg1.bookmanagement.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    public void setUp() {
        // Ensure a valid ISBN-13 format
        String validIsbn = "9781234567897";

        // Check if the author exists, or create it if not
        author = authorRepository.searchByNameName("Agatha Christie").stream().findAny().orElseGet(() -> {
            Author newAuthor = new Author("Agatha Christie", "Renowned mystery novelist", null);
            return authorRepository.save(newAuthor);
        });

        // Check if the genre exists, or create it if not
        genre = genreRepository.findByString("Mystery").orElseGet(() -> {
            Genre newGenre = new Genre("Mystery");
            return genreRepository.save(newGenre);
        });

        // Create the book with the valid ISBN-13 and existing genre and author
        book = new Book(validIsbn, "Murder on the Orient Express",
                "Detective Hercule Poirot investigates a murder on a train.",
                genre, List.of(author), null);

        // Save the book
        bookRepository.save(book);
    }




    @Test
    public void testFindByGenre() {
        List<Book> foundBooks = bookRepository.findByGenre(genre.toString());
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.contains(book));
    }

    @Test
    public void testFindByTitle() {
        List<Book> foundBooks = bookRepository.findByTitle("Murder on the Orient Express");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.contains(book));
    }

    @Test
    public void testFindByAuthorName() {
        List<Book> foundBooks = bookRepository.findByAuthorName("Agatha Christie");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.contains(book));
    }

    @Test
    public void testFindByIsbn() {
        Optional<Book> foundBook = bookRepository.findByIsbn("9781234567897");
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());
    }


    @Test
    public void testFindBooksByAuthorNumber() {
        List<Book> foundBooks = bookRepository.findBooksByAuthorNumber(author.getId());
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.contains(book.getIsbn()));
    }

    @AfterEach
    public void tearDown() {
        bookRepository.delete(book);
        authorRepository.delete(author);
        genreRepository.delete(genre);
    }
}
