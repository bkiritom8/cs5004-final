package util;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {
  @Test
  public void testParseCommandWithArguments() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("take sword");
    assertEquals("take", cmd.command);
    assertEquals(List.of("sword"), cmd.args);
  }

  @Test
  public void testParseCommandWithNoArguments() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("look");
    assertEquals("look", cmd.command);
    assertTrue(cmd.args.isEmpty());
  }

  @Test
  public void testParseEmptyInput() {
    CommandParser.ParsedCommand cmd = CommandParser.parse("");
    assertEquals("", cmd.command);
    assertTrue(cmd.args.isEmpty());
  }
}
