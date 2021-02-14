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

    public void addShelfToRoom(Bookshelf bookshelf) {
        this.shelves.add(bookshelf);
    }

    public String getUsername() {
        return username;
    }

    public List<Bookshelf> getShelves() {
        return shelves;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
