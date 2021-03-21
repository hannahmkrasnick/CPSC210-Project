package model;

// Represents all genres a book can be assigned to
public enum Genre {
    BIOGRAPHY,
    BOARD,
    CLASSIC,
    COMIC,
    COOK_BOOK,
    EASY_CHAPTER,
    FANTASY,
    FICTION,
    GRAPHIC,
    INDIGENOUS,
    MIDDLE_GRADE,
    NON_FICTION,
    PICTURE_BOOK,
    READER,
    REFERENCE,
    SCIENCE_FICTION,
    SELFHELP,
    TEXTBOOK,
    UNCLASSIFIED,
    YOUNG_ADULT;

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

    // solution adapted from:
    // https://stackoverflow.com/questions/1086123/is-there-a-method-for-string-conversion-to-title-case
    // EFFECTS: converts a genre into a capitalized string
    public static String convertGenreToReadableString(Genre genre) {
        String input = String.valueOf(genre).toLowerCase();
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
