package gui;

import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BookshelfView extends JPanel implements ActionListener {
    private Bookshelf bookshelf;
    private GraphicBookRoom gui;

    //TODO
    public BookshelfView(Bookshelf bs, GraphicBookRoom gui) {
        this.gui = gui;
        this.bookshelf = bs;
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height / 5));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
        setBorder(lineBorder);
        setBackground(Color.PINK);
        JLabel label = new JLabel(bs.getBookshelfLabel());
        JButton viewButton = new JButton("View");
        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        add(label);
        add(viewButton);
    }

    //TODO
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            gui.changeBooksDisplay(bookshelf);
        }
    }
}
