package pt.psoft.g1.psoftg1.shared.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {
    @Test
    void ensurePathMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    void ensurePathIsValidToLocalFile() {
        Path fileStorageLocation = Paths.get("uploads-psoft-g1").toAbsolutePath().normalize();
        assertNotEquals(null, fileStorageLocation.toString());

        Photo photo = new Photo(Paths.get("photoTest.jpg"));
        assertEquals(photo.getPhotoFile(), "photoTest.jpg");
    }

    //New unit tests using AAA

    @Test
    void ensureAbsoluteFilePathIsConvertedToString() {
        // Arrange
        Path absolutePath = Paths.get("uploads-psoft-g1/photoTest.jpg").toAbsolutePath();

        // Act
        Photo photo = new Photo(absolutePath);

        // Assert
        assertEquals(absolutePath.toString(), photo.getPhotoFile());
    }

    @Test
    public void ensurePhotoFileStoresNormalizedPath() {
        // Arrange
        Path unnormalizedPath = Paths.get("./uploads-psoft-g1/../uploads-psoft-g1/photoTest.jpg");
        Path expectedNormalizedPath = unnormalizedPath.normalize();

        // Act
        Photo photo = new Photo(unnormalizedPath);
        String storedPhotoPath = photo.getPhotoFile();

        // Assert
        Path storedPathNormalized = Paths.get(storedPhotoPath).normalize();
        assertEquals(expectedNormalizedPath.toString(), storedPathNormalized.toString());
    }

    @Test
    void ensureDefaultConstructorInitializesObject() {
        Photo photo = new Photo();
        assertNotNull(photo);
        assertNull(photo.getPhotoFile()); // Initially null as per default constructor
    }

    @Test
    void ensurePhotoFileCanBeSetAfterCreation() {
        Photo photo = new Photo(Paths.get("initial.jpg"));
        photo.setPhotoFile("updated.jpg");
        assertEquals("updated.jpg", photo.getPhotoFile());
    }
}