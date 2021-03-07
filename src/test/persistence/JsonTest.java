package persistence;

import model.Book;
import model.Bookshelf;
import model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonTest.checkThingy)
    protected void checkBookshelf(Bookshelf bs, String label) {
        assertEquals(label, bs.getBookshelfLabel());
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonTest.checkThingy)
    protected void checkBook(Book book, String title, String author, Genre genre, int rating, String review) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(rating, book.getRating());
        assertEquals(review, book.getReview());
    }
}
