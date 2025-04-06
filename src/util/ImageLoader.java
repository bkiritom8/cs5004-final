package util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Utility class for loading images with caching and fallback support.
 * Provides methods for loading and scaling images.
 */
public class ImageLoader {
  private static final Map<String, Image> cache = new HashMap<>();
  private static final String DEFAULT_IMAGE_PATH = "resources/images/default.png";

  /**
   * Loads an image from the given path with caching.
   * If the image is not found, it falls back to the default image.
   *
   * @param path the path to the image file
   * @return the loaded Image, or null if neither the specified image nor the default image can be loaded
   */
  public static Image load(String path) {
    if (cache.containsKey(path)) {
      return cache.get(path);
    }
    Image img = null;
    try {
      img = ImageIO.read(new File(path));
    } catch (IOException e) {
      System.err.println("Failed to load image from: " + path + ". Using default image.");
      try {
        img = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
      } catch (IOException ex) {
        System.err.println("Failed to load default image from: " + DEFAULT_IMAGE_PATH);
      }
    }
    if (img != null) {
      cache.put(path, img);
    }
    return img;
  }

  /**
   * Returns a scaled version of the given image.
   *
   * @param srcImg the source image to be scaled
   * @param width  the target width
   * @param height the target height
   * @return the scaled image
   */
  public static Image getScaledImage(Image srcImg, int width, int height) {
    return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
  }
}
