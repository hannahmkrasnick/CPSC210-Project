package ui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Book Room application
public class MyBookRoom {
    private static final String JSON_STORE = "./data/bookroom.json";
    private BookRoom bookRoom;
    private Scanner input;
    private Bookshelf allBooks;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the BookRoom application
    public MyBookRoom() throws FileNotFoundException {
        runBookRoom();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
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

    // MODIFIES: this
    // EFFECTS: initializes BookRoom with number of bookshelves
    private void init() {
        Bookshelf toRead = new Bookshelf("To Read");
        Bookshelf completed = new Bookshelf("Completed");

        bookRoom = new BookRoom("My Book Room");
        allBooks = new Bookshelf("All Books");
        bookRoom.addShelfToRoom(allBooks);
        bookRoom.addShelfToRoom(toRead);
        bookRoom.addShelfToRoom(completed);
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tn: Add a new book to my room");
        System.out.println("\tv: View information about a book");
        System.out.println("\tb: View my bookshelves");
        System.out.println("\tr: View my Book Room");
        System.out.println("\ts: Save my Book Room to file");
        System.out.println("\tl: Load my Book Room from file");
        System.out.println("\te: Exit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
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
            case "s":
                saveBookRoom();
                break;
            case "l":
                loadBookRoom();
                break;
            default:
                System.out.print("Please enter a valid input.");
                break;
        }
    }

    // MODIFIES: this and allBooks
    // EFFECTS: adds new book to room and to shelf All Books
    private void addNewBook() {
        System.out.print("Enter title of your book: ");
        String title = input.next() + input.nextLine();
        if (bookRoom.checkBookDoesNotAlreadyExist(title)) {
            Book newBook = new Book(title);
            getAllBooksShelf().addBookToShelf(newBook);
            System.out.println(title + " has been added to your Book Room");
            editBookInfo(newBook);
        } else {
            System.out.println("Cannot add book with duplicate title to Book Room. Please enter a valid name");
            addNewBook();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new shelf to the room
    private void addNewShelf() {
        System.out.println("What would you like to call your new bookshelf?");
        String label = input.next() + input.nextLine();
        if (bookRoom.checkBookshelfDoesNotAlreadyExist(label)) {
            Bookshelf newShelf = new Bookshelf(label);
            bookRoom.addShelfToRoom(newShelf);
            System.out.println("Bookshelf " + label + " has been added to your Book Room");
        } else {
            System.out.println("Cannot add bookshelf that already exists.");
        }
    }

    //solution adapted from: https://stackoverflow.com/questions/3779514/java-for-loop-and-if-algorithm
    // EFFECTS: displays all books and lets user choose which book to view/edit
    private void chooseBookToView() {
        viewAllBooks();
        System.out.print("Enter name of book you'd like to view: ");
        String bookName = input.next().toLowerCase() + input.nextLine().toLowerCase();
        int checkValid = -1;
        for (Book b : getAllBooksShelf().getBooksOnShelf()) {
            if (b.getTitle().toLowerCase().equals(bookName)) {
                checkValid = 0;
                viewBookInfo(b);
                editBookInfo(b);
                break;
            }
        }
        if (checkValid < 0) {
            System.out.println("Invalid name.");
        }
    }

    // EFFECTS: prints out titles of all books
    private void viewAllBooks() {
        System.out.println("Your books:");
        for (Book book : bookRoom.getBooks()) {
            System.out.println(book.getTitle());
        }
    }

    // EFFECTS: prints out information about given book for user
    private void viewBookInfo(Book book) {
        System.out.println("\nBook info:");
        System.out.println("\tTitle: " + book.getTitle());
        System.out.println("\tAuthor: " + book.getAuthor());
        System.out.println("\tGenre: " + book.getGenre());
        System.out.println("\tRating: " + book.getRating());
        System.out.println("\tReview: " + book.getReview());
        viewShelvesBookIsOn(book);
    }

    // MODIFIES: book
    // EFFECTS: allows user to choose which information about book user would like to edit
    private void editBookInfo(Book book) {
        System.out.print("Would you like to add/edit information about this book? Enter y or n: ");
        String answer = input.next().toLowerCase();
        if (answer.equals("y")) {
            viewBookInfo(book);
            System.out.println("\nWhat information would you like to edit? "
                    + "Enter one of: title, author, genre, rating, review, bookshelves, delete");
            processEditBookCommand(book);
        }
    }

    // MODIFIES: book
    // EFFECTS: process user command of what part of book information they would like to edit
    private void processEditBookCommand(Book book) {
        String editCommand = input.next().toLowerCase();
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

    // MODIFIES: this
    // EFFECTS: asks user if they want to remove or add books from/to a shelf and processes the command
    private void moveBookAroundShelves(Book book) {
        viewShelvesBookIsOn(book);
        System.out.print("Would you like to remove or add " + book.getTitle()
                + " from/to a shelf? Enter remove or add: ");
        String response = input.next().toLowerCase();
        if (response.equals("remove")) {
            removeBookFromShelf(book);
        } else if (response.equals("add")) {
            addBookToShelf(book);
        }
    }

    // REQUIRES: input that is existing bookshelf
    // MODIFIES: this
    // EFFECTS: removes book from a shelf the user selects
    private void removeBookFromShelf(Book book) {
        System.out.print("Enter the name of the bookshelf you would like to remove " + book.getTitle() + " from: ");
        String removeShelf = input.next().toLowerCase() + input.nextLine().toLowerCase();
        if (removeShelf.equals("all books")) {
            confirmDeleteBookFromAllBooks(book);
        } else {
            for (Bookshelf bs : bookRoom.getShelves()) {
                if (bs.getBookshelfLabel().toLowerCase().equals(removeShelf)) {
                    bs.removeBookFromShelf(book);
                    System.out.println(book.getTitle() + " has been removed from " + bs.getBookshelfLabel());
                }
            }
        }
    }

    // MODIFIES: book, bookroom
    // EFFECTS: confirms that user wants to delete book from bookroom, and deletes it
    private void confirmDeleteBookFromAllBooks(Book book) {
        System.out.println("If you remove " + book.getTitle()
                + " from All Books it will be deleted from your Book Room. Enter y to proceed: ");
        String answer = input.next().toLowerCase();
        if (answer.equals("y")) {
            deleteBookFromRoom(book);
        }
    }

    // EFFECTS: displays the shelves given book is on
    private void viewShelvesBookIsOn(Book book) {
        System.out.println(book.getTitle() + " is on the following shelves:");
        for (Bookshelf bs : bookRoom.getShelves()) {
            for (Book b : bs.getBooksOnShelf()) {
                if (b.getTitle().equals(book.getTitle())) {
                    System.out.println(bs.getBookshelfLabel());
                }
            }
        }
    }

    // MODIFIES: book
    // EFFECTS: changes book title to input if input not already title of existing Book in room (can be same as book)
    private void editBookTitle(Book book) {
        System.out.print("Enter title of book: ");
        String title = input.next() + input.nextLine();
        if (book.getTitle().toLowerCase().equals(title.toLowerCase()) || bookRoom.checkBookDoesNotAlreadyExist(title)) {
            book.setTitle(title);
            System.out.println("Title of book has been set to " + title);
            editBookInfo(book);
        } else {
            System.out.println("Cannot have book with duplicate title in Book Room.");
        }
    }

    // MODIFIES: book
    // EFFECTS: changes book author to input
    private void editBookAuthor(Book book) {
        System.out.print("Enter name of author: ");
        String name = input.next() + input.nextLine();
        book.setAuthor(name);
        System.out.println("Author of " + book.getTitle() + " has been set to " + name);
        editBookInfo(book);
    }

    // MODIFIES: book
    // EFFECTS: changes book genre to input if input is valid genre
    private void editBookGenre(Book book) {
        System.out.println("\nGenre can be one of: ");
        for (Genre genre : Genre.values()) {
            System.out.println(genre);
        }
        System.out.print("Enter genre: ");
        String newGenre = input.next().toUpperCase();
        if (Genre.checkGenreExists(newGenre)) {
            book.setGenre(Genre.valueOf(newGenre));
            System.out.println("Genre of " + book.getTitle() + " has been set to " + newGenre);
            editBookInfo(book);
        } else {
            System.out.println("Invalid genre.");
        }
    }

    // MODIFIES: book
    // EFFECTS: changes book rating to input and checks that input is valid i.e. [1,10]
    private void editBookRating(Book book) {
        System.out.print("Enter rating value between 1 and 10: ");
        String stringRating = input.next();
        int rating = Integer.parseInt(stringRating);
        if (book.checkRatingIsValid(rating)) {
            book.setRating(rating);
            System.out.println("Rating of " + book.getTitle() + " has been set to " + rating);
            editBookInfo(book);
        } else {
            System.out.println("Input invalid.");
        }
    }

    // MODIFIES: book
    // EFFECTS: changes book review to input
    private void editBookReview(Book book) {
        System.out.print("Enter your review of this book: ");
        String review = input.next() + input.nextLine();
        book.setReview(review);
        System.out.println("Review of " + book.getTitle() + " has been changed");
        editBookInfo(book);
    }

    // EFFECTS: displays list of bookshelves for user and checks if user wants to edit said bookshelves
    private void viewBookshelves() {
        System.out.println("Your bookshelves: ");
        for (Bookshelf bs : bookRoom.getShelves()) {
            System.out.println("\t" + bs.getBookshelfLabel());
        }
        editBookshelves();
    }

    // MODIFIES: this
    // EFFECTS: checks if user wants to edit their bookshelves or add a new one
    private void editBookshelves() {
        System.out.println("Would you like to edit your bookshelves? Enter y or n.");
        String answer = input.next().toLowerCase();
        if (answer.equals("y")) {
            System.out.println("Would you like to edit your existing bookshelves or add a new one? Enter edit or add.");
            String response = input.next().toLowerCase();
            if (response.equals("add")) {
                addNewShelf();
            } else if (response.equals("edit")) {
                editBookshelf();
            }
        }
    }

    // REQUIRES: valid bookshelf name
    // MODIFIES: this
    // EFFECTS: asks user to choose bookshelf they want to edit and gives choices for how to edit the shelf
    private void editBookshelf() {
        System.out.println("Enter the name of the bookshelf you would like to edit: ");
        String shelfName = input.next().toLowerCase() + input.nextLine().toLowerCase();
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().toLowerCase().equals(shelfName)) {
                System.out.println("Bookshelf: " + bs.getBookshelfLabel());
                System.out.println("Contains: ");
                for (Book book : bs.getBooksOnShelf()) {
                    System.out.println("\t" + book.getTitle());
                }
                System.out.println("Enter e to edit bookshelf name, d to delete shelf,"
                        + " or b to add/remove books on this shelf");
                String command = input.next().toLowerCase();
                if ("e".equals(command)) {
                    editBookshelfName(bs);
                } else if ("d".equals(command)) {
                    deleteShelf(bs);
                } else if ("b".equals(command)) {
                    editBooksOnShelf(bs);
                }
                break;
            }
        }
    }

    // solution adapted from SpaceInvaders CPSC 210 program (SIGame.checkCollisions)
    // MODIFIES: this
    // EFFECTS: deletes the bookshelf from the room
    private void deleteShelf(Bookshelf bookshelf) {
        if (bookshelf.getBookshelfLabel().equals(getAllBooksShelf().getBookshelfLabel())) {
            System.out.println("Cannot delete shelf All Books.");
        } else {
            Bookshelf shelfToRemove = new Bookshelf("");

            for (Bookshelf bs : bookRoom.getShelves()) {
                if (bs.getBookshelfLabel().equals(bookshelf.getBookshelfLabel())) {
                    shelfToRemove = bs;
                }
            }
            bookRoom.getShelves().remove(shelfToRemove);
            System.out.println("Shelf has been deleted");
        }
    }

    // MODIFIES: book & this
    // EFFECTS: deletes the book from the room by removing it from all bookshelves it's on
    private void deleteBookFromRoom(Book book) {
        bookRoom.deleteBookFromBookRoom(book);
        System.out.println(book.getTitle() + " has been deleted from your Book Room.");
    }

    // MODIFIES: bookshelf
    // EFFECTS: changes the name of a bookshelf
    private void editBookshelfName(Bookshelf bookshelf) {
        System.out.print("Enter new name for bookshelf: ");
        String response = input.next() + input.nextLine();
        if (bookRoom.checkBookshelfDoesNotAlreadyExist(response)) {
            bookshelf.setBookshelfLabel(response);
            System.out.println("Bookshelf name has been changed to " + response);
        } else {
            System.out.println("Please choose a bookshelf name that doesn't already exist in your Book Room.");
            editBookshelfName(bookshelf);
        }
    }

    // MODIFIES: bookshelf
    // EFFECTS: asks user if they want to remove or add books to the shelf and processes command
    private void editBooksOnShelf(Bookshelf bookshelf) {
        viewAllBooksOnShelf(bookshelf);
        System.out.print("Would you like to remove or add books to this shelf? Enter remove or add: ");
        String response = input.next() + input.nextLine();
        if (response.equals("remove")) {
            removeBookFromGivenShelf(bookshelf);
        } else if (response.equals("add")) {
            addBookToGivenShelf(bookshelf);
        }
    }

    // MODIFIES: bookshelf and book
    // EFFECTS: checks if user input is valid book and adds book to given bookshelf
    private void addBookToGivenShelf(Bookshelf bookshelf) {
        viewAllBooks();
        System.out.print("Enter name of the book you would like to add to " + bookshelf.getBookshelfLabel() + ": ");
        String title = input.next().toLowerCase() + input.nextLine().toLowerCase();
        int checkValid = -1;
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("All Books")) {
                for (Book b : bs.getBooksOnShelf()) {
                    if (b.getTitle().toLowerCase().equals(title) && !bookshelf.getBooksOnShelf().contains(b)) {
                        checkValid = 0;
                        bookshelf.addBookToShelf(b);
                        System.out.println(b.getTitle() + " has been added to " + bookshelf.getBookshelfLabel());
                        break;
                    } else if (b.getTitle().toLowerCase().equals(title) && bookshelf.getBooksOnShelf().contains(b)) {
                        checkValid = 0;
                        System.out.println("Book is already on shelf " + bookshelf.getBookshelfLabel());
                        break;
                    }
                }
            }
        }
        if (checkValid < 0) {
            System.out.println("Invalid name.");
        }
    }

    // solution adapted from: https://stackoverflow.com/questions/3779514/java-for-loop-and-if-algorithm
    // REQUIRES: input that is existing bookshelf
    // MODIFIES: this
    // EFFECTS: adds book to a shelf the user selects
    private void addBookToShelf(Book book) {
        System.out.println("You have the following bookshelves in your Book Room:");
        for (Bookshelf bookshelf : bookRoom.getShelves()) {
            System.out.println(bookshelf.getBookshelfLabel());
        }
        System.out.print("Enter the name of the bookshelf you would like to add " + book.getTitle() + " to: ");
        String addShelf = input.next().toLowerCase() + input.nextLine().toLowerCase();
        int checkValid = -1;
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().toLowerCase().equals(addShelf) && !bs.getBooksOnShelf().contains(book)) {
                checkValid = 0;
                bs.addBookToShelf(book);
                System.out.println(book.getTitle() + " has been added to " + bs.getBookshelfLabel());
                break;
            } else if (bs.getBookshelfLabel().toLowerCase().equals(addShelf) && bs.getBooksOnShelf().contains(book)) {
                checkValid = 0;
                System.out.println("Book is already on shelf " + bs.getBookshelfLabel());
                break;
            }
        }
        if (checkValid < 0) {
            System.out.println("Invalid name.");
        }
    }

    // MODIFIES: bookshelf
    // EFFECTS: checks if user input is valid book and removes book from given bookshelf
    private void removeBookFromGivenShelf(Bookshelf bookshelf) {
        System.out.print("Enter name of the book you would like to remove from "
                + bookshelf.getBookshelfLabel() + ": ");
        String title = input.next().toLowerCase() + input.nextLine().toLowerCase();
        int foundAtSpace = -1;
        int tracker = 0;
        for (Book b : bookshelf.getBooksOnShelf()) {
            tracker++;
            if (b.getTitle().toLowerCase().equals(title) && !bookshelf.getBookshelfLabel().equals("All Books")) {
                foundAtSpace = tracker;
                bookshelf.removeBookFromShelf(b);
                System.out.println(b.getTitle() + " has been removed from " + bookshelf.getBookshelfLabel());
                break;
            } else if (b.getTitle().toLowerCase().equals(title)
                    && bookshelf.getBookshelfLabel().equals("All Books")) {
                foundAtSpace = 0;
                confirmDeleteBookFromAllBooks(b);
                break;
            }
        }
        if (foundAtSpace < 0) {
            System.out.println("Invalid name.");
        }
    }

    // EFFECTS: displays books on given shelf for user
    private void viewAllBooksOnShelf(Bookshelf bookshelf) {
        System.out.println("Here are the books on shelf " + bookshelf.getBookshelfLabel() + ":");
        for (Book book : bookshelf.getBooksOnShelf()) {
            System.out.println("\t" + book.getTitle());
        }
    }

    // EFFECTS: displays name of book room, shelves in room, and books on shelves for user
    private void viewBookRoom() {
        System.out.println(bookRoom.getName());
        for (Bookshelf bs : bookRoom.getShelves()) {
            System.out.println("\tBookshelf: " + bs.getBookshelfLabel());
            System.out.println("\tContains: ");
            for (Book book : bs.getBooksOnShelf()) {
                System.out.println("\t\t" + book.getTitle());
            }
        }
    }

    // EFFECTS: identifies shelf with label "All Books" and returns it
    private Bookshelf getAllBooksShelf() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("All Books")) {
                allBooks = bs;
            }
        }
        return allBooks;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.saveWorkRoom)
    // EFFECTS: saves the bookroom to file
    private void saveBookRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookRoom);
            jsonWriter.close();
            System.out.println("Saved " + bookRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.loadWorkRoom)
    // MODIFIES: this
    // EFFECTS: loads bookroom from file
    private void loadBookRoom() {
        try {
            bookRoom = jsonReader.read();
            System.out.println("Loaded " + bookRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}