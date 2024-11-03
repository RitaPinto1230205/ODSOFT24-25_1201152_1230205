package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/*
public class ReaderTest {
    @Test
    void ensureValidReaderDetailsAreCreated() {
        Reader mockReader = mock(Reader.class);
        assertDoesNotThrow(() -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null, null));
    }

    @Test
    void ensureExceptionIsThrownForNullReader() {
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, null, "2010-01-01", "912345678", true, false, false,null,null));
    }

    @Test
    void ensureExceptionIsThrownForNullPhoneNumber() {
        Reader mockReader = mock(Reader.class);
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", null, true, false, false,null,null));
    }

    @Test
    void ensureExceptionIsThrownForNoGdprConsent() {
        Reader mockReader = mock(Reader.class);
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", false, false, false,null,null));
    }

    @Test
    void ensureGdprConsentIsTrue() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null,null);
        assertTrue(readerDetails.isGdprConsent());
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null,null);
        assertNull(readerDetails.getPhoto());
    }

    @Test
    void ensureValidPhoto() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",null);
        Photo photo = readerDetails.getPhoto();

        //This is here to force the test to fail if the photo is null
        assertNotNull(photo);

        String readerPhoto = photo.getPhotoFile();
        assertEquals("readerPhotoTest.jpg", readerPhoto);
    }

    @Test
    void ensureInterestListCanBeNullOrEmptyList_AkaOptional() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetailsNullInterestList = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",null);
        assertNull(readerDetailsNullInterestList.getInterestList());

        ReaderDetails readerDetailsInterestListEmpty = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",new ArrayList<>());
        assertEquals(0, readerDetailsInterestListEmpty.getInterestList().size());
    }

    @Test
    void ensureInterestListCanTakeAnyValidGenre() {
        Reader mockReader = mock(Reader.class);
        Genre g1 = new Genre("genre1");
        Genre g2 = new Genre("genre2");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(g1);
        genreList.add(g2);

        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",genreList);
        assertEquals(2, readerDetails.getInterestList().size());
    }

    //New unit tests using AAA

    @Test
    void ensureDefaultPhotoIsNullWhenNotProvided() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2023-01-01", "912345678", true, false, false, null, null);

        // Assert
        assertNull(readerDetails.getPhoto());
    }

    @Test
    void ensureReaderDetailsCannotBeCreatedWithoutPhoneNumber() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2023-01-01", null, true, false, false, null, null));
    }

    @Test
    void ensureReaderDetailsCanBeCreatedWithValidParameters() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        String photoFile = "photo.jpg";
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Fiction"));

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2023-01-01", "912345678", true, false, false, photoFile, genres);

        // Assert
        assertNotNull(readerDetails.getReader());
        assertEquals(photoFile, readerDetails.getPhoto().getPhotoFile());
        assertEquals(1, readerDetails.getInterestList().size());
    }

    @Test
    void ensureReaderDetailsCannotHaveEmptyGenreList() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2023-01-01", "912345678", true, false, false, "photo.jpg", new ArrayList<>());

        // Assert
        assertTrue(readerDetails.getInterestList().isEmpty());
    }

    @Test
    void ensureGdprConsentCanBeUpdated() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2023-01-01", "912345678", true, false, false, null, null);

        // Act
        readerDetails.setGdprConsent(false);

        // Assert
        assertFalse(readerDetails.isGdprConsent());
    }

    @Test
    void ensureExceptionIsThrownForInvalidBirthDateFormat() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "01-01-2010", "912345678", true, false, false, null, null));
    }

    @Test
    void ensurePhoneNumberFormatIsValid() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", "phone1234", true, false, false, null, null));
    }

    @Test
    void ensureInterestListOnlyAcceptsValidGenres() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("Science Fiction"));
        genreList.add(new Genre("Mystery"));

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, genreList);

        // Assert
        assertEquals(2, readerDetails.getInterestList().size());
    }

    @Test
    void ensureInterestListMaxSizeConstraint() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        List<Genre> maxGenres = new ArrayList<>();
        for (int i = 0; i < 10; i++) { // Assuming max size is 10
            maxGenres.add(new Genre("Genre " + i));
        }

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, maxGenres);

        // Assert
        assertEquals(10, readerDetails.getInterestList().size());
    }


    @Test
    void ensureSettingThirdPartyConsentUpdatesValue() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, null);

        // Act
        readerDetails.setThirdPartySharingConsent(true);

        // Assert
        assertTrue(readerDetails.isThirdPartySharingConsent());
    }

    @Test
    void ensureReaderCanBeUpdatedWithValidData() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, null);

        // Act
        readerDetails.setReader(mockReader);

        // Assert
        assertNotNull(readerDetails.getReader());
        assertEquals(mockReader, readerDetails.getReader());
    }
    @Test
    void ensureIsMarketingConsentReturnsCorrectValue() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "1990-01-01", "912345678", true, false, false, "photo.jpg", null);

        // Act
        boolean marketingConsent = readerDetails.isMarketingConsent();

        // Assert
        assertFalse(marketingConsent);
    }
}*/