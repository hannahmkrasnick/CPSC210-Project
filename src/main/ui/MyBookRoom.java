package ui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;

import java.util.Scanner;

public class MyBookRoom {
    private BookRoom bookRoom;
    private Scanner input;
    private Bookshelf uncategorized;
    private Bookshelf toRead;
    private Bookshelf completed;


    public MyBookRoom() {
        runBookRoom();
    }

    private void runBookRoom() {
        boolean open = true;
        String command;

        init();

        while (open) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("e")) {
                open = false;
            } else {
                processCommand(command);
            }
        }

    }

    private void init() {
        bookRoom = new BookRoom("My Book Room");
        uncategorized = new Bookshelf("Uncategorized");
        toRead = new Bookshelf("To Read");
        completed = new Bookshelf("Completed");
        bookRoom.addShelfToRoom(uncategorized);
        bookRoom.addShelfToRoom(toRead);
        bookRoom.addShelfToRoom(completed);
        input = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tn: Add a new book to my room");
        System.out.println("\tb: View information about a book");
        System.out.println("\ts: View my bookshelves");
        System.out.println("\tr: View my Book Room");
        System.out.println("\te: Exit");
    }

    private void processCommand(String command) {
        switch (command) {
            case "n":
                doAddBook();
                break;
            case "b":
                doChooseBookToView();
                break;
            case "s":
                doViewBookshelves();
                break;
            case "r":
                doViewBookRoom();
                break;
            default:
                System.out.print("Please enter a valid input.");
                break;
        }
    }

    private void doAddBook() {
        System.out.print("Enter title of your book: ");
        String title = input.next();
        checkBookNotAlreadyThere(title);
        Book newBook = new Book(title);
        uncategorized.addBookToShelf(newBook);

        System.out.println(title + " has been added to your Book Room");

        System.out.print("Would you like to add/edit information about this book? Enter y or n: ");
        String answer = input.next();
        if (answer.equals("y")) {
            doEditBookInfo(newBook);
        }
    }

    private void checkBookNotAlreadyThere(String title) {
        title = title.toLowerCase();
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book book : b.getBooks()) {
                if (book.getTitle().toLowerCase().equals(title)) {
                    System.out.print("Please enter a book title that isn't already in your Book Room.");
                    doAddBook();
                }
            }
        }
    }

        /*System.out.println("Which bookshelf would you like to add this to?");
        for (Bookshelf b: bookRoom.getShelves()) {
            System.out.println(b.getBookshelfLabel());
        }
        System.out.print("Input name of bookshelf or n if you want to add it to a new bookshelf:");
        String label = input.next();

        if (label == "n") {
            System.out.println("What would you like to call this bookshelf?");
            String newLabel = input.next();
            doAddNewShelf(newLabel);
            .addBookToShelf(newBook);
            System.out.println(title + " has been added to the new shelf " + newLabel);
        } else {
            for (Bookshelf b: bookRoom.getShelves()) {
                if (label.equalsIgnoreCase(b.getBookshelfLabel())) {
                    b.addBookToShelf(newBook);
                    System.out.println(title + " has been added to shelf" + label);
                }
            }
        }*/

    private void doAddNewShelf(String label) {
        checkBookshelfNotAlreadyThere(label);
        Bookshelf newShelf = new Bookshelf(label);
        bookRoom.addShelfToRoom(newShelf);
    }

    private void doChooseBookToView() {
        doViewAllBooks();
        System.out.print("Enter name of book you'd like to view: ");
        String bookName = input.next();
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book book : b.getBooks()) {
                if (bookName.equals(book.getTitle())) {
                    doViewBookInfo(book);
                }
            }
        }
    }

    private void doViewAllBooks() {
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book book : b.getBooks()) {
                System.out.println(book.getTitle());
            }
        }
    }

    private void doViewBookInfo(Book book) {
        System.out.println("\nBook info:");
        System.out.println("\tTitle: " + book.getTitle());
        System.out.println("\tAuthor: " + book.getAuthor());
        System.out.println("\tGenre " + book.getGenre());
        System.out.println("\tRating: " + book.getRating());
        System.out.println("\tReview: " + book.getReview());
        System.out.println("\nBook is one the following bookshelves:");
        for (Bookshelf b : bookRoom.getShelves()) {
            for (Book c : b.getBooks()) {
                if (c.getTitle().toLowerCase().equals(book.getTitle())) {
                    System.out.println(b.getBookshelfLabel());
                }
            }
        }
    }

    private void doEditBookInfo(Book book) {
        doViewBookInfo(book);
        System.out.print("\nWhat information would you like to change? "
                + "\tChoose one of: title, author, genre, rating, review");
        processEditBookCommand(book);
    }

    private void processEditBookCommand(Book book) {
        String editCommand = input.next();
        editCommand = editCommand.toLowerCase();
        switch (editCommand) {
            case "title":
                editBookTitle(book);
                break;
            case "author":
                editBookAuthor(book);
                break;
            case "genre":
                editBookGenre(book);
                break;
            case "rating":
                editBookRating(book);
                break;
            case "review":
                editBookReview(book);
                break;
            default:
                System.out.print("Please enter a valid input.");
                processEditBookCommand(book);
                break;
        }
    }

    private void editBookTitle(Book book) {
        System.out.print("Enter title of book: ");
        String title = input.next();
        book.setTitle(title);
        System.out.println("Title of book has been set to " + title);
    }

    private void editBookAuthor(Book book) {
        System.out.print("Enter name of author: ");
        String name = input.next();
        book.setAuthor(name);
        System.out.println("Author of " + book.getTitle() + " has been set to " + name);
    }

    private void editBookGenre(Book book) {
        System.out.println("\nGenre can be one of: ");
        for (Genre genre : Genre.values()) {
            System.out.println(genre);
        }
        System.out.print("Enter genre: ");
        String newGenre = input.next();
        newGenre = newGenre.toUpperCase();
        book.setGenre(Genre.valueOf(newGenre));
        System.out.println("Genre of " + book.getTitle() + " has been set to " + newGenre);
    }

    private void editBookRating(Book book) {
        System.out.print("Enter rating value between 1 and 10: ");
        String stringRating = input.next();
        int rating = Integer.parseInt(stringRating);
        if (rating >= 1 && rating <= 10) {
            book.setRating(rating);
            System.out.println("Rating of " + book.getTitle() + " has been set to " + rating);
        } else {
            System.out.println("Input invalid.");
            editBookRating(book);
        }
    }

    private void editBookReview(Book book) {
        System.out.print("Enter your review of this book: ");
        String review = input.next();
        book.setReview(review);
        System.out.println("Review of " + book.getTitle() + " has been changed");
    }

    private void doViewBookshelves() {
        for (Bookshelf b : bookRoom.getShelves()) {
            System.out.println(b.getBookshelfLabel());
        }
    }

    private void doViewBookRoom() {

    }

    private void checkBookshelfNotAlreadyThere(String label) {
        label = label.toLowerCase();
        for (Bookshelf b : bookRoom.getShelves()) {
            if (b.getBookshelfLabel().toLowerCase().equals(label)) {
                System.out.print("Please enter a bookshelf label that isn't already in your Book Room: ");
                String newLabel = input.next();
                doAddNewShelf(newLabel);
            }
        }
    }


}
