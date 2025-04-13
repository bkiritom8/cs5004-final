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
    String[] basePaths = {"/images/", "/resources/images/"};
    String fallbackName = getCategoryFallbackName(category);

    for (String base : basePaths) {
      String fullPath = base + category + "/" + name;
      try (InputStream in = ImageLoader.class.getResourceAsStream(fullPath)) {
        if (in != null) {
          return ImageIO.read(in);
        } else if (!isExpectedMissing(category) && !isExpectedMissing(name)) {
          LOGGER.warning("Failed to load: " + fullPath);
        }
      } catch (IOException e) {
        LOGGER.warning("IOException reading image: " + fullPath + " -> " + e.getMessage());
      }
    }

    // Try category fallback (e.g., default item.png)
    if (fallbackName != null) {
      for (String base : basePaths) {
        String fallbackPath = base + category + "/" + fallbackName;
        try (InputStream fallbackIn = ImageLoader.class.getResourceAsStream(fallbackPath)) {
          if (fallbackIn != null) {
            LOGGER.fine("Using fallback: " + fallbackPath);
            return ImageIO.read(fallbackIn);
          } else if (!isExpectedMissing(fallbackName)) {
            LOGGER.warning("Failed to load fallback: " + fallbackPath);
          }
        } catch (IOException e) {
          LOGGER.warning("IOException reading fallback: " + e.getMessage());
        }
      }
    }

    // Final fallback from known reliable default
    for (String base : basePaths) {
      String ultimatePath = base + "items/default item.png";
      try (InputStream in = ImageLoader.class.getResourceAsStream(ultimatePath)) {
        if (in != null) {
          LOGGER.fine("Using ultimate fallback image: " + ultimatePath);
          return ImageIO.read(in);
        }
      } catch (IOException e) {
        LOGGER.warning("IOException reading ultimate fallback: " + e.getMessage());
      }
    }

    // Return a blank placeholder image if everything failed
    LOGGER.severe("Returning blank image (no image found anywhere).");
    return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
  }

  private static String getCategoryFallbackName(String category) {
    return switch (category) {
      case "items" -> "default item.png";
      case "monsters" -> "default monster.png";
      case "fixtures" -> "default fixture.png";
      case "rooms" -> "default.png";
      default -> "default item.png"; // fallback for unknown categories like "weird"
    };
  }

  private static boolean isExpectedMissing(String input) {
    String lower = input.toLowerCase();
    return lower.startsWith("default")
            || lower.startsWith("nothing")
            || lower.startsWith("nonexistent")
            || lower.contains("fake")
            || lower.contains("test");
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
