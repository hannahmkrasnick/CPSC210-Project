package gui;

import model.Book;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BookView extends JPanel {

    //TODO
    public BookView(GraphicBookRoom gui) {
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.getHSBColor(63, 41, 82));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
    }

    //TODO
    public void displayBook(Book b) {
        Font myFont = new Font("Sans-Serif", Font.BOLD, 18);

        Box box = Box.createVerticalBox();

        JLabel bookTitle = new JLabel(b.getTitle());
        bookTitle.setFont(myFont);
        box.add(bookTitle);

        JLabel bookAuthor = new JLabel("Author: " + b.getAuthor());
        bookAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookAuthor);

        JLabel bookGenre = new JLabel("Genre: " + String.valueOf(b.getGenre()).toLowerCase());
        bookGenre.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookGenre);

        JLabel bookRating = new JLabel("Rating:");
        if (b.getRating() >= 1 && b.getRating() <= 10) {
            bookRating.setText("Rating: " + b.getRating() + "/10");
        }
        bookRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookRating);

        JLabel bookReview = new JLabel("Review: " + b.getReview());
        bookReview.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookReview);

        add(box);
    }
}
