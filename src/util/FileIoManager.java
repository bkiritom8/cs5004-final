/* FileIOManager.java */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIoManager {

  public static List<String> readFile(String s) {
  return null;
  }

  /**
   * Reads commands from a given file.
   *
   * @param filePath The path to the batch command file.
   * @return A list of command strings.
   */
  public List<String> readCommands(String filePath) {
    List<String> commands = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        commands.add(line.trim());
      }
    } catch (IOException e) {
      System.err.println("Error reading commands from file: " + e.getMessage());
    }
    return commands;
  }

  /**
   * Writes game output to a specified file.
   *
   * @param filePath The path to the output file.
   * @param content The output content to write.
   */
  public void writeOutput(String filePath, String content) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(content);
    } catch (IOException e) {
      System.err.println("Error writing output to file: " + e.getMessage());
    }
  }
}
