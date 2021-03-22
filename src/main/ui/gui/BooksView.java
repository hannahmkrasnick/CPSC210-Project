package ui.gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// JPanel with buttons for all books on given shelf
public class BooksView extends JPanel {
    private Color color = new Color(247, 233, 197);

    //EFFECTS: constructs JPanel for displaying shelves
    public BooksView(BookRoomApplication gui) {
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height));
        setBackground(color);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
        setBorder(lineBorder);
    }

    //MODIFIES: this
    //EFFECTS: displays bookshelf with buttons for all books on shelf
    public void showBookshelfWithBooks(BookRoomApplication gui, Bookshelf bs) {
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();

        Font myFont = new Font("Sans-Serif", Font.BOLD, 16);
        JLabel bookshelfLabel = new JLabel(bs.getBookshelfLabel(), SwingConstants.CENTER);
        bookshelfLabel.setFont(myFont);
        bookshelfLabel.setPreferredSize(new Dimension(width, height / 6));
        add(bookshelfLabel);
        for (Book b : bs.getBooksOnShelf()) {
            BookButton bookButton = new BookButton(b, gui);
            add(bookButton);
        }
    }
}
