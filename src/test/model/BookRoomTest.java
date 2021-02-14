package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("Hannah", testBookRoom.getUsername());
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
    void testChangeUsername() {
        testBookRoom.setUsername("Thunder");
        assertEquals("Thunder", testBookRoom.getUsername());
    }
}
