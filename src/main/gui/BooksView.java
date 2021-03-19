package gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooksView extends JPanel implements ActionListener {
    private static int width = 250;
    private static int height = 250;
    private JLabel bookshelfLabel;
    private Font myFont;
    private GridBagConstraints constraints;
    private Bookshelf bookshelf;
    private GraphicBookRoom gui;
    private BookButton bookButton;

    public BooksView(GraphicBookRoom gui, Bookshelf bs) {
        this.gui = gui;
        this.bookshelf = bs;
        myFont = new Font("Sans-Serif", Font.BOLD, 16);
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        bookshelfLabel = new JLabel(bs.getBookshelfLabel());
        bookshelfLabel.setFont(myFont);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(bookshelfLabel, constraints);
        for (Book b : bs.getBooksOnShelf()) {
            bookButton = new BookButton(b, gui);
            constraints.gridy += 1;
            add(bookButton, constraints);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
