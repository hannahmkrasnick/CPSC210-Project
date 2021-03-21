package ui.gui;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//Represents pop up windows for getting information from user or telling user info
public class OptionPane extends JFrame {
    private BookRoomApplication gui;

    //EFFECTS: constructs frame for JOptionPanes
    public OptionPane(BookRoomApplication gui) {
        super("Book Room");
        this.gui = gui;
    }

    //EFFECTS: asks user if they want to load book room from file and processes response
    public void showLoadOption() {
        int load = JOptionPane.showConfirmDialog(null, "Would you like to load your Book Room "
                + "from file?", "Load Book Room", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (load == JOptionPane.YES_OPTION) {
            gui.loadBookRoom();
        }
    }

    //EFFECTS: asks user if they want to save book room to file and processes response
    public void showSaveOption() {
        int save = JOptionPane.showConfirmDialog(null, "Would you like to save this Book Room "
                + "to file?", "Save Book Room", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (save == JOptionPane.YES_OPTION) {
            gui.saveBookRoom();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    //EFFECTS: tells user there was an error loading book room
    public void loadError() {
        JOptionPane.showMessageDialog(null, "Error loading Book Room", "Load Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: tells user there was an error saving book room
    public void saveError() {
        JOptionPane.showMessageDialog(null, "Error saving Book Room", "Save Error",
                JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: confirms to user that book was added and plays a fun confirmation sound
    public void confirmBookAddedPane() throws IOException {
        playSound();
        JOptionPane.showConfirmDialog(null, "Book was successfully added!",
                "Success", JOptionPane.DEFAULT_OPTION);
    }

    //solution adapted from https://alvinalexander.com/java/java-audio-example-java-au-play-sound/
    //EFFECTS: plays audio from file
    public void playSound() throws IOException {
        String successSound = "/Users/hannahmadden-krasnick/OneDrive/"
                + "School/BCS year 1/CPSC 210/Project/lib/SuccessSound.wav";
        InputStream in = new FileInputStream(successSound);

        AudioStream audioStream = new AudioStream(in);

        AudioPlayer.player.start(audioStream);
    }
}
