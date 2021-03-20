package gui;

import model.Book;
import model.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteBookView extends ChangePanel implements ActionListener {
    private JTextField inputField;
    private GraphicBookRoom gui;
    private static final String newline = "\n";
    private static final int textFieldColumns = 15;
    private List<Book> allBooks;


    public DeleteBookView(GraphicBookRoom gui) {
        super(gui);
        this.gui = gui;

        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter title of book to delete");
        text.setFont(myFont);
        add(text, constraints);
        constraints.gridy += 1;
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        add(inputField, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        allBooks = gui.getAllBooks().getBooksOnShelf();

        if (e.getActionCommand().equals(newline)) {
            String input = inputField.getText();
            for (Book b : allBooks) {
                if (input.equalsIgnoreCase(b.getTitle())) {
                    gui.getBookRoom().deleteBookFromBookRoom(b);
                    break;
                }
            }
            gui.changeToChangePanel();
            gui.revalidate();
        }
    }
}