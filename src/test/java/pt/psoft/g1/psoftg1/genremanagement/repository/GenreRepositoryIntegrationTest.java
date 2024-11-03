package pt.psoft.g1.psoftg1.genremanagement.repository;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;


import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GenreRepositoryIntegrationTest {

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    public void setUp() {
        // Seed initial data for genres here
        Genre genre1 = new Genre("Science Fiction");
        Genre genre2 = new Genre("Mystery");
        Genre genre3 = new Genre("Fantasy");
        Genre genre4 = new Genre("Non-Fiction");
        Genre genre5 = new Genre("Classic Literature");

        genreRepository.save(genre1);
        genreRepository.save(genre2);
        genreRepository.save(genre3);
        genreRepository.save(genre4);
        genreRepository.save(genre5);
    }



    @Test
    public void testGetAverageLendingsInMonth() {
        LocalDate month = LocalDate.of(2023, 5, 1);
        // Assuming Page has no parameters, using it directly
        List<GenreLendingsDTO> result = genreRepository.getAverageLendingsInMonth(month, new Page());

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Expected non-empty result for average lendings");
        for (GenreLendingsDTO dto : result) {
            assertNotNull(dto.getGenre());
            assertTrue(dto.getValue() instanceof Number, "Value should be of type Number");
        }
    }

    @Test
    public void testGetLendingsPerMonthLastYearByGenre() {
        List<GenreLendingsPerMonthDTO> result = genreRepository.getLendingsPerMonthLastYearByGenre();

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Expected non-empty result for lendings per month last year");
    }

}