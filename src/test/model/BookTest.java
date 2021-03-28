package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book testBook1;
    private Book testBook2;
    private Book testBook3;

    @BeforeEach
    void runBefore() {
        testBook1 = new Book("ABC");
        testBook2 = new Book("DEF", "Author name", Genre.COMIC, 8, "Pretty good");
        testBook3 = new Book("ABC");
    }

    @Test
    void testConstructorJustTitle() {
        assertEquals("ABC", testBook1.getTitle());
        assertEquals("", testBook1.getAuthor());
        assertEquals(Genre.UNCLASSIFIED, testBook1.getGenre());
        assertEquals(-1, testBook1.getRating());
        assertEquals("", testBook1.getReview());
    }

    @Test
    void testConstructorMoreParam() {
        assertEquals("DEF", testBook2.getTitle());
        assertEquals("Author name", testBook2.getAuthor());
        assertEquals(Genre.COMIC, testBook2.getGenre());
        assertEquals(8, testBook2.getRating());
        assertEquals("Pretty good", testBook2.getReview());
    }

    @Test
    void testChangeTitle() {
        testBook1.setTitle("NEW");
        assertEquals("NEW", testBook1.getTitle());
    }

    @Test
    void testChangeAuthor() {
        testBook1.setAuthor("NEW");
        assertEquals("NEW", testBook1.getAuthor());
    }

    @Test
    void testChangeRating() {
        testBook1.setRating(5);
        assertEquals(5, testBook1.getRating());
    }

    @Test
    void testChangeReview() {
        testBook1.setReview("This was a good book!");
        assertEquals("This was a good book!", testBook1.getReview());
    }

    @Test
    void testChangeGenre() {
        testBook1.setGenre(Genre.COMIC);
        assertEquals(Genre.COMIC, testBook1.getGenre());
    }

    @Test
    void testCheckRatingIsValid() {
        assertTrue(Book.checkRatingIsValid(1));
        assertTrue(Book.checkRatingIsValid(10));

        assertFalse(Book.checkRatingIsValid(0));
        assertFalse(Book.checkRatingIsValid(11));
    }

    @Test
    void testSetAllBookFields() {
        String title = "DEF";
        String author = "Author name";
        Genre genre = Genre.COMIC;
        int rating = 8;
        String review = "Pretty good";
        Book book = new Book("ABC");
        book.setAllBookFields(title, author, genre, rating, review);

        assertEquals(new Book("DEF"), book);
    }

    @Test
    void testMakeTitleRightLengthForGui() {
        assertEquals("01234567890123456789", testBook1.makeTitleRightLengthForGui("01234567890123456789"));
        assertEquals("01234567890123456789", testBook1.makeTitleRightLengthForGui("01234567890123456789012"));
    }

    @Test
    void testMakeAuthorRightLengthForGui() {
        assertEquals("0123456789012345678901234",
                testBook1.makeAuthorRightLengthForGui("0123456789012345678901234"));
        assertEquals("0123456789012345678901234",
                testBook1.makeAuthorRightLengthForGui("012345678901234567890123456789"));
    }

    @Test
    void testMakeReviewRightLengthForGui() {
        assertEquals("01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789",
                testBook1.makeReviewRightLengthForGui("01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"));
        assertEquals("01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789",
                testBook1.makeReviewRightLengthForGui("01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"
                        + "01234567890123456789012345678901234567890123456789"));
    }

    @Test
    void testMakeRatingInt() {
        assertEquals(1, testBook1.makeRatingInt("1"));
        assertEquals(10, testBook1.makeRatingInt("10"));
        assertEquals(-1, testBook1.makeRatingInt("0"));
        assertEquals(-1, testBook1.makeRatingInt("11"));
        assertEquals(-1, testBook1.makeRatingInt("hello"));
    }

    @Test
    void testHashCodeAndEquals() {
        assertFalse(testBook1.equals(testBook2));
        assertEquals(testBook3, testBook1);
        assertFalse(testBook1.equals("test"));
        assertEquals(testBook3.hashCode(), testBook1.hashCode());
    }
}