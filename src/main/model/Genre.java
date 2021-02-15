package model;

// Represents all genres a book can be assigned to
public enum Genre {
    CLASSIC,
    COOKBOOK,
    EASYCHAPTER,
    FICTION,
    MIDDLEGRADE,
    NONFICTION,
    PICTUREBOOK,
    READER,
    REFERENCE,
    TEXTBOOK,
    YOUNGADULT,
    GRAPHIC,
    COMIC,
    BOARD,
    UNCLASSIFIED;

    // EFFECTS: checks if genre is valid (i.e. it exists)
    public static boolean checkGenreExists(String str) {
        for (Genre me : Genre.values()) {
            if (me.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
