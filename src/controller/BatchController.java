// Fix for BatchController.java
package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Direction;
import model.GameWorld;
import model.Player;
import model.Room;
import util.FileIoManager;
import view.GameView;

/**
 * Controller for running the game in batch mode using a list of predefined commands.
 */
public class BatchController extends GameController {
  private final String batchFilePath;
  private final String outputFilePath;
  private final StringBuilder outputBuffer;
  private final GameView view;

  /**
   * Constructs a BatchController with a game world and a batch file path.
   *
   * @param world         The GameWorld instance
   * @param batchFilePath Path to the batch command file
   * @param view          The GameView to display output
   */
  public BatchController(GameWorld world, String batchFilePath, GameView view) {
    super(world);
    this.batchFilePath = batchFilePath;
    this.outputFilePath = null;
    this.view = view;
    this.outputBuffer = new StringBuilder();
  }

  /**
   * Constructs a BatchController with output to a file.
   *
   * @param world         The GameWorld instance
   * @param batchFilePath Path to the batch command file
   * @param outputFilePath Path to the output file
   * @param view          The GameView to display output
   */
  public BatchController(GameWorld world, String batchFilePath, String outputFilePath, GameView view) {
    super(world);
    this.batchFilePath = batchFilePath;
    this.outputFilePath = outputFilePath;
    this.view = view;
    this.outputBuffer = new StringBuilder();
  }

  /**
   * Runs the game using commands from the batch file.
   */
  public void run() {
    List<String> commands = FileIoManager.readCommands(batchFilePath);

    // Process player name first (first line of command file)
    if (!commands.isEmpty()) {
      String playerName = commands.get(0);
      commands.remove(0);
      gameWorld.setPlayerName(playerName);
      addToOutput("Player name set to: " + playerName);
    }

    // Process the remaining commands
    for (String command : commands) {
      if (command.trim().isEmpty() || command.startsWith("//")) {
        continue; // Skip empty lines and comments
      }

      addToOutput("> " + command);

      // Process the command
      if (command.equals("look") || command.equals("l")) {
        performLook();
      } else if (command.equals("inventory") || command.equals("i")) {
        performInventory();
      } else if (command.equals("north") || command.equals("n")) {
        performMove(Direction.NORTH);
      } else if (command.equals("south") || command.equals("s")) {
        performMove(Direction.SOUTH);
      } else if (command.equals("east") || command.equals("e")) {
        performMove(Direction.EAST);
      } else if (command.equals("west") || command.equals("w")) {
        performMove(Direction.WEST);
      } else if (command.startsWith("take ") || command.startsWith("t ")) {
        String itemName = command.startsWith("take ") ? command.substring(5) : command.substring(2);
        performTake(itemName);
      } else if (command.equals("quit") || command.equals("q")) {
        addToOutput("Exiting game with score: " + gameWorld.getPlayer().getScore());
        break;
      } else {
        addToOutput("Unknown command: " + command);
      }
    }

    // Write output to file if specified
    if (outputFilePath != null) {
      try (FileWriter writer = new FileWriter(outputFilePath)) {
        writer.write(outputBuffer.toString());
      } catch (IOException e) {
        System.err.println("Error writing to output file: " + e.getMessage());
      }
    }
  }

  private void addToOutput(String message) {
    outputBuffer.append(message).append("\n");
    if (outputFilePath == null) {
      // Only print to console if not writing to file
      System.out.println(message);
    }
  }

  private void performLook() {
    Player player = gameWorld.getPlayer();
    Room room = player.getCurrentRoom();

    addToOutput("Health: " + player.getHealth() + " (" + player.getHealthStatus() + ")");
    addToOutput("You are in: " + room.getName());
    addToOutput(room.getDescription());

    // Show items in room
    if (!room.getItems().isEmpty()) {
      StringBuilder items = new StringBuilder("Items here: ");
      room.getItems().forEach(item -> items.append(item.getName()).append(" "));
      addToOutput(items.toString());
    }

    // Show exits
    StringBuilder exits = new StringBuilder("Exits: ");
    boolean hasExits = false;

    if (!room.getExitRoomNumber(Direction.NORTH).equals("0")) {
      exits.append("NORTH ");
      hasExits = true;
    }
    if (!room.getExitRoomNumber(Direction.SOUTH).equals("0")) {
      exits.append("SOUTH ");
      hasExits = true;
    }
    if (!room.getExitRoomNumber(Direction.EAST).equals("0")) {
      exits.append("EAST ");
      hasExits = true;
    }
    if (!room.getExitRoomNumber(Direction.WEST).equals("0")) {
      exits.append("WEST ");
      hasExits = true;
    }

    if (hasExits) {
      addToOutput(exits.toString());
    } else {
      addToOutput("There are no obvious exits.");
    }
  }

  private void performInventory() {
    Player player = gameWorld.getPlayer();

    if (player.getInventory().isEmpty()) {
      addToOutput("Your inventory is empty.");
    } else {
      addToOutput("Inventory:");
      player.getInventory().forEach(item ->
              addToOutput("- " + item.getName() + " (uses: " + item.getUsesRemaining() + ")"));
    }
  }

  private void performMove(Direction direction) {
    Player player = gameWorld.getPlayer();
    Room currentRoom = player.getCurrentRoom();

    String exitNumber = currentRoom.getExitRoomNumber(direction);
    if (exitNumber.equals("0")) {
      addToOutput("You can't go that way.");
      return;
    }

    Room nextRoom = currentRoom.getExit(direction);
    if (nextRoom != null) {
      player.setCurrentRoom(nextRoom);
      addToOutput("You move " + direction.toString().toLowerCase() + ".");
      performLook();
    } else {
      addToOutput("You can't go that way right now.");
    }
  }

  private void performTake(String itemName) {
    Player player = gameWorld.getPlayer();
    Room currentRoom = player.getCurrentRoom();

    model.Item item = currentRoom.getItem(itemName);
    if (item == null) {
      addToOutput("There's no " + itemName + " here to take.");
      return;
    }

    if (player.addToInventory(item)) {
      currentRoom.removeItem(item);
      addToOutput("You pick up the " + item.getName() + ".");
    } else {
      addToOutput("You can't carry any more; your inventory is too heavy.");
    }
  }
}