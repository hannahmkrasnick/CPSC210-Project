package model;

// Represents all genres a book can be assigned to
public enum Genre {
    BIOGRAPHY("Biography"),
    BOARD("Board"),
    CLASSIC("Classic"),
    COMIC("Comic"),
    COOKBOOK("Cookbook"),
    EASYCHAPTER("Easy chapter"),
    FANTASY("Fantasy"),
    FICTION("Fiction"),
    GRAPHIC("Graphic"),
    HISTORICALFICTION("Historical fiction"),
    INDIGENOUS("Indigenous"),
    MIDDLEGRADE("Middle-grade"),
    NONFICTION("Non-fiction"),
    PICTUREBOOK("Picture book"),
    READER("Reader"),
    REFERENCE("Reference"),
    ROMANCE("Romance"),
    SCIENCEFICTION("Science fiction"),
    SELFHELP("Selfhelp"),
    TEXTBOOK("Textbook"),
    UNCLASSIFIED("Unclassified"),
    YOUNGADULT("Young adult");

    private String genreString;

    //Solution adapted from https://www.java67.com/2012/08/how-to-convert-enum-to-string-in-java.html
    //EFFECTS: initiates a string associated with each genre enum
    Genre(String genreString) {
        this.genreString = genreString;
    }

    //EFFECTS: returns genreString
    public String getString() {
        return genreString;
    }

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
        string = string.replaceAll("\\s", "");
        if (!checkGenreExists(string)) {
            return Genre.UNCLASSIFIED;
        } else {
            return Genre.valueOf(string.toUpperCase());
        }
    }
}
