package ui.gui;

import model.Book;
import model.Genre;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Represents panel for displaying info about a book
public class BookView extends JPanel {
    private Color color = new Color(165, 203, 175);
    private Font myFont = new Font("Sans-Serif", Font.BOLD, 18);
    private Font myFont2 = new Font("Sans-Serif", Font.PLAIN, 14);
    private GridBagConstraints constraints;
    private int width;
    private int height;

    //EFFECTS: constructs panel for displaying book
    public BookView(BookRoomApplication gui) {
        width = gui.getPanelWidth();
        height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height + 120));
        setBackground(color);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
    }

    //MODIFIES: this
    //EFFECTS: adds information about a book for display
    public void displayBook(Book book) {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel bookTitle = new JLabel(book.getTitle());
        bookTitle.setFont(myFont);
        add(bookTitle, constraints);

        constraints.gridy += 1;

        String genre = displayGenre(book.getGenre());

        String rating = displayRating(book.getRating());

        String textForArea =  "Author: " + book.getAuthor() + "\nGenre: " + genre
                + "\nRating: " + rating + "\nReview: " + book.getReview();
        JTextArea bookInfo = new JTextArea(textForArea);
        bookInfo.setOpaque(false);
        bookInfo.setPreferredSize(new Dimension(width - 80, height + 20));
        bookInfo.setEditable(false);
        bookInfo.setLineWrap(true);
        bookInfo.setWrapStyleWord(true);
        bookInfo.setFont(myFont2);

        add(bookInfo, constraints);
    }

    //EFFECTS: returns string associated with genre or empty string if book is unclassified
    private String displayGenre(Genre genre) {
        String genreString = genre.getString();
        if (genreString.equals(Genre.UNCLASSIFIED.getString())) {
            genreString = "";
        }
        return genreString;
    }

    //EFFECTS: returns rating as a string, or empty string if rating is -1
    private String displayRating(int rating) {
        String ratingString = rating + "/10";
        if (rating == -1) {
            ratingString = "";
        }
        return ratingString;
    }
}
