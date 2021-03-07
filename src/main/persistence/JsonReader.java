package persistence;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads bookroom from JSON data stored in file
public class JsonReader {
    private final String source;
    private final List<Book> booksInRoom;

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.JsonReader)
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        booksInRoom = new ArrayList<>();
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.read)
    // EFFECTS: reads bookroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookRoom(jsonObject);
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.readFile)
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.parseWorkRoom)
    // EFFECTS: parses bookroom from JSON object and returns it
    private BookRoom parseBookRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BookRoom br = new BookRoom(name);
        addShelves(br, jsonObject);
        return br;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.addThingies)
    // MODIFIES: br
    // EFFECTS: parses shelves from JSON object and adds them to bookroom
    private void addShelves(BookRoom br, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bookshelves");
        for (Object json : jsonArray) {
            JSONObject nextShelf = (JSONObject) json;
            addShelf(br, nextShelf);
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.addThingy)
    // MODIFIES: br
    // EFFECTS: parses shelf from JSON object and adds it to bookroom
    private void addShelf(BookRoom br, JSONObject jsonObject) {
        String label = jsonObject.getString("label");
        Bookshelf bookshelf = new Bookshelf(label);
        addBooks(bookshelf, jsonObject);
        br.addShelfToRoom(bookshelf);
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.addThingies)
    // MODIFIES: bs
    // EFFECTS: parses books from JSON object and adds them to bookshelf
    private void addBooks(Bookshelf bs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bs, nextBook);
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonReader.addThingy)
    // MODIFIES: bs
    // EFFECTS: parses book from JSON object and adds it to bookshelf
    private void addBook(Bookshelf bs, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        Genre genre = Genre.valueOf(jsonObject.getString("genre"));
        int rating = jsonObject.getInt("rating");
        String review = jsonObject.getString("review");
        Book book = new Book(title, author, genre, rating, review);
        if (bookObjectDoesntExistAlready(book)) {
            bs.addBookToShelf(book);
            booksInRoom.add(book);
        } else {
            bs.addBookToShelf(getBookFromTitle(book.getTitle()));
        }
    }

    // EFFECTS: true if book object has not been created yet, else false
    private boolean bookObjectDoesntExistAlready(Book book) {
        for (Book b : booksInRoom) {
            if (b.getTitle().equals(book.getTitle())) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: given a title, finds the book in bookroom with that title and returns it, or null if book doesn't exist
    public Book getBookFromTitle(String title) {
        for (Book b : booksInRoom) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }
}

