package model;

// Represents all genres a book can be assigned to
public enum Genre {
    BOARD,
    CLASSIC,
    COMIC,
    COOKBOOK,
    EASYCHAPTER,
    FANTASY,
    FICTION,
    GRAPHIC,
    MIDDLEGRADE,
    NONFICTION,
    PICTUREBOOK,
    READER,
    REFERENCE,
    SCIENCEFICTION,
    SELFHELP,
    TEXTBOOK,
    UNCLASSIFIED,
    YOUNGADULT;

    // EFFECTS: checks if genre is valid (i.e. it exists)
    public static boolean checkGenreExists(String str) {
        for (Genre me : Genre.values()) {
            if (me.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns genre that matches the string, or UNCLASSIFIED if invalid string
    public static Genre getGenreFromString(String string) {
        if (!checkGenreExists(string)) {
            return Genre.UNCLASSIFIED;
        } else {
            return Genre.valueOf(string.toUpperCase());
        }
    }
}
