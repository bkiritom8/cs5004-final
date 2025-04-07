package controller;

import model.*;
import view.GameView;

import java.io.IOException;
import java.util.Scanner;

/*
controller commands:
(n)orth, (s)outh, (e)ast, (w)est, (t)ake, (d)rop, e(x)amine, attac(k), (l)ook, (u)se, (i)nventory, (a)nswer, sa(v)e, (r)estore, (q)uit.
*/
public class GameController {
    public GameWorld gameWorld;
    public Scanner scanner;
    public Appendable output;
    public boolean gameOver;
    public GameView view;

  // set up game world environment, input, and output
    public GameController(GameWorld gameWorld, Readable input, Appendable output) {
        this.gameWorld = gameWorld;
        this.scanner = new Scanner(input);
        this.output = output;
        this.gameOver = false;
    }

    public GameController(GameWorld world) {
    }

  public GameController() {
  }

  // run main game loop
    public void play() throws IOException {
        displayWelcome();
        promptForPlayerName();
        while (!gameOver && scanner.hasNextLine()) {
            lookAround();
            if (gameWorld.getPlayer().getHealth() <= 0) {
                displayGameOver();
                endGame();
                return;
            }
            displayMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            processCommand(command);
        }
    }
    
    // display welcome message
    public void displayWelcome() throws IOException {
        output.append("welcome to " + gameWorld.getGameName() + "!\n\n");
    }
    
    // prompt for player's name
    public void promptForPlayerName() throws IOException {
        output.append("enter your name: ");
        String name = scanner.nextLine().trim();
        gameWorld.setPlayerName(name);
        output.append("hello, " + name + "! let's start your adventure.\n\n");
    }
    
    // display current room and health status
    public void look() throws IOException {
        Player player = gameWorld.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        output.append("health: " + player.getHealth() + " (" + player.getHealthStatus() + ")\n");
        output.append("you are in the " + currentRoom.getName().toLowerCase() + "\n");
        Puzzle puzzle = currentRoom.getPuzzle();
        Monster monster = currentRoom.getMonster();
        if (puzzle != null && puzzle.isActive() && puzzle.affectsTarget()) {
            output.append(puzzle.getEffects() + "\n");
        } else if (monster != null && monster.isActive()) {
            output.append(monster.getEffects() + "\n");
            monsterAttacksPlayer();
        } else {
            output.append(currentRoom.getDescription() + "\n");
        }
        displayRoomItems();
    }
    
    // display items in the room
    public void displayRoomItems() throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        if (!currentRoom.getItems().isEmpty()) {
            output.append("items here: ");
            for (Item item : currentRoom.getItems()) {
                output.append(item.getName().toLowerCase() + " ");
            }
            output.append("\n");
        }
    }
    
    // let monster counterattack
    public void monsterAttacksPlayer() throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        Monster monster = currentRoom.getMonster();
        if (monster != null && monster.isActive() && monster.canAttack()) {
            int damage = monster.attack(gameWorld.getPlayer());
            if (damage > 0) {
                output.append(monster.getName().toLowerCase() + " " + monster.getAttackDescription() + "\n");
                output.append("you take -" + damage + " damage!\n");
            }
        }
    }
    
    // display list of available commands
    public void displayMenu() throws IOException {
        output.append("\ncommands: (n)orth, (s)outh, (e)ast, (w)est, (t)ake, (d)rop, e(x)amine, attac(k), (l)ook, (u)se, (i)nventory, (a)nswer, sa(v)e, (r)estore, (q)uit\n");
        output.append("your choice: ");
    }
    
    // process player's command
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
            lookAround();
        } else if (command.equals("i") || command.equals("inventory")) {
            showInventory();
        } else if (command.equals("k") || command.equals("attack")) {
            attackMonster();
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
        } else if (command.equals("r") || command.equals("restore")) {
            restoreGame();
        } else if (command.equals("q") || command.equals("quit")) {
            showFinalScore();
            endGame();
        } else {
            output.append("I don't understand that command.\n");
        }
    }

  // move player in the given direction
    public void move(Direction direction) throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        String exitNumber = currentRoom.getExitRoomNumber(direction);
        if (exitNumber.equals("0")) {
            output.append("you can't go that way. there's a wall.\n");
            return;
        }
        if (Integer.parseInt(exitNumber) < 0) {
            if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
                output.append("blocked by puzzle: " + currentRoom.getPuzzle().getDescription() + "\n");
            } else if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
                output.append("blocked by monster: " + currentRoom.getMonster().getDescription() + "\n");
                monsterAttacksPlayer();
            } else {
                output.append("the path is blocked.\n");
            }
            return;
        }
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            gameWorld.getPlayer().setCurrentRoom(nextRoom);
            output.append("you move " + direction.toString().toLowerCase() + ".\n");
        } else {
            output.append("exit error. can't move there.\n");
        }
    }
    
    // display player's inventory
    public void showInventory() throws IOException {
        Player player = gameWorld.getPlayer();
        output.append("inventory (weight: " + player.getInventoryWeight() + "/" + player.getMaxWeight() + "):\n");
        if (player.getInventory().isEmpty()) {
            output.append("your inventory is empty.\n");
        } else {
            for (Item item : player.getInventory()) {
                output.append("- " + item.getName().toLowerCase() + " (weight: " + item.getWeight() +
                            ", uses: " + item.getUsesRemaining() + ")\n");
            }
        }
    }
    
    // attack monster in the room
    public void attackMonster() throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        Monster monster = currentRoom.getMonster();
        if (monster == null || !monster.isActive()) {
            output.append("there's nothing here to attack.\n");
            return;
        }
        output.append("you attack the " + monster.getName().toLowerCase() + ", but it's not very effective.\n");
        monsterAttacksPlayer();
    }
    
    // pick up an item
    public void takeItem(String itemName) throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        if (item == null) {
            output.append("there's no " + itemName + " here to take.\n");
            return;
        }
        if (gameWorld.getPlayer().addToInventory(item)) {
            currentRoom.removeItem(item);
            output.append("you pick up the " + item.getName().toLowerCase() + ".\n");
        } else {
            output.append("you can't carry any more; your inventory is too heavy.\n");
        }
    }
    
    // drop an item from inventory
    public void dropItem(String itemName) throws IOException {
        Player player = gameWorld.getPlayer();
        Item item = player.getItemFromInventory(itemName);
        if (item == null) {
            output.append("you don't have a " + itemName + " in your inventory.\n");
            return;
        }
        if (player.removeFromInventory(item)) {
            player.getCurrentRoom().addItem(item);
            output.append("you drop the " + item.getName().toLowerCase() + ".\n");
        } else {
            output.append("can't drop the " + item.getName().toLowerCase() + ".\n");
        }
    }
    
    // examine an object in inventory, room, or fixture
    public void examine(String target) throws IOException {
        Player player = gameWorld.getPlayer();
        Item invItem = player.getItemFromInventory(target);
        if (invItem != null) {
            output.append(invItem.getDescription() + "\n");
            return;
        }
        Room currentRoom = player.getCurrentRoom();
        Item roomItem = currentRoom.getItem(target);
        if (roomItem != null) {
            output.append(roomItem.getDescription() + "\n");
            return;
        }
        Fixture fixture = currentRoom.getFixture(target);
        if (fixture != null) {
            output.append(fixture.getDescription() + "\n");
            return;
        }
        output.append("you don't see a " + target + " here.\n");
    }
    
    // use an item to solve a puzzle or defeat a monster
    public void useItem(String itemName) throws IOException {
        Player player = gameWorld.getPlayer();
        Item item = player.getItemFromInventory(itemName);
        if (item == null) {
            output.append("you don't have a " + itemName + " in your inventory.\n");
            return;
        }
        if (item.getUsesRemaining() <= 0) {
            output.append("the " + item.getName().toLowerCase() + " has no uses left.\n");
            return;
        }
        Room currentRoom = player.getCurrentRoom();
        boolean solved = gameWorld.applySolution(item.getName());
        if (solved) {
            if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isActive()) {
                output.append("you used the " + item.getName().toLowerCase() + " to solve the puzzle!\n");
                output.append(currentRoom.getPuzzle().getEffects() + "\n");
                output.append("you gain " + currentRoom.getPuzzle().getValue() + " points!\n");
            } else if (currentRoom.getMonster() != null && !currentRoom.getMonster().isActive()) {
                output.append("you used the " + item.getName().toLowerCase() + " to defeat the monster!\n");
                output.append("you gain " + currentRoom.getMonster().getValue() + " points!\n");
            }
            item.use();
        } else {
            output.append("you use the " + item.getName().toLowerCase() + ".\n");
            output.append(item.getWhenUsed() + "\n");
            item.use();
        }
    }
    
    // provide an answer to a puzzle
    public void provideAnswer(String answer) throws IOException {
        Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
        if (currentRoom.getPuzzle() == null || !currentRoom.getPuzzle().isActive()) {
            output.append("there's no active puzzle here.\n");
            return;
        }
        Puzzle puzzle = currentRoom.getPuzzle();
        if (!puzzle.getSolution().startsWith("'")) {
            output.append("this puzzle requires using an item, not answering.\n");
            return;
        }
        boolean solved = gameWorld.applySolution(answer);
        if (solved) {
            output.append("correct! " + puzzle.getEffects() + "\n");
            output.append("you gain " + puzzle.getValue() + " points!\n");
        } else {
            output.append("that's not right. the puzzle is still unsolved.\n");
        }
    }
    
    // save game state
    public void saveGame() throws IOException {
        try {
            gameWorld.saveGame("saved_game.json");
            output.append("game saved successfully!\n");
        } catch (IOException e) {
            output.append("error saving game: " + e.getMessage() + "\n");
        }
    }
    
    // restore game state
    public void restoreGame() throws IOException {
        try {
            gameWorld.loadGame("saved_game.json");
            output.append("game restored successfully!\n");
        } catch (Exception e) {
            output.append("error restoring game: " + e.getMessage() + "\n");
        }
    }

  private void lookAround() {
    // TODO Generate a look around method, Currently used as a placeholder
  }
    
    // display final score and rank
    public void showFinalScore() throws IOException {
        Player player = gameWorld.getPlayer();
        output.append("\ngame over!\n");
        output.append("final score: " + player.getScore() + "\n");
        output.append("rank: " + player.getRank() + "\n");
    }
    
    // display game over message
    public void displayGameOver() throws IOException {
        output.append("\nyour health is depleted. you fall into a deep sleep.\n");
        output.append("game over\n");
    }
    
    // end game loop
    public void endGame() {
        gameOver = true;
    }

  /**
   * Default start method for the base controller.
   * Empty implementation to be overridden by subclasses.
   */
  public void start() {}

  /**
   * Provides an answer to a puzzle.
   * Delegates to the provideAnswer method.
   *
   * @param answer The answer to the puzzle
   */
  public void answerPuzzle(String answer) {
    try {
      provideAnswer(answer);
    } catch (IOException e) {
      System.err.println("Error processing answer: " + e.getMessage());
    }
  }

  /**
   * Quits the game.
   * Shows the final score and ends the game.
   */
  public void quitGame() {
    try {
      showFinalScore();
      endGame();
    } catch (IOException e) {
      System.err.println("Error quitting game: " + e.getMessage());
    }
  }

  /**
   * Loads a saved game.
   * Attempts to load the game state from a save file and updates the view.
   */
  public void loadGame() {
    try {
      gameWorld.loadGame("saved_game.json");
      output.append("Game loaded successfully!\n");

      // Update view with current room information
      Room currentRoom = gameWorld.getPlayer().getCurrentRoom();
      output.append("You are in: " + currentRoom.getName() + "\n");
      output.append(currentRoom.getDescription() + "\n");

      // Show available exits
      showExits(currentRoom);
    } catch (Exception e) {
      try {
        output.append("Error loading game: " + e.getMessage() + "\n");
      } catch (IOException ioEx) {
        System.err.println("Error displaying load error: " + ioEx.getMessage());
      }
    }
  }

  /**
   * Helper method to show available exits in a room.
   * Used after loading a game to display the current room state.
   *
   * @param room The room to show exits for
   * @throws IOException If there is an error writing to the output
   */
  private void showExits(Room room) throws IOException {
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
      output.append(exits.toString() + "\n");
    } else {
      output.append("There are no obvious exits.\n");
    }
  }
}

