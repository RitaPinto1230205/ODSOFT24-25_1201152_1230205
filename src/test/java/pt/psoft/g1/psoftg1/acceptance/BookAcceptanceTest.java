package pt.psoft.g1.psoftg1.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.shared.services.SearchRequest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookAcceptanceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_Success() throws Exception {
        // Given
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Test Book");
        request.setDescription("Test Description");
        request.setGenre("Fiction");
        request.setAuthors(Arrays.asList(1L)); // Author ID
        request.setPhotoURI("http://example.com/photo.jpg"); // Optional

        // When
        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.genre").value("Fiction"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }


    @Test
    void searchBooks_Success() throws Exception {
        // Given
        SearchBooksQuery query = new SearchBooksQuery("Test", "Fiction", null);
        SearchRequest<SearchBooksQuery> request = new SearchRequest<>();
        request.setQuery(query);
        request.setPage(new Page(1, 10));

        // When
        mvc.perform(post("/api/books/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    void getTop5Books_Success() throws Exception {
        // When
        mvc.perform(get("/api/books/top5")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(lessThanOrEqualTo(5)));
    }
}