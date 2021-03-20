package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePanel extends JPanel implements ActionListener {
    private GraphicBookRoom gui;
    private static int width;
    private static int height;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editBookButton;
    protected GridBagConstraints constraints;

    //TODO
    public ChangePanel(GraphicBookRoom gui) {
        this.gui = gui;
        this.width = gui.getPanelWidth();
        this.height = gui.getPanelHeight();
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
        addButton = new JButton("Add a book");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        add(addButton, constraints);
    }

    //TODO
    public void makeEditBookButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        editBookButton = new JButton("Edit a book");
        editBookButton.setActionCommand("edit");
        editBookButton.addActionListener(this);
        add(editBookButton, constraints);
    }

    //TODO
    public void makeDeleteButton() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        deleteButton = new JButton("Delete a book");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        add(deleteButton, constraints);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit")) {
            gui.changeToEditView();
        } else if (e.getActionCommand().equals("add")) {
            gui.changeToAddBookView();
        } else if (e.getActionCommand().equals("delete")) {
            gui.changeToDeleteBookView();
        }
    }
}
