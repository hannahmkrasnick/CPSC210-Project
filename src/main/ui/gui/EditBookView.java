package ui.gui;

import model.Book;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents a panel that allows user to edit a book in app
public class EditBookView extends ChangePanel implements ActionListener {
    private static final String newline = "\n";
    private static final int textFieldColumns = 16;

    private JTextField inputField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField reviewField;
    private BookRoomApplication gui;
    private Book book;
    private JCheckBox allBooksCheckBox;
    private JCheckBox toReadCheckBox;
    private JCheckBox completedCheckBox;
    private JCheckBox favouritesCheckBox;

    //EFFECTS: constructs ChangePanel
    public EditBookView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: prompts user to input book they'd like to edit
    public void enterTitleToEditView() {
        JLabel text = new JLabel("Enter title of book to edit:");
        add(text, constraints);
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        constraints.gridy += 1;
        add(inputField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds fields for user to edit info about book
    public void editBookFields(Book book) {
        this.book = book;
        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter your changes and press submit");
        text.setFont(myFont);
        constraints.gridwidth = 4;
        add(text, constraints);

        addFieldLabels(constraints);

        addTextFields(constraints);

        addInstructions(constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        addBookshelfCheckBoxes(constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 4;
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds checkboxes for each bookshelf for user to pick which ones they'd like their book on
    private void addBookshelfCheckBoxes(GridBagConstraints constraints) {
        this.constraints = constraints;
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
    }

    //MODIFIES: this
    //EFFECTS: adds instructions for how the checkboxes work
    private void addInstructions(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 4;
        JTextArea instructions = new JTextArea("Select the shelves "
                + "\nyou'd like this book on."
                + "\nNote: deselecting All "
                + "\nBooks will delete the "
                + "\nbook from your Book Room");
        instructions.setOpaque(false);
        add(instructions, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds labels for all the editable fields
    private void addFieldLabels(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridy += 1;
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
    //EFFECTS: adds text fields for all editable fields
    private void addTextFields(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridwidth = 3;
        constraints.gridx = 1;
        constraints.gridy = 1;
        titleField = new JTextField(book.getTitle(), textFieldColumns);
        add(titleField, constraints);

        constraints.gridy += 1;
        authorField = new JTextField(book.getAuthor(), textFieldColumns);
        add(authorField, constraints);

        constraints.gridy += 1;
        genreField = new JTextField(String.valueOf(book.getGenre()), textFieldColumns);
        add(genreField, constraints);

        constraints.gridy += 1;
        ratingField = new JTextField(String.valueOf(book.getRating()), textFieldColumns);
        add(ratingField, constraints);

        constraints.gridy += 1;
        reviewField = new JTextField(book.getReview(), textFieldColumns);
        add(reviewField, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: processes "submit" button press and edits book based on user input, and changes panel back to main
    //         ChangePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(newline)) {
            goToEditBookFieldsView();

        } else if (e.getActionCommand().equals("submit")) {
            if (!allBooksCheckBox.isSelected()) {
                gui.getBookRoom().deleteBookFromBookRoom(book);
            } else {
                String title = titleField.getText();
                String author = authorField.getText();
                String genreString = genreField.getText();
                Genre genre = Genre.getGenreFromString(genreString);
                String ratingString = ratingField.getText();
                int rating = makeRatingInt(ratingString);
                String review = reviewField.getText();

                book.setAllBookFields(title, author, genre, rating, review);

                addToSelectedShelves(book);
            }
            gui.changeToChangePanel();
        }
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
    //EFFECTS: if user presses enter after inputting title, sends user to panel for editing book fields
    private void goToEditBookFieldsView() {
        List<Book> allBooks = gui.getAllBooks().getBooksOnShelf();
        String input = inputField.getText();

        for (Book b : allBooks) {
            if (input.equalsIgnoreCase(b.getTitle())) {
                gui.changeToEditBookFieldsView(b);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: checks which boxes have been checked and adds/removes book from appropriate shelves
    private void addToSelectedShelves(Book book) {
        if (toReadCheckBox.isSelected()) {
            gui.getToRead().addBookToShelf(book);
        } else {
            gui.getToRead().removeBookFromShelf(book);
        }

        if (completedCheckBox.isSelected()) {
            gui.getCompleted().addBookToShelf(book);
        } else {
            gui.getCompleted().removeBookFromShelf(book);
        }

        if (favouritesCheckBox.isSelected()) {
            gui.getFavourites().addBookToShelf(book);
        } else {
            gui.getFavourites().removeBookFromShelf(book);
        }
    }
}
