package model;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

  /**
   * @param gameFileName
   * @throws IOException
   */
  public GameWorld(String gameFileName) throws IOException {
    this.rooms = new HashMap<>();
    this.items = new HashMap<>();
    this.fixtures = new HashMap<>();
    this.puzzles = new HashMap<>();
    this.monsters = new HashMap<>();

    try {
      loadGameData(gameFileName);
    } catch (Exception e) {
      throw new IOException("Error loading game data" + e.getMessage(), e);
    }

    // Initialize player in the first room
    if (!rooms.isEmpty()) {
      Room startRoom = rooms.values().iterator().next();
      this.player = new Player(startRoom);
    } else {
      throw new IOException("No rooms defined in the game file");
    }
  }

  /**
   *
   * @param gameFileName
   * @throws IOException
   * @throws ParseException
   */
  private void loadGameData(String gameFileName) throws IOException, ParseException {
    JSONParser parser = new JSONParser();

    try (FileReader reader = new FileReader(gameFileName)) {
      JSONObject gameData = (JSONObject) parser.parse(reader);

      // Load game metadata
      this.gameName = (String) gameData.get("name");
      this.version = (String) gameData.get("version");

      // Load items first so they can be referenced by rooms
      if (gameData.containsKey("rooms")) {
        loadItems((JSONArray) gameData.get("items"));
      }

      // Load fixtures
      if (gameData.containsKey("fixtures")) {
        loadFixtures((JSONArray) gameData.get("fixtures"));
      }

      // Load puzzles
      if (gameData.containsKey("puzzles")) {
        loadPuzzles((JSONArray) gameData.get("puzzles"));
      }

      // Load monsters
      if (gameData.containsKey("monsters")) {
        loadMonsters((JSONArray) gameData.get("monsters"));
      }

      // Load rooms (must be loaded last)
      if (gameData.containsKey("rooms")) {
        loadRooms((JSONArray) gameData.get("rooms"));
      } else {
        throw new IOException("No rooms defined in the game file");
      }

    } catch (IOException | ParseException e) {
      throw e;
    }
  }

  /**
   *
   * @param roomsArray
   */
  private void loadRooms(JSONArray roomsArray) {
    for (Object obj : roomsArray) {
      JSONObject roomData = (JSONObject) obj;

      String roomName = (String) roomData.get("room_name");
      String roomNumber = (String) roomData.get("room_number");
      String description = (String) roomData.get("description");

      // Parse exits
      Map<Direction, String> exits = new HashMap<>();
      exits.put(Direction.NORTH, (String) roomData.get("N"));
      exits.put(Direction.SOUTH, (String) roomData.get("S"));
      exits.put(Direction.EAST, (String) roomData.get("E"));
      exits.put(Direction.WEST, (String) roomData.get("W"));

      // Create room
      Room room = new Room(roomName, roomNumber, description, exits);

      // Add items to room if present
      String itemsList = (String) roomData.get("items");
      if (itemsList != null && !itemsList.isEmpty()) {
        for (String itemName : itemsList.split(",")) {
          Item item = items.get(itemName.trim().toUpperCase());
          if (item != null) {
            room.addItem(item);
          }
        }
      }

      // Add Fixtures to room if present
      String fixturesList = (String) roomData.get("fixtures");
      if (fixturesList != null && !fixturesList.isEmpty()) {
        for (String fixtureName : fixturesList.split(",")) {
          Fixture fixture = fixtures.get(fixtureName.trim().toUpperCase());
          if (fixture != null) {
            room.addFixture(fixture);
          }
        }
      }

      // Add Puzzle to room if present
      String puzzlesList = (String) roomData.get("puzzles");
      if (puzzlesList != null && !puzzlesList.isEmpty()) {
        for (String puzzleName : puzzlesList.split(",")) {
          Puzzle puzzle = puzzles.get(puzzleName.trim().toLowerCase());
          if (puzzle != null) {
            room.addPuzzle(puzzle);
          }
        }
      }

      // Add Monster to room if present
      String monstersList = (String) roomData.get("monsters");
      if (monstersList != null && !monstersList.isEmpty()) {
        for (String monsterName : monstersList.split(",")) {
          Monster monster = monsters.get(monsterName.trim().toUpperCase());
          if (monster != null) {
            room.addMonster(monster);
          }
        }

        // Add room to map
        rooms.put(roomName, room);
      }

      // Connect rooms after all rooms are loaded
      connectRooms();
    }

    /**
     *
     */
    private void loadItems(JSONArray itemsArray) {
      if (itemsArray == null) {
        return;
      }

      for (Object obj : itemsArray) {
        JSONObject itemData = (JSONObject) obj;

        String name = (String) itemData.get("item_name");
        int weight = parseIntOrDefault(itemData.get("weight"), 1);
        int maxUses = parseIntOrDefault(itemData.get("max_uses"), 1);
        int usesRemaining = parseIntOrDefault(itemData.get("uses_remaining"), 1);
        int value = parseIntOrDefault(itemData.get("value"), 0);
        String whenUsed = (String) itemData.get("when_used");
        String description = (String) itemData.get("description");

        Item item = new Item(name, weight, maxUses,usesRemaining, value, whenUsed, description);
        items.put(name.toUpperCase(), item);
      }
    }

    /**
     *
     */
    private void loadFixtures(JSONArray fixturesArray) {
      if (fixtures == null) {
        return;
      }

      for (Object obj : fixturesArray) {
        JSONObject fixtureData = (JSONObject) obj;

        String name = (String) fixtureData.get("fixture_name");
        int weight = parseIntOrDefault(fixtureData.get("weight"), 1000);
        String description = (String) fixtureData.get("description");

        Fixture fixture = new Fixture(name, weight, description);
        fixtures.put(name.toUpperCase(), fixture);
      }
    }

    /**
     *
     */
    private void loadPuzzles(JSONArray puzzleArray) {
      if (puzzles == null) {
        return;
      }

      for (Object obj : puzzlesArray) {
        JSONObject puzzleData = (JSONObject) obj;

        String name = (String) puzzleData.get("puzzle_name");
        boolean active = Boolean.parseBoolean((String) puzzleData.get("active"));
        boolean affectsTarget = Boolean.parseBoolean((String) puzzleData.get("affect_target"));
        boolean affectsPlayer = Boolean.parseBoolean((String) puzzleData.get("affect_player"));
        String solution = (String) puzzleData.get("solution");
        int value = parseIntOrDefualt(puzzleData.get("value"), 0);
        String description = (String) puzzleData.get("description");
        String effects = (String) puzzleData.get("effects");
        String target = (String) puzzleData.get("target");

        Puzzle puzzle = new Puzzle(name, active, affectsTarget,affectsPlayer,solution,value,
                description,effects,target);
        puzzles.put(name.toUpperCase(), puzzle);
      }
    }

  }
}
