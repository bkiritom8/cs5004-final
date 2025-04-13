package controller;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.*;
import view.swing.GameWindow;

/**
 * SwingController manages the game state and UI interactions for the GUI version.
 * It processes commands, updates the view, and maintains game state.
 */
public class SwingController extends GameController {
  private final GameWorld gameWorld;
  private final Map<String, Runnable> commandMap;
  private JTextArea outputArea;
  private JPanel controlPanel;
  private GameWindow gameWindow;

  /**
   * Creates a new SwingController with the specified GameWorld.
   *
   * @param gameWorld the game world model
   */
  public SwingController(GameWorld gameWorld) {
    super(gameWorld);
    this.gameWorld = gameWorld;
    this.commandMap = new HashMap<>();
    initCommands();
    createControlPanel();
  }

  /**
   * Initialize command mappings.
   */
  private void initCommands() {
    commandMap.put("q", this::quitGame);
    commandMap.put("quit", this::quitGame);
    commandMap.put("n", () -> move(Direction.NORTH));
    commandMap.put("north", () -> move(Direction.NORTH));
    commandMap.put("s", () -> move(Direction.SOUTH));
    commandMap.put("south", () -> move(Direction.SOUTH));
    commandMap.put("e", () -> move(Direction.EAST));
    commandMap.put("east", () -> move(Direction.EAST));
    commandMap.put("w", () -> move(Direction.WEST));
    commandMap.put("west", () -> move(Direction.WEST));
    commandMap.put("look", this::look);
    commandMap.put("i", this::showInventory);
    commandMap.put("inventory", this::showInventory);
    commandMap.put("help", this::showHelp);
    commandMap.put("?", this::showHelp);
  }

  /**
   * Create the control panel with output area.
   */
  private void createControlPanel() {
    // Create panel instead of frame
    controlPanel = new JPanel(new BorderLayout(5, 5));

    // Create text area for output with improved readability
    outputArea = new JTextArea();
    outputArea.setEditable(false);
    outputArea.setLineWrap(true);
    outputArea.setWrapStyleWord(true);
    outputArea.setFont(new Font("SansSerif", Font.PLAIN, 12));

    // Add components to the panel
    JScrollPane scrollPane = new JScrollPane(outputArea);
    scrollPane.setPreferredSize(new Dimension(500, 150));
    scrollPane.setBorder(BorderFactory.createTitledBorder("Game Output"));
    controlPanel.add(scrollPane, BorderLayout.CENTER);

    // Add an input field for command entry
    JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
    JTextField inputField = new JTextField();
    inputField.addActionListener(e -> processCommand(inputField.getText()));
    inputPanel.add(new JLabel("Command:"), BorderLayout.WEST);
    inputPanel.add(inputField, BorderLayout.CENTER);

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(e -> {
      processCommand(inputField.getText());
      inputField.setText("");
    });
    inputPanel.add(submitButton, BorderLayout.EAST);

    controlPanel.add(inputPanel, BorderLayout.SOUTH);
  }

  /**
   * Starts the GUI controller by creating and showing the game window.
   */
  @Override
  public void start() throws IOException {
    // Create and show the main game window
    SwingUtilities.invokeLater(() -> {
      gameWindow = new GameWindow(gameWorld.getGameName(), this);
      showWelcome();
      updateView();
    });
  }

  /**
   * Displays the welcome message and initial room.
   */
  private void showWelcome() {
    appendText("Welcome to " + gameWorld.getGameName() + "!");
    appendText("Type 'help' or '?' for a list of commands.");
    updateView();
  }

  /**
   * Updates the view with current game state.
   */
  private void updateView() {
    if (gameWindow != null) {
      Player player = gameWorld.getPlayer();
      Room currentRoom = player.getCurrentRoom();

      // Update room display
      gameWindow.displayRoom(currentRoom);

      // Update health status
      gameWindow.displayHealth(player.getHealth(), player.getHealthStatus());

      // Update inventory display
      gameWindow.displayInventory(player.getInventory());
    }
  }

  /**
   * Process a command string entered by the user.
   *
   * @param commandString The command to process
   */
  public void processCommand(String commandString) {
    if (commandString == null || commandString.trim().isEmpty()) {
      return;
    }

    // Display the command
    appendText("> " + commandString);

    // Extract the command and arguments
    String[] parts = commandString.trim().toLowerCase().split("\\s+", 2);
    String command = parts[0];
    String argument = parts.length > 1 ? parts[1] : "";

    // Look for exact command match
    Runnable action = commandMap.get(command);
    if (action != null) {
      action.run();
      return;
    }

    // Handle commands with arguments
    if (command.equals("take") || command.equals("t")) {
      takeItem(argument);
    } else if (command.equals("drop") || command.equals("d")) {
      dropItem(argument);
    } else if (command.equals("examine") || command.equals("x")) {
      examine(argument);
    } else if (command.equals("use") || command.equals("u")) {
      useItem(argument);
    } else if (command.equals("answer") || command.equals("a")) {
      answerPuzzle(argument);
    } else {
      appendText("Unknown command. Type 'help' or '?' for help.");
    }

    // Update the view after processing the command
    updateView();
  }

  /**
   * Moves the player in the specified direction.
   *
   * @param dir The direction to move
   */
  @Override
  public void move(Direction dir) {
    Player player = gameWorld.getPlayer();
    Room current = player.getCurrentRoom();
    String exitNumber = current.getExitRoomNumber(dir);

    if ("0".equals(exitNumber)) {
      appendText("You can't go that way.");
      return;
    }

    // Check for blocked path (negative exit number)
    if (Integer.parseInt(exitNumber) < 0) {
      if (current.getPuzzle() != null && current.getPuzzle().isActive()) {
        appendText("Your path is blocked by a puzzle: " + current.getPuzzle().getDescription());
      } else if (current.getMonster() != null && current.getMonster().isActive()) {
        appendText("Your path is blocked by a monster: " + current.getMonster().getDescription());
        monsterAttack();
      } else {
        appendText("Something blocks your way in that direction.");
      }
      return;
    }

    // Try to move
    Room next = current.getExit(dir);
    if (next != null) {
      player.setCurrentRoom(next);
      appendText("You move " + dir.toString().toLowerCase() + ".");
      appendText("You are in: " + next.getName());
      appendText(next.getDescription());
      updateView();
    } else {
      appendText("There's nothing in that direction.");
    }
  }

  /**
   * Handles monster attack on the player.
   */
  private void monsterAttack() {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()
            && currentRoom.getMonster().canAttack()) {
      Monster monster = currentRoom.getMonster();
      int damage = monster.attack(gameWorld.getPlayer());
      if (damage > 0) {
        appendText(monster.getName() + " " + monster.getAttackDescription());
        appendText("You take " + damage + " damage!");
        updateView(); // Update health display
      }
    }
  }

  /**
   * Displays the current room description.
   */
  public void look() {
    Room room = gameWorld.getPlayer().getCurrentRoom();
    appendText("You are in: " + room.getName());
    appendText(room.getDescription());

    // Show monster or puzzle if present
    if (room.getMonster() != null && room.getMonster().isActive()) {
      appendText(room.getMonster().getEffects());
    } else if (room.getPuzzle() != null && room.getPuzzle().isActive()) {
      appendText(room.getPuzzle().getEffects());
    }

    // Show items in room
    if (!room.getItems().isEmpty()) {
      StringBuilder itemList = new StringBuilder("You see: ");
      for (Item item : room.getItems()) {
        itemList.append(item.getName()).append(", ");
      }
      // Remove trailing comma and space
      if (itemList.length() > 8) {
        itemList.setLength(itemList.length() - 2);
      }
      appendText(itemList.toString());
    }

    // Show exits
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

  /**
   * Displays the player's inventory.
   */
  public void showInventory() {
    Player player = gameWorld.getPlayer();
    appendText("Inventory (weight: " + player.getInventoryWeight() + "/" + player.getMaxWeight() + "):");

    if (player.getInventory().isEmpty()) {
      appendText("Your inventory is empty.");
    } else {
      for (Item item : player.getInventory()) {
        appendText("- " + item.getName() + " (uses: " + item.getUsesRemaining() + ")");
      }
    }
  }

  /**
   * Picks up an item from the current room.
   *
   * @param itemName The name of the item to take
   */
  public void takeItem(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      appendText("What do you want to take?");
      return;
    }

    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    Item item = currentRoom.getItem(itemName);

    if (item == null) {
      appendText("There's no " + itemName + " here to take.");
      return;
    }

    if (gameWorld.getPlayer().addToInventory(item)) {
      currentRoom.removeItem(item);
      appendText("You pick up the " + item.getName() + ".");
      updateView();
    } else {
      appendText("You can't carry any more. Your inventory is too heavy.");
    }
  }

  /**
   * Drops an item from the player's inventory.
   *
   * @param itemName The name of the item to drop
   */
  public void dropItem(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      appendText("What do you want to drop?");
      return;
    }

    Player player = gameWorld.getPlayer();
    Item item = player.getItemFromInventory(itemName);

    if (item == null) {
      appendText("You don't have a " + itemName + " in your inventory.");
      return;
    }

    if (player.removeFromInventory(item)) {
      player.getCurrentRoom().addItem(item);
      appendText("You drop the " + item.getName() + ".");
      updateView();
    } else {
      appendText("You can't drop the " + item.getName() + " for some reason.");
    }
  }

  /**
   * Examines an item, monster, or fixture.
   *
   * @param target The name of the thing to examine
   */
  public void examine(String target) {
    if (target == null || target.trim().isEmpty()) {
      appendText("What do you want to examine?");
      return;
    }

    // Check inventory first
    Player player = gameWorld.getPlayer();
    Item invItem = player.getItemFromInventory(target);
    if (invItem != null) {
      appendText(invItem.getDescription());
      return;
    }

    // Check room items
    Room currentRoom = player.getCurrentRoom();
    Item roomItem = currentRoom.getItem(target);
    if (roomItem != null) {
      appendText(roomItem.getDescription());
      return;
    }

    // Check fixtures
    Fixture fixture = currentRoom.getFixture(target);
    if (fixture != null) {
      appendText(fixture.getDescription());
      return;
    }

    // Check monster
    Monster monster = currentRoom.getMonster();
    if (monster != null && monster.isActive() && monster.getName().equalsIgnoreCase(target)) {
      appendText(monster.getDescription());
      return;
    }

    // Check puzzle
    Puzzle puzzle = currentRoom.getPuzzle();
    if (puzzle != null && puzzle.isActive() && puzzle.getName().equalsIgnoreCase(target)) {
      appendText(puzzle.getDescription());
      return;
    }

    appendText("You don't see a " + target + " here.");
  }

  /**
   * Uses an item, potentially solving a puzzle or defeating a monster.
   *
   * @param itemName The name of the item to use
   */
  public void useItem(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      appendText("What do you want to use?");
      return;
    }

    Player player = gameWorld.getPlayer();
    Item item = player.getItemFromInventory(itemName);

    if (item == null) {
      appendText("You don't have a " + itemName + " in your inventory.");
      return;
    }

    if (item.getUsesRemaining() <= 0) {
      appendText("The " + item.getName() + " has no uses left.");
      return;
    }

    Room currentRoom = player.getCurrentRoom();
    boolean solved = gameWorld.applySolution(item.getName());

    if (solved) {
      if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isActive()) {
        appendText("You used the " + item.getName() + " to solve the puzzle!");
        appendText(currentRoom.getPuzzle().getEffects());
        appendText("You gain " + currentRoom.getPuzzle().getValue() + " points!");
      } else if (currentRoom.getMonster() != null && !currentRoom.getMonster().isActive()) {
        appendText("You used the " + item.getName() + " to defeat the monster!");
        appendText("You gain " + currentRoom.getMonster().getValue() + " points!");
      }
      item.use();
      updateView();
    } else {
      appendText("You use the " + item.getName() + ".");
      appendText(item.getWhenUsed());
      item.use();
      updateView();
    }
  }

  /**
   * Provides an answer to a puzzle.
   *
   * @param answer The answer to the puzzle
   */
  @Override
  public void answerPuzzle(String answer) {
    if (answer == null || answer.trim().isEmpty()) {
      appendText("What's your answer?");
      return;
    }

    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();

    if (currentRoom.getPuzzle() == null || !currentRoom.getPuzzle().isActive()) {
      appendText("There's no active puzzle here to solve.");
      return;
    }

    Puzzle puzzle = currentRoom.getPuzzle();

    if (!puzzle.getSolution().startsWith("'")) {
      appendText("This puzzle requires using an item, not answering with words.");
      return;
    }

    boolean solved = gameWorld.applySolution(answer);

    if (solved) {
      appendText("Correct! " + puzzle.getEffects());
      appendText("You gain " + puzzle.getValue() + " points!");
      updateView();
    } else {
      appendText("That's not the right answer. The puzzle remains unsolved.");
    }
  }

  /**
   * Displays the help menu.
   */
  private void showHelp() {
    appendText("\nAvailable commands:");
    appendText("- n, north: Move north");
    appendText("- s, south: Move south");
    appendText("- e, east: Move east");
    appendText("- w, west: Move west");
    appendText("- l, look: Look around the room");
    appendText("- i, inventory: Show your inventory");
    appendText("- t, take [item]: Pick up an item");
    appendText("- d, drop [item]: Drop an item");
    appendText("- x, examine [target]: Examine something");
    appendText("- u, use [item]: Use an item");
    appendText("- a, answer [text]: Answer a puzzle");
    appendText("- k, attack: Attack a monster");
    appendText("- help, ?: Show this help menu");
    appendText("- q, quit: Exit the game");
  }

  /**
   * Quits the game.
   */
  @Override
  public void quitGame() {
    // Show final score
    Player player = gameWorld.getPlayer();
    appendText("\nGame over!");
    appendText("Final score: " + player.getScore());
    appendText("Rank: " + player.getRank());

    // Ask if player wants to save
    int option = JOptionPane.showConfirmDialog(
            null,
            "Do you want to save your progress before quitting?",
            "Save Game",
            JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
      try {
        gameWorld.saveGame("saved_game.json");
        appendText("Game saved successfully!");
      } catch (IOException e) {
        appendText("Error saving game: " + e.getMessage());
      }
    }

    // Exit with confirmation dialog
    option = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to quit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
      if (gameWindow != null) {
        gameWindow.dispose();
      }
      System.exit(0);
    }
  }

  /**
   * Appends text to the output area.
   *
   * @param text The text to append
   */
  private void appendText(String text) {
    if (outputArea != null) {
      outputArea.append(text + "\n");
      outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
  }

  /**
   * Gets the controller panel containing the UI components.
   *
   * @return The controller panel
   */
  public JPanel getControlPanel() {
    return controlPanel;
  }

  /**
   * Gets the output area used for displaying game messages.
   *
   * @return The output text area
   */
  public JTextArea getOutputArea() {
    return outputArea;
  }

  /**
   * Sets the output area for the controller.
   * Used when the GameWindow creates its own output area.
   *
   * @param area The text area to use for output
   */
  public void setOutputArea(JTextArea area) {
    this.outputArea = area;
  }
}