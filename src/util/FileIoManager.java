package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading commands and writing output to files.
 */
public class FileIoManager {

  /**
   * Reads commands line-by-line from a file.
   *
   * @param path Path to the input file
   * @return List of trimmed command strings
   * @throws IOException If the file cannot be read
   */
  public static List<String> readCommands(String path) throws IOException {
    List<String> commands = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        commands.add(line.trim());
      }
    }
    return commands;
  }

  /**
   * Writes text output to a file.
   *
   * @param path    Path to the output file
   * @param content Text content to write
   * @throws IOException If the file cannot be written
   */
  public static void writeOutput(String path, String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      writer.write(content);
    }
  }

  /**
   * Placeholder method to read a file into a list of strings.
   *
   * @param batchFilePath Path to the file
   * @return An empty list (not implemented)
   */
  public static List<String> readFile(String batchFilePath) {
    return List.of(); // Placeholder; implement as needed
  }
}
