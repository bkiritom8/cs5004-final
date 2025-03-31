package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading commands from input files and writing output.
 */
public class FileIoManager {

  /**
   * Reads command lines from a file.
   *
   * @param path the file path
   * @return list of commands
   */
  public static List<String> readCommandsFromFile(String path) {
    List<String> commands = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
      String line;
      while ((line = reader.readLine()) != null) {
        commands.add(line.trim());
      }
    } catch (IOException e) {
      System.err.println("Failed to read file: " + path);
      System.err.println(e.getMessage());
    }
    return commands;
  }

  /**
   * Writes output to a file.
   *
   * @param path   the file path
   * @param output the output content
   */
  public static void writeOutputToFile(String path, String output) {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
      writer.write(output);
    } catch (IOException e) {
      System.err.println("Failed to write to file: " + path);
      System.err.println(e.getMessage());
    }
  }
}
