package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a book having a title, author, genre, rating, and review
public class Book implements Writable {
    private String title;
    private String author;
    private int rating;
    private String review;
    private Genre genre;


    //REQUIRES: book title has non-zero length
    //EFFECTS: creates a book with given title, empty string for title and review, genre set to unclassified, and rating
    //         initialized at -1
    public Book(String title) {
        this.title = title;
        this.author = "";
        genre = Genre.UNCLASSIFIED;
        rating = -1;
        review = "";
    }

    //REQUIRES: none of the parameters are null
    //EFFECTS: creates a book with given title, author, genre, rating, and review
    public Book(String title, String author, Genre genre, int rating, String review) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
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

    public static boolean checkRatingIsValid(int rating) {
        if (rating >= 1 && rating <= 10) {
            return true;
        }
        return false;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (Thingy.toJson)
    // EFFECTS: returns JSON object representation of book
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        json.put("review", review);
        json.put("genre", genre);
        return json;
    }

    //TODO
    public void setAllBookFields(String title, String author, int rating, String review, Genre genre) {
        setTitle(title);
        setAuthor(author);
        setRating(rating);
        setReview(review);
        setGenre(genre);
    }
}

