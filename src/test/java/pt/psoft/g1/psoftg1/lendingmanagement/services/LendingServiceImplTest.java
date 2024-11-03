package pt.psoft.g1.psoftg1.lendingmanagement.services;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.LendingForbiddenException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class LendingServiceImplTest {
    @Autowired
    private LendingService lendingService;
    @Autowired
    private LendingRepository lendingRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    private Lending lending;
    private ReaderDetails readerDetails;
    private Reader reader;
    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {

        // Initialize entities
        author = new Author("Manuel Antonio Pina", "Portuguese writer and journalist", null);
        authorRepository.save(author);

        genre = new Genre("Género");
        genreRepository.save(genre);

        book = new Book("9782826012092", "O Inspetor Max", "Description here", genre, List.of(author), null);
        bookRepository.save(book);

        reader = Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
        userRepository.save(reader);

        readerDetails = new ReaderDetails(1, reader, "2000-01-01", "919191919", true, true, true, null, null);
        readerRepository.save(readerDetails);

        lending = Lending.newBootstrappingLending(book, readerDetails, LocalDate.now().getYear(), 999,
                LocalDate.of(LocalDate.now().getYear(), 1, 1), LocalDate.of(LocalDate.now().getYear(), 1, 11), 15, 300);
        lendingRepository.save(lending);

        Lending anotherLending = Lending.newBootstrappingLending(book, readerDetails, LocalDate.now().getYear(), 1000,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), 15, 300);
        lendingRepository.save(anotherLending);
    }



    @Test
    void testFindByLendingNumber() {
        assertThat(lendingService.findByLendingNumber(LocalDate.now().getYear() + "/999")).isPresent();
        assertThat(lendingService.findByLendingNumber(LocalDate.now().getYear() + "/1")).isEmpty();
    }

    @Test
    void testCreate() {
        var request = new CreateLendingRequest("9782826012092", LocalDate.now().getYear() + "/1");
        var lending1 = lendingService.create(request);
        assertThat(lending1).isNotNull();

        // Additional lendings
        var lending2 = lendingService.create(request);
        assertThat(lending2).isNotNull();
        var lending3 = lendingService.create(request);
        assertThat(lending3).isNotNull();

        // Expect exception on the 4th lending due to limit
        assertThrows(LendingForbiddenException.class, () -> lendingService.create(request));
    }

    @Test
    void testSetReturned() {
        int year = 2024, seq = 888;
        var notReturnedLending = lendingRepository.save(Lending.newBootstrappingLending(book, readerDetails, year, seq,
                LocalDate.of(2024, 3, 1), null, 15, 300));

        var request = new SetLendingReturnedRequest(null);

        // Test stale state exception
        assertThrows(StaleObjectStateException.class,
                () -> lendingService.setReturned(year + "/" + seq, request, notReturnedLending.getVersion() - 1));

        // Valid update
        assertDoesNotThrow(() -> lendingService.setReturned(year + "/" + seq, request, notReturnedLending.getVersion()));
    }

    @Test
    void testGetOverdue() {
        var returnedLateLending = lendingRepository.save(Lending.newBootstrappingLending(book, readerDetails, 2024, 998,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1), 15, 300));
        var notReturnedLending = lendingRepository.save(Lending.newBootstrappingLending(book, readerDetails, 2024, 997,
                LocalDate.of(2024, 3, 1), null, 15, 300));
        var notOverdueLending = lendingRepository.save(Lending.newBootstrappingLending(book, readerDetails, 2024, 996,
                LocalDate.now(), null, 15, 300));

        Page page = new Page(1, 10);
        List<Lending> overdueLendings = lendingService.getOverdue(page);

        assertThat(overdueLendings).contains(notReturnedLending);
        assertThat(overdueLendings).doesNotContain(returnedLateLending, notOverdueLending);
    }

    @Test
    void testGetLendingCountFromCurrentYear() {
        int count = lendingRepository.getCountFromCurrentYear();
        assertEquals(2, count);

        lendingRepository.save(Lending.newBootstrappingLending(book, readerDetails, LocalDate.now().getYear(), 998,
                LocalDate.now(), null, 15, 300));

        count = lendingRepository.getCountFromCurrentYear();
        assertEquals(3, count);
    }

    @Test
    void testSearchLendingsByReaderNumber() {
        SearchLendingQuery query = new SearchLendingQuery();
        query.setReaderNumber(readerDetails.getReaderNumber());
        query.setReturned(null);

        Page page = new Page(1, 10);
        List<Lending> results = lendingService.searchLendings(page, query);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(Lending::getLendingNumber).containsExactlyInAnyOrder(
                LocalDate.now().getYear() + "/999", "2024/1000");
    }

    @Test
    void testSearchLendingsByIsbn() {
        SearchLendingQuery query = new SearchLendingQuery();
        query.setIsbn(book.getIsbn());
        query.setReturned(null);

        Page page = new Page(1, 10);
        List<Lending> results = lendingService.searchLendings(page, query);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(Lending::getLendingNumber).containsExactlyInAnyOrder(
                LocalDate.now().getYear() + "/999", "2024/1000");
    }
/*
    @Test
    void testSearchLendingsByReturned() {
        lendingService.setReturned(LocalDate.now().getYear() + "/999", new SetLendingReturnedRequest(""), 1);

        SearchLendingQuery query = new SearchLendingQuery();
        query.setReaderNumber(readerDetails.getReaderNumber());
        query.setReturned(true);

        Page page = new Page(1, 10);
        List<Lending> results = lendingService.searchLendings(page, query);

        assertThat(results).hasSize(1);
        assertThat(results).extracting(Lending::getLendingNumber).containsExactly(
                LocalDate.now().getYear() + "/999");
    }*/

    @Test
    void testSearchLendingsByDateRange() {
        Lending additionalLending = Lending.newBootstrappingLending(book, readerDetails, LocalDate.now().getYear(), 1002,
                LocalDate.of(2024, 1, 5), null, 15, 300);
        lendingRepository.save(additionalLending);

        SearchLendingQuery query = new SearchLendingQuery();
        query.setStartDate(LocalDate.of(2024, 1, 1).toString());
        query.setEndDate(LocalDate.of(2024, 1, 15).toString());

        Page page = new Page(1, 10);
        List<Lending> results = lendingService.searchLendings(page, query);

        assertThat(results).hasSize(3);
        assertThat(results).extracting(Lending::getLendingNumber).containsExactlyInAnyOrder(
                LocalDate.now().getYear() + "/999", "2024/1000", "2024/1002");
    }
}