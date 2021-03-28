package ui.gui;

import model.Book;
import model.Bookshelf;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

// Represents a panel that allows user to add a book to app
public class AddBookView extends ChangePanel implements ActionListener {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField reviewField;
    private BookRoomApplication gui;
    private static final int textFieldColumns = 15;
    private List<JCheckBox> shelfCheckBoxes;

    //EFFECTS: constructs a ChangePanel with fields to add a book to book room and checkboxes for which shelves to add
    //         book to
    public AddBookView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;
        constraints = new GridBagConstraints();

        addInstructionLabel(constraints);

        addLabelsForFields(constraints);

        addTextFields(constraints);

        addInstructions(constraints);
        
        addShelfCheckBoxes(constraints);

        addAddButton(constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds check boxes for each bookshelf
    private void addShelfCheckBoxes(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        shelfCheckBoxes = new ArrayList<>();
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();
        for (int i = 1; i <= shelves.size() - 1; i++) {
            JCheckBox newCheckBox = new JCheckBox("Add to " + shelves.get(i).getBookshelfLabel());
            shelfCheckBoxes.add(newCheckBox);
            if (constraints.gridx == 0) {
                constraints.gridx = 1;
            } else {
                constraints.gridx = 0;
                constraints.gridy += 1;
            }
            add(newCheckBox, constraints);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds instructions for inputting text to each field
    private void addInstructions(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        JTextArea instruction1 = makeInstruction("Title max. 20 characters, must be unique");
        add(instruction1, constraints);

        constraints.gridy += 2;
        JTextArea instruction2 = makeInstruction("Author max. 25 characters");
        add(instruction2, constraints);

        constraints.gridy += 2;
        JTextArea instruction3 = makeInstruction("See bar on right for valid genres");
        add(instruction3, constraints);

        constraints.gridy += 2;
        JTextArea instruction4 = makeInstruction("Enter no. between 1 and 10");
        add(instruction4, constraints);

        constraints.gridy += 2;
        JTextArea instruction5 = makeInstruction("Review max. 400 characters");
        add(instruction5, constraints);
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
    //EFFECTS: constructs text fields for inputting info about book and adds fields to this
    private void addTextFields(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 1;
        constraints.gridy = 1;
        titleField = new JTextField(textFieldColumns);
        add(titleField, constraints);

        constraints.gridy += 2;
        authorField = new JTextField(textFieldColumns);
        add(authorField, constraints);

        constraints.gridy += 2;
        genreField = new JTextField(textFieldColumns);
        add(genreField, constraints);

        constraints.gridy += 2;
        ratingField = new JTextField(textFieldColumns);
        add(ratingField, constraints);

        constraints.gridy += 2;
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
    //EFFECTS: constructs add button which prompts system to add book to room and adds button to this
    private void addAddButton(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 2;
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
        String title = Book.makeTitleRightLengthForGui(titleField.getText());
        String author = Book.makeAuthorRightLengthForGui(authorField.getText());
        Genre genre = Genre.getGenreFromString(genreField.getText());
        int rating = Book.makeRatingInt(ratingField.getText());
        String review = Book.makeReviewRightLengthForGui(reviewField.getText());
        if (e.getActionCommand().equals("add")) {
            if (gui.getBookRoom().checkBookDoesNotAlreadyExist(title) && !title.equals("")) {
                Book newBook = new Book(title, author, genre, rating, review);
                addToSelectedShelves(newBook);
                gui.changeBookDisplay(newBook);
                OptionPane confirmBookAdded = new OptionPane(gui);
                try {
                    confirmBookAdded.confirmBookAddedPane();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        gui.changeToChangePanel();
        if (gui.getCurrentlyDisplayedBookshelf() != null) {
            gui.changeBooksDisplay(gui.getCurrentlyDisplayedBookshelf(), gui.getCurrentPage());
        }
    }

    //MODIFIES: gui
    //EFFECTS: checks which bookshelf check boxes are selected and adds b to those shelves, or removes it from
    //         deselected shelves
    private void addToSelectedShelves(Book b) {
        gui.getShelfWithLabel("All Books").addBookToShelf(b);
        int i = 1;
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();
        for (JCheckBox cb : shelfCheckBoxes) {
            if (cb.isSelected()) {
                shelves.get(i).addBookToShelf(b);
                i++;
            }
        }
    }
}
