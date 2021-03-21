package gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;


public class BooksView extends JPanel {

    //TODO
    public BooksView(GraphicBookRoom gui, Bookshelf bs) {
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        Font myFont = new Font("Sans-Serif", Font.BOLD, 16);
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
