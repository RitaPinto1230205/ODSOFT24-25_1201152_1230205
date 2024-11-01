package pt.psoft.g1.psoftg1.authormanagement.model;


    import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
    import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
    import org.hibernate.StaleObjectStateException;

public class AuthorTestBB {
    private final String validName = "João Alberto";
    private final String validBio = "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";
    private Author author;
    private UpdateAuthorRequest request;

    @BeforeEach
    void setUp() {
        author = new Author(validName, validBio, null);
        request = new UpdateAuthorRequest(validName, validBio, null, null);
    }

   
    // Opaque-box test cases (Black-box testing)
    @Test
    void ensureNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Author(null, validBio, null));
    }

    @Test
    void ensureBioCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Author(validName, null, null));
    }

    @Test
    void ensureValidAuthorCreation() {
        Author author = new Author(validName, validBio, null);
        assertEquals(validName, author.getName());
        assertEquals(validBio, author.getBio());
        assertNotNull(author.getPhoto());
    }

    @Test
    void whenVersionIsStale_applyPatchThrowsStaleObjectStateException() {
        Author author = new Author(validName, validBio, null);
        assertThrows(StaleObjectStateException.class, () -> author.applyPatch(999, request));
    }

    // Transparent-box test cases (White-box testing)
    @Test
    void testApplyPatchWithMatchingVersion() {
        Author author = new Author(validName, validBio, null);
        long currentVersion = author.getVersion();
        UpdateAuthorRequest updateRequest = new UpdateAuthorRequest("Updated Name", null, null, null);
        author.applyPatch(currentVersion, updateRequest);
        assertEquals("Updated Name", author.getName());
    }

    @Test
    void testRemovePhotoWithValidVersion() {
        Author author = new Author(validName, validBio, null);
        long currentVersion = author.getVersion();
        author.removePhoto(currentVersion);
        assertNull(author.getPhoto());
    }

    @Test
    void testRemovePhotoWithStaleVersionThrowsException() {
        Author author = new Author(validName, validBio,null);
        assertThrows(ConflictException.class, () -> author.removePhoto(999));
    }

    // Additional mutation testing preparation
    @Test
    void testInvalidVersionThrowsStaleObjectStateExceptionInPatch() {
        Author author = new Author(validName, validBio, null);
        assertThrows(StaleObjectStateException.class, () -> author.applyPatch(author.getVersion() + 1, request));
    }
}


    

