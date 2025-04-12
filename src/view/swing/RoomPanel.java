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

  /**
   * Constructs the layout, border/s, and adds images and descriptions for rooms.
   */
  public RoomPanel() {
    // Set layout
    setLayout(new BorderLayout());

    // Initialize components
    JLabel roomImageLabel = new JLabel();

    JTextArea descriptionArea = new JTextArea();
    descriptionArea.setEditable(false);

    JTextArea messageArea = new JTextArea();
    messageArea.setEditable(false);

    JLabel healthLabel = new JLabel();

    // Add components
    add(roomImageLabel, BorderLayout.NORTH);
    add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(healthLabel, BorderLayout.NORTH);
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
   * @param status Player's health status
   */
  public void updateHealth(int health, String status) {
    healthLabel.setText("Health: " + health + " (" + status + ")");
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
