package gui;

import model.Book;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookView extends ChangePanel implements ActionListener {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField reviewField;
    private GraphicBookRoom gui;
    private JCheckBox addToToRead;
    private JCheckBox addToCompleted;
    private JCheckBox addToFavourites;
    private static final int textFieldColumns = 15;

    //TODO
    public AddBookView(GraphicBookRoom gui) {
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

        addSubmitButton(constraints);
    }

    //TODO
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

    //TODO
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

    //TODO
    private void addSubmitButton(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridx = 1;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        JButton submitButton = new JButton("Add");
        submitButton.setActionCommand("add");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //TODO
    private void addInstructionLabel(GridBagConstraints constraints) {
        this.constraints = constraints;
        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Press add to submit");
        text.setFont(myFont);
        constraints.gridwidth = 2;
        add(text, constraints);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        int rating;
        String title = titleField.getText();
        String author = authorField.getText();
        String genreString = genreField.getText();
        Genre genre = Genre.getGenreFromString(genreString);
        String ratingString = ratingField.getText();
        try {
            rating = Integer.parseInt(ratingString);
            if (!Book.checkRatingIsValid(rating)) {
                rating = -1;
            }

        } catch (NumberFormatException nfe) {
            rating = -1;
        }
        String review = reviewField.getText();

        if (e.getActionCommand().equals("add")) {
            if (gui.getBookRoom().checkBookDoesNotAlreadyExist(title) && !title.equals("")) {
                Book newBook = new Book(title, author, genre, rating, review);
                gui.getAllBooks().addBookToShelf(newBook);
                addToSelectedShelves(newBook);
            }
        }
        gui.changeToChangePanel();
    }

    //TODO
    private void addToSelectedShelves(Book b) {
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
