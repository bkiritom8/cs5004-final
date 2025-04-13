package view.swing;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import controller.SwingController;
import controller.GameController;

import model.Item;
import model.Room;
import view.GameView;

/**
 * Main window for the game's GUI interface.
 * Implements GameView interface and contains all GUI panels.
 */
public class GameWindow extends JFrame implements GameView {
  private RoomPanel roomPanel;
  private InventoryPanel inventoryPanel;
  private NavigationPanel navigationPanel;
  private ActionPanel actionPanel;
  private SwingController controller;
  private JScrollPane outputScrollPane;
  private JTextArea outputArea;

  /**
   * Constructs a new GameWindow.
   *
   * @param title Window title
   * @param controller The controller handling game logic
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

    // Ensure the window is visible
    setVisible(true);
  }

  /**
   * Initializes all UI components and arranges them in the window.
   */
  private void initComponents() {
    // Create main layout
    JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

    // Initialize panels
    roomPanel = new RoomPanel();
    inventoryPanel = new InventoryPanel(controller);
    navigationPanel = new NavigationPanel(controller);
    actionPanel = new ActionPanel(controller);

    // Create output area for controller messages (if not already created)
    if (controller.getOutputArea() == null) {
      outputArea = new JTextArea();
      outputArea.setEditable(false);
      outputArea.setLineWrap(true);
      outputArea.setWrapStyleWord(true);
      outputScrollPane = new JScrollPane(outputArea);
      // Provide the output area to the controller
      controller.setOutputArea(outputArea);
    } else {
      // Use the existing output area from controller if available
      outputArea = controller.getOutputArea();
      outputScrollPane = new JScrollPane(outputArea);
    }

    // Create center panel to hold room information
    JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
    centerPanel.add(roomPanel, BorderLayout.CENTER);
    centerPanel.add(outputScrollPane, BorderLayout.SOUTH);

    // Add panels to window
    mainPanel.add(centerPanel, BorderLayout.CENTER);
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
   * Displays the current room and updates all panels.
   *
   * @param room The room to display
   */
  public void displayRoom(Room room) {
    if (room != null) {
      SwingUtilities.invokeLater(() -> {
        roomPanel.updateRoom(room);
        navigationPanel.updateAvailableDirections(room);
      });
    }
  }

  /**
   * Displays the current room description.
   * This implementation passes the description to the room panel.
   *
   * @param roomDescription The description of the room
   */
  @Override
  public void displayRoom(String roomDescription) {
    if (roomPanel != null) {
      SwingUtilities.invokeLater(() ->
              displayMessage("Room: " + roomDescription)
      );
    }
  }

  /**
   * Displays a message to the player.
   *
   * @param message The message to display
   */
  @Override
  public void displayMessage(String message) {
    SwingUtilities.invokeLater(() -> {
      if (outputArea != null) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
      }
      if (roomPanel != null) {
        roomPanel.addMessage(message);
      }
    });
  }

  /**
   * Displays the player's health information.
   *
   * @param health The health value
   * @param status The player's health status text
   */
  public void displayHealth(int health, String status) {
    SwingUtilities.invokeLater(() -> {
      if (roomPanel != null) {
        roomPanel.updateHealth(health, status);
      }
    });
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

  /**
   * Displays the player's inventory.
   *
   * @param inventory String representation of inventory items
   */
  @Override
  public void displayInventory(String inventory) {
    SwingUtilities.invokeLater(() ->
            displayMessage("Inventory: " + inventory)
    );
  }

  /**
   * Displays a message (alternative method that delegates to displayMessage).
   *
   * @param message The message to display
   */
  @Override
  public void display(String message) {
    displayMessage(message);
  }

  /**
   * Displays the game over message and shows endgame dialog.
   */
  @Override
  public void displayGameOver() {
    SwingUtilities.invokeLater(() -> {
      displayMessage("GAME OVER");
      showEndGameDialog(false, "Game Over!");
    });
  }

  /**
   * Shows a message in a dialog box.
   *
   * @param message The message to show
   */
  @Override
  public void showMessage(String message) {
    SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(this, message)
    );
  }

  /**
   * Shows the end game dialog.
   *
   * @param isWin true if player won, false if player lost
   * @param message The end game message
   */
  public void showEndGameDialog(boolean isWin, String message) {
    SwingUtilities.invokeLater(() -> {
      EndGameDialog dialog = new EndGameDialog(this, isWin, message, controller);
      dialog.setVisible(true);
    });
  }

  /**
   * Updates the player's inventory display with the provided items.
   *
   * @param items List of items in the inventory
   */
  public void displayInventory(List<Item> items) {
    SwingUtilities.invokeLater(() -> {
      if (inventoryPanel != null) {
        inventoryPanel.updateInventory(items);
      }
    });
  }
}