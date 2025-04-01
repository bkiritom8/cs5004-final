package util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for loading and caching images from resources.
 */
public class ImageLoader {

  private static final Map<String, Image> cache = new HashMap<>();
  private static final String DEFAULT_PATH = "/images/default/placeholder.png";

  /**
   * Loads and scales an image from the resources folder.
   *
   * @param type   the image category (room, item, etc.)
   * @param name   the image file name (e.g., sword.png)
   * @param width  desired width
   * @param height desired height
   * @return scaled image or fallback image if not found
   */
  public static Image loadImage(String type, String name, int width, int height) {
    String key = type + "/" + name + "_" + width + "x" + height;
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    String imagePath = "/images/" + type + "/" + name;
    Image image = tryLoadImage(imagePath, width, height);

    if (image == null) {
      image = tryLoadImage(DEFAULT_PATH, width, height);
    }

    cache.put(key, image);
    return image;
  }

  /**
   * Attempts to load and scale an image from a given resource path.
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

