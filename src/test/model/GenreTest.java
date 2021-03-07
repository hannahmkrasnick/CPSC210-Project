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
}
