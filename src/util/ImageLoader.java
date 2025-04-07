package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.Room;


/**
 * Utility class for loading images with caching and fallback support.
 * Provides methods for loading and managing game images.
 */
public class ImageLoader {
  private static final Map<String, Image> cache = new HashMap<>();
  private static final String BASE_IMAGE_PATH = "resources/images/";
  private static final String DEFAULT_IMAGE_PATH = BASE_IMAGE_PATH + "default.png";
  private static final int DEFAULT_WIDTH = 100;
  private static final int DEFAULT_HEIGHT = 100;

  /**
   * Initializes the ImageLoader by preloading the default image.
   * This ensures the fallback image is ready when needed.
   */
  public static void initialize() {
    if (!cache.containsKey(DEFAULT_IMAGE_PATH)) {
      try {
        Image defaultImage = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
        if (defaultImage != null) {
          cache.put(DEFAULT_IMAGE_PATH, defaultImage);
        }
      } catch (IOException e) {
        System.err.println("Failed to preload default image from: " + DEFAULT_IMAGE_PATH);
      }
    }
  }

  /**
   * Loads an image from the given full path with caching.
   * Falls back to the default image if loading fails.
   *
   * @param path the full path to the image file
   * @return the loaded Image, or null if all loading fails
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
   * Loads an image by category and filename. Falls back to default or blank image.
   *
   * @param category The subdirectory under /resources/images/
   * @param filename The image file name (e.g., "dragon.png")
   * @return A BufferedImage if successful, fallback image otherwise
   */
  public static BufferedImage loadImage(String category, String filename) {
    String path = BASE_IMAGE_PATH + category + "/" + filename;
    try {
      return ImageIO.read(new File(path));
    } catch (IOException e) {
      System.err.println("Failed to load: " + path + ", falling back to default.");
      try {
        return ImageIO.read(new File(DEFAULT_IMAGE_PATH));
      } catch (IOException ex) {
        System.err.println("Failed to load fallback image. Returning blank image.");
        return createBlankImage();
      }
    }
  }

  /**
   * Returns an ImageIcon for the specified Room.
   * Uses the room name as the image filename (e.g., "kitchen.png").
   *
   * @param room the Room whose image should be loaded
   * @return an ImageIcon representing the room
   */
  public static Icon getRoomImage(Room room) {
    String filename = room.getName() + ".png"; // Assumes getName() is available
    String path = BASE_IMAGE_PATH + "rooms/" + filename;
    Image img = load(path);
    return new ImageIcon(Objects.requireNonNullElseGet(img, ImageLoader::createBlankImage));
  }

  /**
   * Creates a blank transparent BufferedImage using default size.
   *
   * @return a blank BufferedImage
   */
  private static BufferedImage createBlankImage() {
    return new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
  }
}
