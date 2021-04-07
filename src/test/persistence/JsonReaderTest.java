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

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    private Bookshelf testAllBooks;
    private Bookshelf testToRead;
    private Bookshelf testCompleted;
    private Bookshelf testFavourites;
    private Book testBookGOT;
    private Book testBookHP;


    @BeforeEach
    void runBefore() {
        testAllBooks = new Bookshelf("All Books");
        testToRead = new Bookshelf("To Read");
        testCompleted = new Bookshelf("Completed");
        testFavourites = new Bookshelf("Favourites");
        testBookGOT = new Book("Game of Thrones", "George R R Martin", Genre.FANTASY,
                9, "Very good!");
        testBookHP = new Book("Harry Potter", "JK Rowling", Genre.YOUNGADULT, 8, "Not bad!");
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReaderTest.testReaderNonExistentFile)
    @Test
    void testReaderNonExistentFile() throws DuplicateBookshelfNameException {
        JsonReader reader = new JsonReader("./data/doesNotExist.json");
        try {
            BookRoom br = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReaderTest.testReaderEmptyWorkRoom)
    @Test
    void testReaderEmptyBookRoom() throws DuplicateBookshelfNameException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookRoom.json");
        try {
            BookRoom br = reader.read();
            assertEquals("My Book Room", br.getName());
            assertEquals(3, br.getShelves().size());
            assertEquals(0, br.getBooks().size());
            assertEquals(null, reader.getBookFromTitle("ABC"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReaderTest.testReaderGeneralWorkRoom)
    @Test
    void testReaderExampleBookRoomNoDuplicateObjects() throws DuplicateBookshelfNameException {
        JsonReader reader = new JsonReader("./data/testReaderExampleBookRoomNoDuplicates.json");
        try {
            BookRoom br = reader.read();
            testAllBooks.addBookToShelf(testBookGOT);
            testAllBooks.addBookToShelf(testBookHP);

            assertEquals("My Book Room", br.getName());
            List<Bookshelf> shelves = br.getShelves();
            assertEquals(3, shelves.size());

            checkBookshelf(shelves.get(0), testAllBooks.getBookshelfLabel());
            assertEquals(2, testAllBooks.getBooksOnShelf().size());
            checkBook(shelves.get(0).getBooksOnShelf().get(0), testBookGOT.getTitle(), testBookGOT.getAuthor(),
                    testBookGOT.getGenre(), testBookGOT.getRating(), testBookGOT.getReview());
            checkBook(shelves.get(0).getBooksOnShelf().get(1), testBookHP.getTitle(), testBookHP.getAuthor(),
                    testBookHP.getGenre(), testBookHP.getRating(), testBookHP.getReview());

            checkBookshelf(shelves.get(1), testToRead.getBookshelfLabel());
            assertEquals(0, testToRead.getBooksOnShelf().size());

            checkBookshelf(shelves.get(2), testCompleted.getBookshelfLabel());
            assertEquals(0, testCompleted.getBooksOnShelf().size());


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReaderTest.testReaderGeneralWorkRoom)
    @Test
    void testReaderExampleBookRoomDuplicateObjects() throws DuplicateBookshelfNameException {
        JsonReader reader = new JsonReader("./data/testReaderExampleBookRoom.json");
        try {
            BookRoom br = reader.read();
            testAllBooks.addBookToShelf(testBookGOT);
            testAllBooks.addBookToShelf(testBookHP);
            testToRead.addBookToShelf(testBookHP);
            testFavourites.addBookToShelf(testBookGOT);

            assertEquals("My Book Room", br.getName());
            List<Bookshelf> shelves = br.getShelves();
            assertEquals(4, shelves.size());

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
            assertEquals(0, testCompleted.getBooksOnShelf().size());

            checkBookshelf(shelves.get(3), testFavourites.getBookshelfLabel());
            assertEquals(1, testFavourites.getBooksOnShelf().size());
            checkBook(shelves.get(3).getBooksOnShelf().get(0), testBookGOT.getTitle(), testBookGOT.getAuthor(),
                    testBookGOT.getGenre(), testBookGOT.getRating(), testBookGOT.getReview());

            assertEquals(null, reader.getBookFromTitle("ABC"));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}