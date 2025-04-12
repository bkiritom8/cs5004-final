package util;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ImageLoader utility class.
 */
class ImageLoaderTest {

  /**
   * Tests that an existing image file is loaded successfully.
   */
  @Test
  void testLoadExistingImage() {
    BufferedImage img = ImageLoader.loadImage("items", "default.png");
    assertNotNull(img, "Should load existing image");
  }

  /**
   * Tests that a missing image falls back to the default image.
   */
  @Test
  void testLoadMissingImageFallsBack() {
    BufferedImage img = ImageLoader.loadImage("monsters", "nonexistent.png");
    assertNotNull(img, "Should fallback to default image");
  }

  /**
   * Tests fallback behavior when no default image is available.
   */
  @Test
  void testFallbackBlankImage() {
    // This test assumes no default.png exists for "weird"
    BufferedImage img = ImageLoader.loadImage("weird", "nothing.png");
    assertEquals(64, img.getWidth(), "Should return fallback blank image with width 64");
    assertEquals(64, img.getHeight(), "Should return fallback blank image with height 64");
  }
  /**
   * Tests that an image can be loaded directly from the classpath for Swing use.
   */
  @Test
  void testClasspathImageLoad() {
    try {
      var stream = ImageLoader.class.getResourceAsStream("/images/items/sword.png");
      assertNotNull(stream, "Stream for sword.png should not be null");
      BufferedImage img = javax.imageio.ImageIO.read(stream);
      assertNotNull(img, "Image should load from classpath for Swing");
    } catch (Exception e) {
      fail("Exception while loading image from classpath: " + e.getMessage());
    }
  }

}
