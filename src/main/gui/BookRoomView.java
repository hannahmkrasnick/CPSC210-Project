package gui;

import model.Book;
import model.BookRoom;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BookRoomView extends JPanel {
    private BookRoom bookRoom;
    private Book book;
    private static int width = 300;
    private static int height = 250;
    private Font myFont;
    private GraphicBookRoom gui;
    private GridBagConstraints constraints;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JLabel bookGenre;
    private JLabel bookRating;
    private JLabel bookReview;

    public BookRoomView(GraphicBookRoom gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.getHSBColor(63, 41, 82));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
    }

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

        bookGenre = new JLabel("Genre: " + String.valueOf(b.getGenre()));
        bookGenre.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookGenre);

        bookRating = new JLabel("Rating: " + String.valueOf(b.getRating()) + "/10");
        bookRating.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookRating);

        bookReview = new JLabel("Review: " + b.getReview());
        bookReview.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(bookReview);

        add(box, constraints);
    }
}
