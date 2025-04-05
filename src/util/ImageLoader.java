package util;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

import model.Room;


/**
 * Utility class to load and cache images by category and name with default fallbacks.
 */
public class ImageLoader {
  private static final Map<String, BufferedImage> cache = new HashMap<>();
  private static final String BASE_PATH = "/images/";
  private static final String DEFAULT_IMAGE = "default.png";

  /**
   * Loads an image by category and name, or returns a default if not found.
   *
   * @param category The image folder (e.g., "items", "rooms")
   * @param name     The image filename (e.g., "sword.png")
   * @return The loaded image or a fallback image
   */
  public static BufferedImage loadImage(String category, String name) {
    String key = category + "/" + name;
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    String path = BASE_PATH + category + "/" + name;
    try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
      if (stream != null) {
        BufferedImage image = ImageIO.read(stream);
        cache.put(key, image);
        return image;
      }
    } catch (Exception ignored) {}

    return loadDefaultImage(category);
  }

  /**
   * Loads the default fallback image for a given category.
   *
   * @param category The image category folder
   * @return The fallback image or blank placeholder
   */
  private static BufferedImage loadDefaultImage(String category) {
    String fallbackPath = BASE_PATH + category + "/" + DEFAULT_IMAGE;
    try (InputStream stream = ImageLoader.class.getResourceAsStream(fallbackPath)) {
      if (stream != null) {
        return ImageIO.read(stream);
      }
    } catch (Exception ignored) {}
    return new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
  }

  /**
   * Initializes the image loader, currently a placeholder method.
   */
  public static void initialize() {
  }

  /**
   * Loads an image from a combined path string.
   *
   * @param s The relative image path (e.g., "items/sword.png")
   * @return The loaded image or fallback image
   */
  public static BufferedImage loadImage(String s) {
    return loadImageFromPath(s);
  }

  /**
   * Loads an image directly from a path string.
   *
   * @param path Relative path to image within resources
   * @return Buffered image or blank fallback image
   */
  private static BufferedImage loadImageFromPath(String path) {
    if (cache.containsKey(path)) {
      return cache.get(path);
    }

    String fullPath = BASE_PATH + path;
    try (InputStream stream = ImageLoader.class.getResourceAsStream(fullPath)) {
      if (stream != null) {
        BufferedImage image = ImageIO.read(stream);
        cache.put(path, image);
        return image;
      }
    } catch (Exception ignored) {}

    return new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
  }

  public static Icon getRoomImage(Room room) {
    return null;
  }
}
