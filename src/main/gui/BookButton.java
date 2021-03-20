package gui;

import model.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookButton extends JPanel implements ActionListener {
    private Book book;
    private GraphicBookRoom gui;

    //TODO
    public BookButton(Book b, GraphicBookRoom gui) {
        this.book = b;
        this.gui = gui;
        JButton button = new JButton(b.getTitle());
        button.setActionCommand(b.getTitle());
        button.addActionListener(this);
        add(button);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(book.getTitle())) {
            gui.changeBookDisplay(book);
        }
    }
}
