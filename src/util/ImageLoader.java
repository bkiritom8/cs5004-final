package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Room;

/**
 * Utility class for loading images with fallback support.
 */
public class ImageLoader {
  private static final Logger LOGGER = Logger.getLogger(ImageLoader.class.getName());

  /**
   * Loads an image from a given category and filename.
   * Applies fallback to category default, then a global blank image if needed.
   *
   * @param category the folder name (e.g., "items", "monsters")
   * @param name     the image file name (e.g., "sword.png")
   * @return a BufferedImage or a blank fallback if loading fails
   */
  public static BufferedImage loadImage(String category, String name) {
    Path primaryPath = Paths.get("resources/images", category, name);

    try {
      return ImageIO.read(primaryPath.toFile());
    } catch (IOException e) {
      LOGGER.warning("Failed to load: " + primaryPath + ", falling back to category default.");
      Path categoryFallback = Paths.get(
              "resources/images",
              category,
              "default " + category.substring(0, category.length() - 1) + ".png"
      );
      try {
        return ImageIO.read(categoryFallback.toFile());
      } catch (IOException e1) {
        LOGGER.warning("Failed to load category fallback. Trying global fallback.");
        Path globalFallback = Paths.get("resources/images", "blank.png");
        try {
          return ImageIO.read(globalFallback.toFile());
        } catch (IOException e2) {
          LOGGER.severe("Failed to load global fallback. Returning blank image.");
          return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
      }
    }
  }

  /**
   * Initialization placeholder, currently does nothing.
   */
  public static void initialize() {
    // No setup needed currently.
  }

  /**
   * Returns a room image icon. Always returns a default room image for now.
   *
   * @param room the room to get the image for (currently unused)
   * @return an Icon containing the room image
   */
  public static Icon getRoomImage(Room room) {
    String roomName = room.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-");
    String fileName = roomName + ".png";
    BufferedImage image = loadImage("rooms", fileName);
    return new ImageIcon(image);
  }
}
