package gui;

import javax.swing.*;

public class OptionPane extends JFrame {
    private GraphicBookRoom gui;

    //TODO
    public OptionPane(GraphicBookRoom gui) {
        super("Book Room");
        this.gui = gui;
    }

    //TODO
    public void showLoadOption() {
        int load = JOptionPane.showConfirmDialog(null, "Would you like to load your Book Room "
                + "from file?", "Load Book Room", JOptionPane.YES_NO_OPTION);
        if (load == JOptionPane.YES_OPTION) {
            gui.loadBookRoom();
        }
    }

    //TODO
    public void showSaveOption() {
        int save = JOptionPane.showConfirmDialog(null, "Would you like to save this Book Room "
                        + "to file?", "Save Book Room", JOptionPane.YES_NO_OPTION);
        if (save == JOptionPane.YES_OPTION) {
            gui.saveBookRoom();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
