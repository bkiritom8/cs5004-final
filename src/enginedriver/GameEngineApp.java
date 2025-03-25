package enginedriver;

import java.io.IOException;
import model.GameWorld;
import controller.GameController;

/**
 *
 */
public class GameEngineApp {
  private final String gameFileName;
  private final Readable input;
  private final  Appendable output;

  /**
   *
   * @param gameFileName
   * @param input
   * @param output
   */
  public GameEngineApp(String gameFileName, Readable input, Appendable output) {
    this.gameFileName = gameFileName;
    this.input = input;
    this.output = output;
  }

  /**
   *
   * @throws IOException
   */
  public void  start() throws IOException {
    try {
      // Create the game model by loading the specified JSON
      GameWorld gameWorld = new GameWorld(gameFileName);

      // Create the controller, linking it to the model and I/O
      GameController controller = new GameController(gameWorld, input, output);

      // Start game loop
      controller.play();
    } catch (IOException e) {
      throw new IOException("Error starting game: " + e.getMessage(), e);
    }
  }
}
