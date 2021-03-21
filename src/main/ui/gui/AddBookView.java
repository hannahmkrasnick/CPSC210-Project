package ui.gui;

import model.Book;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents a panel that allows user to add a book to app
public class AddBookView extends ChangePanel implements ActionListener {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField reviewField;
    private BookRoomApplication gui;
    private JCheckBox addToToRead;
    private JCheckBox addToCompleted;
    private JCheckBox addToFavourites;
    private int rating;
    private static final int textFieldColumns = 15;

    //EFFECTS: constructs a ChangePanel with fields to add a book to book room and checkboxes for which shelves to add
    //         book to
    public AddBookView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;
        constraints = new GridBagConstraints();

        addInstructionLabel(constraints);

        addLabelsForFields(constraints);

        addTextFields(constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 2;
        addToToRead = new JCheckBox("Add to To Read");
        add(addToToRead, constraints);

        constraints.gridy += 1;
        addToCompleted = new JCheckBox("Add to Completed");
        add(addToCompleted, constraints);

        constraints.gridy += 1;
        addToFavourites = new JCheckBox("Add to Favourites");
        add(addToFavourites, constraints);

        addAddButton(constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs text fields for inputting info about book and adds fields to this
    private void addTextFields(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 1;
        constraints.gridy = 1;
        titleField = new JTextField(textFieldColumns);
        add(titleField, constraints);

        constraints.gridy += 1;
        authorField = new JTextField(textFieldColumns);
        add(authorField, constraints);

        constraints.gridy += 1;
        genreField = new JTextField(textFieldColumns);
        add(genreField, constraints);

        constraints.gridy += 1;
        ratingField = new JTextField(textFieldColumns);
        add(ratingField, constraints);

        constraints.gridy += 1;
        reviewField = new JTextField(textFieldColumns);
        add(reviewField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs labels for book fields and adds labels to this
    private void addLabelsForFields(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        JLabel titleLabel = new JLabel("Title:");
        add(titleLabel, constraints);

        constraints.gridy += 1;
        JLabel authorLabel = new JLabel("Author:");
        add(authorLabel, constraints);

        constraints.gridy += 1;
        JLabel genreLabel = new JLabel("Genre:");
        add(genreLabel, constraints);

        constraints.gridy += 1;
        JLabel ratingLabel = new JLabel("Rating:");
        add(ratingLabel, constraints);

        constraints.gridy += 1;
        JLabel reviewLabel = new JLabel("Review:");
        add(reviewLabel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs add button which prompts system to add book to room and adds button to this
    private void addAddButton(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 1;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        JButton submitButton = new JButton("Add");
        submitButton.setActionCommand("add");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs instruction label for top of panel and adds it to this
    private void addInstructionLabel(GridBagConstraints constraints) {
        this.constraints = constraints;
        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Press add to submit");
        text.setFont(myFont);
        constraints.gridwidth = 2;
        add(text, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: called when "add" button is pressed, if title doesn't already exists, adds new book with given fields
    //         to "All Books" shelf and any selected shelves, and changes panel back to main ChangePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genreString = genreField.getText();
        Genre genre = Genre.getGenreFromString(genreString);
        String ratingString = ratingField.getText();
        int rating = makeRatingInt(ratingString);
        String review = reviewField.getText();

        if (e.getActionCommand().equals("add")) {
            if (gui.getBookRoom().checkBookDoesNotAlreadyExist(title) && !title.equals("")) {
                Book newBook = new Book(title, author, genre, rating, review);
                addToSelectedShelves(newBook);
                OptionPane confirmBookAdded = new OptionPane(gui);
                try {
                    confirmBookAdded.confirmBookAddedPane();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        gui.changeToChangePanel();
    }

    //EFFECTS: converts string to valid rating int for book, return -1 if ratingString not between 1 and 10
    private int makeRatingInt(String ratingString) {
        int rating;
        try {
            rating = Integer.parseInt(ratingString);
            if (!Book.checkRatingIsValid(rating)) {
                rating = -1;
            }

        } catch (NumberFormatException nfe) {
            rating = -1;
        }
        return rating;
    }

    //MODIFIES: gui
    //EFFECTS: checks which bookshelf check boxes are selected and adds b to those shelves, or removes it from
    //         deselected shelves
    private void addToSelectedShelves(Book b) {
        gui.getAllBooks().addBookToShelf(b);
        if (addToToRead.isSelected()) {
            gui.getToRead().addBookToShelf(b);
        }
        if (addToCompleted.isSelected()) {
            gui.getCompleted().addBookToShelf(b);
        }
        if (addToFavourites.isSelected()) {
            gui.getFavourites().addBookToShelf(b);
        }
    }
}
