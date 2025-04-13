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

  /**
   * Test that loading a valid item image works.
   */
  @Test
  void testLoadValidItemImage() {
    BufferedImage image = ImageLoader.loadImage("items", "diamond.png");
    assertNotNull(image, "Expected to load known item image");
    assertTrue(image.getWidth() > 0 && image.getHeight() > 0);
  }

  /**
   * Test fallback to category default image for missing item.
   */
  @Test
  void testFallbackToCategoryDefault() {
    BufferedImage image = ImageLoader.loadImage("items", "nonexistent-item.png");
    assertNotNull(image, "Expected to load fallback image for missing item");
    assertTrue(image.getWidth() > 0 && image.getHeight() > 0);
  }

  /**
   * Test fallback to default item image for unknown category.
   */
  @Test
  void testFallbackForUnknownCategory() {
    BufferedImage image = ImageLoader.loadImage("weird", "nothing.png");
    assertNotNull(image, "Expected fallback to 'default item.png' for unknown category");
    assertTrue(image.getWidth() > 0 && image.getHeight() > 0);
  }

  /**
   * Test ultimate fallback to blank image.
   */
  @Test
  void testFallbackToUltimateBackup() {
    BufferedImage image = ImageLoader.loadImage("fakecategory", "fakeimage.png");
    assertNotNull(image, "Expected fallback to ultimate backup image");
    assertEquals(64, image.getWidth(), "Should return blank 64x64 image if all fails");
    assertEquals(64, image.getHeight(), "Should return blank 64x64 image if all fails");
  }
}

