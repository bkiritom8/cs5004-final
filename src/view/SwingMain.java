package view;

import java.io.IOException;

import controller.SwingController;
import model.GameWorld;
import view.swing.GameWindow;

public class SwingMain {

  public static void main(String[] args) {
    try {
      // Create a new game world and controller
      GameWorld world = new GameWorld("align_quest_game_elements.json");
      SwingController controller = new SwingController(world);

      // Create the main game window
      new GameWindow("Adventure Game", controller).setVisible(true);

      // Start the game
    } catch (IOException e) {
      System.err.println("Error loading game world: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
    }
  }
}
