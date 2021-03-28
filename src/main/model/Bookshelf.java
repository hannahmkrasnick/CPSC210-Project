package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents an bookshelf with a label and a list of books
public class Bookshelf implements Writable {
    private String bookshelfLabel;
    private final List<Book> booksOnShelf;

    //REQUIRES: label with non-zero length
    //EFFECTS: created a bookshelf with given label and empty list of books
    public Bookshelf(String label) {
        this.bookshelfLabel = label;
        booksOnShelf = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds book to shelf's list of books if not already there
    public void addBookToShelf(Book book) {
        if (checkBookIsNotAlreadyOnShelf(book)) {
            this.booksOnShelf.add(book);
        }
    }

    //EFFECTS: return true if book is not shelf, else false
    public boolean checkBookIsNotAlreadyOnShelf(Book book) {
        for (Book b : booksOnShelf) {
            if (b.getTitle().equalsIgnoreCase(book.getTitle())) {
                return false;
            }
        }
        return true;
    }

    //MODIFIES: this
    //EFFECTS: removes book from shelf's list of books if book is on shelf
    public void removeBookFromShelf(Book book) {
        if (!checkBookIsNotAlreadyOnShelf(book)) {
            this.booksOnShelf.remove(book);
        }
    }

    //getters
    public String getBookshelfLabel() {
        return bookshelfLabel;
    }

    public List<Book> getBooksOnShelf() {
        return booksOnShelf;
    }

    //setters
    public void setBookshelfLabel(String label) {
        this.bookshelfLabel = label;
    }

    //EFFECTS: trims string to 13 characters or less
    public static String makeLabelRightLengthForGui(String label) {
        if (label.length() > 13) {
            return label.substring(0, 13);
        } else {
            return label;
        }
    }

    //EFFECTS: compares if bookshelves are equal based on their labels
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookshelf)) {
            return false;
        }
        Bookshelf bookshelf = (Bookshelf) o;
        return bookshelfLabel.equalsIgnoreCase(bookshelf.bookshelfLabel);
    }

    //EFFECTS: initiates a bookshelf's hash code as based on their label
    @Override
    public int hashCode() {
        return Objects.hash(bookshelfLabel);
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (Thingy.toJson)
    // EFFECTS: returns JSON object representation of bookshelf
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", bookshelfLabel);
        json.put("books", booksToJson());
        return json;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoom.thingiesToJson)
    // EFFECTS: returns books on this bookshelf as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : booksOnShelf) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
