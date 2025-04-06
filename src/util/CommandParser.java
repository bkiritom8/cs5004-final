package util;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for parsing command strings into command names and arguments.
 */
public class CommandParser {

  /**
   * Parses an input string into a command and its arguments.
   *
   * @param input The full input string from the user or file
   * @return A ParsedCommand containing the command and its arguments
   */
  public static ParsedCommand parse(String input) {
    String[] parts = input.trim().split("\\s+");
    if (parts.length != 0) {
      String command = parts[0];
      List<String> args = Arrays.asList(parts).subList(1, parts.length);
      return new ParsedCommand(command, args);
    } else {
      return new ParsedCommand("", List.of());
    }
  }

  /**
   * A simple data class that holds a parsed command and its arguments.
   */
  public static class ParsedCommand {
    /** The command keyword (e.g., "take", "look"). */
    public final String command;

    /** The list of arguments following the command. */
    public final List<String> args;

    /**
     * Constructs a new ParsedCommand.
     *
     * @param command The command keyword
     * @param args    The list of arguments
     */
    public ParsedCommand(String command, List<String> args) {
      this.command = command;
      this.args = args;
    }
  }
}
