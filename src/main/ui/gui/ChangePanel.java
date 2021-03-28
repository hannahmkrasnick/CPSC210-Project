package ui.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a panel for user to edit application
public class ChangePanel extends JPanel implements ActionListener {
    private BookRoomApplication gui;
    protected GridBagConstraints constraints;
    private Color color = new Color(208, 217, 168);

    //EFFECTS: constructs panel where changes to book room will be made
    public ChangePanel(BookRoomApplication gui) {
        this.gui = gui;
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        setPreferredSize(new Dimension(width, height + 120));
        setBackground(color);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for adding a new book and adds it to this
    public void makeAddButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton addButton = new JButton("Add a book");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        add(addButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for editing a book and adds it to this
    public void makeEditBookButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        JButton editBookButton = new JButton("Edit a book");
        editBookButton.setActionCommand("edit");
        editBookButton.addActionListener(this);
        add(editBookButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for deleting a book and adds it to this
    public void makeDeleteButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        JButton deleteButton = new JButton("Delete a book");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        add(deleteButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for searching for a book and adds it to this
    public void makeSearchButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        JButton searchButton = new JButton("Search books");
        searchButton.setActionCommand("search");
        searchButton.addActionListener(this);
        add(searchButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for adding a bookshelf and adds it to this
    public void makeAddBookshelfButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        JButton addShelfButton = new JButton("Add a bookshelf");
        addShelfButton.setActionCommand("addShelf");
        addShelfButton.addActionListener(this);
        add(addShelfButton, constraints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button for deleting a bookshelf and adds it to this
    public void makeDeleteBookshelfButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        JButton deleteShelfButton = new JButton("Delete a bookshelf");
        deleteShelfButton.setActionCommand("deleteShelf");
        deleteShelfButton.addActionListener(this);
        add(deleteShelfButton, constraints);
    }

    //MODIFIES: gui
    //EFFECTS: switches edit panel in gui depending on which button was pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "edit":
                gui.changeToEditView();
                break;
            case "add":
                gui.changeToAddBookView();
                break;
            case "delete":
                gui.changeToDeleteBookView();
                break;
            case "search":
                gui.changeToSearchBookView();
                break;
            case "addShelf":
                gui.changeToAddBookshelfView();
                break;
            case "deleteShelf":
                gui.changeToDeleteBookshelfView();
                break;
        }
    }
}
