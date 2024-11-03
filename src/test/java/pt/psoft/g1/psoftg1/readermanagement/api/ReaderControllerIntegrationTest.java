package pt.psoft.g1.psoftg1.readermanagement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.psoft.g1.psoftg1.testutils.UserTestDataFactory;
import pt.psoft.g1.psoftg1.usermanagement.api.UserView;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserTestDataFactory userTestDataFactory;

    private String testUsername;
    private String testFullName;
    private String testPassword;
    private UserView testUser;

    @BeforeEach
    void setUp() {
        testUsername = String.format("test.user.%d@nix.io", System.currentTimeMillis());
        testFullName = "Test User";
        testPassword = "Test12345_";
        testUser = userTestDataFactory.createUser(testUsername, testFullName, testPassword);
    }

    @Test
    void testCreateReader_Success() throws Exception {
        String requestBody = "{ \"username\": \"" + testUsername + "\", \"password\": \"" + testPassword + "\" }";

        mockMvc.perform(post("/api/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetReader_Success() throws Exception {
        // Assuming a reader has been created for the test user
        mockMvc.perform(get("/api/readers/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(testUsername)));
    }

    @Test
    void testUpdateReader_Success() throws Exception {
        String updateRequestBody = "{ \"username\": \"updatedUser\", \"password\": \"newPassword123\" }";

        mockMvc.perform(put("/api/readers/" + testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andExpect(status().isOk());

        // Verify that the reader was updated (optional)
        mockMvc.perform(get("/api/readers/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updatedUser")));
    }

    @Test
    void testDeleteReader_Success() throws Exception {
        // Create a reader first (mocking a real scenario)
        mockMvc.perform(post("/api/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"" + testUsername + "\", \"password\": \"" + testPassword + "\" }"))
                .andExpect(status().isCreated());

        // Now delete the reader
        mockMvc.perform(delete("/api/readers/" + testUser.getId()))
                .andExpect(status().isNoContent());

        // Verify that the reader no longer exists
        mockMvc.perform(get("/api/readers/" + testUser.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetNonExistentReader_NotFound() throws Exception {
        mockMvc.perform(get("/api/readers/99999")) // Using a non-existent ID
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateReader_InvalidData_BadRequest() throws Exception {
        String invalidRequestBody = "{ \"username\": \"\", \"password\": \"\" }"; // Invalid data

        mockMvc.perform(post("/api/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("must not be blank")));
    }
}