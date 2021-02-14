package ui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;

import java.util.Scanner;

public class MyBookRoom {
    private BookRoom bookRoom;
    private Scanner input;
    private Bookshelf allBooks;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public MyBookRoom() {
        runBookRoom();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void runBookRoom() {
        boolean open = true;
        String command;

        init();

        while (open) {
            displayMenu();
            command = input.next();
            command += input.nextLine();
            command = command.toLowerCase();

            if (command.equals("e")) {
                open = false;
            } else {
                processCommand(command);
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void init() {
        Bookshelf toRead;
        Bookshelf completed;

        bookRoom = new BookRoom("My Book Room");
        allBooks = new Bookshelf("All Books");
        toRead = new Bookshelf("To Read");
        completed = new Bookshelf("Completed");
        bookRoom.addShelfToRoom(allBooks);
        bookRoom.addShelfToRoom(toRead);
        bookRoom.addShelfToRoom(completed);
        input = new Scanner(System.in);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tn: Add a new book to my room");
        System.out.println("\tv: View information about a book");
        System.out.println("\tb: View my bookshelves");
        System.out.println("\tr: View my Book Room");
        System.out.println("\te: Exit");
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void processCommand(String command) {
        switch (command) {
            case "n":
                addNewBook();
                break;
            case "v":
                chooseBookToView();
                break;
            case "b":
                viewBookshelves();
                break;
            case "r":
                viewBookRoom();
                break;
            default:
                System.out.print("Please enter a valid input.");
                break;
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void addNewBook() {
        System.out.print("Enter title of your book: ");
        String title = input.next();
        title += input.nextLine();
        if (checkBookDoesntAlreadyExist(title)) {
            Book newBook = new Book(title);
            allBooks.addBookToShelf(newBook);
            System.out.println(title + " has been added to your Book Room");
            editBookInfo(newBook);
        } else {
            System.out.println("Cannot add book with duplicate title to Book Room. Please enter a valid name");
            addNewBook();
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private boolean checkBookDoesntAlreadyExist(String title) {
        title = title.toLowerCase();
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book book : b.getBooks()) {
                if (book.getTitle().toLowerCase().equals(title)) {
                    return false;
                }
            }
        }
        return true;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void addNewShelf() {
        System.out.println("What would you like to call your new bookshelf?");
        String label = input.next();
        label += input.nextLine();
        if (checkBookshelfDoesntAlreadyExist(label)) {
            Bookshelf newShelf = new Bookshelf(label);
            bookRoom.addShelfToRoom(newShelf);
            System.out.println("Bookshelf " + label + " has been added to your Book Room");
        } else {
            System.out.println("Please enter the name of a bookshelf that doesn't already exist.");
            addNewShelf();
        }
    }

    //solution adapted from: https://stackoverflow.com/questions/3779514/java-for-loop-and-if-algorithm
    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void chooseBookToView() {
        viewAllBooks();
        System.out.print("Enter name of book you'd like to view: ");
        String bookName = input.next();
        bookName += input.nextLine();
        bookName = bookName.toLowerCase();
        int foundAtSpace = -1;
        int tracker = 0;
        for (Book book : allBooks.getBooks()) {
            tracker++;
            if (book.getTitle().toLowerCase().equals(bookName)) {
                foundAtSpace = tracker;
                viewBookInfo(book);
                editBookInfo(book);
                break;
            }
        }
        if (foundAtSpace < 0) {
            System.out.println("Invalid name.");
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewAllBooks() {
        System.out.println("Your books:");
        for (Book book : allBooks.getBooks()) {
            System.out.println(book.getTitle());
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewBookInfo(Book book) {
        System.out.println("\nBook info:");
        System.out.println("\tTitle: " + book.getTitle());
        System.out.println("\tAuthor: " + book.getAuthor());
        System.out.println("\tGenre: " + book.getGenre());
        System.out.println("\tRating: " + book.getRating());
        System.out.println("\tReview: " + book.getReview());
        viewShelvesBookIsOn(book);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookInfo(Book book) {
        System.out.print("Would you like to add/edit information about this book? Enter y or n: ");
        String answer = input.next();
        answer = answer.toLowerCase();
        if (answer.equals("y")) {
            viewBookInfo(book);
            System.out.println("\nWhat information would you like to edit? "
                    + "Enter one of: title, author, genre, rating, review, bookshelves, delete");
            processEditBookCommand(book);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void processEditBookCommand(Book book) {
        String editCommand = input.next();
        editCommand = editCommand.toLowerCase();
        switch (editCommand) {
            case "title": editBookTitle(book);
                break;
            case "author": editBookAuthor(book);
                break;
            case "genre": editBookGenre(book);
                break;
            case "rating": editBookRating(book);
                break;
            case "review": editBookReview(book);
                break;
            case "bookshelves": moveBookAroundShelves(book);
                break;
            case "delete": deleteBookFromRoom(book);
                break;
            default:
                System.out.print("Please enter a valid input.");
                processEditBookCommand(book);
                break;
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void moveBookAroundShelves(Book book) {
        viewShelvesBookIsOn(book);
        System.out.print("Would you like to remove or add " + book.getTitle()
                + " from/to a shelf? Enter remove or add: ");
        String response = input.next();
        response = response.toLowerCase();
        if (response.equals("remove")) {
            removeBookFromShelf(book);
        } else if (response.equals("add")) {
            addBookToShelf(book);
        }
    }

    //solution adapted from: https://stackoverflow.com/questions/3779514/java-for-loop-and-if-algorithm
    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void addBookToShelf(Book book) {
        System.out.println("You have the following bookshelves in your Book Room:");
        for (Bookshelf bookshelf : bookRoom.getShelves()) {
            System.out.println(bookshelf.getBookshelfLabel());
        }
        System.out.println("Enter the name of the bookshelf you would like to add " + book.getTitle() + " to");
        String addShelf = input.next();
        addShelf += input.nextLine();
        addShelf = addShelf.toLowerCase();
        int foundAtSpace = -1;
        int tracker = 0;
        for (Bookshelf bookshelf : bookRoom.getShelves()) {
            tracker++;
            if (bookshelf.getBookshelfLabel().toLowerCase().equals(addShelf)) {
                foundAtSpace = tracker;
                bookshelf.addBookToShelf(book);
                System.out.println(book.getTitle() + " has been added to " + bookshelf.getBookshelfLabel());
                break;
            }
        }
        if (foundAtSpace < 0) {
            System.out.println("Invalid name.");
            addBookToShelf(book);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void removeBookFromShelf(Book book) {
        System.out.println("Enter the name of the bookshelf you would like to remove " + book.getTitle() + " from");
        String removeShelf = input.next();
        removeShelf += input.nextLine();
        removeShelf = removeShelf.toLowerCase();
        if (removeShelf.equals("all books")) {
            System.out.println("If you remove " + book.getTitle()
                    + " from All Books it will be deleted from your Book Room. Enter y to proceed: ");
            String answer = input.next();
            if (answer.equals("y")) {
                deleteBookFromRoom(book);
            }
        } else {
            for (Bookshelf bookshelf : bookRoom.getShelves()) {
                if (bookshelf.getBookshelfLabel().toLowerCase().equals(removeShelf)) {
                    bookshelf.removeBookFromShelf(book);
                    System.out.println(book.getTitle() + " has been removed from " + bookshelf.getBookshelfLabel());
                }
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void deleteBookFromRoom(Book book) {
        allBooks.removeBookFromShelf(book);
        for (Bookshelf bookshelf: bookRoom.getShelves()) {
            for (Book b : bookshelf.getBooks()) {
                if (b.getTitle().equals(book.getTitle())) {
                    bookshelf.removeBookFromShelf(book);
                }
            }
        }
        System.out.println(book.getTitle() + " has been deleted from your Book Room.");
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewShelvesBookIsOn(Book book) {
        System.out.println(book.getTitle() + " is on the following shelves:");
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book c : b.getBooks()) {
                if (c.getTitle().toLowerCase().equals(book.getTitle())) {
                    System.out.println(b.getBookshelfLabel());
                }
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookTitle(Book book) {
        System.out.print("Enter title of book: ");
        String title = input.next();
        title += input.nextLine();
        if (checkBookDoesntAlreadyExist(title)) {
            book.setTitle(title);
            System.out.println("Title of book has been set to " + title);
            editBookInfo(book);
        } else {
            System.out.println("Cannot add book with duplicate title to Book Room. Please enter a valid name");
            editBookTitle(book);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookAuthor(Book book) {
        System.out.print("Enter name of author: ");
        String name = input.next();
        name += input.nextLine();
        book.setAuthor(name);
        System.out.println("Author of " + book.getTitle() + " has been set to " + name);
        editBookInfo(book);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookGenre(Book book) {
        System.out.println("\nGenre can be one of: ");
        for (Genre genre : Genre.values()) {
            System.out.println(genre);
        }
        System.out.print("Enter genre: ");
        String newGenre = input.next();
        newGenre = newGenre.toUpperCase();
        if (checkGenreExists(newGenre)) {
            book.setGenre(Genre.valueOf(newGenre));
            System.out.println("Genre of " + book.getTitle() + " has been set to " + newGenre);
            editBookInfo(book);
        } else {
            System.out.println("Please input valid genre.");
            editBookGenre(book);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public boolean checkGenreExists(String str) {
        for (Genre me : Genre.values()) {
            if (me.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookRating(Book book) {
        System.out.print("Enter rating value between 1 and 10: ");
        String stringRating = input.next();
        int rating = Integer.parseInt(stringRating);
        if (rating >= 1 && rating <= 10) {
            book.setRating(rating);
            System.out.println("Rating of " + book.getTitle() + " has been set to " + rating);
            editBookInfo(book);
        } else {
            System.out.println("Input invalid.");
            editBookRating(book);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookReview(Book book) {
        System.out.print("Enter your review of this book: ");
        String review = input.next();
        review += input.nextLine();
        book.setReview(review);
        System.out.println("Review of " + book.getTitle() + " has been changed");
        editBookInfo(book);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewBookshelves() {
        System.out.println("Your bookshelves: ");
        for (Bookshelf b : bookRoom.getShelves()) {
            System.out.println("\t" + b.getBookshelfLabel());
        }
        editBookshelves();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookshelves() {
        System.out.println("Would you like to edit your bookshelves? Enter y or n.");
        String answer = input.next();
        if (answer.equals("y")) {
            System.out.println("Would you like to edit your existing bookshelves or add a new one? Enter edit or new.");
            String response = input.next();
            response += input.nextLine();
            if (response.equals("new")) {
                addNewShelf();
            } else if (response.equals("edit")) {
                editBookshelf();
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookshelf() {
        System.out.println("Enter the name of the bookshelf you would like to edit: ");
        String shelfName = input.next();
        shelfName += input.nextLine();
        for (Bookshelf b : bookRoom.getShelves()) {
            if (b.getBookshelfLabel().toLowerCase().equals(shelfName)) {
                System.out.println("Enter 'e' to edit bookshelf name or 'b' to add/remove books on this shelf");
                String command = input.next();
                switch (command) {
                    case "e":
                        editBookshelfName(b);
                        break;
                    case "b":
                        editBooksOnShelf(b);
                        break;
                }
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBookshelfName(Bookshelf bookshelf) {
        System.out.print("Enter new name for bookshelf: ");
        String response = input.next();
        response += input.nextLine();
        if (checkBookshelfDoesntAlreadyExist(response)) {
            bookshelf.setBookshelfLabel(response);
            System.out.println("Bookshelf name has been changed to " + response);
        } else {
            System.out.println("Please choose a bookshelf name that doesn't already exist in your Book Room.");
            editBookshelfName(bookshelf);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void editBooksOnShelf(Bookshelf bookshelf) {
        viewAllBooksOnShelf(bookshelf);
        System.out.print("Would you like to remove or add books to this shelf? Enter remove or add: ");
        String response = input.next();
        response += input.nextLine();
        if (response.equals("remove")) {
            removeBookFromGivenShelf(bookshelf);
        } else if (response.equals("add")) {
            addBookToGivenShelf(bookshelf);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void addBookToGivenShelf(Bookshelf bookshelf) {
        viewAllBooks();
        System.out.print("Enter name of the book you would like to add to " + bookshelf.getBookshelfLabel() + ": ");
        String title = input.next();
        title += input.nextLine();
        title = title.toLowerCase();
        int foundAtSpace = -1;
        int tracker = 0;
        for (Book book : allBooks.getBooks()) {
            tracker++;
            if (book.getTitle().toLowerCase().equals(title)) {
                foundAtSpace = tracker;
                bookshelf.addBookToShelf(book);
                System.out.println(book.getTitle() + " has been added to " + bookshelf.getBookshelfLabel());
                break;
            }
        }
        if (foundAtSpace < 0) {
            System.out.println("Invalid name.");
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void removeBookFromGivenShelf(Bookshelf bookshelf) {
        System.out.print("Enter name of the book you would like to remove from " + bookshelf.getBookshelfLabel());
        String title = input.next();
        title += input.nextLine();
        title = title.toLowerCase();
        int foundAtSpace = -1;
        int tracker = 0;
        for (Book book : bookshelf.getBooks()) {
            tracker++;
            if (book.getTitle().toLowerCase().equals(title)) {
                foundAtSpace = tracker;
                bookshelf.removeBookFromShelf(book);
                System.out.println(book.getTitle() + " has been removed from " + bookshelf.getBookshelfLabel());
                break;
            }
        }
        if (foundAtSpace < 0) {
            System.out.println("Invalid name.");
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewAllBooksOnShelf(Bookshelf bookshelf) {
        System.out.println("Here are the books on shelf " + bookshelf.getBookshelfLabel());
        for (Book book : bookshelf.getBooks()) {
            System.out.println(book.getTitle());
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewBookRoom() {
        for (Bookshelf bookshelf : bookRoom.getShelves()) {
            System.out.println("\nBookshelf: " + bookshelf.getBookshelfLabel());
            System.out.println("Contains: ");
            for (Book book : bookshelf.getBooks()) {
                System.out.println("\t" + book.getTitle());
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private boolean checkBookshelfDoesntAlreadyExist(String label) {
        label = label.toLowerCase();
        for (Bookshelf b : bookRoom.getShelves()) {
            if (b.getBookshelfLabel().toLowerCase().equals(label)) {
                return false;
            }
        }
        return true;
    }

}