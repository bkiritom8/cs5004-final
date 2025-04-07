package view.swing;

import model.Direction;
import model.Item;
import model.Room;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class to demonstrate the Swing GUI components.
 * This includes GameWindow, AboutDialog, EndGameDialog, and MenuBarSetup.
 * 
 * This test class uses simple objects without dependencies on the controller.
 */
public class SwingGuiTest {
    
    /**
     * Main method to run the test.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Run on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> createAndShowTestUI());
    }
    
    /**
     * Creates and shows the test UI components.
     */
    private static void createAndShowTestUI() {
        // Create a test frame
        JFrame testFrame = new JFrame("Swing GUI Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(600, 400);
        testFrame.setLocationRelativeTo(null);
        
        // Add test buttons to demonstrate dialogs
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.GridLayout(4, 1, 10, 10));
        
        // Button to show About dialog
        javax.swing.JButton aboutButton = new javax.swing.JButton("Show About Dialog");
        aboutButton.addActionListener(e -> {
            AboutDialog dialog = new AboutDialog(testFrame);
            dialog.setVisible(true);
        });
        
        // Button to show Win dialog
        javax.swing.JButton winButton = new javax.swing.JButton("Show Win Dialog");
        winButton.addActionListener(e -> {
            EndGameDialog dialog = new EndGameDialog(
                testFrame, true, "Congratulations! You found the treasure!"
            );
            dialog.setVisible(true);
        });
        
        // Button to show Game Over dialog
        javax.swing.JButton loseButton = new javax.swing.JButton("Show Game Over Dialog");
        loseButton.addActionListener(e -> {
            EndGameDialog dialog = new EndGameDialog(
                testFrame, false, "Game Over! You have been defeated."
            );
            dialog.setVisible(true);
        });
        
        // Button to show Menu Bar Test
        javax.swing.JButton menuBarButton = new javax.swing.JButton("Show Menu Bar Test");
        menuBarButton.addActionListener(e -> {
            showMenuBarTest();
        });
        
        // Add buttons to panel
        panel.add(aboutButton);
        panel.add(winButton);
        panel.add(loseButton);
        panel.add(menuBarButton);
        
        // Add panel to frame
        testFrame.add(panel);
        
        // Show the frame
        testFrame.setVisible(true);
        
        // Display message
        JOptionPane.showMessageDialog(testFrame, 
            "This test demonstrates the dialog components.\n" +
            "Use the buttons to show different dialogs.");
    }
    
    /**
     * Shows a test window with a menu bar to demonstrate MenuBarSetup structure.
     */
    private static void showMenuBarTest() {
        JFrame menuTestFrame = new JFrame("Menu Bar Test");
        menuTestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuTestFrame.setSize(400, 300);
        menuTestFrame.setLocationRelativeTo(null);
        
        // Create a custom menu bar for demonstration purposes
        JMenuBar menuBar = new JMenuBar();
        
        // Game menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.add(new JMenuItem("Save Game"));
        gameMenu.add(new JMenuItem("Load Game"));
        gameMenu.addSeparator();
        gameMenu.add(new JMenuItem("Exit"));
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("About"));
        
        // Add menus to menu bar
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        
        // Add menu bar to frame
        menuTestFrame.setJMenuBar(menuBar);
        
        // Add description text
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(
            "This window demonstrates the menu bar structure.\n\n" +
            "In the real implementation, MenuBarSetup would create a menu bar with:\n" +
            "- Game menu: Save Game, Load Game, Exit\n" +
            "- Help menu: About\n\n" +
            "The actions would be connected to controller methods."
        );
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // Add text area to frame with scrolling
        menuTestFrame.add(new javax.swing.JScrollPane(textArea));
        
        // Show the frame
        menuTestFrame.setVisible(true);
    }
} 