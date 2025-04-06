package util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for FileIoManager file reading and writing functionality.
 */
class FileIoManagerTest {

  /**
   * Tests writing to a file and then reading from it to verify content round-trip.
   */
  @Test
  void testReadWriteRoundTrip() throws IOException {
    String path = "test_output.txt";
    String content = "look\nattack\nquit";

    FileIoManager.writeOutput(path, content);

    List<String> read = FileIoManager.readCommands(path);
    assertEquals(3, read.size());
    assertEquals("look", read.get(0));
    assertEquals("quit", read.get(2));

    assertTrue(new File(path).delete());
  }

  /**
   * Tests that reading a missing file throws an IOException.
   */
  @Test
  void testReadFromMissingFileThrows() {
    assertThrows(IOException.class, () -> FileIoManager.readCommands("missing.txt"));
  }

  @Test
  void writeOutput() {
  }

  @Test
  void readFile() {
  }

  @Test
  void readCommands() {
  }

  @Test
  void testWriteOutput() {
  }

  @Test
  void testReadFile() {
  }
}
