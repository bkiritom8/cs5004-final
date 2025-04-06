package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file input and output operations.
 * Provides methods for reading command sequences and writing game output.
 */
public class FileIoManager {

  /**
   * Reads command sequences from the specified file.
   *
   * @param filePath the path to the command file
   * @return a list of command strings read from the file
   */
  public List<String> readCommands(String filePath) {
    List<String> commands = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        commands.add(line.trim());
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + filePath);
      e.printStackTrace();
    }
    return commands;
  }

  /**
   * Writes game output to the specified file.
   *
   * @param filePath the path to the output file
   * @param output   the output string to write to the file
   */
  public void writeOutput(String filePath, String output) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(output);
    } catch (IOException e) {
      System.err.println("Error writing to file: " + filePath);
      e.printStackTrace();
    }
  }
}
