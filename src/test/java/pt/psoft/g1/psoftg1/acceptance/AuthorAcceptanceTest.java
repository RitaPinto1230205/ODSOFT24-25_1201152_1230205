package pt.psoft.g1.psoftg1.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthorAcceptanceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAuthor_Success() throws Exception {
        // Given
        CreateAuthorRequest request = new CreateAuthorRequest(
                "Test Author",
                "Test Bio",
                null,
                null
        );

        // When
        mvc.perform(put("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Author"))
                .andExpect(jsonPath("$.bio").value("Test Bio"));
    }

    @Test
    void getAuthorDetails_Success() throws Exception {
        // Given
        Long authorNumber = 1L; // Assuming author exists

        // When
        mvc.perform(get("/api/authors/{authorNumber}", authorNumber)
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorNumber").value(authorNumber));
    }

    @Test
    void getAuthorBooks_Success() throws Exception {
        // Given
        Long authorNumber = 1L;

        // When
        mvc.perform(get("/api/authors/{authorNumber}/books", authorNumber)
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }
}