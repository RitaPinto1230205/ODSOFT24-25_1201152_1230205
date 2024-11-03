
package pt.psoft.g1.psoftg1.bookmanagement.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_BASE_PATH = "/api/books";

    // Sample data for tests
    private CreateBookRequest createBookRequest;
    private UpdateBookRequest updateBookRequest;
    private Book book;

    @BeforeEach
    void setUp() {
        // Set up mock data
        createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setGenre("Test Genre");
        createBookRequest.setAuthors(List.of(1L));

        updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setTitle("Updated Title");

        book = new Book("1234567890123", "Test Book", "Test Description", new Genre("Test Genre"), List.of(new Author("Author Name",null ,null)), "test.jpg");
    }

    @Test
    void createBook_ShouldReturnCreatedStatus() throws Exception {
        when(bookService.create(any(CreateBookRequest.class), anyString())).thenReturn(book);

        mockMvc.perform(put(API_BASE_PATH + "/{isbn}", "1234567890123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("/api/books/1234567890123")))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void findByIsbn_ShouldReturnBook() throws Exception {
        when(bookService.findByIsbn("1234567890123")).thenReturn(book);

        mockMvc.perform(get(API_BASE_PATH + "/{isbn}", "1234567890123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.isbn").value("1234567890123"));
    }

    @Test
    void deleteBookPhoto_ShouldReturnOkStatus() throws Exception {
        when(bookService.findByIsbn("1234567890123")).thenReturn(book);

        mockMvc.perform(delete(API_BASE_PATH + "/{isbn}/photo", "1234567890123"))
                .andExpect(status().isOk());

        verify(fileStorageService).deleteFile(anyString());
    }

    @Test
    void getSpecificBookPhoto_ShouldReturnPhoto() throws Exception {
        byte[] imageBytes = new byte[] {1, 2, 3};
        when(bookService.findByIsbn("1234567890123")).thenReturn(book);
        when(fileStorageService.getFile("test.jpg")).thenReturn(imageBytes);

        mockMvc.perform(get(API_BASE_PATH + "/{isbn}/photo", "1234567890123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageBytes));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        when(bookService.update(any(UpdateBookRequest.class), anyString())).thenReturn(book);

        mockMvc.perform(patch(API_BASE_PATH + "/{isbn}", "1234567890123")
                .header(HttpHeaders.IF_MATCH, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void findBooks_ShouldReturnListOfBooks() throws Exception {
        when(bookService.findByTitle(anyString())).thenReturn(List.of(book));

        mockMvc.perform(get(API_BASE_PATH)
                .param("title", "Test Book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].title").value("Test Book"));
    }

    @Test
    void getTop5BooksLent_ShouldReturnTop5Books() throws Exception {
        BookCountDTO bookCountDto = new BookCountDTO(book, 5L);
        when(bookService.findTop5BooksLent()).thenReturn(List.of(bookCountDto));

        mockMvc.perform(get(API_BASE_PATH + "/top5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].bookView.title").value("Test Book"));
    }
}
