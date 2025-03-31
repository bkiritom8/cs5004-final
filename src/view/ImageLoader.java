package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Utility class for loading and caching images from resources.
 */
public class ImageLoader {
  private static final Map<String, Image> cache = new HashMap<>();
  private static final String DEFAULT_PATH = "/resources/images/default/";
  private static final String[] TYPES = {"room", "item", "monster", "fixture"};

  /**
   * Loads and scales an image from resources, with fallback and caching.
   */
  public static Image loadImage(String type, String name, int width, int height) {
    String key = type + "/" + name + "_" + width + "x" + height;
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    String imagePath = "/resources/images/" + type + "/" + name;
    Image image = tryLoadImage(imagePath, width, height);

    if (image == null) {
      // Fallback to default
      image = tryLoadImage(DEFAULT_PATH + "placeholder.png", width, height);
    }

    cache.put(key, image);
    return image;
  }

  /**
   * Attempts to load and scale an image from a given path.
   */
  private static Image tryLoadImage(String path, int width, int height) {
    try (InputStream is = ImageLoader.class.getResourceAsStream(path)) {
      if (is != null) {
        BufferedImage img = ImageIO.read(is);
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      }
    } catch (IOException e) {
      System.err.println("Error loading image: " + path);
    }
    return null;
  }
}
