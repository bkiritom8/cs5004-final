package view.swing;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import controller.SwingController;
import model.Direction;
import model.Room;

/**
 * The NavigationPanel class provides a UI panel for the player's navigation controls.
 */
public class NavigationPanel extends JPanel {
  private final Map<Direction, JButton> directionButtons;
  private final SwingController controller;

  /**
   * NavigationPanel constructor that uses SwingController for handling movement events.
   *
   * @param controller SwingController that processes navigation commands
   */
  public NavigationPanel(SwingController controller) {
    this.controller = controller;

    // Set layout and border
    setLayout(new GridLayout(3, 3, 5, 5));
    setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Navigation"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    // Initialize buttons map
    directionButtons = new HashMap<>();

    // Create navigation grid with proper button placement
    for (int i = 0; i < 9; i++) {
      if (i == 1) {
        // North button
        JButton northButton = createDirectionButton("North", Direction.NORTH);
        directionButtons.put(Direction.NORTH, northButton);
        add(northButton);
      }
      else if (i == 3) {
        // West button
        JButton westButton = createDirectionButton("West", Direction.WEST);
        directionButtons.put(Direction.WEST, westButton);
        add(westButton);
      }
      else if (i == 4) {
         //Center space - empty panel
          add(new JPanel());
      }
      else if (i == 5) {
        // East button
        JButton eastButton = createDirectionButton("East", Direction.EAST);
        directionButtons.put(Direction.EAST, eastButton);
        add(eastButton);
      }
      else if (i == 7) {
        // South button
        JButton southButton = createDirectionButton("South", Direction.SOUTH);
        directionButtons.put(Direction.SOUTH, southButton);
        add(southButton);
      }
      else {
        // Empty cell
        add(new JPanel());
      }
    }
  }

  /**
   * Helper method to create a direction button with consistent styling and behavior.
   *
   * @param label Button label
   * @param direction The direction this button represents
   * @return A configured JButton
   */
  private JButton createDirectionButton(String label, Direction direction) {
    JButton button = new JButton(label);

    // Set initial state (disabled until we know directions are available)
    button.setEnabled(false);

    // Add action listener to handle movement
    button.addActionListener(e -> {
      if (controller != null) {
        controller.move(direction);
      }
    });

    return button;
  }

  /**
   * Updates the available directions based on the specified room's exits.
   *
   * @param room The room to check for available exits
   */
  public void updateAvailableDirections(Room room) {
    if (room == null) {
      disableAllButtons();
      return;
    }

    // Update each direction button based on available exits
    for (Direction direction : Direction.values()) {
      JButton button = directionButtons.get(direction);
      if (button != null) {
        String exitNumber = room.getExitRoomNumber(direction);

        // Enable if exit exists and is not blocked (positive number)
        boolean enabled = !exitNumber.equals("0") && Integer.parseInt(exitNumber) > 0;
        button.setEnabled(enabled);

        // Visual indication of available exits
        if (enabled) {
          button.setBackground(new Color(200, 255, 200));
          button.setForeground(Color.BLACK);
        } else {
          button.setBackground(null);
          button.setForeground(Color.GRAY);
        }
      }
    }
  }

  /**
   * Disables all direction buttons.
   * Used when no room is loaded or during initialization.
   */
  private void disableAllButtons() {
    for (JButton button : directionButtons.values()) {
      if (button != null) {
        button.setEnabled(false);
      }
    }
  }
}