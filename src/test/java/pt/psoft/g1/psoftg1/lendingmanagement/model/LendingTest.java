package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource({"classpath:config/library.properties"})
class LendingTest {
    private static final ArrayList<Author> authors = new ArrayList<>();
    private static Book book;
    private static ReaderDetails readerDetails;
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    @BeforeAll
    public static void setup(){
        Author author = new Author("Manuel Antonio Pina",
                "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões",
                null);
        authors.add(author);
        book = new Book("9782826012092",
                "O Inspetor Max",
                "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                new Genre("Romance"),
                authors,
                null);
        readerDetails = new ReaderDetails(1,
                Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives"),
                "2000-01-01",
                "919191919",
                true,
                true,
                true,
                null,
                null);
    }

    @Test
    void ensureBookNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Lending(null, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void ensureReaderNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Lending(book, null, 1, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void ensureValidReaderNumber(){
        assertThrows(IllegalArgumentException.class, () -> new Lending(book, readerDetails, -1, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void testSetReturned(){
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.setReturned(0,null);
        assertEquals(LocalDate.now(), lending.getReturnedDate());
    }

    @Test
    void testGetDaysDelayed(){
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(0, lending.getDaysDelayed());
    }

    @Test
    void testGetDaysUntilReturn(){
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(Optional.of(lendingDurationInDays), lending.getDaysUntilReturn());
    }

    @Test
    void testGetDaysOverDue(){
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(Optional.empty(), lending.getDaysOverdue());
    }

    @Test
    void testGetTitle() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals("O Inspetor Max", lending.getTitle());
    }

    @Test
    void testGetLendingNumber() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(LocalDate.now().getYear() + "/1", lending.getLendingNumber());
    }

    @Test
    void testGetBook() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(book, lending.getBook());
    }

    @Test
    void testGetReaderDetails() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(readerDetails, lending.getReaderDetails());
    }

    @Test
    void testGetStartDate() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(LocalDate.now(), lending.getStartDate());
    }

    @Test
    void testGetLimitDate() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertEquals(LocalDate.now().plusDays(lendingDurationInDays), lending.getLimitDate());
    }

    @Test
    void testGetReturnedDate() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        assertNull(lending.getReturnedDate());
    }



    //NEW TESTS


    // Test optimistic locking with version control (simulated concurrency)
    @Test
    void ensureVersioningPreventsStaleObjectModification() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        // Simulating that the version has been changed before calling setReturned()
        lending.setReturned(0, "Returned with some commentary");
        assertThrows(StaleObjectStateException.class, () -> lending.setReturned(1, "New commentary"), 
                     "Should throw StaleObjectStateException if version is stale");
    }

    // Test that book returned on the exact limit date does not incur a fine
    @Test
    void ensureNoFineIfReturnedOnLimitDate() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.setReturned(0, null); // Simulating book returned on the exact limit date
        assertEquals(0, lending.getDaysDelayed(), "There should be no delay if returned on the limit date");
        assertEquals(Optional.empty(), lending.getFineValueInCents(), "No fine should be applied if returned on time");
    }

    // Test fine calculation for overdue books
    @Test
    void testFineForOverdueBook() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        // Simulate a return 3 days after the limit date
        lending.setReturned(0, null);
        lending.returnedDate = LocalDate.now().plusDays(3);
        assertEquals(3, lending.getDaysDelayed(), "Book should be 3 days overdue");
        assertEquals(Optional.of(3 * fineValuePerDayInCents), lending.getFineValueInCents(), 
                     "Fine should be calculated as 3 days * fineValuePerDayInCents");
    }

    // Test invalid returned date before the start date
    @Test
    void ensureInvalidReturnedDateThrowsException() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.returnedDate = lending.getStartDate().minusDays(1); // Invalid return date before start date
        assertThrows(IllegalArgumentException.class, () -> lending.setReturned(0, null), 
                     "Returned date cannot be before the start date");
    }

    // Test returned date exactly on limit date should not trigger overdue
    @Test
    void ensureNoOverdueOnLimitDate() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.returnedDate = lending.getLimitDate(); // Return exactly on the limit date
        assertEquals(0, lending.getDaysDelayed(), "There should be no delay if returned on the limit date");
        assertEquals(Optional.empty(), lending.getFineValueInCents(), "No fine should be applied if returned on time");
    }

    // Test days overdue when the returned date is after the limit date
    @Test
    void ensureCorrectDaysOverdue() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.returnedDate = lending.getLimitDate().plusDays(5); // Return 5 days after the limit date
        assertEquals(5, lending.getDaysDelayed(), "Lending should be 5 days overdue");
    }

    // Test fine calculation for multiple overdue days
    @Test
    void testFineValueCalculation() {
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        lending.returnedDate = lending.getLimitDate().plusDays(2); // 2 days late
        assertEquals(Optional.of(2 * fineValuePerDayInCents), lending.getFineValueInCents(), 
                     "Fine should be correctly calculated for 2 overdue days");
    }
}
    
