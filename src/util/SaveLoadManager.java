package util;

import model.GameWorld;

import java.io.*;

/**
 * Utility class for saving and loading GameWorld objects to and from files.
 */
public class SaveLoadManager {

  /**
   * Saves the current game state to a file.
   *
   * @param gameWorld The GameWorld instance to save
   * @param filePath  The file path to save the game to
   * @throws IOException If an I/O error occurs during saving
   */
  public static void saveGame(GameWorld gameWorld, String filePath) throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(gameWorld);
    }
  }

  /**
   * Loads a previously saved game state from a file.
   *
   * @param filePath The file path to load the game from
   * @return The loaded GameWorld instance
   * @throws IOException If an I/O error occurs during loading
   * @throws ClassNotFoundException If the GameWorld class cannot be found
   */
  public static GameWorld loadGame(String filePath) throws IOException, ClassNotFoundException {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      return (GameWorld) in.readObject();
    }
  }
}
