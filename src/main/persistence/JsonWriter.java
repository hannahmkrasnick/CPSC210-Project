package persistence;

import model.BookRoom;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of bookroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriter.JsonWriter)
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriter.open)
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriter.write)
    // MODIFIES: this
    // EFFECTS: writes JSON representation of bookroom to file
    public void write(BookRoom bookroom) {
        JSONObject json = bookroom.toJson();
        saveToFile(json.toString(TAB));
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriter.close)
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (JsonWriter.saveToFile)
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}