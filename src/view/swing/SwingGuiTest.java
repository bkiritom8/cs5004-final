package view.swing;

import controller.SwingController;
import view.GameView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Test class to demonstrate the Swing GUI components.
 * This includes GameWindow, AboutDialog, EndGameDialog, and MenuBarSetup.
 */
public class SwingGuiTest {
    
    /**
     * Main method to run the test.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Run on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowTestUI();
            }
        });
    }
    
    // Rest of class unchanged...
} 