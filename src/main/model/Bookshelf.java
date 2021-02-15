package model;

import java.util.ArrayList;
import java.util.List;

// Represents an bookshelf with a label and a list of books
public class Bookshelf {
    private String bookshelfLabel;
    private List<Book> books;

    //REQUIRES: label with non-zero length
    //EFFECTS: created a bookshelf with given label and empty list of books
    public Bookshelf(String label) {
        this.bookshelfLabel = label;
        books = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds book to shelf's list of books
    public void addBookToShelf(Book book) {
        this.books.add(book);
    }

    //REQUIRES: book must be on shelf
    //MODIFIES: this
    //EFFECTS: removes book from shelf's list of books
    public void removeBookFromShelf(Book book) {
        this.books.remove(book);
    }

    //getters
    public String getBookshelfLabel() {
        return bookshelfLabel;
    }

    public List<Book> getBooks() {
        return books;
    }

    //setters
    public void setBookshelfLabel(String bookshelfLabel) {
        this.bookshelfLabel = bookshelfLabel;
    }
}
