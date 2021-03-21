package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button that is associated with a book
public class BookButton extends JButton implements ActionListener {
    private Book book;
    private BookRoomApplication gui;

    //EFFECTS: creates button from given book
    public BookButton(Book b, BookRoomApplication gui) {
        this.book = b;
        this.gui = gui;
        setText(b.getTitle());
        setActionCommand(b.getTitle());
        addActionListener(this);
    }

    //MODIFIES: gui
    //EFFECTS: changes book display on main frame to book when this is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(book.getTitle())) {
            gui.changeBookDisplay(book);
        }
    }
}
