package pt.psoft.g1.psoftg1.readermanagement.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.*;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
public class ReaderServiceIntegrationTest {

    @Autowired
    private ReaderServiceImpl readerService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ForbiddenNameRepository forbiddenNameRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @BeforeEach
    void setUp() {
        // Optional setup for initial data (if required for your tests)
    }

    @Test
    public void testCreateReader_Success() {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setUsername("newUser");
        request.setPassword("password123");
        request.setFullName("Alice Wonderland");
        request.setBirthDate("1992-05-15");
        request.setPhoneNumber("1234567890");
        request.setInterestList(List.of("Fantasy", "Adventure"));

        ReaderDetails createdReader = readerService.create(request, "photoURI/path");

        assertNotNull(createdReader);
        assertEquals("newUser", createdReader.getReader().getUsername());
        assertEquals("Alice Wonderland", createdReader.getReader().getName());
        assertEquals("1234567890", createdReader.getPhoneNumber());
    }

    @Test
    public void testCreateReader_UsernameConflict() {
        // Assuming "existingUser" is already present in the user repository
        CreateReaderRequest request = new CreateReaderRequest();
        request.setUsername("existingUser");
        request.setPassword("password123");
        request.setFullName("Bob Builder");
        request.setBirthDate("1985-08-20");
        request.setPhoneNumber("9876543210");

        ConflictException exception = assertThrows(ConflictException.class, () ->
                readerService.create(request, "photoURI/path")
        );

        assertEquals("Username already exists!", exception.getMessage());
    }

    @Test
    public void testFindTopByGenre_Success() {
        String genre = "Science Fiction";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        List<ReaderBookCountDTO> topReaders = readerService.findTopByGenre(genre, startDate, endDate);

        assertNotNull(topReaders);
        assertTrue(topReaders.size() <= 5); // Assuming the service returns a maximum of 5 top readers
    }

    @Test
    public void testFindTopByGenre_InvalidDateRange() {
        String genre = "Fantasy";
        LocalDate startDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                readerService.findTopByGenre(genre, startDate, endDate)
        );

        assertEquals("Start date cannot be after end date", exception.getMessage());
    }

    @Test
    public void testUpdateReader_Success() {
        Long readerId = 1L; // Replace with a valid reader ID in the test database
        UpdateReaderRequest request = new UpdateReaderRequest();
        request.setFullName("Updated Name");
        request.setPhoneNumber("1112223333");

        ReaderDetails updatedReader = readerService.update(readerId, request, 1, "newPhotoURI/path");

        assertNotNull(updatedReader);
        assertEquals("Updated Name", updatedReader.getReader().getName());
        assertEquals("1112223333", updatedReader.getPhoneNumber());
    }

    @Test
    public void testRemoveReaderPhoto_Success() {
        String readerNumber = "12345"; // Replace with a valid reader number in the test database

        Optional<ReaderDetails> readerWithoutPhoto = readerService.removeReaderPhoto(readerNumber, 1);

        assertTrue(readerWithoutPhoto.isPresent());
        assertNull(readerWithoutPhoto.get().getPhoto());
    }

    @Test
    public void testSearchReaders_Success() {
        pt.psoft.g1.psoftg1.shared.services.Page page = new pt.psoft.g1.psoftg1.shared.services.Page(1, 10);
        SearchReadersQuery query = new SearchReadersQuery("Alice", "1234567890", "alice@example.com");

        List<ReaderDetails> searchResults = readerService.searchReaders(page, query);

        assertNotNull(searchResults);
        assertFalse(searchResults.isEmpty()); // Assuming matching readers exist
    }

    @Test
    public void testSearchReaders_NoResults() {
        pt.psoft.g1.psoftg1.shared.services.Page page = new pt.psoft.g1.psoftg1.shared.services.Page(1, 10);
        SearchReadersQuery query = new SearchReadersQuery("Nonexistent Name", "0000000000", "nonexistent@example.com");

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                readerService.searchReaders(page, query)
        );

        assertEquals("No results match the search query", exception.getMessage());
    }
}
