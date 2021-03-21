package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private Book testBook1;

    @BeforeEach
    void runBefore() {
        testBookshelf1 = new Bookshelf("Bookshelf 1");
        testBookshelf2 = new Bookshelf("Bookshelf 2");
        testBook1 = new Book("Book 1");
    }

    @Test
    void testConstructor() {
        assertEquals("Bookshelf 1", testBookshelf1.getBookshelfLabel());
        assertTrue((testBookshelf1.getBooksOnShelf().isEmpty()));
    }

    @Test
    void testAddBookNotAlreadyThere() {
        testBookshelf1.addBookToShelf(testBook1);
        assertTrue(testBookshelf1.getBooksOnShelf().contains(testBook1));
    }

    @Test
    void testAddBookAlreadyThere() {
        testBookshelf1.addBookToShelf(testBook1);
        testBookshelf1.addBookToShelf(testBook1);
        assertTrue(testBookshelf1.getBooksOnShelf().contains(testBook1));
        assertEquals(1, testBookshelf1.getBooksOnShelf().size());
    }

    @Test
    void testRemoveBookWhichIsOnShelf() {
        testBookshelf1.addBookToShelf(testBook1);
        assertTrue(testBookshelf1.getBooksOnShelf().contains(testBook1));
        testBookshelf1.removeBookFromShelf(testBook1);
        assertFalse(testBookshelf1.getBooksOnShelf().contains(testBook1));
    }

    @Test
    void testRemoveBookWhichIsNotOnShelf() {
        assertFalse(testBookshelf1.getBooksOnShelf().contains(testBook1));
        testBookshelf1.removeBookFromShelf(testBook1);
        assertFalse(testBookshelf1.getBooksOnShelf().contains(testBook1));
    }

    @Test
    void testAddBookAlreadyOnDifferentShelf() {
        testBookshelf1.addBookToShelf(testBook1);
        testBookshelf2.addBookToShelf(testBook1);
        assertTrue(testBookshelf1.getBooksOnShelf().contains(testBook1));
        assertTrue(testBookshelf2.getBooksOnShelf().contains(testBook1));
    }

    @Test
    void testChangeBookshelfLabel() {
        testBookshelf1.setBookshelfLabel("Favourites");
        assertEquals("Favourites", testBookshelf1.getBookshelfLabel());
    }
}
