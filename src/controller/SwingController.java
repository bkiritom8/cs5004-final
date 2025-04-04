package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.Direction;
import model.GameWorld;
import model.Item;
import model.Player;
import model.Room;

/**
 * SwingController shows a simple GUI for the game.
 *
 * Steps:
 * 1. Set up GUI.
 * 2. Show welcome screen (game name, current room, available exits).
 * 3. Process player commands (move, look, inventory, help, quit).
 */
public class SwingController extends GameController {
  private final GameWorld gameWorld;
  private final Map<String, Runnable> commandMap;
  private JTextArea outputArea;
  private JTextField inputField;

  /**
   * Creates a new SwingController with the specified GameWorld.
   *
   * @param gameWorld the game world model
   */
  public SwingController(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
    this.commandMap = new HashMap<>();
    initCommands();       // 1a. Map commands to actions.
    createGui();          // 1b. Build and show the window.
    showWelcome();        // 2. Display welcome message.
  }

  // 1a. Initialize command mappings.
  private void initCommands() {
    commandMap.put("q", this::exitGame);           // quit game
    commandMap.put("quit", this::exitGame);        // quit game
    commandMap.put("n", () -> move(Direction.NORTH)); // move north
    commandMap.put("north", () -> move(Direction.NORTH)); // move north
    commandMap.put("s", () -> move(Direction.SOUTH)); // move south
    commandMap.put("south", () -> move(Direction.SOUTH)); // move south
    commandMap.put("e", () -> move(Direction.EAST));  // move east
    commandMap.put("east", () -> move(Direction.EAST));  // move east
    commandMap.put("w", () -> move(Direction.WEST));  // move west
    commandMap.put("west", () -> move(Direction.WEST));  // move west
    commandMap.put("look", this::look);              // look around
    commandMap.put("i", this::showInventory);        // show inventory
    commandMap.put("inventory", this::showInventory); // show inventory
    commandMap.put("help", this::showHelp);          // show help
    commandMap.put("?", this::showHelp);             // show help
  }

  // 1b. Create and display the GUI.
  private void createGui() {
    JFrame frame = new JFrame("Adventure Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);

    outputArea = new JTextArea();
    outputArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(outputArea);

    inputField = new JTextField();
    inputField.addActionListener(new InputHandler());

    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    frame.getContentPane().add(inputField, BorderLayout.SOUTH);
    frame.setVisible(true);
  }

  // 2. Display welcome message and room info.
  private void showWelcome() {
    appendText("Welcome to " + gameWorld.getGameName() + "!");
    appendText("Type 'help' or '?' for a list of commands.");
    Room current = gameWorld.getPlayer().getCurrentRoom();
    appendText("You are in: " + current.getName());
    appendText(current.getDescription());
    showExits(current);
  }

  // Show available exits in the room.
  private void showExits(Room room) {
    StringBuilder exits = new StringBuilder("Exits: ");
    boolean hasExits = false;

    if (!"0".equals(room.getExitRoomNumber(Direction.NORTH))) {
      exits.append("NORTH ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.SOUTH))) {
      exits.append("SOUTH ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.EAST))) {
      exits.append("EAST ");
      hasExits = true;
    }
    if (!"0".equals(room.getExitRoomNumber(Direction.WEST))) {
      exits.append("WEST ");
      hasExits = true;
    }

    if (hasExits) {
      appendText(exits.toString());
    } else {
      appendText("There are no obvious exits.");
    }
  }

  // 3a. Process the command using the command map.
  private void processCommand(String command) {
    Runnable action = commandMap.get(command);
    if (action != null) {
      action.run();
    } else {
      appendText("Unknown command. Type 'help' or '?' for help.");
    }
  }

  // Moves the player in the specified direction.
  private void move(Direction dir) {
    Player player = gameWorld.getPlayer();
    Room current = player.getCurrentRoom();
    String nextId = current.getExitRoomNumber(dir);
    if ("0".equals(nextId)) {
      appendText("You can't go that way.");
      return;
    }
    Room next = current.getExit(dir);
    if (next != null) {
      player.setCurrentRoom(next);
      appendText("You move " + dir.toString().toLowerCase() + ".");
      appendText("You are in: " + next.getName());
      appendText(next.getDescription());
      showExits(next);
    } else {
      appendText("There's nothing in that direction.");
    }
  }

  // Displays the current room again.
  private void look() {
    Room room = gameWorld.getPlayer().getCurrentRoom();
    appendText("You are in: " + room.getName());
    appendText(room.getDescription());
    showExits(room);
    if (!room.getItems().isEmpty()) {
      appendText("You see:");
      for (Item item : room.getItems()) {
        appendText("- " + item.getName());
      }
    }
  }

  // Displays the player's inventory.
  private void showInventory() {
    Player player = gameWorld.getPlayer();
    if (player.getInventory().isEmpty()) {
      appendText("Your inventory is empty.");
    } else {
      appendText("Inventory:");
      for (Item item : player.getInventory()) {
        appendText("- " + item.getName());
      }
    }
  }

  // Displays the help menu.
  private void showHelp() {
    appendText("\nAvailable commands:");
    appendText("- n, north: Move north");
    appendText("- s, south: Move south");
    appendText("- e, east: Move east");
    appendText("- w, west: Move west");
    appendText("- look: Look around the room");
    appendText("- i, inventory: Show your inventory");
    appendText("- help, ?: Show this help menu");
    appendText("- q, quit: Exit the game");
  }

  // Exits the game.
  private void exitGame() {
    appendText("Goodbye!");
    System.exit(0);
  }

  // Appends text to the output area.
  private void appendText(String text) {
    outputArea.append(text + "\n");
    outputArea.setCaretPosition(outputArea.getDocument().getLength());
  }

  // 3. Handle user input from the text field.
  private class InputHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String command = inputField.getText().trim().toLowerCase();
      inputField.setText("");
      appendText("> " + command);
      processCommand(command);
    }
  }
}