package ui.gui;

import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a JPanel for a single bookshelf, with option to view books on that shelf
public class BookshelfView extends JPanel implements ActionListener {
    private Bookshelf bookshelf;
    private BookRoomApplication gui;
    private Color color = new Color(253, 211, 187);

    //EFFECTS: constructs a JPanel with Bookshelf label and button to view book
    public BookshelfView(Bookshelf bs, BookRoomApplication gui) {
        this.gui = gui;
        this.bookshelf = bs;
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height / 5));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
        setBorder(lineBorder);
        setBackground(color);
        JLabel label = new JLabel(bs.getBookshelfLabel());
        JButton viewButton = new JButton("View");
        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        add(label);
        add(viewButton);
    }

    // getter
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    //EFFECTS: changes books display to books on the shelf
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            gui.changeBooksDisplay(bookshelf);
        }
    }
}
