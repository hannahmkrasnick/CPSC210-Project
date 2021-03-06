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
    //EFFECTS: creates a book with given title (max 20 characters), author (max 25 characters, genre, rating,
    // and review (max 500 characters)
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

    //EFFECTS: return true if rating is between 1 and 10, else false
    public static boolean checkRatingIsValid(int rating) {
        return rating >= 1 && rating <= 10;
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

    //MODIFIES: this
    //EFFECTS: sets all book fields to given parameters
    public void setAllBookFields(String title, String author, Genre genre, int rating, String review) {
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setRating(rating);
        setReview(review);
    }

    //EFFECTS: returns a string trimmed to 20 characters or less
    public static String makeTitleRightLengthForGui(String title) {
        if (title.length() > 20) {
            return title.substring(0, 20);
        } else {
            return title;
        }
    }

    //EFFECTS: returns a string trimmed to 25 characters or less
    public static String makeAuthorRightLengthForGui(String author) {
        if (author.length() > 25) {
            return author.substring(0, 25);
        } else {
            return author;
        }
    }

    //EFFECTS: converts string to valid rating int for book, return -1 if ratingString not between 1 and 10
    public static int makeRatingInt(String ratingString) {
        int rating;
        try {
            rating = Integer.parseInt(ratingString);
            if (!checkRatingIsValid(rating)) {
                return -1;
            }
        } catch (NumberFormatException nfe) {
            return -1;
        }
        return rating;
    }

    //EFFECTS: returns a string trimmed to 400 characters or less
    public static String makeReviewRightLengthForGui(String review) {
        if (review.length() > 400) {
            return review.substring(0, 400);
        } else {
            return review;
        }
    }

    //EFFECTS: compares if books are equal based on their title
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return title.equalsIgnoreCase(book.title);
    }

    //EFFECTS: initiates a book's hash code as based on their title
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

