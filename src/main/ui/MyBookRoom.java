package ui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;

import java.util.Scanner;

public class MyBookRoom {
    private BookRoom bookRoom;
    private Scanner input;
    private Bookshelf uncategorized;
    private Bookshelf toRead;
    private Bookshelf completed;
    private Book book;


    public MyBookRoom() {
        runBookRoom();
    }

    private void runBookRoom() {
        boolean open = true;
        String command = null;

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
        if (command.equals("n")) {
            doAddBook();
        } else if (command.equals("b")) {
            doViewBookInfo();
        } else if (command.equals("s")) {
            doViewBookshelves();
        } else if (command.equals("r")) {
            doViewBookRoom();
        } else {
            System.out.println("Please enter a valid input.");
        }
    }

    private void doAddBook() {
        System.out.print("Enter title of your book:");
        String title = input.next();
        Book newBook = new Book(title);

        System.out.println("Book with title " + title + " has been added");
        System.out.println("Which bookshelf would you like to add this to?");
        for (Bookshelf b: bookRoom.getShelves()) {
            System.out.println(b.getBookshelfLabel());
        }
        System.out.print("Input name of bookshelf or 'new' if you want to add it to a new bookshelf:");
        String label = input.next();



        System.out.print("Would you like to add/edit information about this book? Enter yes or no:");
        String answer = input.next();
        if (answer == "yes") {
            doChooseBookToView();
        }
    }

    private void doChooseBookToView() {

    }

    private void doViewBookInfo() {

    }

    private void doViewBookshelves() {

    }

    private void doViewBookRoom() {

    }


}
