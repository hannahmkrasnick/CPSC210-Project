package ui.gui;

import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// Represents a panel with bookshelves
public class BookshelvesPanel extends JPanel {
    private GridBagConstraints constraints;
    private Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
    private Font myFont = new Font("Sans-Serif", Font.BOLD, 18);
    private BookRoomApplication gui;
    private int width;
    private int height;

    //EFFECTS: displays bookshelves currently in room
    public BookshelvesPanel(BookRoomApplication gui) {
        this.gui = gui;
        width = gui.getPanelWidth();
        height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        drawBookRoomLabel(constraints);

        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        addBookshelfPanels(constraints);
    }

    //MODIFIES: this
    //EFFECTS: adds panels for each bookshelf to this
    private void addBookshelfPanels(GridBagConstraints constraints) {
        this.constraints = constraints;
        for (Bookshelf bs : gui.getBookRoom().getShelves()) {
            BookshelfView bookshelfView = new BookshelfView(bs, this.gui);
            if (constraints.gridx == 0) {
                constraints.gridx = 1;
            } else {
                constraints.gridx = 0;
                constraints.gridy += 1;
            }
            add(bookshelfView, constraints);
        }
    }

    //EFFECTS: creates label for above bookshelves
    private void drawBookRoomLabel(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel("Your Bookshelves:", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setBorder(lineBorder);
        label.setFont(myFont);
        label.setPreferredSize(new Dimension(width, height / 5));
        add(label, constraints);
    }
}
