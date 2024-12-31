package pt.psoft.g1.psoftg1.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.psoft.g1.psoftg1.auth.api.AuthRequest;
import pt.psoft.g1.psoftg1.usermanagement.services.CreateUserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationAcceptanceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_Success() throws Exception {
        // Given
        AuthRequest request = new AuthRequest("user@example.com", "password123");

        // When
        mvc.perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void register_Success() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest(
                "test@example.com",
                "password123",
                "Test User"
        );

        // When
        mvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test@example.com"));
    }
} 