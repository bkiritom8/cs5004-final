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

  @BeforeEach
  void setUp() {
    output = new StringWriter();
    view = new ConsoleView(output);
  }

  @Test
  void testDisplayMessage() {
    view.displayMessage("Hello world");
    String result = output.toString().trim();
    assertEquals("Hello world", result);
  }

  @Test
  void testDisplayRoom() {
    view.displayRoom("You are in a room.");
    String result = output.toString().trim();
    assertTrue(result.contains("== Room =="), "Missing room header");
    assertTrue(result.contains("You are in a room."), "Missing room description");
  }

  @Test
  void testDisplayGameOver() {
    view.displayGameOver();
    String result = output.toString().trim();
    assertEquals("GAME OVER", result);
  }
}
