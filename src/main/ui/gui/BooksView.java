package ui.gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;

// JPanel with buttons for all books on given shelf
public class BooksView extends JPanel {
    private Color color = new Color(247, 233, 197);

    //EFFECTS: constructs JPanel with buttons for all books on given shelf
    public BooksView(BookRoomApplication gui, Bookshelf bs) {
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        Font myFont = new Font("Sans-Serif", Font.BOLD, 16);
        setBackground(color);
        setPreferredSize(new Dimension(width, height));
        JLabel bookshelfLabel = new JLabel(bs.getBookshelfLabel(), SwingConstants.CENTER);
        bookshelfLabel.setFont(myFont);
        bookshelfLabel.setPreferredSize(new Dimension(width, height / 5));
        add(bookshelfLabel);
        for (Book b : bs.getBooksOnShelf()) {
            BookButton bookButton = new BookButton(b, gui);
            add(bookButton);
        }
    }
}
