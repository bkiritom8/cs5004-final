package util;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
  public static ParsedCommand parse(String input) {
    String[] parts = input.trim().split("\\s+");
    if (parts.length == 0) return new ParsedCommand("", List.of());
    String command = parts[0];
    List<String> args = Arrays.asList(parts).subList(1, parts.length);
    return new ParsedCommand(command, args);
  }

  public static class ParsedCommand {
    public final String command;
    public final List<String> args;

    public ParsedCommand(String command, List<String> args) {
      this.command = command;
      this.args = args;
    }
  }
}