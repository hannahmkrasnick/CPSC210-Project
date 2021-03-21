package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePanel extends JPanel implements ActionListener {
    private GraphicBookRoom gui;
    protected GridBagConstraints constraints;

    //TODO
    public ChangePanel(GraphicBookRoom gui) {
        this.gui = gui;
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
    }

    //TODO
    public void makeAddButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton addButton = new JButton("Add a book");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        add(addButton, constraints);
    }

    //TODO
    public void makeEditBookButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        JButton editBookButton = new JButton("Edit a book");
        editBookButton.setActionCommand("edit");
        editBookButton.addActionListener(this);
        add(editBookButton, constraints);
    }

    //TODO
    public void makeDeleteButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        JButton deleteButton = new JButton("Delete a book");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        add(deleteButton, constraints);
    }

    //TODO
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
        }
    }
}
