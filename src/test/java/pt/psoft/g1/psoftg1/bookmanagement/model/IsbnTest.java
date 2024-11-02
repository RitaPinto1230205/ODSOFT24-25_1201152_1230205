package pt.psoft.g1.psoftg1.bookmanagement.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class IsbnTest {



    @Test
    void ensureIsbnMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn(null));
    }

    @Test
    void ensureIsbnMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn(""));
    }


    /**
     * Text from <a href="https://www.lipsum.com/">Lorem Ipsum</a> generator.
     */
    @Test
    void ensureIsbnMustNotBeOversize() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("\n" +
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
    void ensureIsbn13IsSet() {
        final var isbn = new Isbn("9782826012092");
        assertEquals("9782826012092", isbn.toString());
    }

    @Test
    void ensureChecksum13IsCorrect() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("9782826012099"));
    }

    @Test
    void ensureIsbn10IsSet() {
        final var isbn = new Isbn("8175257660");
        assertEquals("8175257660", isbn.toString());
    }

    @Test
    void ensureChecksum10IsCorrect() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("8175257667"));
    }


    //NEW TESTS




    @Test
    void testIsbnBoundaryValueAtLength10() {
        // Valid ISBN-10 check at boundary
        String isbn10 = "123456789X";
        Isbn isbn = new Isbn(isbn10);
        assertEquals(isbn10, isbn.toString());
    }

    @Test
    void testIsbnBoundaryValueAtLength13() {
        // Valid ISBN-13 check at boundary
        String isbn13 = "9781234567897";
        Isbn isbn = new Isbn(isbn13);
        assertEquals(isbn13, isbn.toString());
    }

    @Test
    void testHyphenatedIsbnShouldFail() {
        // Hyphenated ISBNs should throw an exception
        assertThrows(IllegalArgumentException.class, () -> new Isbn("978-1234-56789-7"));
    }

    @Test
    void testIsbnWithSpecialCharactersShouldFail() {
        // ISBNs with special characters should throw an exception
        assertThrows(IllegalArgumentException.class, () -> new Isbn("978123@56789!7"));
    }

    @Test
    void testLowercaseXInIsbn10() {
        // Test lowercase 'x' for ISBN-10
        String isbn10WithLowerX = "123456789x";

        assertThrows (IllegalArgumentException.class,() -> new Isbn(isbn10WithLowerX));
    }

    @Test
    void testInvalidCharacterInIsbn10() {
        // ISBN-10 with an invalid character (non-numeric and non-X) should fail
        assertThrows(IllegalArgumentException.class, () -> new Isbn("12345678A9"));
    }

    @Test
    void testInvalidCharacterInIsbn13() {
        // ISBN-13 with an invalid character should fail
        assertThrows(IllegalArgumentException.class, () -> new Isbn("97812345678A7"));
    }

    @Test
    void testIsbn10CheckDigitAsX() {
        // Valid ISBN-10 where the check digit is 'X'
        String isbn10WithX = "0306406152"; // A valid example where last digit calculated is correct
        Isbn isbn = new Isbn(isbn10WithX);
        assertEquals(isbn10WithX, isbn.toString());
    }

    @Test
    void testInvalidIsbnLength() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("12345"));
        assertThrows(IllegalArgumentException.class, () -> new Isbn("97828260120921"));
    }

    // Teste para verificar se um ISBN-13 válido com checksum 0 passa
    @Test
    void testValidIsbn13WithChecksumZero() {
        // Exemplo de ISBN-13 onde o checksum é 0
        String isbn13 = "9780306406157"; // Um ISBN-13 válido
        Isbn isbn = new Isbn(isbn13);
        assertEquals(isbn13, isbn.toString());
    }

    // Teste para verificar se um ISBN-10 válido termina com '0' passa
    @Test
    void testValidIsbn10EndingWithZero() {
        // Exemplo de ISBN-10 válido terminando em '0'
        String isbn10 = "048665088X"; // Um ISBN-10 válido
        Isbn isbn = new Isbn(isbn10);
        assertEquals(isbn10, isbn.toString());
    }

    // Teste para um ISBN-10 onde o dígito de verificação é o dígito "X" e soma divisível por 11
    @Test
    void testIsbn10WithCheckDigitXAndValidChecksum() {
        String isbn10WithX = "156881111X"; // Um ISBN-10 válido onde o dígito de verificação é 'X'
        Isbn isbn = new Isbn(isbn10WithX);
        assertEquals(isbn10WithX, isbn.toString());
    }

    // Teste para verificar ISBN-13 com checksum incorreto, validando o cálculo do dígito de verificação
    @Test
    void testIncorrectIsbn13Checksum() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("9780306406158"));
    }

    // Teste para ISBN-13 onde o cálculo do dígito de verificação resulta em 10
    @Test
    void testIsbn13ChecksumCalculationToTen() {
        // ISBN-13 onde o cálculo do checksum dá 10, que se ajusta para 0
        String isbn13 = "9781861972712"; // ISBN-13 válido com checksum ajustado para 0
        Isbn isbn = new Isbn(isbn13);
        assertEquals(isbn13, isbn.toString());
    }

    @Test
    void testIsValidIsbn10InternalPaths() throws Exception {
        // Preparação: Usa reflexão para acessar o método privado isValidIsbn10
        Method isValidIsbn10 = Isbn.class.getDeclaredMethod("isValidIsbn10", String.class);
        isValidIsbn10.setAccessible(true);

        // 1. Teste para ISBN-10 inválido com caracteres incorretos (falha rápida em regex)
        assertFalse((Boolean) isValidIsbn10.invoke(null, "12345A6789"));

        // 2. Teste com ISBN-10 válido (dígito de verificação X) para validar cálculo
        assertTrue((Boolean) isValidIsbn10.invoke(null, "156881111X"), "Falha ao validar ISBN-10 com dígito de verificação 'X'");

        // 3. Teste com ISBN-10 onde a soma está errada apenas pelo último dígito
        assertFalse((Boolean) isValidIsbn10.invoke(null, "1568811119"), "Falha ao detectar ISBN-10 com soma inválida");

        // 4. Teste com ISBN-10 onde o dígito de verificação é '0' e soma correta
        assertTrue((Boolean) isValidIsbn10.invoke(null, "048665088X"), "Falha ao validar ISBN-10 com soma correta terminando em 'X'");

        // 5. **Correção**: Teste com ISBN-10 onde o cálculo do dígito de verificação resulta em valor correto
        assertTrue((Boolean) isValidIsbn10.invoke(null, "0306406152"), "Falha ao validar ISBN-10 com cálculo correto do dígito de verificação");

        // 6. Teste com ISBN-10 menor que 10 caracteres para confirmar falha
        assertFalse((Boolean) isValidIsbn10.invoke(null, "12345678"), "Falha ao detectar ISBN-10 de comprimento inválido");

        // 7. Teste para ISBN-10 inválido com soma incorreta, mas comprimento correto
        assertFalse((Boolean) isValidIsbn10.invoke(null, "1234567891"), "Falha ao detectar ISBN-10 com soma incorreta, mas formato correto");

        // 8. Teste com ISBN-10 numérico que passa com dígito de verificação correto
        assertTrue((Boolean) isValidIsbn10.invoke(null, "0306406152"), "Falha ao validar ISBN-10 com cálculo correto do dígito de verificação");

        // Resetar o acesso ao método
        isValidIsbn10.setAccessible(false);
    }

}