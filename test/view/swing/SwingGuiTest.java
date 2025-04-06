package view.swing;

import controller.SwingController;

import javax.swing.*;
import java.awt.*;
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
    
    /**
     * Creates and displays the test UI.
     */
    private static void createAndShowTestUI() {
        // Create test frame
        JFrame testFrame = new JFrame("Swing GUI Components Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(500, 400);
        testFrame.setLayout(new BorderLayout());
        
        // Create panel with test buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Button to test full GameWindow
        JButton fullGameWindowButton = new JButton("Test Full GameWindow");
        fullGameWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFullGameWindow();
            }
        });
        
        // Button to test GameWindow
        JButton gameWindowButton = new JButton("Test GameWindow Components");
        gameWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTestWindow();
            }
        });
        
        // Button to test AboutDialog
        JButton aboutDialogButton = new JButton("Test AboutDialog");
        aboutDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutDialog dialog = new AboutDialog(testFrame);
                dialog.setVisible(true);
            }
        });
        
        // Button to test EndGameDialog (Win)
        JButton winDialogButton = new JButton("Test EndGameDialog (Win)");
        winDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EndGameDialog dialog = new EndGameDialog(
                    testFrame, 
                    true, 
                    "Congratulations! You found the hidden treasure and escaped the dungeon!"
                );
                dialog.setVisible(true);
            }
        });
        
        // Button to test EndGameDialog (Lose)
        JButton loseDialogButton = new JButton("Test EndGameDialog (Lose)");
        loseDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EndGameDialog dialog = new EndGameDialog(
                    testFrame, 
                    false, 
                    "Game Over! You were defeated by the troll."
                );
                dialog.setVisible(true);
            }
        });
        
        // Add buttons to panel
        panel.add(fullGameWindowButton);
        panel.add(gameWindowButton);
        panel.add(aboutDialogButton);
        panel.add(winDialogButton);
        panel.add(loseDialogButton);
        
        // Add panel to frame
        testFrame.add(panel, BorderLayout.CENTER);
        
        // Add description
        JLabel descLabel = new JLabel("Test for Person 2 Swing GUI Components", SwingConstants.CENTER);
        descLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        descLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        testFrame.add(descLabel, BorderLayout.NORTH);
        
        // Show the test frame
        testFrame.setLocationRelativeTo(null); // Center on screen
        testFrame.setVisible(true);
    }
    
    /**
     * Shows a full GameWindow with stub controller
     */
    private static void showFullGameWindow() {
        // Create a stub controller
        MockSwingController controller = new MockSwingController();
        
        // Create and show the GameWindow
        GameWindow window = new GameWindow("Adventure Game", controller);
        window.setVisible(true);
        
        // Test sending some messages and updates
        window.displayMessage("Welcome to the Adventure Game!");
        window.displayRoom("You are in a dark cave. There are exits to the north and east.");
        window.displayInventory("Sword\nLantern\nKey");
        
        // Update available directions
        window.navigationPanel.updateAvailableDirections(true, true, false, true);
    }
    
    /**
     * Shows a test instance of GameWindow with stub controller
     */
    private static void showTestWindow() {
        // This creates a standalone test window with a simplified demonstration
        JFrame testWindow = new JFrame("GameWindow Test");
        testWindow.setSize(600, 450);
        testWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea description = new JTextArea(
            "This is a demonstration of what the GameWindow would look like.\n\n" +
            "In a real implementation, it would include:\n" +
            "- Room display panel\n" +
            "- Inventory panel\n" +
            "- Navigation buttons\n" +
            "- Action buttons\n" +
            "- Menu bar with game options\n\n" +
            "Person 3 will implement the actual panels, and Person 4 will implement the controller."
        );
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel();
        JButton aboutButton = new JButton("Show About Dialog");
        aboutButton.addActionListener(e -> {
            AboutDialog dialog = new AboutDialog(testWindow);
            dialog.setVisible(true);
        });
        
        JButton gameOverButton = new JButton("Show Game Over Dialog");
        gameOverButton.addActionListener(e -> {
            EndGameDialog dialog = new EndGameDialog(testWindow, false, "Game Over!");
            dialog.setVisible(true);
        });
        
        JButton menuBarButton = new JButton("Show Menu Bar");
        menuBarButton.addActionListener(e -> {
            showMenuBarTest();
        });
        
        buttonPanel.add(aboutButton);
        buttonPanel.add(gameOverButton);
        buttonPanel.add(menuBarButton);
        
        panel.add(new JLabel("GameWindow Test", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(description), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        testWindow.add(panel);
        testWindow.setLocationRelativeTo(null);
        testWindow.setVisible(true);
    }
    
    /**
     * Show the menu bar structure in a test window
     */
    private static void showMenuBarTest() {
        JFrame testWindow = new JFrame("MenuBar Test");
        testWindow.setSize(400, 300);
        testWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JTextArea content = new JTextArea(
            "This window demonstrates the MenuBarSetup class.\n\n" +
            "The menu bar should contain:\n" +
            "Game menu: Save, Load, Exit\n" +
            "Help menu: Commands, About\n\n" +
            "Note: Since the actual MenuBarSetup requires a GameWindow and SwingController,\n" +
            "this test is showing a manually created menu bar with the same structure."
        );
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        
        testWindow.add(new JScrollPane(content));
        
        // Create menu bar manually to demonstrate the structure
        JMenuBar menuBar = createManualMenuBar();
        testWindow.setJMenuBar(menuBar);
        
        testWindow.setLocationRelativeTo(null);
        testWindow.setVisible(true);
    }
    
    /**
     * Create a menu bar manually as a demonstration of MenuBarSetup's output
     */
    private static JMenuBar createManualMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Game menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem saveItem = new JMenuItem("Save Game");
        JMenuItem loadItem = new JMenuItem("Load Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        saveItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Save Game would be called here"));
        loadItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Load Game would be called here"));
        exitItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to exit?", 
                "Confirm Exit", 
                JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Exit function would be called here");
            }
        });
        
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem commandsItem = new JMenuItem("Commands");
        JMenuItem aboutItem = new JMenuItem("About");
        
        commandsItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                null,
                "Game Commands:\n\n" +
                "north, n - Move north\n" +
                "south, s - Move south\n" +
                "east, e - Move east\n" +
                "west, w - Move west\n" +
                "look, l - Look at current room\n" +
                "inventory, i - Show inventory\n" +
                "take [item] - Take an item\n" +
                "drop [item] - Drop an item\n" +
                "use [item] - Use an item\n" +
                "examine [item] - Examine an item\n" +
                "attack - Attack a monster\n" +
                "answer [text] - Answer a puzzle",
                "Game Commands",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                null,
                "This would show the About Dialog in a real implementation",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        helpMenu.add(commandsItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }
    
    /**
     * Mock implementation of SwingController for testing.
     * This allows testing the GameWindow without requiring Person 4's implementation.
     */
    private static class MockSwingController extends SwingController {
        public MockSwingController() {
            super(null); // Pass null for GameWorld argument
        }
        
        // Override methods that would normally interact with GameWorld
        public void executeCommand(String command) {
            System.out.println("Command executed: " + command);
        }
        
        public void saveGame() {
            JOptionPane.showMessageDialog(null, "Game saved (mock implementation)");
        }
        
        public void loadGame() {
            JOptionPane.showMessageDialog(null, "Game loaded (mock implementation)");
        }
        
        public void quitGame() {
            int option = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to quit?", 
                "Confirm Exit", 
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Game would exit here");
            }
        }
    }
} 