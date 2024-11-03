
package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceImplIntegrationTest {


        @Autowired
        private BookService bookService;

        @MockBean
        private BookRepository bookRepository;

    private Book exampleBook;

    @BeforeEach
    public void setUp() {
        List<Author> lA=(List.of(new Author("Sample Author", "An example author bio", null)));
        exampleBook = new Book("978-3-16-148410-0", "Sample Book", "Fiction", new Genre("Fiction"),lA,null);


        Mockito.when(bookRepository.findByIsbn(exampleBook.getIsbn())).thenReturn(Optional.of(exampleBook));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(exampleBook);
    }

    @Test
    public void whenValidId_thenBookShouldBeFound() {
        Optional<Book> found = Optional.ofNullable(bookService.findByIsbn(exampleBook.getIsbn()));
        found.ifPresent(book -> assertThat(book.getIsbn()).isEqualTo(exampleBook.getIsbn()));
    }

    @Test
    public void whenSaveBook_thenBookShouldBeSaved() {
        Book savedBook = bookService.save(exampleBook);
        assertThat(savedBook.getDescription()).isEqualTo("Fiction");
    }


}
