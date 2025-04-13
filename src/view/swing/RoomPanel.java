package view.swing;

import java.awt.*;

import javax.swing.*;

import model.Item;
import model.Room;
import util.ImageLoader;

/**
 * The RoomPanel class provides a UI panel containing room information.
 */
public class RoomPanel extends JPanel {
  private JLabel roomImageLabel;
  private JLabel healthLabel;
  private JTextArea descriptionArea;
  private JTextArea messageArea;
  private JProgressBar healthBar;

  /**
   * Constructs the layout, border/s, and adds images and descriptions for rooms.
   */
  public RoomPanel() {
    // Set layout
    setLayout(new BorderLayout());

    // Initialize components
    roomImageLabel = new JLabel();

    descriptionArea = new JTextArea();
    descriptionArea.setEditable(false);

    messageArea = new JTextArea();
    messageArea.setEditable(false);

    // Health Bar using JProgressBar
    JProgressBar healthBar = new JProgressBar(0, 100);
    // TODO: Set the initial value based on player's health | Update health every time the health is changed
    healthBar.setValue(100);
    healthBar.setStringPainted(true);

    healthBar.setForeground(Color.GREEN);
    healthBar.setString("Health");

    // Add components
    add(roomImageLabel, BorderLayout.NORTH);
    add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(healthBar, BorderLayout.NORTH);
    bottomPanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

    add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the room information.
   *
   * @param room The room to be updated
   */
  public void updateRoom(Room room) {
    // Get image for room
    Icon image = ImageLoader.getRoomImage(room);
    roomImageLabel.setIcon(image);

    // Update description
    if (room.getPuzzle().isActive() && room.getPuzzle().affectsTarget()) {
      descriptionArea.setText(room.getPuzzle().getEffects());
    } else if (room.getMonster().isActive()) {
      descriptionArea.setText("A "
              + room.getMonster().getName()
              + " is here!\n"
              + room.getMonster().getEffects());
    } else {
      descriptionArea.setText(room.getDescription());
    }

    // Add items in room
    if (room.getItems() != null) {
      StringBuilder itemsText = new StringBuilder("Items here: ");
      for (Item item : room.getItems()) {
        itemsText.append(item.getName()).append(" ");
        descriptionArea.append("\n" + itemsText);
      }
    }
  }

  /**
   * Updates the player's health information.
   *
   * @param health Player's numerical health value
   */
  public void updateHealth(int health) {
    healthBar.setValue(health);
  }

  /**
   * Adds the player's message.
   *
   * @param message The message the player wants to add
   */
  public void addMessage(String message) {
    messageArea.append(message + "\n");

    // Auto-scroll to bottom
    messageArea.setCaretPosition(messageArea.getDocument().getLength());
  }
}