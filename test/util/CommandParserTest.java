package util;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CommandParser class.
 */
public class CommandParserTest {

  /**
   * Tests parsing a command string with one argument.
   */
  @Test
  public void testParseCommandWithArguments() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("take sword");
    assertEquals("take", cmd.command);
    assertEquals(List.of("sword"), cmd.args);
  }

  /**
   * Tests parsing a command string with no arguments.
   */
  @Test
  public void testParseCommandWithNoArguments() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("look");
    assertEquals("look", cmd.command);
    assertTrue(cmd.args.isEmpty());
  }

  /**
   * Tests parsing an empty command string.
   */
  @Test
  public void testParseEmptyInput() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("");
    assertEquals("", cmd.command);
    assertTrue(cmd.args.isEmpty());
  }
}
