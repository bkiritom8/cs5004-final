package view.swing;

import java.awt.*;

import javax.swing.*;

import model.Item;
import model.Monster;
import model.Puzzle;
import model.Room;
import model.Direction;
import util.ImageLoader;

/**
 * The RoomPanel class provides a UI panel containing room information.
 */
public class RoomPanel extends JPanel {
  private JLabel roomImageLabel;
  private JTextArea descriptionArea;
  private JTextArea messageArea;
  private JProgressBar healthBar;
  private JPanel healthPanel;

  /**
   * Constructs the layout, border/s, and adds images and descriptions for rooms.
   */
  public RoomPanel() {
    // Set layout
    setLayout(new BorderLayout(5, 5));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Initialize components
    roomImageLabel = new JLabel();
    roomImageLabel.setHorizontalAlignment(JLabel.CENTER);

    // Create health panel with a more visible layout
    healthPanel = new JPanel(new BorderLayout(5, 0));
    healthPanel.setBorder(BorderFactory.createTitledBorder("Health Status"));

    // Initialize health bar with proper size and colors
    healthBar = new JProgressBar(0, 100);
    healthBar.setValue(100);
    healthBar.setStringPainted(true);
    healthBar.setForeground(Color.GREEN);
    healthBar.setString("Health: 100%");
    healthBar.setPreferredSize(new Dimension(200, 25));

    // Add health bar to its panel
    healthPanel.add(healthBar, BorderLayout.CENTER);

    // Create description area with scroll capability
    descriptionArea = new JTextArea(10, 40);
    descriptionArea.setEditable(false);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setFont(new Font("Serif", Font.PLAIN, 14));
    JScrollPane descriptionScroll = new JScrollPane(descriptionArea);

    // Create message area for game feedback
    messageArea = new JTextArea(5, 40);
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JScrollPane messageScroll = new JScrollPane(messageArea);
    messageScroll.setBorder(BorderFactory.createTitledBorder("Messages"));

    // Add components to main panel
    add(roomImageLabel, BorderLayout.NORTH);

    // Create center panel to hold description and health
    JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
    centerPanel.add(healthPanel, BorderLayout.NORTH);
    centerPanel.add(descriptionScroll, BorderLayout.CENTER);

    add(centerPanel, BorderLayout.CENTER);
    add(messageScroll, BorderLayout.SOUTH);
  }

  /**
   * Updates the room information.
   *
   * @param room The room to be updated
   */
  public void updateRoom(Room room) {
    if (room == null) {
      descriptionArea.setText("Error: Room is null");
      return;
    }

    // Get image for room
    Icon image = ImageLoader.getRoomImage(room);
    roomImageLabel.setIcon(image);

    // Update description based on room state
    StringBuilder description = new StringBuilder();
    description.append("You are in: ").append(room.getName()).append("\n\n");

    // Handle puzzle or monster effects
    Puzzle puzzle = room.getPuzzle();
    Monster monster = room.getMonster();

    if (puzzle != null && puzzle.isActive() && puzzle.affectsTarget()) {
      description.append(puzzle.getEffects()).append("\n\n");
    } else if (monster != null && monster.isActive()) {
      description.append("A ").append(monster.getName())
              .append(" is here!\n").append(monster.getEffects()).append("\n\n");
    } else {
      description.append(room.getDescription()).append("\n\n");
    }

    // Add items in room
    if (room.getItems() != null && !room.getItems().isEmpty()) {
      description.append("Items here: ");
      boolean first = true;
      for (Item item : room.getItems()) {
        if (!first) {
          description.append(", ");
        }
        description.append(item.getName());
        first = false;
      }
      description.append("\n");
    }

    // Add exits information
    description.append("\nExits: ");
    boolean hasExits = false;

    if (!"0".equals(room.getExitRoomNumber(Direction.NORTH))) {
      description.append("NORTH ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.SOUTH))) {
      description.append("SOUTH ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.EAST))) {
      description.append("EAST ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.WEST))) {
      description.append("WEST ");
      hasExits = true;
    }

    if (!hasExits) {
      description.append("NONE");
    }

    descriptionArea.setText(description.toString());
    descriptionArea.setCaretPosition(0); // Scroll to top
  }

  /**
   * Updates the player's health information.
   *
   * @param health Player's numerical health value
   * @param status Player's health status text
   */
  public void updateHealth(int health, String status) {
    healthBar.setValue(health);

    // Set color based on health
    if (health > 70) {
      healthBar.setForeground(Color.GREEN);
    } else if (health > 30) {
      healthBar.setForeground(Color.YELLOW);
    } else {
      healthBar.setForeground(Color.RED);
    }

    healthBar.setString("Health: " + health + "% (" + status + ")");
  }

  /**
   * Adds a message to the message area.
   *
   * @param message The message to add
   */
  public void addMessage(String message) {
    messageArea.append(message + "\n");
    // Auto-scroll to bottom
    messageArea.setCaretPosition(messageArea.getDocument().getLength());
  }

  /**
   * Clears the message area.
   */
  public void clearMessages() {
    messageArea.setText("");
  }
}