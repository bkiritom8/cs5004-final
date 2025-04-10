package view.swing;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.SwingController;
import model.Item;
import model.Room;
import view.GameView;

/**
 * Main window for the game's GUI interface.
 * Implements GameView interface and contains all GUI panels.
 */
public class GameWindow extends JFrame implements GameView {
  protected RoomPanel roomPanel;
  protected InventoryPanel inventoryPanel;
  protected NavigationPanel navigationPanel;
  protected ActionPanel actionPanel;
  protected SwingController controller;
  
  /**
   * Constructs a new GameWindow.
   *
   * @param title Window title.
   *
   * @param controller The controller handling game logic.
   */
  public GameWindow(String title, SwingController controller) {
    // Set up main window
    super(title);
    this.controller = controller;
    
    // Initialize UI components
    initComponents();
    createMenuBar();
    
    // Set window properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null); // Center on screen
  }
  
  /**
   * Initializes all UI components and arranges them in the window.
   */
  private void initComponents() {
    // Create main layout
    final JPanel mainPanel = new JPanel(new BorderLayout());
    
    // Initialize panels
    roomPanel = new RoomPanel();
    inventoryPanel = new InventoryPanel(controller);
    navigationPanel = new NavigationPanel(controller);
    actionPanel = new ActionPanel(controller);
    
    // Add panels to window
    mainPanel.add(roomPanel, BorderLayout.CENTER);
    mainPanel.add(inventoryPanel, BorderLayout.EAST);
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(navigationPanel, BorderLayout.NORTH);
    bottomPanel.add(actionPanel, BorderLayout.SOUTH);
    
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    
    this.add(mainPanel);
  }
  
  /**
   * Creates the menu bar for the window.
   */
  private void createMenuBar() {
    setJMenuBar(MenuBarSetup.createMenuBar(this, controller));
  }
  
  /**
   * Displays the current room.
   *
   * @param room The room to display.
   */
  public void displayRoom(Room room) {
    if (roomPanel != null) {
      roomPanel.updateRoom(room);
      navigationPanel.updateAvailableDirections(room);
    }
  }

  /**
   * Displays the current room description.
   *
   * @param roomDescription The description of the room.
   */
  @Override
  public void displayRoom(String roomDescription) {
    if (roomPanel != null) {
      displayMessage("Room: " + roomDescription);
    }
  }

  /**
   * Displays a message to the player.
   *
   * @param message The message to display
   */
  @Override
  public void displayMessage(String message) {
    if (roomPanel != null) {
      roomPanel.addMessage(message);
    }
  }
  
  /**
   * Displays the player's health information.
   *
   * @param health The health value
   * @param status The health status description
   */
  public void displayHealth(int health, String status) {
    if (roomPanel != null) {
      roomPanel.updateHealth(health, status);
    }
  }
  
  /**
   * Closes the window and releases resources.
   */
  public void close() {
    dispose();
  }
  
  /**
   * Shows the About dialog.
   */
  public void showAboutDialog() {
    AboutDialog aboutDialog = new AboutDialog(this);
    aboutDialog.setVisible(true);
  }
  
  // GameView interface implementation methods
  /**
   * Displays the player's inventory.
   *
   * @param inventory The inventory text to display.
   */
  public void displayInventory(String inventory) {
    displayMessage("Inventory: " + inventory);
  }
  
  /**
   * Displays a generic message (alternative to displayMessage).
   *
   * @param message The message to display.
   */
  @Override
  public void display(String message) {
    displayMessage(message);
  }
  
  /**
   * Displays the game over message.
   */
  @Override
  public void displayGameOver() {
    showEndGameDialog(false, "Game Over!");
  }
  
  /**
   * Shows a message in a dialog box.
   *
   * @param message The message to show.
   */
  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
  
  /**
   * Gets user input. In GUI mode, this returns null as input is handled asynchronously.
   *
   * @return null since input is handled via events.
   */
  public String getUserInput() {
    // GUI input is handled via the text field and its action listener
    return null;
  }
  
  /**
   * Shows the end game dialog.
   *
   * @param isWin true if player won, false if player lost.
   * @param message The end game message.
   */
  public void showEndGameDialog(boolean isWin, String message) {
    EndGameDialog dialog = new EndGameDialog(this, isWin, message, controller);
    dialog.setVisible(true);
  }
  
  /**
   * Displays the player's inventory items.
   *
   * @param items List of items in the inventory
   */
  public void displayInventory(List<Item> items) {
    if (inventoryPanel != null) {
      inventoryPanel.updateInventory(items);
    }
  }
}