package gui;

import model.Book;
import model.Bookshelf;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditBookView extends ChangePanel implements ActionListener {
    private JTextField inputField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField reviewField;
    private GraphicBookRoom gui;
    private static final String newline = "\n";
    private static final int textFieldColumns = 15;
    private Book book;
    private String title;
    private String author;
    private Genre genre;
    private int rating;
    private String review;
    private JCheckBox allBooksCheckBox;
    private JCheckBox toReadCheckBox;
    private JCheckBox completedCheckBox;
    private JCheckBox favouritesCheckBox;
    private List<Book> allBooks;


    //TODO
    public EditBookView(GraphicBookRoom gui) {
        super(gui);
        this.gui = gui;
    }

    //TODO
    public void enterTitleToEditView() {
        JLabel text = new JLabel("Enter title of book to edit:");
        add(text, constraints);
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        constraints.gridy += 1;
        add(inputField, constraints);
    }

    //TODO
    public void editBookFields(Book book) {
        this.book = book;
        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter your changes and press submit");
        text.setFont(myFont);
        constraints.gridwidth = 2;
        add(text, constraints);
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        JLabel titleLabel = new JLabel("Title:");
        add(titleLabel, constraints);
        titleField = new JTextField(book.getTitle(), textFieldColumns);
        constraints.gridx = 1;
        add(titleField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel authorLabel = new JLabel("Author:");
        add(authorLabel, constraints);
        authorField = new JTextField(book.getAuthor(), textFieldColumns);
        constraints.gridx = 1;
        add(authorField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel genreLabel = new JLabel("Genre:");
        add(genreLabel, constraints);
        genreField = new JTextField(String.valueOf(book.getGenre()), textFieldColumns);
        constraints.gridx = 1;
        add(genreField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel ratingLabel = new JLabel("Rating:");
        add(ratingLabel, constraints);
        ratingField = new JTextField(String.valueOf(book.getRating()), textFieldColumns);
        constraints.gridx = 1;
        add(ratingField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel reviewLabel = new JLabel("Review:");
        add(reviewLabel, constraints);
        reviewField = new JTextField(book.getReview(), textFieldColumns);
        constraints.gridx = 1;
        add(reviewField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridheight = 4;
        JTextArea instructions = new JTextArea("Select the shelves "
                + "\nyou'd like this book on."
                + "\nNote: deselecting All "
                + "\nBooks will delete the "
                + "\nbook from your Book Room");
        instructions.setOpaque(false);
        add(instructions, constraints);

        constraints.gridx = 1;
        constraints.gridy += 1;
        constraints.gridheight = 1;
        allBooksCheckBox = new JCheckBox("All Books", true);
        add(allBooksCheckBox, constraints);

        constraints.gridy += 1;
        toReadCheckBox = new JCheckBox("To Read");
        if (!gui.getToRead().checkBookIsNotAlreadyOnShelf(book)) {
            toReadCheckBox.setSelected(true);
        }
        add(toReadCheckBox, constraints);

        constraints.gridy += 1;
        completedCheckBox = new JCheckBox("Completed");
        if (!gui.getCompleted().checkBookIsNotAlreadyOnShelf(book)) {
            completedCheckBox.setSelected(true);
        }
        add(completedCheckBox, constraints);

        constraints.gridy += 1;
        favouritesCheckBox = new JCheckBox("Favourites");
        if (!gui.getFavourites().checkBookIsNotAlreadyOnShelf(book)) {
            favouritesCheckBox.setSelected(true);
        }
        add(favouritesCheckBox, constraints);

        constraints.gridy += 1;
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        allBooks = gui.getAllBooks().getBooksOnShelf();

        if (e.getActionCommand().equals(newline)) {
            String input = inputField.getText();
            for (Book b : allBooks) {
                if (input.equalsIgnoreCase(b.getTitle())) {
                    gui.changeToEditBookFieldsView(b);
                }
            }
        } else if (e.getActionCommand().equals("submit")) {
            if (!allBooksCheckBox.isSelected()) {
                gui.getBookRoom().deleteBookFromBookRoom(book);
            } else {
                title = titleField.getText();
                author = authorField.getText();
                String genreString = genreField.getText();
                if (Genre.checkGenreExists(genreString)) {
                    genre = Genre.getGenreFromString(genreString);
                } else {
                    genre = Genre.UNCLASSIFIED;
                }
                String ratingString = ratingField.getText();
                try {
                    rating = Integer.parseInt(ratingString);
                    if (!book.checkRatingIsValid(rating)) {
                        rating = -1;
                    }

                } catch (NumberFormatException nfe) {
                    rating = -1;
                }
                review = reviewField.getText();

                book.setTitle(title);
                book.setAuthor(author);
                book.setRating(rating);
                book.setReview(review);
                book.setGenre(genre);

                if (toReadCheckBox.isSelected()) {
                    if (gui.getToRead().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getToRead().addBookToShelf(book);
                    }
                } else {
                    if (!gui.getToRead().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getToRead().removeBookFromShelf(book);
                    }
                }

                if (completedCheckBox.isSelected()) {
                    if (gui.getCompleted().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getCompleted().addBookToShelf(book);
                    }
                } else {
                    if (!gui.getCompleted().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getCompleted().removeBookFromShelf(book);
                    }
                }

                if (favouritesCheckBox.isSelected()) {
                    if (gui.getFavourites().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getFavourites().addBookToShelf(book);
                    }
                } else {
                    if (!gui.getFavourites().checkBookIsNotAlreadyOnShelf(book)) {
                        gui.getFavourites().removeBookFromShelf(book);
                    }
                }
            }
            gui.changeToChangePanel();
            gui.revalidate();
        }
    }
}
