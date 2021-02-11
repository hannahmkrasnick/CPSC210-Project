package model;


import java.util.Date;

public class Book {
    private String title;
    private String author;
    private Date startDate;
    private Date endDate;
    private int rating;
    private String review;
    private Genre genre;
    private long dateAdded;


    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public Book(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        rating = 0;
        review = "";
        startDate = null;
        endDate = null;
        dateAdded = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
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

    public long getDateAdded() {
        return dateAdded;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
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

