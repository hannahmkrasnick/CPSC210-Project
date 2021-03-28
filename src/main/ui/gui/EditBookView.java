package ui.gui;

import model.Book;
import model.Bookshelf;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
    private List<JCheckBox> shelfCheckBoxes;


    //EFFECTS: constructs ChangePanel
    public EditBookView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;
    }

    //MODIFIES: this
    //EFFECTS: prompts user to input book they'd like to edit
    public void enterTitleToEditView() {
        JLabel text = new JLabel("Enter title of book to edit:");
        text.setFont(myFont);
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
        addTitle(constraints);

        constraints.gridy += 1;
        constraints.gridwidth = 1;
        addFieldLabels(constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 1;
        constraints.gridy = 1;
        addTextFields(constraints);

        addFieldInstructions(constraints);

        constraints.gridy += 1;
        addBookshelfCheckBoxes(constraints);

        constraints.gridy += 1;
        addInstructions(constraints);

        constraints.gridy += 1;
        constraints.gridwidth = 4;
        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs title for this
    private void addTitle(GridBagConstraints constraints) {
        this.constraints = constraints;
        JLabel text = new JLabel("Enter your changes and press submit");
        text.setFont(myFont);
        constraints.gridwidth = 4;
        add(text, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds labels for all the editable fields
    private void addFieldLabels(GridBagConstraints constraints) {
        this.constraints = constraints;
        JLabel titleLabel = new JLabel("Title:");
        add(titleLabel, constraints);

        constraints.gridy += 2;
        JLabel authorLabel = new JLabel("Author:");
        add(authorLabel, constraints);

        constraints.gridy += 2;
        JLabel genreLabel = new JLabel("Genre:");
        add(genreLabel, constraints);

        constraints.gridy += 2;
        JLabel ratingLabel = new JLabel("Rating:");
        add(ratingLabel, constraints);

        constraints.gridy += 2;
        JLabel reviewLabel = new JLabel("Review:");
        add(reviewLabel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds text fields for all editable fields
    private void addTextFields(GridBagConstraints constraints) {
        this.constraints = constraints;
        titleField = new JTextField(book.getTitle(), textFieldColumns);
        add(titleField, constraints);

        constraints.gridy += 2;
        authorField = new JTextField(book.getAuthor(), textFieldColumns);
        add(authorField, constraints);

        constraints.gridy += 2;
        String genre = book.getGenre().getString();
        if (genre.equals(Genre.UNCLASSIFIED.getString())) {
            genre = "";
        }
        genreField = new JTextField(genre, textFieldColumns);
        add(genreField, constraints);

        constraints.gridy += 2;
        String rating = String.valueOf(book.getRating());
        if (book.getRating() == -1) {
            rating = "";
        }
        ratingField = new JTextField(rating, textFieldColumns);
        add(ratingField, constraints);

        constraints.gridy += 2;
        reviewField = new JTextField(book.getReview(), textFieldColumns);
        add(reviewField, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds instructions for inputting text to each field
    private void addFieldInstructions(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        JTextArea titleInstructions = makeInstruction("Title max. 20 characters, must be unique");
        add(titleInstructions, constraints);

        constraints.gridy += 2;
        JTextArea authorInstructions = makeInstruction("Author max. 25 characters");
        add(authorInstructions, constraints);

        constraints.gridy += 2;
        JTextArea genreInstructions = makeInstruction("See bar on right for valid genres");
        add(genreInstructions, constraints);

        constraints.gridy += 2;
        JTextArea ratingInstructions = makeInstruction("Enter no. between 1 and 10");
        add(ratingInstructions, constraints);

        constraints.gridy += 2;
        JTextArea reviewInstructions = makeInstruction("Review max. 400 characters");
        add(reviewInstructions, constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs a JTextArea with given text
    private JTextArea makeInstruction(String text) {
        JTextArea instruction = new JTextArea(text);
        instruction.setForeground(Color.GRAY);
        instruction.setOpaque(false);
        instruction.setEditable(false);
        return instruction;
    }

    //MODIFIES: this
    //EFFECTS: adds instructions for how the checkboxes work
    private void addInstructions(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        JTextArea instructions = new JTextArea("Select the shelves you'd like this book on."
                + "\nNote: deselecting All Books will delete the book.");
        instructions.setOpaque(false);
        instructions.setEditable(false);
        add(instructions, constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds checkboxes for each bookshelf for user to pick which ones they'd like their book on
    private void addBookshelfCheckBoxes(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        shelfCheckBoxes = new ArrayList<>();
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();
        for (int i = 0; i <= shelves.size() - 1; i++) {
            JCheckBox newCheckBox = new JCheckBox(shelves.get(i).getBookshelfLabel());
            if (shelves.get(i).getBooksOnShelf().contains(book)) {
                newCheckBox.setSelected(true);
            }
            shelfCheckBoxes.add(newCheckBox);
            if (constraints.gridx == 0) {
                constraints.gridx = 2;
            } else {
                constraints.gridx = 0;
                constraints.gridy += 1;
            }
            add(newCheckBox, constraints);
        }
    }

    //MODIFIES: gui
    //EFFECTS: processes "submit" button press and edits book based on user input, and changes panel back to main
    //         ChangePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(newline)) {
            goToEditBookFieldsView();

        } else if (e.getActionCommand().equals("submit")) {
            if (!shelfCheckBoxes.get(0).isSelected()) {
                gui.getBookRoom().deleteBookFromBookRoom(book);
            } else {
                String title = Book.makeTitleRightLengthForGui(titleField.getText());
                if (title.equals("") || !gui.getBookRoom().checkBookDoesNotAlreadyExist(title)) {
                    title = book.getTitle();
                }
                String author = Book.makeAuthorRightLengthForGui(authorField.getText());
                Genre genre = Genre.getGenreFromString(genreField.getText());
                int rating = Book.makeRatingInt(ratingField.getText());
                String review = Book.makeReviewRightLengthForGui(reviewField.getText());
                book.setAllBookFields(title, author, genre, rating, review);

                addToSelectedShelves(book);
                gui.changeBookDisplay(book);
            }
            gui.changeToChangePanel();
        }
        if (gui.getCurrentlyDisplayedBookshelf() != null) {
            gui.changeBooksDisplay(gui.getCurrentlyDisplayedBookshelf(), gui.getCurrentPage());
        }
    }

    //MODIFIES: gui
    //EFFECTS: if user presses enter after inputting title, sends user to panel for editing book fields
    private void goToEditBookFieldsView() {
        List<Book> allBooks = gui.getShelfWithLabel("All Books").getBooksOnShelf();
        String input = inputField.getText();
        Book inputBook = new Book(input);

        if (allBooks.contains(inputBook)) {
            for (Book b : allBooks) {
                if (input.equalsIgnoreCase(b.getTitle())) {
                    gui.changeToEditBookFieldsView(b);
                }
            }
        } else {
            gui.changeToChangePanel();
        }
    }

    //MODIFIES: this
    //EFFECTS: checks which boxes have been checked and adds/removes book from appropriate shelves
    private void addToSelectedShelves(Book b) {
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();
        for (int i = 1; i <= shelves.size() - 1; i++) {
            if (shelfCheckBoxes.get(i).isSelected()) {
                shelves.get(i).addBookToShelf(b);
            } else {
                shelves.get(i).removeBookFromShelf(b);
            }
        }
    }
}
