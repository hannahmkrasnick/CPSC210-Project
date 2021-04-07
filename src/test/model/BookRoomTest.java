package model;

import exceptions.DuplicateBookshelfNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookRoomTest {
    private BookRoom testBookRoom;
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;
    private Book testBook1;
    private Book testBook2;


    @BeforeEach
    void runBefore() {
        testBookRoom = new BookRoom("Hannah");
        testBookshelf1 = new Bookshelf("Bookshelf 1");
        testBookshelf2 = new Bookshelf("Bookshelf 2");
        testBook1 = new Book("Book 1");
        testBook2 = new Book("Book 2");
    }

    @Test
    void testConstructorNonZeroLengthName() {
        assertEquals("Hannah", testBookRoom.getName());
        assertTrue((testBookRoom.getShelves().isEmpty()));
    }

    @Test
    void testConstructorZeroLengthName() {
        BookRoom bookRoom = new BookRoom("");
        assertEquals("My Book Room", bookRoom.getName());
        assertTrue((testBookRoom.getShelves().isEmpty()));
    }

    @Test
    void testAddShelfToRoomDoesNotAlreadyExist() {
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAddShelfToRoomDoesAlreadyExist() {
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
            testBookRoom.addShelfToRoom(testBookshelf1);
            fail("Exception should have been thrown.");
        } catch (DuplicateBookshelfNameException e) {
            assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
        }
    }

    @Test
    void testDeleteShelfFromRoom() {
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            testBookRoom.addShelfToRoom(testBookshelf2);
            testBookRoom.deleteShelfFromRoom(testBookshelf1);
            assertFalse(testBookRoom.getShelves().contains(testBookshelf1));
            assertTrue(testBookRoom.getShelves().contains(testBookshelf2));
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testCheckBookshelfDoesNotAlreadyExist() {
        Bookshelf newShelf = new Bookshelf("Bookshelf 1");
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            assertTrue(testBookRoom.getShelves().contains(testBookshelf1));

            assertFalse(testBookRoom.checkBookshelfDoesNotAlreadyExist(newShelf.getBookshelfLabel()));
            assertTrue(testBookRoom.checkBookshelfDoesNotAlreadyExist(testBookshelf2.getBookshelfLabel()));
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testCheckBookDoesNotAlreadyExist() {
        Book newBook = new Book("Book 1");

        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            testBookRoom.addShelfToRoom(testBookshelf2);
            assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
            assertTrue(testBookRoom.getShelves().contains(testBookshelf2));

            testBookshelf1.addBookToShelf(testBook1);
            assertFalse(testBookRoom.checkBookDoesNotAlreadyExist(newBook.getTitle()));
            assertTrue(testBookRoom.checkBookDoesNotAlreadyExist(testBook2.getTitle()));
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testDeleteBookFromBookRoomBookExists() {
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            testBookRoom.addShelfToRoom(testBookshelf2);

            testBookshelf1.addBookToShelf(testBook1);
            testBookshelf2.addBookToShelf(testBook1);

            testBookRoom.deleteBookFromBookRoom(testBook1);
            assertTrue(testBookshelf1.getBooksOnShelf().isEmpty());
            assertTrue(testBookshelf2.getBooksOnShelf().isEmpty());
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testDeleteBookFromBookRoomBookDoesNotExist() {
        try {
            testBookRoom.addShelfToRoom(testBookshelf1);
            testBookRoom.addShelfToRoom(testBookshelf2);

            testBookshelf1.addBookToShelf(testBook1);
            testBookshelf2.addBookToShelf(testBook1);

            testBookRoom.deleteBookFromBookRoom(testBook2);
            assertFalse(testBookshelf1.getBooksOnShelf().isEmpty());
            assertFalse(testBookshelf2.getBooksOnShelf().isEmpty());
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }
}
