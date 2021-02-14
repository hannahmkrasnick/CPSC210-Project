package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Book("ABC");
    }

    @Test
    void testConstructor() {
        assertEquals("ABC", testBook.getTitle());
        assertEquals("", testBook.getAuthor());
        assertEquals(Genre.UNCLASSIFIED, testBook.getGenre());
        assertEquals(0, testBook.getRating());
        assertEquals("", testBook.getReview());
    }

    @Test
    void testChangeTitle() {
        testBook.setTitle("NEW");
        assertEquals("NEW", testBook.getTitle());
    }

    @Test
    void testChangeAuthor() {
        testBook.setAuthor("NEW");
        assertEquals("NEW", testBook.getAuthor());
    }

    @Test
    void testChangeRating() {
        testBook.setRating(5);
        assertEquals(5, testBook.getRating());
    }

    @Test
    void testChangeReview() {
        testBook.setReview("This was a good book!");
        assertEquals("This was a good book!", testBook.getReview());
    }

    @Test
    void testChangeGenre() {
        testBook.setGenre(Genre.COMIC);
        assertEquals(Genre.COMIC, testBook.getGenre());
    }
}