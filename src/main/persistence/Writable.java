package persistence;

import org.json.JSONObject;

public interface Writable {
    // solution adapted from JsonSerializationDemo CPSC 210 program (Writable.toJson)
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
