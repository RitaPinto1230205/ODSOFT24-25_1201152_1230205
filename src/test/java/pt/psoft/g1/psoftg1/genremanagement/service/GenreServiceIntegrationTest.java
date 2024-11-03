package pt.psoft.g1.psoftg1.genremanagement.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;
import pt.psoft.g1.psoftg1.genremanagement.services.GetAverageLendingsQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.Assert.*;

@SpringBootTest
@Transactional
public class GenreServiceIntegrationTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;



    @Test
    public void testFindTopGenreByBooks() {
        List<GenreBookCountDTO> result = genreService.findTopGenreByBooks();
        assertNotEmpty(result, "Result should not be empty");
        assertTrue(result.size() <= 5, "Result size should be up to 5");
    }

    @Test
    public void testGetAverageLendings_ValidQuery() {
        GetAverageLendingsQuery query = new GetAverageLendingsQuery(2023, 5);
        Page page = new Page(1, 10);
        List<GenreLendingsDTO> result = genreService.getAverageLendings(query, page);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetLendingsPerMonthLastYearByGenre() {
        List<GenreLendingsPerMonthDTO> result = genreService.getLendingsPerMonthLastYearByGenre();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetLendingsAverageDurationPerMonth_ValidDates() {
        String start = "2023-01-01";
        String end = "2023-12-31";
        List<GenreLendingsPerMonthDTO> result = genreService.getLendingsAverageDurationPerMonth(start, end);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetLendingsAverageDurationPerMonth_InvalidDates() {
        String start = "2023-12-01";
        String end = "2023-01-01";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            genreService.getLendingsAverageDurationPerMonth(start, end);
        });
        assertEquals("Start date cannot be after end date", exception.getMessage());
    }
}
