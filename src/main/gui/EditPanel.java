package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel implements ActionListener {
    private GraphicBookRoom gui;
    private static int width = 300;
    private static int height = 200;
    private JButton addButton;
    private JButton removeButton;
    private JButton editBookButton;
    private GridBagConstraints constraints;

    public EditPanel(GraphicBookRoom gui) {
        this.gui = gui;
        constraints = new GridBagConstraints();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
        makeAddButton(constraints);
        constraints.gridy += 1;
        makeRemoveButton(constraints);
        constraints.gridy += 1;
        makeEditBookButton(constraints);
    }

    private void makeAddButton(GridBagConstraints constraints) {
        addButton = new JButton("Add a book");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        add(addButton, constraints);
    }

    private void makeRemoveButton(GridBagConstraints constraints) {
        removeButton = new JButton("Remove a book");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        add(removeButton, constraints);
    }

    private void makeEditBookButton(GridBagConstraints constraints) {
        editBookButton = new JButton("Edit a book");
        editBookButton.setActionCommand("edit");
        editBookButton.addActionListener(this);
        add(editBookButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
