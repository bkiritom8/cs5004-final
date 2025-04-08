package controller;

import java.io.StringReader;
import java.util.List;
import model.GameWorld;
import util.CommandParser;
import util.CommandParser.ParsedCommand;
import util.FileIoManager;
import view.GameView;

/**
 * Controller for running the game in batch mode using a list of predefined commands.
 */
public class BatchController extends GameController {
  private final String batchFilePath;
  private final GameView view;

  /**
   * Constructs a BatchController with a game world and input file.
   *
   * @param world         The GameWorld instance
   * @param batchFilePath Path to the batch command file
   * @param view          The GameView to display output
   */
  public BatchController(GameWorld world, String batchFilePath, GameView view) {
    // Use the constructor from GameController that takes a world, input, and output
    super(world, new StringReader(""), new StringBuilder());
    this.batchFilePath = batchFilePath;
    this.view = view;
  }

  /**
   * Runs the game using the commands from the batch file.
   */
  @Override
  public void start() {
    run();
  }

  /**
   * Runs the game using the commands from the batch file.
   */
  public void run() {
    List<String> commands = FileIoManager.readFile(batchFilePath);
    if (commands == null) {
      if (view != null) {
        view.displayMessage("Error reading command file: " + batchFilePath);
      }
      return;
    }

    for (String line : commands) {
      if (line.trim().isEmpty() || line.startsWith("//")) {
        continue; // Skip empty lines and comments
      }

      ParsedCommand parsed = CommandParser.parse(line);
      processCommandString(parsed.command(), parsed.args());
    }
  }

  /**
   * Processes a command string with arguments.
   *
   * @param commandName The command name
   * @param args The command arguments
   */
  private void processCommandString(String commandName, List<String> args) {
    try {
      String fullCommand = commandName;
      if (!args.isEmpty()) {
        fullCommand += " " + String.join(" ", args);
      }

      // Use the existing command processing in GameController
      processCommand(fullCommand);

    } catch (Exception e) {
      if (view != null) {
        view.displayMessage("Error processing command: " + e.getMessage());
      }
    }
  }
}