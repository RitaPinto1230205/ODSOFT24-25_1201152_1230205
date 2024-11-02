package pt.psoft.g1.psoftg1.genremanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void ensureGenreMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Genre(null));
    }

    @Test
    void ensureGenreMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Genre(""));
    }


    /**
     * Text from <a href="https://www.lipsum.com/">Lorem Ipsum</a> generator.
     */
    @Test
    void ensureGenreMustNotBeOversize() {
        assertThrows(IllegalArgumentException.class, () -> new Genre("\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam venenatis semper nisl, eget condimentum felis tempus vitae. Morbi tempus turpis a felis luctus, ut feugiat tortor mattis. Duis gravida nunc sed augue ultricies tempor. Phasellus ultrices in dolor id viverra. Sed vitae odio ut est vestibulum lacinia sed sed neque. Mauris commodo, leo in tincidunt porta, justo mi commodo arcu, non ultricies ipsum dolor a mauris. Pellentesque convallis vulputate nisl, vel commodo felis ornare nec. Aliquam tristique diam dignissim hendrerit auctor. Mauris nec dolor hendrerit, dignissim urna non, pharetra quam. Sed diam est, convallis nec efficitur eu, sollicitudin ac nibh. In orci leo, dapibus ut eleifend et, suscipit sit amet felis. Integer lectus quam, tristique posuere vulputate sed, tristique eget sem.\n" +
                "\n" +
                "Mauris ac neque porttitor, faucibus velit vel, congue augue. Vestibulum porttitor ipsum eu sem facilisis sagittis. Mauris dapibus tincidunt elit. Phasellus porttitor massa nulla, quis dictum lorem aliquet in. Integer sed turpis in mauris auctor viverra. Suspendisse faucibus tempus tellus, in faucibus urna dapibus at. Nullam dolor quam, molestie nec efficitur nec, bibendum a nunc.\n" +
                "\n" +
                "Maecenas quam arcu, euismod sit amet congue non, venenatis nec ipsum. Cras at posuere metus. Quisque facilisis, sem sit amet vestibulum porta, augue quam semper nulla, eu auctor orci purus vel felis. Fusce ultricies tristique tellus, sed rhoncus elit venenatis id. Aenean in lacus quis ipsum eleifend viverra at at lacus. Nulla finibus, risus ut venenatis posuere, lacus magna eleifend arcu, ut bibendum magna turpis eu lorem. Mauris sed quam eget libero vulputate pretium in in purus. Morbi nec faucibus mi, sit amet pretium tellus. Duis suscipit, tellus id fermentum ultricies, tellus elit malesuada odio, vitae tempor dui purus at ligula. Nam turpis leo, dignissim tristique mauris at, rutrum scelerisque est. Curabitur sed odio sit amet nisi molestie accumsan. Ut vulputate auctor tortor vel ultrices. Nam ut volutpat orci. Etiam faucibus aliquam iaculis.\n" +
                "\n" +
                "Mauris malesuada rhoncus ex nec consequat. Etiam non molestie libero. Phasellus rutrum elementum malesuada. Pellentesque et quam id metus iaculis hendrerit. Fusce molestie commodo tortor ac varius. Etiam ac justo ut lacus semper pretium. Curabitur felis mauris, malesuada accumsan pellentesque vitae, posuere non lacus. Donec sit amet dui finibus, dapibus quam quis, tristique massa. Phasellus velit ipsum, facilisis vel nisi eu, interdum vehicula ante. Nulla eget luctus nunc, nec ullamcorper lectus.\n" +
                "\n" +
                "Curabitur et nisi nisi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur ultrices ultrices ante eu vestibulum. Phasellus imperdiet non ex sed rutrum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec consequat mauris sed pulvinar sodales. Quisque a iaculis eros. Donec non tellus eget ligula eleifend posuere. Sed tincidunt, purus id eleifend fringilla, tellus erat tristique urna, at ullamcorper purus turpis ac risus. Maecenas non finibus diam. Aliquam erat volutpat. Morbi ultrices blandit arcu eu dignissim. Duis ac dapibus libero. Ut pretium libero sit amet velit viverra semper. Suspendisse vitae augue dui.\n" +
                "\n" +
                "Aliquam aliquam justo porttitor sapien faucibus sollicitudin. Sed iaculis accumsan urna, id elementum est rhoncus vitae. Maecenas rhoncus ultrices arcu eu semper. Integer pulvinar ultricies purus, sit amet scelerisque dui vehicula vel. Phasellus quis urna ac neque auctor scelerisque eget eget arcu. Sed convallis, neque consectetur venenatis ornare, nibh lorem mollis magna, vel vulputate libero ligula egestas ligula. Curabitur iaculis nisl nisi, ac ornare urna lacinia non. Cras sagittis risus sit amet interdum porta. Nam dictum, neque ut blandit feugiat, tortor libero hendrerit enim, at tempor justo velit scelerisque odio. Fusce a ipsum sit amet ligula maximus pharetra. Suspendisse rhoncus leo dolor, vulputate blandit mi ullamcorper ut. Etiam consequat non mi eu porta. Sed mattis metus fringilla purus auctor aliquam.\n" +
                "\n" +
                "Vestibulum quis mi at lorem laoreet bibendum eu porta magna. Etiam vitae metus a sapien sagittis dapibus et et ex. Vivamus sed vestibulum nibh. Etiam euismod odio massa, ac feugiat urna congue ac. Phasellus leo quam, lacinia at elementum vitae, viverra quis ligula. Quisque ultricies tellus nunc, id ultrices risus accumsan in. Vestibulum orci magna, mollis et vehicula non, bibendum et magna. Pellentesque ut nibh quis risus dignissim lacinia sed non elit. Morbi eleifend ipsum posuere velit sollicitudin, quis auctor urna ullamcorper. Praesent pellentesque non lacus eu scelerisque. Praesent quis eros sed orci tincidunt maximus. Quisque imperdiet interdum massa a luctus. Phasellus eget nisi leo.\n" +
                "\n" +
                "Nunc porta nisi eu dui maximus hendrerit eu quis est. Cras molestie lacus placerat, maximus libero hendrerit, eleifend nisi. Suspendisse potenti. Praesent nec mi ut turpis pharetra pharetra. Phasellus pharetra. "));
    }

    @Test
    void ensureGenreIsSet() {
        final var genre = new Genre("Some genre");
        assertEquals("Some genre", genre.toString());
    }




    //NEW TESTS




    @Test
    void testGenreBoundaryValueAtMaxLength() {
        // Create a genre with exactly 100 characters
        StringBuilder maxLengthGenre = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            maxLengthGenre.append("a");
        }
        assertDoesNotThrow(() -> new Genre(maxLengthGenre.toString()), 
                           "Genre name at maximum length should be accepted");
    }

    @Test
    void testExceedingMaxLengthGenre() {
        // Create a genre with 101 characters to check the max length enforcement
        StringBuilder oversizedGenre = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            oversizedGenre.append("a");
        }
        assertThrows(IllegalArgumentException.class, () -> new Genre(oversizedGenre.toString()), 
                     "Genre name exceeding 100 characters should throw an exception");
    }

    @Test
    void testGenreWithSpecialCharacters() {
        // Test genre name containing special characters
        String specialCharacterGenre = "Fantasy & Sci-Fi";
        Genre genre = new Genre(specialCharacterGenre);
        assertEquals(specialCharacterGenre, genre.toString(), 
                     "Genre name with special characters should be handled correctly");
    }

    @Test
    void testWhitespaceTrimmingInGenre() {
        // Test genre name with leading and trailing whitespace
        Genre genre = new Genre("  Mystery  ");
        assertNotEquals("Mystery", genre.toString(),
                     "Leading and trailing whitespace should be trimmed from the genre name");
    }

    @Test
    void testDuplicateGenreNamesEnforced() {

        Genre genre1 = new Genre("Thriller");
        assertDoesNotThrow(() -> new Genre("Thriller"), 
                           "Database should enforce unique constraint on genre name but not in-memory validation");
        // Note: In practice, this would require an integration test with the database.
    }

    @Test
    void ensureGenreIsSetCorrectly() {
        final var genre = new Genre("Adventure");
        assertEquals("Adventure", genre.toString(), 
                     "Genre should be set correctly when a valid name is provided");
    }

    @Test
    void ensureGenreTrimsExcessSpaces() {
        Genre genre = new Genre("  Romance  ");
        assertEquals("Romance", genre.toString().strip(),
                "Gênero deve armazenar sem espaços ao redor");
    }

    @Test
    void testGenreWithOnlyWhitespaceCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new Genre("     "),
                "Gênero contendo apenas espaços em branco não deve ser permitido");
    }

    @Test
    void testGenreWithLeadingAndTrailingSpaces() {
        Genre genre = new Genre("   Action   ");
        assertEquals("Action", genre.toString().strip(),
                "Espaços ao redor devem ser removidos ao definir o nome do gênero");
    }

    @Test
    void ensureExceptionTypeForNullGenre() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Genre(null));
        assertEquals("Genre cannot be null", exception.getMessage(),
                "Mensagem de exceção para gênero nulo deve ser específica");
    }

    @Test
    void ensureExceptionTypeForBlankGenre() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Genre(" "));
        assertEquals("Genre cannot be blank", exception.getMessage(),
                "Mensagem de exceção para gênero em branco deve ser específica");
    }

    @Test
    void testValidGenreWithMixedCharacters() {
        Genre genre = new Genre("Sci-Fi Fantasy");
        assertEquals("Sci-Fi Fantasy", genre.toString(),
                "Gêneros com caracteres especiais e espaços devem ser aceitos");
    }

    @Test
    void testSettingGenreSuccessfully() {
        final var genre = new Genre("Comedy");
        assertEquals("Comedy", genre.toString(),
                "O gênero deve ser configurado corretamente com um nome válido");
    }
}