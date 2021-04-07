package persistence;

import exceptions.DuplicateBookshelfNameException;
import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    private Bookshelf testAllBooks;
    private Bookshelf testToRead;
    private Bookshelf testCompleted;
    private Book testBookGOT;
    private Book testBookHP;


    @BeforeEach
    void runBefore() {
        testAllBooks = new Bookshelf("All Books");
        testToRead = new Bookshelf("To Read");
        testCompleted = new Bookshelf("Completed");
        testBookGOT = new Book("Game of Thrones", "George R R Martin", Genre.FANTASY,
                9, "Very good!");
        testBookHP = new Book("Harry Potter", "JK Rowling", Genre.YOUNGADULT, 8, "Not bad!");
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriterTest.testWriterInvalidFile)
    @Test
    void testWriterInvalidFile() {
        try {
            BookRoom br = new BookRoom("My Book Room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriterTest.testWriterEmptyWorkroom)
    @Test
    void testWriterEmptyBookRoom() {
        try {
            BookRoom bookRoom = new BookRoom("My Book Room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookRoom.json");
            writer.open();
            writer.write(bookRoom);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookRoom.json");
            bookRoom = reader.read();
            assertEquals("My Book Room", bookRoom.getName());
            assertEquals(0, bookRoom.getShelves().size());
            assertEquals(0, bookRoom.getBooks().size());

        } catch (IOException | DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriterTest.testWriterGeneralWorkroom)
    @Test
    void testWriterExampleBookRoom() {
        try {
            BookRoom bookRoom = new BookRoom("My Book Room");
            bookRoom.addShelfToRoom(testAllBooks);
            bookRoom.addShelfToRoom(testToRead);
            bookRoom.addShelfToRoom(testCompleted);
            testAllBooks.addBookToShelf(testBookGOT);
            testAllBooks.addBookToShelf(testBookHP);
            testToRead.addBookToShelf(testBookHP);
            testCompleted.addBookToShelf(testBookGOT);
            JsonWriter writer = new JsonWriter("./data/testWriterExampleBookRoom.json");
            writer.open();
            writer.write(bookRoom);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterExampleBookRoom.json");
            bookRoom = reader.read();
            List<Bookshelf> shelves = bookRoom.getShelves();
            assertEquals("My Book Room", bookRoom.getName());
            assertEquals(3, shelves.size());
            assertEquals(2, bookRoom.getBooks().size());

            checkBookshelf(shelves.get(0), testAllBooks.getBookshelfLabel());
            assertEquals(2, testAllBooks.getBooksOnShelf().size());
            checkBook(shelves.get(0).getBooksOnShelf().get(0), testBookGOT.getTitle(), testBookGOT.getAuthor(),
                    testBookGOT.getGenre(), testBookGOT.getRating(), testBookGOT.getReview());
            checkBook(shelves.get(0).getBooksOnShelf().get(1), testBookHP.getTitle(), testBookHP.getAuthor(),
                    testBookHP.getGenre(), testBookHP.getRating(), testBookHP.getReview());

            checkBookshelf(shelves.get(1), testToRead.getBookshelfLabel());
            assertEquals(1, testToRead.getBooksOnShelf().size());
            checkBook(shelves.get(1).getBooksOnShelf().get(0), testBookHP.getTitle(), testBookHP.getAuthor(),
                    testBookHP.getGenre(), testBookHP.getRating(), testBookHP.getReview());

            checkBookshelf(shelves.get(2), testCompleted.getBookshelfLabel());
            assertEquals(1, testCompleted.getBooksOnShelf().size());
            checkBook(shelves.get(2).getBooksOnShelf().get(0), testBookGOT.getTitle(), testBookGOT.getAuthor(),
                    testBookGOT.getGenre(), testBookGOT.getRating(), testBookGOT.getReview());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (DuplicateBookshelfNameException e) {
            fail("Exception should not have been thrown");
        }
    }
}
