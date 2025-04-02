package util;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileIoManagerTest {
  @Test
  public void testReadExistingBatchFile() {
    List<String> lines = FileIoManager.readFile("/batch/example_commands.txt");
    assertNotNull(lines);
    assertFalse(lines.isEmpty(), "Batch file should not be empty");
    assertTrue(lines.contains("look"));
  }

  @Test
  public void testReadNonExistentFile() {
    List<String> lines = FileIoManager.readFile("/batch/nonexistent.txt");
    assertTrue(lines.isEmpty(), "Should return empty list for nonexistent file");
  }
}
