package view.swing;

import controller.SwingController;
import model.Room;
import model.Item;
import view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
  private JTextField inputField;
  private JScrollPane messageScrollPane;
  private JTextArea messageArea;
  private JLabel statusLabel;
  
  /**
   * Constructs a new GameWindow.
   * 
   * @param title Window title
   * @param controller The controller handling game logic
   */
  public GameWindow(String title, SwingController controller) {
    super(title);
    this.controller = controller;
    
    // Initialize UI components
    initComponents();
    createMenuBar();
    
    // Set window properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 700);
    setMinimumSize(new Dimension(800, 600));
    setLocationRelativeTo(null); // Center on screen
  }
  
  /**
   * Initializes all UI components and arranges them in the window.
   */
  private void initComponents() {
    // Create main layout
    JPanel mainPanel = new JPanel(new BorderLayout());
    
    // Initialize panels
    initPanels();
    
    // Status label at the bottom
    statusLabel = new JLabel("Ready");
    statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    // Message area for showing game output
    messageArea = new JTextArea();
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
    messageScrollPane = new JScrollPane(messageArea);
    messageScrollPane.setPreferredSize(new Dimension(600, 150));
    
    // User input field
    inputField = new JTextField();
    inputField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
          // Echo input to message area
          messageArea.append("> " + input + "\n");
          // Process input through controller
          if (controller != null) {
            // Call controller method to process command
            displayMessage("Processing command: " + input);
          }
          // Clear input field
          inputField.setText("");
        }
      }
    });
    
    // Add panels to window
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(roomPanel, BorderLayout.CENTER);
    
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(inventoryPanel, BorderLayout.CENTER);
    
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(rightPanel, BorderLayout.EAST);
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(navigationPanel, BorderLayout.NORTH);
    bottomPanel.add(actionPanel, BorderLayout.CENTER);
    
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(messageScrollPane, BorderLayout.CENTER);
    
    JPanel inputLinePanel = new JPanel(new BorderLayout());
    inputLinePanel.add(new JLabel("Command: "), BorderLayout.WEST);
    inputLinePanel.add(inputField, BorderLayout.CENTER);
    
    inputPanel.add(inputLinePanel, BorderLayout.SOUTH);
    
    bottomPanel.add(inputPanel, BorderLayout.SOUTH);
    
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    
    // Add status bar
    mainPanel.add(statusLabel, BorderLayout.NORTH);
    
    this.add(mainPanel);
  }
  
  /**
   * Initializes panel components.
   * This method can be overridden in test classes to provide mock implementations.
   */
  protected void initPanels() {
    roomPanel = new RoomPanel();
    inventoryPanel = new InventoryPanel(controller);
    navigationPanel = new NavigationPanel(controller);
    actionPanel = new ActionPanel(controller);
  }
  
  /**
   * Creates the menu bar for the window.
   */
  protected void createMenuBar() {
    setJMenuBar(MenuBarSetup.createMenuBar(this, controller));
  }
  
  /**
   * Displays the current room description.
   *
   * @param roomDescription The description of the room
   */
  @Override
  public void displayRoom(String roomDescription) {
    if (roomPanel != null) {
      // For now, just pass the description to be displayed
      roomPanel.updateRoom(roomDescription);
    }
    displayMessage("Room: " + roomDescription);
  }
  
  /**
   * Displays the player's inventory.
   *
   * @param inventory The inventory text to display
   */
  @Override
  public void displayInventory(String inventory) {
    if (inventoryPanel != null) {
      // Convert the inventory string to an array of item descriptions
      String[] items = inventory.split("\n");
      inventoryPanel.updateInventory(items);
    }
    displayMessage("Inventory: " + inventory);
  }
  
  /**
   * Displays a message to the player.
   *
   * @param message The message to display
   */
  @Override
  public void displayMessage(String message) {
    if (messageArea != null) {
      messageArea.append(message + "\n");
      // Auto-scroll to bottom
      messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }
    
    // Also pass to room panel for its message area
    if (roomPanel != null) {
      roomPanel.addMessage(message);
    }
  }
  
  /**
   * Displays a generic message (alternative to displayMessage).
   * 
   * @param message The message to display
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
   * @param message The message to show
   */
  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
  
  /**
   * Gets user input. In GUI mode, this returns null as input is handled asynchronously.
   * 
   * @return null since input is handled via events
   */
  @Override
  public String getUserInput() {
    // GUI input is handled via the text field and its action listener
    return null;
  }
  
  /**
   * Shows the About dialog.
   */
  public void showAboutDialog() {
    AboutDialog aboutDialog = new AboutDialog(this);
    aboutDialog.setVisible(true);
  }
  
  /**
   * Shows the end game dialog.
   * 
   * @param isWin true if player won, false if player lost
   * @param message The end game message
   */
  public void showEndGameDialog(boolean isWin, String message) {
    EndGameDialog dialog = new EndGameDialog(this, isWin, message);
    dialog.setVisible(true);
  }
  
  /**
   * Updates the status label.
   * 
   * @param status New status message
   */
  public void updateStatus(String status) {
    if (statusLabel != null) {
      statusLabel.setText(status);
    }
  }
  
  /**
   * Gets the controller associated with this window.
   * 
   * @return The SwingController instance
   */
  public SwingController getController() {
    return controller;
  }
  
  /**
   * Inner class for RoomPanel - This would be replaced by Person 3's implementation.
   */
  protected class RoomPanel extends JPanel {
    protected JTextArea descriptionArea = new JTextArea();
    protected JTextArea messageArea = new JTextArea();
    
    public RoomPanel() {
      setLayout(new BorderLayout());
      descriptionArea.setEditable(false);
      messageArea.setEditable(false);
      add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
      add(new JScrollPane(messageArea), BorderLayout.SOUTH);
    }
    
    public void updateRoom(String description) {
      descriptionArea.setText(description);
    }
    
    public void addMessage(String message) {
      messageArea.append(message + "\n");
    }
  }
  
  /**
   * Inner class for InventoryPanel - This would be replaced by Person 3's implementation.
   */
  protected class InventoryPanel extends JPanel {
    protected DefaultListModel<String> model = new DefaultListModel<>();
    protected JList<String> list = new JList<>(model);
    
    public InventoryPanel(SwingController controller) {
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createTitledBorder("Inventory"));
      add(new JScrollPane(list), BorderLayout.CENTER);
    }
    
    public void updateInventory(String[] items) {
      model.clear();
      if (items != null) {
        for (String item : items) {
          model.addElement(item);
        }
      }
    }
  }
  
  /**
   * Inner class for NavigationPanel - This would be replaced by Person 3's implementation.
   */
  protected class NavigationPanel extends JPanel {
    protected JButton[] directionButtons = new JButton[4]; // N, E, S, W
    
    public NavigationPanel(SwingController controller) {
      setLayout(new GridLayout(3, 3));
      setBorder(BorderFactory.createTitledBorder("Navigation"));
      
      // Create basic layout with empty buttons
      for (int i = 0; i < 9; i++) {
        if (i == 1) { // North
          directionButtons[0] = new JButton("North");
          add(directionButtons[0]);
        } else if (i == 3) { // West
          directionButtons[3] = new JButton("West");
          add(directionButtons[3]);
        } else if (i == 5) { // East
          directionButtons[1] = new JButton("East");
          add(directionButtons[1]);
        } else if (i == 7) { // South
          directionButtons[2] = new JButton("South");
          add(directionButtons[2]);
        } else {
          add(new JPanel());
        }
      }
    }
    
    public void updateAvailableDirections(boolean north, boolean east, boolean south, boolean west) {
      if (directionButtons[0] != null) directionButtons[0].setEnabled(north);
      if (directionButtons[1] != null) directionButtons[1].setEnabled(east);
      if (directionButtons[2] != null) directionButtons[2].setEnabled(south);
      if (directionButtons[3] != null) directionButtons[3].setEnabled(west);
    }
  }
  
  /**
   * Inner class for ActionPanel - This would be replaced by Person 3's implementation.
   */
  protected class ActionPanel extends JPanel {
    public ActionPanel(SwingController controller) {
      setLayout(new FlowLayout());
      setBorder(BorderFactory.createTitledBorder("Actions"));
      
      add(new JButton("Look"));
      add(new JButton("Take"));
      add(new JButton("Attack"));
      add(new JButton("Answer"));
    }
  }
}
