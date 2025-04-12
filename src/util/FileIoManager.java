package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for file input and output operations.
 */
public class FileIoManager {

  private static final Logger logger = Logger.getLogger(FileIoManager.class.getName());

  /**
   * Reads lines from a file and returns them as a list of strings.
   * Each line is trimmed of leading and trailing whitespace.
   *
   * @param filePath the path to the input file
   * @return a list of trimmed lines read from the file
   */
  public static List<String> readFile(String filePath) {
    List<String> lines = new ArrayList<>();

    if (filePath == null) {
      return lines; // Return empty list if null to prevent crash
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line.trim());
      }
    } catch (FileNotFoundException e) {
      // Suppress logging for expected missing file scenario
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading file: " + filePath, e);
    }
    return lines;
  }

  /**
   * Reads command sequences from the specified file.
   * This method delegates to {@link #readFile(String)} for the actual logic.
   *
   * @param filePath the path to the command file
   * @return a list of command strings read from the file
   */
  public static List<String> readCommands(String filePath) {
    return readFile(filePath);
  }

  /**
   * Writes the given output string to the specified file.
   *
   * @param filePath the path to the output file
   * @param output   the string content to be written to the file
   */
  public static void writeOutput(String filePath, String output) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(output);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error writing to file: " + filePath, e);
    }
  }
}