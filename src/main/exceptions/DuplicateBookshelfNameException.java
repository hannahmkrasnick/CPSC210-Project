package exceptions;

//Exception thrown when bookshelf with same name as one already in BookRoom is added
public class DuplicateBookshelfNameException extends Exception {

    public DuplicateBookshelfNameException(String msg) {
        super(msg);
    }
}
