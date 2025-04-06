package view.swing;

import controller.SwingController;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Utility class for creating and configuring the application menu bar.
 */
public class MenuBarSetup {
  
  /**
   * Creates a menu bar for the game window.
   * 
   * @param gameWindow The parent game window
   * @param controller The controller for handling menu actions
   * @return The configured JMenuBar
   */
  public static JMenuBar createMenuBar(GameWindow gameWindow, SwingController controller) {
    JMenuBar menuBar = new JMenuBar();
    
    // Game menu
    JMenu gameMenu = new JMenu("Game");
    gameMenu.setMnemonic(KeyEvent.VK_G);
    
    JMenuItem newGameItem = new JMenuItem("New Game", KeyEvent.VK_N);
    newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    newGameItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Show confirmation dialog if game in progress
        int option = JOptionPane.showConfirmDialog(
            gameWindow,
            "Start a new game? Your current progress will be lost.",
            "New Game",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
          // Start new game (this would be handled by the controller)
        }
      }
    });
    
    JMenuItem saveItem = new JMenuItem("Save Game", KeyEvent.VK_S);
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    saveItem.addActionListener(e -> controller.saveGame());
    
    JMenuItem loadItem = new JMenuItem("Load Game", KeyEvent.VK_L);
    loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
    loadItem.addActionListener(e -> controller.loadGame());
    
    JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
    exitItem.addActionListener(e -> controller.quitGame());
    
    // Add items to game menu
    gameMenu.add(newGameItem);
    gameMenu.addSeparator();
    gameMenu.add(saveItem);
    gameMenu.add(loadItem);
    gameMenu.addSeparator();
    gameMenu.add(exitItem);
    
    // View menu
    JMenu viewMenu = new JMenu("View");
    viewMenu.setMnemonic(KeyEvent.VK_V);
    
    JCheckBoxMenuItem showStatusBarItem = new JCheckBoxMenuItem("Show Status Bar");
    showStatusBarItem.setSelected(true);
    showStatusBarItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Toggle status bar visibility (would be implemented in GameWindow)
        // gameWindow.toggleStatusBar(showStatusBarItem.isSelected());
      }
    });
    
    JMenuItem clearMessagesItem = new JMenuItem("Clear Messages");
    clearMessagesItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Clear message area (would be implemented in GameWindow)
        // gameWindow.clearMessages();
      }
    });
    
    // Add items to view menu
    viewMenu.add(showStatusBarItem);
    viewMenu.add(clearMessagesItem);
    
    // Help menu
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    
    JMenuItem commandsItem = new JMenuItem("Commands", KeyEvent.VK_C);
    commandsItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Display commands help
        String helpText =
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
            "answer [text] - Answer a puzzle\n";
        
        JOptionPane.showMessageDialog(
            gameWindow,
            helpText,
            "Game Commands",
            JOptionPane.INFORMATION_MESSAGE
        );
      }
    });
    
    JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
    aboutItem.addActionListener(e -> gameWindow.showAboutDialog());
    
    // Add items to help menu
    helpMenu.add(commandsItem);
    helpMenu.addSeparator();
    helpMenu.add(aboutItem);
    
    // Add menus to menu bar
    menuBar.add(gameMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);
    
    return menuBar;
  }
} 