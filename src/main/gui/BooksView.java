package gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooksView extends JPanel implements ActionListener {
    private static int width;
    private static int height;
    private JLabel bookshelfLabel;
    private Font myFont;
    private GridBagConstraints constraints;
    private Bookshelf bookshelf;
    private GraphicBookRoom gui;
    private BookButton bookButton;

    //TODO
    public BooksView(GraphicBookRoom gui, Bookshelf bs) {
        this.gui = gui;
        this.bookshelf = bs;
        this.width = gui.getPanelWidth();
        this.height = gui.getPanelHeight();
        myFont = new Font("Sans-Serif", Font.BOLD, 16);
        setPreferredSize(new Dimension(width, height));
        bookshelfLabel = new JLabel(bs.getBookshelfLabel(), SwingConstants.CENTER);
        bookshelfLabel.setFont(myFont);
        bookshelfLabel.setPreferredSize(new Dimension(width, height / 5));
        add(bookshelfLabel);
        for (Book b : bs.getBooksOnShelf()) {
            bookButton = new BookButton(b, gui);
            add(bookButton);
        }
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {}
}
