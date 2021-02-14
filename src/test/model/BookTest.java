package model;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearMonthDV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.NullType;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.YEAR;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Book("ABC");
    }

    @Test
    void testConstructor() {
        assertEquals("ABC", testBook.getTitle());
        assertTrue(testBook.getAuthor()==null);
        assertTrue(testBook.getGenre()==null);
        assertEquals(0, testBook.getRating());
        assertTrue(testBook.getReview()==null);
        assertTrue(testBook.getStartDate()==null);
        assertTrue(testBook.getEndDate()==null);
        assertFalse(testBook.getDateAdded()==null);
    }

    @Test
    void testChangeTitle() {
        testBook.setTitle("NEW");
        assertEquals("NEW", testBook.getTitle());
    }

    @Test
    void testChangeAuthor() {
        testBook.setAuthor("NEW");
        assertEquals("NEW", testBook.getAuthor());
    }

    @Test
    void testChangeStartDate() {
        testBook.setStartDate(LocalDate.of(2021, 2, 13));
        assertEquals(LocalDate.of(2021, 2, 13), testBook.getStartDate());
    }

    @Test
    void testChangeEndDate() {
        testBook.setEndDate(LocalDate.of(2021, 2, 13));
        assertEquals(LocalDate.of(2021, 2, 13), testBook.getEndDate());
    }

    @Test
    void testChangeRating() {
        testBook.setRating(5);
        assertEquals(5, testBook.getRating());
    }

    @Test
    void testChangeReview() {
        testBook.setReview("This was a good book!");
        assertEquals("This was a good book!", testBook.getReview());
    }

    @Test
    void testChangeGenre() {
        testBook.setGenre(Genre.COMIC);
        assertEquals(Genre.COMIC, testBook.getGenre());
    }
}