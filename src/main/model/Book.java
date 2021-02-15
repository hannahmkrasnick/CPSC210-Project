package model;

// Represents a book having a title, author, genre, rating, and review
public class Book {
    private String title;
    private String author;
    private int rating;
    private String review;
    private Genre genre;


    //REQUIRES: book title has non-zero length
    //EFFECTS: creates a book with given title, empty string for title and review, genre set to unclassified, and rating
    //         initialized at 0
    public Book(String title) {
        this.title = title;
        this.author = "";
        genre = Genre.UNCLASSIFIED;
        rating = 0;
        review = "";
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
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

