package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    @Test
    void testCheckGenreExists() {
        assertFalse(Genre.checkGenreExists("happy"));
        assertTrue(Genre.checkGenreExists("fiction"));
        assertTrue(Genre.checkGenreExists("FANTASY"));
    }

    @Test
    void testGetGenreFromStringGenreExists(){
        assertEquals(Genre.FANTASY, Genre.getGenreFromString("fantasy"));
        assertEquals(Genre.FICTION, Genre.getGenreFromString("FiCtIoN"));
        assertEquals(Genre.SCIENCEFICTION, Genre.getGenreFromString("SCIENCEFICTION"));
    }

    @Test
    void testGetGenreFromStringGenreDoesNotExist(){
        assertEquals(Genre.UNCLASSIFIED, Genre.getGenreFromString(""));
        assertEquals(Genre.UNCLASSIFIED, Genre.getGenreFromString("ficion"));
        assertEquals(Genre.UNCLASSIFIED, Genre.getGenreFromString("182437"));


    }
}
