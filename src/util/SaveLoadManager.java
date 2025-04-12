package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import model.GameWorld;

/**
 * Utility for saving and loading the GameWorld state.
 */
public class SaveLoadManager {

  /**
   * Saves the current game state to a file.
   *
   * @param world the GameWorld to save
   * @param filePath the file path to save to
   */
  public static void saveGame(GameWorld world, String filePath) {
    File file = new File(filePath);
    file.getParentFile().mkdirs(); // Ensure directories exist
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
      out.writeObject(world);
    } catch (IOException e) {
      System.err.println("Failed to save game: " + e.getMessage());
    }
  }

  /**
   * Loads a game state from a file.
   *
   * @param filePath the file path to load from
   * @return the loaded GameWorld, or null if failed
   */
  public static GameWorld loadGame(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      System.err.println("Save file not found: " + filePath);
      return null;
    }

    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
      return (GameWorld) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Failed to load game: " + e.getMessage());
      return null;
    }
  }
}
