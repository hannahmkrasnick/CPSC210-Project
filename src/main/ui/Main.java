package ui;

import java.io.FileNotFoundException;

public class Main {
    // solution adapted from JsonSerializationDemo CPSC 210 program (Main.main)
    public static void main(String[] args) {
        try {
            new MyBookRoom();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
