package model;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Book {
    private String title;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rating;
    private String review;
    private Genre genre;
    private LocalDate dateAdded;


    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public Book(String title) {
        this.title = title;
        this.author = null;
        genre = null;
        rating = 0;
        review = null;
        startDate = null;
        endDate = null;
        dateAdded = LocalDate.now();
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

