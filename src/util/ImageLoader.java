/* ImageLoader.java */
package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageLoader {
  // Cache to store loaded images
  private Map<String, BufferedImage> cache = new HashMap<>();

  public static BufferedImage loadImage(String image) {
 return null;
  }

  // Enum to define image categories
  public enum ImageCategory {
    ROOMS("rooms"),
    ITEMS("items"),
    MONSTERS("monsters"),
    FIXTURES("fixtures");

    private final String folderName;

    ImageCategory(String folderName) {
      this.folderName = folderName;
    }

    public String getFolderName() {
      return folderName;
    }
  }

  // Base path for image resources
  private static final String BASE_PATH = "resources/images/";

  /**
   * Loads an image for a given category and image name.
   * If the image is not found, falls back to the default image.
   * Optionally scales the image to the specified width and height.
   *
   * @param category The image category.
   * @param imageName The name of the image file (with extension).
   * @param width The desired width (pass <=0 to skip scaling).
   * @param height The desired height (pass <=0 to skip scaling).
   * @return The loaded BufferedImage, or null if not found.
   */
  public BufferedImage loadImage(ImageCategory category, String imageName, int width, int height) {
    // Create a unique key for caching
    String key = category.getFolderName() + "/" + imageName + "_" + width + "x" + height;
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    // Build the file path for the requested image
    String filePath = BASE_PATH + category.getFolderName() + "/" + imageName;
    BufferedImage image = null;

    try {
      image = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      System.err.println("Could not load image: " + filePath + ". Falling back to default image.");
    }

    // Fallback to default image if not loaded
    if (image == null) {
      filePath = BASE_PATH + category.getFolderName() + "/default.png";
      try {
        image = ImageIO.read(new File(filePath));
      } catch (IOException e) {
        System.err.println("Could not load default image for category: " + category.getFolderName());
        return null;
      }
    }

    // Scale the image if valid dimensions are provided
    if (width > 0 && height > 0) {
      Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      scaledImage.getGraphics().drawImage(scaled, 0, 0, null);
      image = scaledImage;
    }

    // Store the image in cache and return it
    cache.put(key, image);
    return image;
  }
}
