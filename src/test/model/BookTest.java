package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book testBook1;
    private Book testBook2;

    @BeforeEach
    void runBefore() {
        testBook1 = new Book("ABC");
        testBook2 = new Book("DEF", "Author name", Genre.COMIC, 8, "Pretty good");
    }

    @Test
    void testConstructorJustTitle() {
        assertEquals("ABC", testBook1.getTitle());
        assertEquals("", testBook1.getAuthor());
        assertEquals(Genre.UNCLASSIFIED, testBook1.getGenre());
        assertEquals(-1, testBook1.getRating());
        assertEquals("", testBook1.getReview());
    }

    @Test
    void testConstructorMoreParam() {
        assertEquals("DEF", testBook2.getTitle());
        assertEquals("Author name", testBook2.getAuthor());
        assertEquals(Genre.COMIC, testBook2.getGenre());
        assertEquals(8, testBook2.getRating());
        assertEquals("Pretty good", testBook2.getReview());
    }

    @Test
    void testChangeTitle() {
        testBook1.setTitle("NEW");
        assertEquals("NEW", testBook1.getTitle());
    }

    @Test
    void testChangeAuthor() {
        testBook1.setAuthor("NEW");
        assertEquals("NEW", testBook1.getAuthor());
    }

    @Test
    void testChangeRating() {
        testBook1.setRating(5);
        assertEquals(5, testBook1.getRating());
    }

    @Test
    void testChangeReview() {
        testBook1.setReview("This was a good book!");
        assertEquals("This was a good book!", testBook1.getReview());
    }

    @Test
    void testChangeGenre() {
        testBook1.setGenre(Genre.COMIC);
        assertEquals(Genre.COMIC, testBook1.getGenre());
    }
}