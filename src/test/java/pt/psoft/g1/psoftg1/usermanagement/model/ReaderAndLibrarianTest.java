package pt.psoft.g1.psoftg1.usermanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ReaderAndLibrarianTest {

    @Test
    public void testReaderRole() {
        Reader reader = Reader.newReader("reader@example.com", "ReaderPass1!", "ReaderName");
        assertEquals("reader@example.com", reader.getUsername());
        assertTrue(reader.getAuthorities().contains(new Role(Role.READER)));
    }

    @Test
    public void testLibrarianRole() {
        Librarian librarian = Librarian.newLibrarian("librarian@example.com", "LibrarianPass1!", "LibrarianName");
        assertEquals("librarian@example.com", librarian.getUsername());
        assertTrue(librarian.getAuthorities().contains(new Role(Role.LIBRARIAN)));
    }
}

