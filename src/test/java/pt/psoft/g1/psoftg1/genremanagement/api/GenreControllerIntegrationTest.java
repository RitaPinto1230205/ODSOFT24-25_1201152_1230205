package pt.psoft.g1.psoftg1.genremanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GetAverageLendingsQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.shared.services.SearchRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    public void setUp() {
        // If deleteAllInBatch() is supported, otherwise use a custom delete method
        seedGenres();
    }

    private void seedGenres() {
        genreRepository.save(new Genre("Science Fiction"));
        genreRepository.save(new Genre("Mystery"));
        genreRepository.save(new Genre("Fantasy"));
        genreRepository.save(new Genre("Non-Fiction"));
        genreRepository.save(new Genre("Classic Literature"));
    }

    @Test
    public void testGetTopGenres() throws Exception {
        mockMvc.perform(get("/api/genres/top5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());

    }

    @Test
    public void testGetLendingsAverageDurationPerMonth() throws Exception {
        mockMvc.perform(get("/api/genres/lendingsAverageDurationPerMonth")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-12-31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetLendingsAverageDurationPerMonth_InvalidDates() throws Exception {
        mockMvc.perform(get("/api/genres/lendingsAverageDurationPerMonth")
                .param("startDate", "2023-12-01")
                .param("endDate", "2023-01-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Start date cannot be after end date"));
    }

    @Test
    public void testGetAverageLendings() throws Exception {
        SearchRequest<GetAverageLendingsQuery> searchRequest = new SearchRequest<>(new Page(1, 10), new GetAverageLendingsQuery(2023, 5));
        mockMvc.perform(post("/api/genres/avgLendingsPerGenre")
                .content(new ObjectMapper().writeValueAsString(searchRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}