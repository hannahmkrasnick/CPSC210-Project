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

        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Press add to submit");
        text.setFont(myFont);
        constraints.gridwidth = 2;
        add(text, constraints);
        constraints.gridx = 0;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        JLabel titleLabel = new JLabel("Title:");
        add(titleLabel, constraints);
        titleField = new JTextField(textFieldColumns);
        constraints.gridx = 1;
        add(titleField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel authorLabel = new JLabel("Author:");
        add(authorLabel, constraints);
        authorField = new JTextField(textFieldColumns);
        constraints.gridx = 1;
        add(authorField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel genreLabel = new JLabel("Genre:");
        add(genreLabel, constraints);
        genreField = new JTextField(textFieldColumns);
        constraints.gridx = 1;
        add(genreField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel ratingLabel = new JLabel("Rating:");
        add(ratingLabel, constraints);
        ratingField = new JTextField(textFieldColumns);
        constraints.gridx = 1;
        add(ratingField, constraints);

        constraints.gridx = 0;
        constraints.gridy += 1;
        JLabel reviewLabel = new JLabel("Review:");
        add(reviewLabel, constraints);
        reviewField = new JTextField(textFieldColumns);
        constraints.gridx = 1;
        add(reviewField, constraints);

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

        constraints.gridx = 1;
        constraints.gridy += 1;
        constraints.gridwidth = 1;
        JButton submitButton = new JButton("Add");
        submitButton.setActionCommand("add");
        submitButton.addActionListener(this);
        add(submitButton, constraints);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        Genre genre;
        int rating;

        String title = titleField.getText();
        String author = authorField.getText();
        String genreString = genreField.getText();
        if (Genre.checkGenreExists(genreString)) {
            genre = Genre.getGenreFromString(genreString);
        } else {
            genre = Genre.UNCLASSIFIED;
        }
        String ratingString = ratingField.getText();
        try {
            rating = Integer.parseInt(ratingString);
        } catch (NumberFormatException nfe) {
            rating = -1;
        }
        String review = reviewField.getText();

        if (e.getActionCommand().equals("add")) {
            if (gui.getBookRoom().checkBookDoesNotAlreadyExist(title) && !title.equals("")) {
                Book newBook = new Book(title, author, genre, rating, review);
                gui.getAllBooks().addBookToShelf(newBook);
                if (addToToRead.isSelected()) {
                    gui.getToRead().addBookToShelf(newBook);
                }
                if (addToCompleted.isSelected()) {
                    gui.getCompleted().addBookToShelf(newBook);
                }
                if (addToFavourites.isSelected()) {
                    gui.getFavourites().addBookToShelf(newBook);
                }

            }
        }
        gui.changeToChangePanel();
        gui.revalidate();
    }
}
