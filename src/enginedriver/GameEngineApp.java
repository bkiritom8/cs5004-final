package enginedriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;

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
        runTextMode(gameWorld);
      }
      else if ("graphics".equals(mode)) {
        runGraphicsMode(gameWorld);
      }
      else if ("batch".equals(mode)) {
        runBatchMode(gameWorld);
      }
      else {
        throw new IllegalArgumentException("Invalid mode: " + mode);
      }
    } catch (IOException e) {
      throw new IOException("Error starting game: " + e.getMessage(), e);
    }
  }

  /**
   * Runs the game in text mode with console I/O.
   *
   * @param gameWorld The game world model
   * @throws IOException If there is an error with I/O operations
   */
  private void runTextMode(GameWorld gameWorld) throws IOException {
    // Create a text controller with standard input/output
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    TextController textController = new TextController(gameWorld, reader, System.out);

    // Start the text controller
    textController.start();
  }

  /**
   * Runs the game in graphics mode with Swing UI.
   *
   * @param gameWorld The game world model
   */
  private void runGraphicsMode(GameWorld gameWorld) throws IOException {
    // Create and start swing controller
    SwingController swingController = new SwingController(gameWorld);
    swingController.start();
  }

  /**
   * Runs the game in batch mode with file I/O.
   *
   * @param gameWorld The game world model
   * @throws IOException If there is an error with file I/O
   */
  private void runBatchMode(GameWorld gameWorld) throws IOException {
    if (inputFile == null) {
      throw new IllegalArgumentException("Batch mode requires an input file");
    }

    // Create batch controller with or without output file
    BatchController batchController;
    if (outputFile != null) {
      // Output to file
      batchController = new BatchController(gameWorld, inputFile, outputFile, null);
    } else {
      // Output to console
      batchController = new BatchController(gameWorld, inputFile, null);
    }

    // Run the batch controller
    batchController.run();
  }
  }
