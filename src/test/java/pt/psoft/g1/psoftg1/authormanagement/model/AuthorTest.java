package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Photo;


import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    private final String validName = "João Alberto";
    private final String validBio = "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";
    private final String photoURI="photoTest.jpg";

    private final UpdateAuthorRequest request = new UpdateAuthorRequest(validName, validBio, null, null);

    private static class EntityWithPhotoImpl extends EntityWithPhoto { }
    @BeforeEach
    void setUp() {
    }
    @Test
    void ensureNameNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(null,validBio, null));
    }

    @Test
    void ensureBioNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(validName,null, null));
    }

    @Test
    void whenVersionIsStaleItIsNotPossibleToPatch() {
        final var subject = new Author(validName,validBio, null);

        assertThrows(StaleObjectStateException.class, () -> subject.applyPatch(999, request));
    }

    @Test
    void testCreateAuthorWithoutPhoto() {
        Author author = new Author(validName, validBio, null);
        assertNotNull(author);
        assertNull(author.getPhoto());
    }

    @Test
    void testCreateAuthorRequestWithPhoto() {
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, "photoTest.jpg");
        Author author = new Author(request.getName(), request.getBio(), "photoTest.jpg");
        assertNotNull(author);
        assertEquals(request.getPhotoURI(), author.getPhoto().getPhotoFile());
    }

    @Test
    void testCreateAuthorRequestWithoutPhoto() {
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, null);
        Author author = new Author(request.getName(), request.getBio(), null);
        assertNotNull(author);
        assertNull(author.getPhoto());
    }

    @Test
    void testEntityWithPhotoSetPhotoInternalWithValidURI() {
        EntityWithPhoto entity = new EntityWithPhotoImpl();
        String validPhotoURI = "photoTest.jpg";
        entity.setPhoto(validPhotoURI);
        assertNotNull(entity.getPhoto());
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        Author author = new Author(validName, validBio, null);
        assertNull(author.getPhoto());
    }

    @Test
    void ensureValidPhoto() {
        Author author = new Author(validName, validBio, "photoTest.jpg");
        Photo photo = author.getPhoto();
        assertNotNull(photo);
        assertEquals("photoTest.jpg", photo.getPhotoFile());
    }
    
    //NEW TESTS

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
        Author author = new Author(validName, validBio, photoURI);
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
        Author author = new Author(validName, validBio, photoURI);
        long currentVersion = author.getVersion();
        author.removePhoto(currentVersion);
        assertNull(author.getPhoto());
    }

    @Test
    void testRemovePhotoWithStaleVersionThrowsException() {
        Author author = new Author(validName, validBio, photoURI);
        assertThrows(ConflictException.class, () -> author.removePhoto(999));
    }

    // Additional mutation testing preparation
    @Test
    void testInvalidVersionThrowsStaleObjectStateExceptionInPatch() {
        Author author = new Author(validName, validBio, null);
        assertThrows(StaleObjectStateException.class, () -> author.applyPatch(author.getVersion() + 1, request));
    }
}


