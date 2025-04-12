package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import model.Direction;
import model.Fixture;
import model.GameWorld;
import model.Item;
import model.Monster;
import model.Player;
import model.Puzzle;
import model.Room;

/**
 * TextController handles text-based interaction with the game.
 * It displays the welcome screen, processes player commands,
 * and provides a complete interactive experience.
 */
public class TextController extends GameController {
  private final BufferedReader bufferedReader;
  private final PrintStream out;
  private boolean running;

  /**
   * Constructs a TextController with the given GameWorld.
   *
   * @param gameWorld      the game world model
   * @param bufferedReader input reader
   * @param out            output stream
   */
  public TextController(GameWorld gameWorld, BufferedReader bufferedReader, PrintStream out) {
    super(gameWorld);
    this.bufferedReader = bufferedReader;
    this.out = out;
    this.running = false;
  }

  /**
   * Starts the text controller, displaying the welcome screen
   * and entering the main game loop.
   */
  @Override
  public void start() throws IOException {
    running = true;

    // Display welcome screen
    displayWelcome();

    // Get player name
    promptForPlayerName();

    // Show initial room description
    lookAround();

    // Enter main game loop
    while (running) {
      try {
        // Show command prompt
        displayPrompt();

        // Get and process player command
        String command = bufferedReader.readLine();
        if (command != null) {
          processCommand(command.trim().toLowerCase());
        }

        // Check if player's health is depleted
        if (gameWorld.getPlayer().getHealth() <= 0) {
          displayGameOver();
          endGame();
        }
      } catch (IOException e) {
        out.println("Error reading input: " + e.getMessage());
        endGame();
      }
    }
  }

  /**
   * Displays the welcome message for the game.
   */
  public void displayWelcome() {
    out.println("==================================");
    out.println("Welcome to " + gameWorld.getGameName() + "!");
    out.println("An adventure awaits you...");
    out.println("==================================\n");
  }

  /**
   * Prompts the player to enter their name.
   */
  public void promptForPlayerName() throws IOException {
    out.print("Enter your name: ");
    out.flush();
    String name = bufferedReader.readLine();
    if (name != null && !name.trim().isEmpty()) {
      gameWorld.setPlayerName(name.trim());
      out.println("Welcome, " + name.trim() + "! Your adventure begins now.\n");
    } else {
      gameWorld.setPlayerName("Adventurer");
      out.println("Welcome, Adventurer! Your journey begins now.\n");
    }
  }

  /**
   * Displays the current room and its contents.
   */
  private void lookAround() {
    try {
      look();
    } catch (IOException e) {
      out.println("Error displaying room: " + e.getMessage());
    }
  }

  /**
   * Displays a prompt for the player's next command.
   */
  public void displayPrompt() {
    out.print("\nWhat would you like to do? ");
    out.flush();
  }

  /**
   * Displays all available commands to the player.
   */
  public void displayHelp() {
    out.println("\nAvailable commands:");
    out.println("  (n)orth, (s)outh, (e)ast, (w)est - Move in a direction");
    out.println("  (l)ook - Look around the room");
    out.println("  (i)nventory - Check your inventory");
    out.println("  (t)ake [item] - Pick up an item");
    out.println("  (d)rop [item] - Drop an item");
    out.println("  e(x)amine [target] - Examine something");
    out.println("  (u)se [item] - Use an item");
    out.println("  (a)nswer [text] - Answer a riddle or puzzle");
    out.println("  attac(k) - Attack a monster");
    out.println("  sa(v)e - Save the game");
    out.println("  (r)estore - Restore a saved game");
    out.println("  (h)elp - Show this help message");
    out.println("  (q)uit - Quit the game");
  }

  /**
   * Process player commands for the text interface.
   * Overrides the base controller implementation to add text-specific command handling.
   */
  @Override
  public void processCommand(String command) throws IOException {
    if (command.isEmpty()) return;

    if (command.equals("n") || command.equals("north")) {
      move(Direction.NORTH);
    } else if (command.equals("s") || command.equals("south")) {
      move(Direction.SOUTH);
    } else if (command.equals("e") || command.equals("east")) {
      move(Direction.EAST);
    } else if (command.equals("w") || command.equals("west")) {
      move(Direction.WEST);
    } else if (command.equals("l") || command.equals("look")) {
      look();
    } else if (command.equals("i") || command.equals("inventory")) {
      showInventory();
    } else if (command.equals("k") || command.equals("attack")) {
      attackMonster();
    } else if (command.equals("h") || command.equals("help")) {
      displayHelp();
    } else if (command.startsWith("t ") || command.startsWith("take ")) {
      String itemName = command.startsWith("t ") ? command.substring(2) : command.substring(5);
      takeItem(itemName);
    } else if (command.startsWith("d ") || command.startsWith("drop ")) {
      String itemName = command.startsWith("d ") ? command.substring(2) : command.substring(5);
      dropItem(itemName);
    } else if (command.startsWith("x ") || command.startsWith("examine ")) {
      String target = command.startsWith("x ") ? command.substring(2) : command.substring(8);
      examine(target);
    } else if (command.startsWith("u ") || command.startsWith("use ")) {
      String itemName = command.startsWith("u ") ? command.substring(2) : command.substring(4);
      useItem(itemName);
    } else if (command.startsWith("a ") || command.startsWith("answer ")) {
      String answer = command.startsWith("a ") ? command.substring(2) : command.substring(7);
      provideAnswer(answer);
    } else if (command.equals("v") || command.equals("save")) {
      saveGame();
    } else if (command.equals("r") || command.equals("restore") || command.equals("load")) {
      loadGame();
    } else if (command.equals("q") || command.equals("quit")) {
      quitGame();
    } else {
      out.println("I don't understand that command. Type 'help' for a list of commands.");
    }
  }

  /**
   * Move the player in the specified direction.
   */
  public void move(Direction direction) throws IOException {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    String exitNumber = currentRoom.getExitRoomNumber(direction);
    if (exitNumber.equals("0")) {
      out.println("You can't go that way. There's a wall.");
      return;
    }
    if (Integer.parseInt(exitNumber) < 0) {
      if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
        out.println("Blocked by puzzle: " + currentRoom.getPuzzle().getDescription());
      } else if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
        out.println("Blocked by monster: " + currentRoom.getMonster().getDescription());
        monsterAttacksPlayer();
      } else {
        out.println("The path is blocked.");
      }
      return;
    }
    Room nextRoom = currentRoom.getExit(direction);
    if (nextRoom != null) {
      gameWorld.getPlayer().setCurrentRoom(nextRoom);
      out.println("You move " + direction.toString().toLowerCase() + ".");
      look();
    } else {
      out.println("Exit error. Can't move there.");
    }
  }

  /**
   * Display the current room and its contents.
   */
  public void look() throws IOException {
    Player player = gameWorld.getPlayer();
    Room currentRoom = player.getCurrentRoom();
    out.println("Health: " + player.getHealth() + " (" + player.getHealthStatus() + ")");
    out.println("You are in the " + currentRoom.getName());
    out.println(currentRoom.getDescription());

    // Display puzzle or monster if active
    if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
      out.println(currentRoom.getPuzzle().getEffects());
    }
    if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
      out.println(currentRoom.getMonster().getEffects());
      monsterAttacksPlayer();
    }

    // Display items in room
    if (!currentRoom.getItems().isEmpty()) {
      out.print("Items here: ");
      for (int i = 0; i < currentRoom.getItems().size(); i++) {
        out.print(currentRoom.getItems().get(i).getName());
        if (i < currentRoom.getItems().size() - 1) {
          out.print(", ");
        }
      }
      out.println();
    }

    // Display exits
    out.print("Exits: ");
    boolean hasExits = false;
    if (!currentRoom.getExitRoomNumber(Direction.NORTH).equals("0")) {
      out.print("NORTH ");
      hasExits = true;
    }
    if (!currentRoom.getExitRoomNumber(Direction.SOUTH).equals("0")) {
      out.print("SOUTH ");
      hasExits = true;
    }
    if (!currentRoom.getExitRoomNumber(Direction.EAST).equals("0")) {
      out.print("EAST ");
      hasExits = true;
    }
    if (!currentRoom.getExitRoomNumber(Direction.WEST).equals("0")) {
      out.print("WEST ");
      hasExits = true;
    }
    if (!hasExits) {
      out.print("NONE");
    }
    out.println();
  }

  /**
   * Let monster counterattack
   */
  public void monsterAttacksPlayer() throws IOException {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    Monster monster = currentRoom.getMonster();
    if (monster != null && monster.isActive() && monster.canAttack()) {
      int damage = monster.attack(gameWorld.getPlayer());
      if (damage > 0) {
        out.println(monster.getName() + " " + monster.getAttackDescription());
        out.println("You take " + damage + " damage!");
      }
    }
  }

  /**
   * Display player's inventory
   */
  public void showInventory() throws IOException {
    Player player = gameWorld.getPlayer();
    out.println("Inventory (weight: " + player.getInventoryWeight() + "/" + player.getMaxWeight() + "):");
    if (player.getInventory().isEmpty()) {
      out.println("Your inventory is empty.");
    } else {
      for (Item item : player.getInventory()) {
        out.println("- " + item.getName() + " (weight: " + item.getWeight() +
                ", uses: " + item.getUsesRemaining() + ")");
      }
    }
  }

  /**
   * Take an item from the current room
   */
  public void takeItem(String itemName) throws IOException {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    Item item = currentRoom.getItem(itemName);
    if (item == null) {
      out.println("There's no " + itemName + " here to take.");
      return;
    }
    if (gameWorld.getPlayer().addToInventory(item)) {
      currentRoom.removeItem(item);
      out.println("You pick up the " + item.getName() + ".");
    } else {
      out.println("You can't carry any more; your inventory is too heavy.");
    }
  }

  /**
   * Drop an item from inventory
   */
  public void dropItem(String itemName) throws IOException {
    Player player = gameWorld.getPlayer();
    Item item = player.getItemFromInventory(itemName);
    if (item == null) {
      out.println("You don't have a " + itemName + " in your inventory.");
      return;
    }
    if (player.removeFromInventory(item)) {
      player.getCurrentRoom().addItem(item);
      out.println("You drop the " + item.getName() + ".");
    } else {
      out.println("Can't drop the " + item.getName() + ".");
    }
  }

  /**
   * Examine an object in inventory or room
   */
  public void examine(String target) throws IOException {
    // Check inventory first
    Player player = gameWorld.getPlayer();
    Item invItem = player.getItemFromInventory(target);
    if (invItem != null) {
      out.println(invItem.getDescription());
      return;
    }

    // Check room items
    Room currentRoom = player.getCurrentRoom();
    Item roomItem = currentRoom.getItem(target);
    if (roomItem != null) {
      out.println(roomItem.getDescription());
      return;
    }

    // Check fixtures
    Fixture fixture = currentRoom.getFixture(target);
    if (fixture != null) {
      out.println(fixture.getDescription());
      return;
    }

    // Check monster
    Monster monster = currentRoom.getMonster();
    if (monster != null && monster.isActive() && monster.getName().equalsIgnoreCase(target)) {
      out.println(monster.getDescription());
      return;
    }

    out.println("You don't see a " + target + " here.");
  }

  /**
   * Use an item
   */
  public void useItem(String itemName) throws IOException {
    Player player = gameWorld.getPlayer();
    Item item = player.getItemFromInventory(itemName);
    if (item == null) {
      out.println("You don't have a " + itemName + " in your inventory.");
      return;
    }
    if (item.getUsesRemaining() <= 0) {
      out.println("The " + item.getName() + " has no uses left.");
      return;
    }

    Room currentRoom = player.getCurrentRoom();
    boolean solved = gameWorld.applySolution(item.getName());
    if (solved) {
      if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isActive()) {
        out.println("You used the " + item.getName() + " to solve the puzzle!");
        out.println(currentRoom.getPuzzle().getEffects());
        out.println("You gain " + currentRoom.getPuzzle().getValue() + " points!");
      } else if (currentRoom.getMonster() != null && !currentRoom.getMonster().isActive()) {
        out.println("You used the " + item.getName() + " to defeat the monster!");
        out.println("You gain " + currentRoom.getMonster().getValue() + " points!");
      }
      item.use();
    } else {
      out.println("You use the " + item.getName() + ".");
      out.println(item.getWhenUsed());
      item.use();
    }
  }

  /**
   * Provide answer to a puzzle
   */
  public void provideAnswer(String answer) throws IOException {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    if (currentRoom.getPuzzle() == null || !currentRoom.getPuzzle().isActive()) {
      out.println("There's no active puzzle here.");
      return;
    }

    Puzzle puzzle = currentRoom.getPuzzle();
    if (!puzzle.getSolution().startsWith("'")) {
      out.println("This puzzle requires using an item, not answering.");
      return;
    }

    boolean solved = gameWorld.applySolution(answer);
    if (solved) {
      out.println("Correct! " + puzzle.getEffects());
      out.println("You gain " + puzzle.getValue() + " points!");
    } else {
      out.println("That's not right. The puzzle is still unsolved.");
    }
  }

  /**
   * Attack monster in room
   */
  public void attackMonster() throws IOException {
    Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
    Monster monster = currentRoom.getMonster();
    if (monster == null || !monster.isActive()) {
      out.println("There's nothing here to attack.");
      return;
    }

    Player player = gameWorld.getPlayer();
    int damage = player.attack(monster);
    if (damage > 0) {
      out.println("You attack the " + monster.getName() + " for " + damage + " damage!");
      if (!monster.isActive()) {
        out.println("You defeated the " + monster.getName() + "!");
        out.println("You gain " + monster.getValue() + " points!");
      }
    } else {
      out.println("Your attack is ineffective.");
    }

    // Monster counterattack
    if (monster.isActive()) {
      monsterAttacksPlayer();
    }
  }

  /**
   * Save the game
   */
  public void saveGame() throws IOException {
    try {
      gameWorld.saveGame("saved_game.json");
      out.println("Game saved successfully!");
    } catch (IOException e) {
      out.println("Error saving game: " + e.getMessage());
    }
  }

  /**
   * Load a saved game
   */
  @Override
  public void loadGame() {
    try {
      gameWorld.loadGame("saved_game.json");
      out.println("Game loaded successfully!");
      look();
    } catch (Exception e) {
      try {
        out.println("Error loading game: " + e.getMessage());
      } catch (Exception ex) {
        System.err.println("Error displaying load error: " + ex.getMessage());
      }
    }
  }

  /**
   * Quit the game
   */
  @Override
  public void quitGame() {
    try {
      showFinalScore();
      endGame();
    } catch (IOException e) {
      System.err.println("Error quitting game: " + e.getMessage());
    }
  }

  /**
   * Display the game over message.
   */
  public void displayGameOver() {
    out.println("\n===== GAME OVER =====");
    out.println("Your health has been depleted.");
    out.println("Final score: " + gameWorld.getPlayer().getScore());
    out.println("=====================\n");
  }

  /**
   * Display final score and rank
   */
  public void showFinalScore() throws IOException {
    Player player = gameWorld.getPlayer();
    out.println("\nGame over!");
    out.println("Final score: " + player.getScore());
    out.println("Rank: " + player.getRank());
  }

  /**
   * Ends the game loop.
   */
  public void endGame() {
    running = false;
  }
}