package model;

import java.util.ArrayList;
import java.util.List;

public class Bookshelf {
    private String bookshelfLabel;
    private List<Book> books;

    public Bookshelf(String label) {
        this.bookshelfLabel = label;
        books = new ArrayList<>();
    }

    public void addBookToShelf(Book book) {
        this.books.add(book);
    }

    public void removeBookFromShelf(Book book) {
        this.books.remove(book);
    }

    public String getBookshelfLabel() {
        return bookshelfLabel;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBookshelfLabel(String bookshelfLabel) {
        this.bookshelfLabel = bookshelfLabel;
    }
}
