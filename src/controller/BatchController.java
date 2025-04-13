package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Direction;
import model.GameWorld;
import model.Player;
import model.Room;
import model.Item;
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
   * @param world          The GameWorld instance
   * @param batchFilePath  Path to the batch command file
   * @param outputFilePath Path to the output file
   * @param view           The GameView to display output
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

    for (String command : commands) {
      if (command.trim().isEmpty() || command.startsWith("//")) {
        continue;
      }

      addToOutput("> " + command);

      switch (command.toLowerCase()) {
        case "look", "l" -> performLook();
        case "inventory", "i" -> performInventory();
        case "north", "n" -> performMove(Direction.NORTH);
        case "south", "s" -> performMove(Direction.SOUTH);
        case "east", "e" -> performMove(Direction.EAST);
        case "west", "w" -> performMove(Direction.WEST);
        case "quit", "q" -> {
          addToOutput("Exiting game with score: " + gameWorld.getPlayer().getScore());
          break;
        }
        default -> {
          if (command.startsWith("take ") || command.startsWith("t ")) {
            String itemName = command.startsWith("take ") ? command.substring(5) : command.substring(2);
            performTake(itemName);
          } else {
            addToOutput("Unknown command: " + command);
          }
        }
      }
    }

    if (outputFilePath != null) {
      try (FileWriter writer = new FileWriter(outputFilePath)) {
        writer.write(outputBuffer.toString());
      } catch (IOException e) {
        System.err.println("Error writing to output file: " + e.getMessage());
      }
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

  private void performLook() {
    Player player = gameWorld.getPlayer();
    Room room = player.getCurrentRoom();

    addToOutput("Health: " + player.getHealth() + " (" + player.getHealthStatus() + ")");
    addToOutput("You are in: " + room.getName());
    addToOutput(room.getDescription());

    if (!room.getItems().isEmpty()) {
      StringBuilder items = new StringBuilder("Items here: ");
      room.getItems().forEach(item -> items.append(item.getName()).append(" "));
      addToOutput(items.toString().trim());
    }

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

    addToOutput(hasExits ? exits.toString().trim() : "There are no obvious exits.");
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

  private void performTake(String itemName) {
    Player player = gameWorld.getPlayer();
    Room currentRoom = player.getCurrentRoom();

    Item item = currentRoom.getItem(itemName);
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

  private void addToOutput(String message) {
    outputBuffer.append(message).append("\n");
    if (outputFilePath == null) {
      System.out.println(message);
    }
  }
}
