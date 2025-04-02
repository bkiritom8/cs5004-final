package util;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.*;

public class ImageLoaderTest {
  @Test
  public void testLoadDefaultImage() {
    BufferedImage image = ImageLoader.loadImage("rooms/default.png");
    assertNotNull(image, "Default room image should load successfully");
  }

  @Test
  public void testLoadNonExistentImage() {
    BufferedImage image = ImageLoader.loadImage("nonexistent.png");
    assertNull(image, "Non-existent image should return null");
  }
}