package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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

  public static BufferedImage loadImage(String category, String name) {
    String primaryPath = "/images/" + category + "/" + name;

    try (InputStream in = ImageLoader.class.getResourceAsStream(primaryPath)) {
      if (in != null) {
        return ImageIO.read(in);
      } else {
        LOGGER.warning("Failed to load: " + primaryPath + ", falling back to category default.");
      }
    } catch (IOException e) {
      LOGGER.warning("IOException reading image: " + e.getMessage());
    }

    // Try category fallback
    String fallbackPath = "/images/" + category + "/default " + category.substring(0, category.length() - 1) + ".png";
    try (InputStream fallbackIn = ImageLoader.class.getResourceAsStream(fallbackPath)) {
      if (fallbackIn != null) {
        return ImageIO.read(fallbackIn);
      } else {
        LOGGER.warning("Failed to load category fallback. Trying global fallback.");
      }
    } catch (IOException e) {
      LOGGER.warning("IOException reading category fallback: " + e.getMessage());
    }

    // Try global fallback
    try (InputStream globalIn = ImageLoader.class.getResourceAsStream("/images/blank.png")) {
      if (globalIn != null) {
        return ImageIO.read(globalIn);
      } else {
        LOGGER.severe("Failed to load global fallback. Returning blank image.");
      }
    } catch (IOException e) {
      LOGGER.severe("IOException reading global fallback: " + e.getMessage());
    }

    return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
  }

  public static void initialize() {
    // No setup needed currently.
  }

  public static Icon getRoomImage(Room room) {
    String roomName = room.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-");
    String fileName = roomName + ".png";
    BufferedImage image = loadImage("rooms", fileName);
    return new ImageIcon(image);
  }

  /**
   * Returns an icon for a given item name.
   *
   * @param itemName the name of the item (e.g., "Diamond Sword")
   * @return an Icon of the item
   */
  public static Icon getItemIcon(String itemName) {
    String filename = itemName.toLowerCase().replaceAll("[^a-z0-9]+", "-") + ".png";
    BufferedImage image = loadImage("items", filename);
    return new ImageIcon(image);
  }

  /**
   * Returns an icon for a given monster name.
   *
   * @param monsterName the name of the monster (e.g., "Troll")
   * @return an Icon of the monster
   */
  public static Icon getMonsterIcon(String monsterName) {
    String filename = monsterName.toLowerCase().replaceAll("[^a-z0-9]+", "-") + ".png";
    BufferedImage image = loadImage("monsters", filename);
    return new ImageIcon(image);
  }
}
