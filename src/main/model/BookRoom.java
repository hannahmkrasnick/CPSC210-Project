package model;

import java.util.ArrayList;
import java.util.List;

public class BookRoom {
    private String username;
    private List<Bookshelf> shelves;

    public BookRoom(String username) {
        this.username = username;
        shelves = new ArrayList<>();
    }

    public void addNewShelf(Bookshelf bookshelf) {
        this.shelves.add(bookshelf);
    }
}
