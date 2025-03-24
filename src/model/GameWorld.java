package model;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The main model class representing the game world.
 * This class is responsible for managing the game state, including rooms,
 * items, puzzles, monsters, and the player.
 */
public class GameWorld {
  // Game Metadata
  private String gameName;
  private String version;

  // Game elements
  private Map<String, Room> rooms;
  private Map<String, Item> items;
  private Map<String, Fixture> fixtures;
  private Map<String, Puzzle> puzzles;
  private Map<String, Monster> monsters;

  // Player
  private Player player;
}
