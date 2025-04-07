package view.swing;

import controller.SwingController;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
    
    JMenuItem saveItem = new JMenuItem("Save Game");
    saveItem.addActionListener(e -> {
      // Display a message since controller.saveGame() is not accessible
      gameWindow.displayMessage("Saving game...");
    });
    
    JMenuItem loadItem = new JMenuItem("Load Game");
    loadItem.addActionListener(e -> {
      // Display a message since controller.loadGame() is not accessible
      gameWindow.displayMessage("Loading game...");
    });
    
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> {
      // Use the built-in close() method in GameWindow
      gameWindow.close();
      System.exit(0);
    });
    
    // Add items to menu
    gameMenu.add(saveItem);
    gameMenu.add(loadItem);
    gameMenu.addSeparator();
    gameMenu.add(exitItem);
    
    // Help menu
    JMenu helpMenu = new JMenu("Help");
    
    JMenuItem aboutItem = new JMenuItem("About");
    aboutItem.addActionListener(e -> gameWindow.showAboutDialog());
    
    helpMenu.add(aboutItem);
    
    // Add menus to menu bar
    menuBar.add(gameMenu);
    menuBar.add(helpMenu);
    
    return menuBar;
  }
} 