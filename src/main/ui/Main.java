package ui;

import ui.CollectionApp;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    //Creates the GUI and displays it to the user
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("AlbumCollection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = null;
        try {
            newContentPane = new CollectionApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}