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
  Map<Direction, JButton> directionButtons;
  SwingController controller;

  /**
   * NavigationPanel class constructor using SwingController for handling GUI events.
   *
   * @param controller {@link SwingController} used to manage user interactions
   */
  public NavigationPanel(SwingController controller) {
    this.controller = controller;

    // Set layout and border
    setLayout(new GridLayout(3, 3));
    setBorder(BorderFactory.createTitledBorder("Navigation"));

    // Initialize buttons
    directionButtons = new HashMap<>();

    // Create 3x3 grid with direction buttons in cardinal positions
    for (int i = 0; i < 9; i++) {
      if (i == 1) {
        // North
        JButton button = new JButton("North");
        button.addActionListener(e -> controller.move(Direction.NORTH));
        directionButtons.put(Direction.NORTH, button);
        add(button);
      } else if (i == 3) {
        // West
        JButton button = new JButton("West");
        button.addActionListener(e -> controller.move(Direction.WEST));
        directionButtons.put(Direction.WEST, button);
        add(button);
      } else if (i == 5) {
        // East
        JButton button = new JButton("East");
        button.addActionListener(e -> controller.move(Direction.EAST));
        directionButtons.put(Direction.EAST, button);
        add(button);
      } else if (i == 7) {
        // South
        JButton button = new JButton("South");
        button.addActionListener(e -> controller.move(Direction.SOUTH));
        directionButtons.put(Direction.SOUTH, button);
        add(button);
      } else {
        add(new JPanel()); // Empty cell
      }
    }
  }

  /**
   * Updates the exits and available directions based on the specified room.
   *
   * @param room The room to be updated to
   */
  public void updateAvailableDirections(Room room) {
    // Enable/disable buttons based on available exits
    JButton button;
    String exitNumber;

    for (Direction direction : Direction.values()) {
      button = directionButtons.get(direction);
      exitNumber = room.getExitRoomNumber(direction);

      // Enable if exit exists and is not blocked
      button.setEnabled(!exitNumber.equals("0") && Integer.parseInt(exitNumber) > 0);
    }
  }
}
