package util;

import model.GameWorld;

import java.io.*;

/**
 * Utility for saving and loading GameWorld objects.
 */
public class SaveLoadManager {

  public static void saveGame(GameWorld world, String filePath) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(world);
    } catch (IOException e) {
      System.err.println("Failed to save game: " + e.getMessage());
    }
  }

  public static GameWorld loadGame(String filePath) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      return (GameWorld) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Failed to load game: " + e.getMessage());
      return null;
    }
  }
}

