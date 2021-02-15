package model;

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
    void testConstructor() {
        assertEquals("Hannah", testBookRoom.getName());
        assertTrue((testBookRoom.getShelves().isEmpty()));
    }

    @Test
    void testAddShelvesToRoom() {
        testBookRoom.addShelfToRoom(testBookshelf1);
        testBookRoom.addShelfToRoom(testBookshelf2);
        assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
        assertTrue(testBookRoom.getShelves().contains(testBookshelf2));
    }

    @Test
    void testDeleteShelfFromRoom() {
        testBookRoom.addShelfToRoom(testBookshelf1);
        testBookRoom.addShelfToRoom(testBookshelf2);
        testBookRoom.deleteShelfFromRoom(testBookshelf1);
        assertFalse(testBookRoom.getShelves().contains(testBookshelf1));
        assertTrue(testBookRoom.getShelves().contains(testBookshelf2));
    }

    @Test
    void testCheckBookshelfDoesNotAlreadyExist() {
        Bookshelf newShelf = new Bookshelf("Bookshelf 1");

        testBookRoom.addShelfToRoom(testBookshelf1);
        assertTrue(testBookRoom.getShelves().contains(testBookshelf1));

        assertFalse(testBookRoom.checkBookshelfDoesNotAlreadyExist(newShelf.getBookshelfLabel()));
        assertTrue(testBookRoom.checkBookshelfDoesNotAlreadyExist(testBookshelf2.getBookshelfLabel()));
    }

    @Test
    void testCheckBookDoesNotAlreadyExist() {
        Book newBook = new Book("Book 1");

        testBookRoom.addShelfToRoom(testBookshelf1);
        testBookRoom.addShelfToRoom(testBookshelf2);
        assertTrue(testBookRoom.getShelves().contains(testBookshelf1));
        assertTrue(testBookRoom.getShelves().contains(testBookshelf2));

        testBookshelf1.addBookToShelf(testBook1);
        assertFalse(testBookRoom.checkBookDoesNotAlreadyExist(newBook.getTitle()));
        assertTrue(testBookRoom.checkBookDoesNotAlreadyExist(testBook2.getTitle()));
    }

}
