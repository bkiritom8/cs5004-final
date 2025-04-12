package enginedriver;

import java.io.IOException;
import javax.swing.SwingUtilities;
import util.ImageLoader;

/**
 * Main entry point for the Adventure Game application.
 * Handles command-line arguments and launches the appropriate game mode.
 */
public class Main {

  /**
   * The main method that processes command-line arguments and starts the game.
   *
   * @param args Command line arguments
   *             Format: <game_file> [-text|-graphics|-batch <input_file> [output_file]]
   */
  public static void main(String[] args) {
    // If no arguments provided, use default behavior
    if (args.length == 0) {
      try {
        runDefaultGame();
      } catch (IOException e) {
        System.err.println("Error running default game: " + e.getMessage());
        e.printStackTrace();
      }
      return;
    }

    // Process command-line arguments
    if (args.length < 2) {
      printUsage();
      return;
    }

    String gameFile = args[0];
    String mode = args[1];

    try {
      switch (mode) {
        case "-text":
          // Make sure we're explicitly showing the welcome screen
          System.out.println("Starting text adventure mode...");
          GameEngineApp app = new GameEngineApp(gameFile, "text", null, null);
          app.start();
          break;

        case "-graphics":
          // Initialize image resources
          ImageLoader.initialize();

          // Run in graphics mode
          System.out.println("Starting graphics mode...");
          GameEngineApp graphicsApp = new GameEngineApp(gameFile, "graphics", null, null);

          // Run on the Event Dispatch Thread for Swing
          SwingUtilities.invokeLater(() -> {
            try {
              graphicsApp.start();
            } catch (Exception e) {
              System.err.println("Error starting graphics mode: " + e.getMessage());
              e.printStackTrace();
            }
          });
          break;

        case "-batch":
          if (args.length < 3) {
            System.out.println("Batch mode requires an input file");
            printUsage();
            return;
          }

          String inputFile = args[2];
          String outputFile = (args.length > 3) ? args[3] : null;

          // Run in batch mode
          System.out.println("Starting batch mode...");
          GameEngineApp batchApp = new GameEngineApp(gameFile, "batch", inputFile, outputFile);
          batchApp.start();
          break;

        default:
          System.out.println("Invalid mode: " + mode);
          printUsage();
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      printUsage();
    }
  }

  /**
   * Runs the default game in text mode
   *
   * @throws IOException If there is an error with I/O operations
   */
  private static void runDefaultGame() throws IOException {
    // For simplicity, start the text adventure with a default game file
    System.out.println("Starting default game in text mode...");

    // Assuming "default_game.json" is bundled with the application
    GameEngineApp app = new GameEngineApp("default_game.json", "text", null, null);
    app.start();
  }

  /**
   * Prints usage information for the command-line interface.
   */
  private static void printUsage() {
    System.out.println("Usage: java -jar game_engine.jar <game_file> [-text|-graphics|-batch <input_file> [output_file]]");
    System.out.println("  <game_file>      : Path to the JSON game data file");
    System.out.println("  -text            : Run in interactive text mode");
    System.out.println("  -graphics        : Run in graphical mode using Swing UI");
    System.out.println("  -batch <in>      : Run in batch mode with commands from input file");
    System.out.println("  -batch <in> <out>: Run in batch mode with output to file");
    System.out.println("\nWith no arguments, runs a default game in text mode.");
  }
}