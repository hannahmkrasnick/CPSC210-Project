package ui.gui;

import model.Bookshelf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Represents a panel that allows user to delete a bookshelf from app
public class DeleteBookshelfView extends ChangePanel implements ActionListener {
    private JTextField inputField;
    private BookRoomApplication gui;
    private static final String newline = "\n";
    private static final int textFieldColumns = 15;

    //EFFECTS: constructs a ChangePanel for user to input which bookshelf they'd like to delete from app
    public DeleteBookshelfView(BookRoomApplication gui) {
        super(gui);
        this.gui = gui;

        Font myFont = new Font("Sans-Serif", Font.BOLD, 14);
        JLabel text = new JLabel("Enter label of bookshelf to delete");
        text.setFont(myFont);
        add(text, constraints);
        constraints.gridy += 1;
        inputField = new JTextField(textFieldColumns);
        inputField.setActionCommand(newline);
        inputField.addActionListener(this);
        add(inputField, constraints);
        JLabel warning = new JLabel("Note: Bookshelf All Books cannot be deleted");
        constraints.gridy += 1;
        add(warning, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: if prompted, deletes bookshelf from room and changes panel back to main ChangePanel
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Bookshelf> shelves = gui.getBookRoom().getShelves();

        if (e.getActionCommand().equals(newline)) {
            String input = inputField.getText();
            Bookshelf deleteShelf = new Bookshelf(input);
            if (!input.equalsIgnoreCase("all books")) {
                for (Bookshelf bs : shelves) {
                    if (bs.equals(deleteShelf)) {
                        gui.getBookRoom().getShelves().remove(bs);
                        gui.changeBookshelvesView();
                        break;
                    }
                }
            }
        }
        gui.changeToChangePanel();
    }
}