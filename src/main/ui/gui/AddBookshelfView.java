package ui.gui;

import exceptions.DuplicateBookshelfNameException;
import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents a panel that allows user to add a bookshelf to app
public class AddBookshelfView extends ChangePanel implements ActionListener {
    private JTextField inputField;
    private BookRoomApplication gui;
    private static final String newline = "\n";
    private static final int textFieldColumns = 15;

    //MODIFIES: gui
    //EFFECTS: displays a panel with option for adding a new bookshelf and instructions
    public AddBookshelfView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;

        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter label of new bookshelf");
        text.setFont(myFont);
        add(text, constraints);
        constraints.gridy += 1;
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        add(inputField, constraints);
        JTextArea instructions = new JTextArea("Note: Book Room can hold max 8 bookshelves. "
                + "\nBookshelf must have unique name. \nMax. 13 characters.");
        instructions.setOpaque(false);
        instructions.setEditable(false);
        constraints.gridy += 1;
        add(instructions, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: if enter pressed, creates a new bookshelf with user input. Adds bookshelf unless input is empty string
    // or bookroom already has 8 shelves or shelf already exists with same label
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();

        if (e.getActionCommand().equals(newline)) {
            String input = Bookshelf.makeLabelRightLengthForGui(inputField.getText());
            Bookshelf newShelf = new Bookshelf(input);
            try {
                if (!input.equals("") && (shelves.size() < 8)) {
                    gui.getBookRoom().addShelfToRoom(newShelf);
                    gui.changeBookshelvesView();
                }
            } catch (DuplicateBookshelfNameException de) {
                gui.getOptionPane().generalErrorPain(de.getMessage());
            } finally {
                gui.changeToChangePanel();
            }
        }
    }
}
