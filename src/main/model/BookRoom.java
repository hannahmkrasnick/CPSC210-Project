package model;

import exceptions.DuplicateBookshelfNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents book room with a name and list of bookshelves
public class BookRoom implements Writable {
    private final String name;
    private final List<Bookshelf> shelves;

    //EFFECTS: creates a room with given name and empty list of bookshelves, if initialized with empty string, name is
    //         changed to "My Book Room"
    public BookRoom(String name) {
        if (name.equals("")) {
            this.name = "My Book Room";
        } else {
            this.name = name;
        }
        shelves = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds shelf to this, throws DuplicateBookshelfNameException if bookshelf with same name already exists in
    //         book room
    public void addShelfToRoom(Bookshelf bookshelf) throws DuplicateBookshelfNameException {
        if (this.shelves.contains(bookshelf)) {
            throw new DuplicateBookshelfNameException("Cannot add bookshelf with duplicate name.");
        } else {
            this.shelves.add(bookshelf);
        }
    }

    //MODIFIES: this
    //EFFECTS: removes shelf from this
    public void deleteShelfFromRoom(Bookshelf bookshelf) {
        if (this.shelves.contains(bookshelf)) {
            this.shelves.remove(bookshelf);
        }
    }

    //EFFECTS: checks if the given label isn't already the label of an existing bookshelf in the room
    public boolean checkBookshelfDoesNotAlreadyExist(String label) {
        label = label.toLowerCase();
        for (Bookshelf b : this.getShelves()) {
            if (b.getBookshelfLabel().toLowerCase().equals(label)) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: returns false if a book with same title is already in room, else returns true
    public boolean checkBookDoesNotAlreadyExist(String title) {
        title = title.toLowerCase();
        for (Bookshelf b : this.getShelves()) {
            for (Book book : b.getBooksOnShelf()) {
                if (book.getTitle().toLowerCase().equals(title)) {
                    return false;
                }
            }
        }
        return true;
    }

    //getters
    public String getName() {
        return name;
    }

    public List<Bookshelf> getShelves() {
        return shelves;
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        for (Bookshelf bs : shelves) {
            if (bs.getBookshelfLabel().equals("All Books")) {
                books.addAll(bs.getBooksOnShelf());
            }
        }
        return books;
    }

    //MODIFIES: this, book
    //EFFECTS: deletes book from room by deleting it from every shelf it's on
    public void deleteBookFromBookRoom(Book book) {
        for (Bookshelf bs: shelves) {
            for (Book b : bs.getBooksOnShelf()) {
                if (b.getTitle().equalsIgnoreCase(book.getTitle())) {
                    bs.removeBookFromShelf(b);
                    break;
                }
            }
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoom.toJson)
    // EFFECTS: returns JSON object representation of bookroom
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("bookshelves", bookshelvesToJson());
        return json;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoom.thingiesToJson)
    // EFFECTS: returns shelves in this bookroom as a JSON array
    private JSONArray bookshelvesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bookshelf b : shelves) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}
