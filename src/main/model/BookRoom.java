package model;

import java.util.ArrayList;
import java.util.List;

// Represents book room with a name and list of bookshelves
public class BookRoom {
    private String name;
    private List<Bookshelf> shelves;

    //REQUIRES: book room name has non-zero length
    //EFFECTS: creates a room with given name and empty list of bookshelves
    public BookRoom(String name) {
        this.name = name;
        shelves = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds shelf to this
    public void addShelfToRoom(Bookshelf bookshelf) {
        this.shelves.add(bookshelf);
    }

    //REQUIRES: shelf must be in book room
    //MODIFIES: this
    //EFFECTS: removes shelf from this
    public void deleteShelfFromRoom(Bookshelf bookshelf) {
        this.shelves.remove(bookshelf);
    }

    //getters
    public String getName() {
        return name;
    }

    public List<Bookshelf> getShelves() {
        return shelves;
    }
}
