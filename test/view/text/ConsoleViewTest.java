package view.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ConsoleView text-based implementation of GameView.
 */
class ConsoleViewTest {

  private StringWriter output;
  private ConsoleView view;

  /**
   * Sets up a new ConsoleView with StringWriter before each test.
   */
  @BeforeEach
  void setUp() {
    output = new StringWriter();
    view = new ConsoleView(output);
  }

  /**
   * Tests that displayMessage outputs the correct message.
   */
  @Test
  void testDisplayMessage() {
    view.displayMessage("Hello world");
    assertTrue(output.toString().contains("Hello world"));
  }

  /**
   * Tests that displayRoom includes header and room description.
   */
  @Test
  void testDisplayRoom() {
    view.displayRoom("You are in a room.");
    String out = output.toString();
    assertTrue(out.contains("== Room =="));
    assertTrue(out.contains("You are in a room."));
  }

  /**
   * Tests that displayGameOver outputs the game over message.
   */
  @Test
  void testDisplayGameOver() {
    view.displayGameOver();
    assertTrue(output.toString().contains("GAME OVER"));
  }
}
