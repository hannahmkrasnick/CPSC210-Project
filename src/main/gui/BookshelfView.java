package gui;

import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BookshelfView extends JPanel implements ActionListener {
    private Bookshelf bookshelf;
    private static int width = 250;
    private static int height = 50;
    private BooksView booksView;
    private JButton viewButton;
    private GraphicBookRoom gui;

    public BookshelfView(Bookshelf bs, GraphicBookRoom gui) {
        this.gui = gui;
        this.bookshelf = bs;
        setPreferredSize(new Dimension(width, height));
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
        setBorder(lineBorder);
        setBackground(Color.PINK);
        JLabel label = new JLabel(bs.getBookshelfLabel());
        viewButton = new JButton("View");
        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        add(label);
        add(viewButton);
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            gui.changeContentView(bookshelf);
        }
    }
}
