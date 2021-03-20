package gui;

import model.Book;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BookView extends JPanel {
    private Book book;
    private static int width;
    private static int height;
    private Font myFont;
    private GraphicBookRoom gui;
    private GridBagConstraints constraints;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JLabel bookGenre;
    private JLabel bookRating;
    private JLabel bookReview;

    //TODO
    public BookView(GraphicBookRoom gui) {
        this.gui = gui;
        width = gui.getPanelWidth();
        height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.getHSBColor(63, 41, 82));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
    }

    //TODO
    public void displayBook(Book b) {
        this.book = b;
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        myFont = new Font("Sans-Serif", Font.BOLD, 18);

        Box box = Box.createVerticalBox();

        bookTitle = new JLabel(b.getTitle());
        bookTitle.setFont(myFont);
        box.add(bookTitle);

        bookAuthor = new JLabel("Author: " + b.getAuthor());
        bookAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookAuthor);

        bookGenre = new JLabel("Genre: " + String.valueOf(b.getGenre()).toLowerCase());
        bookGenre.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookGenre);

        bookRating = new JLabel("Rating: " + b.getRating() + "/10");
        bookRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookRating);

        bookReview = new JLabel("Review: " + b.getReview());
        bookReview.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookReview);

        add(box, constraints);
    }
}
