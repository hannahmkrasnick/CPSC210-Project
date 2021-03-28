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
    private int width;
    private int height;
    private GridBagConstraints constraints;

    //EFFECTS: constructs a JPanel with Bookshelf label and button to view book
    public BookshelfView(Bookshelf bs, BookRoomApplication gui) {
        this.gui = gui;
        this.bookshelf = bs;
        width = gui.getPanelWidth();
        height = gui.getPanelHeight();
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(width / 2, calculateBookshelfHeight()));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
        setBorder(lineBorder);
        setBackground(color);
        JLabel label = new JLabel(bs.getBookshelfLabel());
        JButton viewButton = new JButton("View");
        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        add(label, constraints);
        add(viewButton, constraints);
    }

    // getter
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    //EFFECTS: changes books display to books on the shelf
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            gui.changeBooksDisplay(bookshelf, 0);
        }
    }

    //EFFECTS: calculates and returns the proper height for each shelf
    // https://stackoverflow.com/questions/7342237/check-whether-number-is-even-or-odd
    private int calculateBookshelfHeight() {
        int numShelves = gui.getBookRoom().getShelves().size();
        int numRows;
        if ((numShelves % 2) == 0) {
            numRows = numShelves / 2;
        } else {
            numRows = numShelves / 2 + 1;
        }
        int heightOfShelvesPortion = height / 5 * 4;
        return heightOfShelvesPortion / numRows;
    }

}
