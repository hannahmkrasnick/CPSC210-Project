package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookRoomTest {
    private BookRoom testBookRoom;
    private Bookshelf testBookshelf1;
    private Bookshelf testBookshelf2;


    @BeforeEach
    void runBefore() {
        testBookRoom = new BookRoom("Hannah");
        testBookshelf1 = new Bookshelf("Bookshelf 1");
        testBookshelf2 = new Bookshelf("Bookshelf 2");
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
}
