package enginedriver;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.BatchController;
import controller.SwingController;
import controller.TextController;

import model.GameWorld;

import view.GameView;
import view.text.ConsoleView;

/**
 * The main application class that initializes and runs the adventure game.
 * This class serves as a facade for creating and connecting the appropriate model,
 * view, and controller components based on the selected game mode.
 */
public class GameEngineApp {
  private final String gameFileName;
  private final String mode;
  private final String inputFile;
  private final String outputFile;

  /**
   * Constructs a new GameEngineApp with the specified parameters.
   *
   * @param gameFileName The path to the JSON file containing game data
   * @param mode The mode to run the game in ("text", "graphics", or "batch")
   * @param inputFile The input file for batch mode (null for other modes)
   * @param outputFile The output file for batch mode (null for console output or other modes)
   */
  public GameEngineApp(String gameFileName, String mode, String inputFile, String outputFile) {
    this.gameFileName = gameFileName;
    this.mode = mode;
    this.inputFile = inputFile;
    this.outputFile = outputFile;
  }

  /**
   * Initializes and starts the game.
   * This method creates the game world, appropriate controller, and begins the game.
   *
   * @throws IOException If there is an error reading the game file or during I/O operations
   */
  public void start() throws IOException {
    try {
      // Create model from game file
      GameWorld gameWorld = new GameWorld(gameFileName);

      // Process different modes
      if ("text".equals(mode)) {
        // Text mode with console I/O
        TextController textController = new TextController(
                gameWorld,
                new BufferedReader(new InputStreamReader(System.in)),
                System.out
        );
        textController.start();
      }
      else if ("graphics".equals(mode)) {
        // Graphical mode with Swing UI
        SwingController swingController = new SwingController(gameWorld);
        swingController.start();
      }
      else if ("batch".equals(mode)) {
        // Batch mode with file I/O
        if (outputFile != null) {
          // Create a view that writes to a file
          try (FileWriter writer = new FileWriter(outputFile)) {
            GameView view = new ConsoleView(true);
            BatchController batchController = new BatchController(
                    gameWorld,
                    inputFile,
                    view
            );
            batchController.run();
          }
        } else {
          // Create a view that writes to console
          GameView view = new ConsoleView(false);
          BatchController batchController = new BatchController(
                  gameWorld,
                  inputFile,
                  view
          );
          batchController.run();
        }
      }
      else {
        throw new IllegalArgumentException("Invalid mode: " + mode);
      }
    } catch (IOException e) {
      throw new IOException("Error starting game: " + e.getMessage(), e);
    }
  }
}