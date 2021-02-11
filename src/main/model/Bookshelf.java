package model;

import java.util.ArrayList;
import java.util.List;

public class Bookshelf {
    private String bookshelfLabel;
    private List<Book> books;

    public Bookshelf(String name) {
        this.bookshelfLabel = name;
        books = new ArrayList<>();
    }

    public void addToShelf(Book bk) {
        this.books.add(bk);
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
