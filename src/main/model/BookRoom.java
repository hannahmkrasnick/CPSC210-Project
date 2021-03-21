package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents book room with a name and list of bookshelves
public class BookRoom implements Writable {
    private final String name;
    private final List<Bookshelf> shelves;

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

    //EFFECTS: checks if a book with same title is already in room
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
