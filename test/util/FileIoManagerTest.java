package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FileIoManager file reading and writing functionality.
 */
class FileIoManagerTest {

  @BeforeAll
  static void suppressLogging() {
    Logger logger = Logger.getLogger(FileIoManager.class.getName());
    logger.setLevel(Level.OFF);
  }

  @Test
  void testReadWriteRoundTrip() {
    String path = "test_output.txt";
    String content = "look\nattack\nquit";

    FileIoManager.writeOutput(path, content);
    List<String> read = FileIoManager.readCommands(path);

    assertEquals(3, read.size());
    assertEquals("look", read.get(0));
    assertEquals("attack", read.get(1));
    assertEquals("quit", read.get(2));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }

  @Test
  void testReadFromMissingFileReturnsEmptyList() {
    List<String> result = FileIoManager.readCommands("nonexistent_file.txt");
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  void writeOutput() {
    String path = "write_test.txt";
    String content = "test\nwrite";

    FileIoManager.writeOutput(path, content);
    List<String> lines = FileIoManager.readFile(path);

    assertEquals(2, lines.size());
    assertEquals("test", lines.get(0));
    assertEquals("write", lines.get(1));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }

  @Test
  void readFile() {
    String path = "read_file_test.txt";
    String content = "line1\nline2";

    FileIoManager.writeOutput(path, content);
    List<String> lines = FileIoManager.readFile(path);

    assertEquals(2, lines.size());
    assertEquals("line1", lines.get(0));
    assertEquals("line2", lines.get(1));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }

  @Test
  void readCommands() {
    String path = "commands_test.txt";
    String content = "go north\npick up key";

    FileIoManager.writeOutput(path, content);
    List<String> lines = FileIoManager.readCommands(path);

    assertEquals(2, lines.size());
    assertEquals("go north", lines.get(0));
    assertEquals("pick up key", lines.get(1));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }

  @Test
  void testWriteOutput() {
    String path = "write_output_test.txt";
    String content = "hello\nworld";

    FileIoManager.writeOutput(path, content);
    List<String> result = FileIoManager.readFile(path);

    assertEquals(2, result.size());
    assertEquals("hello", result.get(0));
    assertEquals("world", result.get(1));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }

  @Test
  void testReadFile() {
    String path = "file_read_test.txt";
    String content = "x\ny\nz";

    FileIoManager.writeOutput(path, content);
    List<String> result = FileIoManager.readFile(path);

    assertEquals(3, result.size());
    assertEquals("x", result.get(0));
    assertEquals("y", result.get(1));
    assertEquals("z", result.get(2));

    File file = new File(path);
    if (!file.delete()) {
      System.err.println("Warning: Failed to delete test file: " + path);
    }
  }
}
