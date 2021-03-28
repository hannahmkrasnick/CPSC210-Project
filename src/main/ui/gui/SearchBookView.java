package ui.gui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents a panel that allows user to search for a book
public class SearchBookView extends ChangePanel implements ActionListener {
    private JTextField inputField;
    private BookRoomApplication gui;
    private static final String newline = "\n";
    private static final int textFieldColumns = 15;

    //EFFECTS: constructs a ChangePanel for user to input which book they'd like to find
    public SearchBookView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;

        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter title of book to search");
        text.setFont(myFont);
        add(text, constraints);
        constraints.gridy += 1;
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        add(inputField, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: if prompted, displays book and changes panel back to main ChangePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Book> allBooks = gui.getShelfWithLabel("All Books").getBooksOnShelf();

        if (e.getActionCommand().equals(newline)) {
            String input = inputField.getText();
            for (Book b : allBooks) {
                if (input.equalsIgnoreCase(b.getTitle())) {
                    gui.changeBookDisplay(b);
                    gui.revalidate();
                    break;
                }
            }
            gui.changeToChangePanel();
        }
    }
}
