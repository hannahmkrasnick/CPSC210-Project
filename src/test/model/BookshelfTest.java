package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private Bookshelf testBookshelf3;
    private Book testBook1;

    @BeforeEach
    void runBefore() {
        testBookshelf1 = new Bookshelf("Bookshelf 1");
        testBookshelf2 = new Bookshelf("Bookshelf 2");
        testBookshelf3 = new Bookshelf("Bookshelf 1");
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

    @Test
    void testMakeLabelRightLengthForGuiAlreadyShort() {
        assertEquals(testBookshelf1.makeLabelRightLengthForGui("Short enough"), "Short enough");
        assertEquals(testBookshelf1.makeLabelRightLengthForGui("0123456789012"), "0123456789012");

    }

    @Test
    void testMakeLabelRightLengthForGuiTooLong() {
        assertEquals(testBookshelf1.makeLabelRightLengthForGui("This is too long!!!!!"), "This is too l");
    }

    @Test
    void testHashCodeAndEquals() {
        assertFalse(testBookshelf1.equals(testBookshelf2));
        assertTrue(testBookshelf1.equals(testBookshelf3));
        assertFalse(testBookshelf1.equals("test"));
        assertTrue(testBookshelf1.hashCode() == testBookshelf3.hashCode());
    }
}
