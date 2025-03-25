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

import javax.swing.*;

/**
 *
 */
public class GameWorld {
  // Game metadata
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
      throw new IOException("Error loading game data: " + e.getMessage(), e);
    }

    // Initialize player in the first room
    if (!rooms.isEmpty()) {
      Room startRoom = rooms.values().iterator().next(); // Get the first room
      this.player = new Player(startRoom);
    } else {
      throw new IOException("No rooms defined in the game file.");
    }
  }

  /**
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
      if (gameData.containsKey("items")) {
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
        throw new IOException("No rooms defined in the game file.");
      }

    } catch (IOException | ParseException e) {
      throw e;
    }
  }

  /**
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

      // Add fixtures to room if present
      String fixturesList = (String) roomData.get("fixtures");
      if (fixturesList != null && !fixturesList.isEmpty()) {
        for (String fixtureName : fixturesList.split(",")) {
          Fixture fixture = fixtures.get(fixtureName.trim().toUpperCase());
          if (fixture != null) {
            room.addFixture(fixture);
          }
        }
      }

      // Add puzzle to room if present
      String puzzleName = (String) roomData.get("puzzle");
      if (puzzleName != null && !puzzleName.isEmpty()) {
        Puzzle puzzle = puzzles.get(puzzleName.trim().toUpperCase());
        if (puzzle != null) {
          room.setPuzzle(puzzle);
        }
      }

      // Add monster to room if present
      String monsterName = (String) roomData.get("monster");
      if (monsterName != null && !monsterName.isEmpty()) {
        Monster monster = monsters.get(monsterName.trim().toUpperCase());
        if (monster != null) {
          room.setMonster(monster);
        }
      }

      // Add room to map
      rooms.put(roomNumber, room);
    }

    // Connect rooms after all rooms are loaded
    connectRooms();
  }

  /**
   * @param itemsArray
   */
  private void loadItems(JSONArray itemsArray) {
    if (itemsArray == null) {
      return;
    }

    for (Object obj : itemsArray) {
      JSONObject itemData = (JSONObject) obj;

      String name = (String) itemData.get("name");
      int weight = parseIntOrDefault(itemData.get("weight"), 1);
      int maxUses = parseIntOrDefault(itemData.get("max_uses"), 1);
      int usesRemaining = parseIntOrDefault(itemData.get("uses_remaining"), 1);
      int value = parseIntOrDefault(itemData.get("value"), 0);
      String whenUsed = (String) itemData.get("when_used");
      String description = (String) itemData.get("description");

      Item item = new Item(name, weight, maxUses, usesRemaining, value, whenUsed, description);
      items.put(name.toUpperCase(), item);
    }
  }

  /**
   * @param fixturesArray
   */
  private void loadFixtures(JSONArray fixturesArray) {
    if (fixturesArray == null) {
      return;
    }

    for (Object obj : fixturesArray) {
      JSONObject fixtureData = (JSONObject) obj;

      String name = (String) fixtureData.get("name");
      int weight = parseIntOrDefault(fixtureData.get("weight"), 1000);
      String description = (String) fixtureData.get("description");

      Fixture fixture = new Fixture(name, weight, description);
      fixtures.put(name.toUpperCase(), fixture);
    }
  }

  /**
   * @param puzzlesArray
   */
  private void loadPuzzles(JSONArray puzzlesArray) {
    if (puzzlesArray == null) {
      return;
    }

    for (Object obj : puzzlesArray) {
      JSONObject puzzleData = (JSONObject) obj;

      String name = (String) puzzleData.get("name");
      boolean active = Boolean.parseBoolean((String) puzzleData.get("active"));
      boolean affectsTarget = Boolean.parseBoolean((String) puzzleData.get("affects_target"));
      boolean affectsPlayer = Boolean.parseBoolean((String) puzzleData.get("affects_player"));
      String solution = (String) puzzleData.get("solution");
      int value = parseIntOrDefault(puzzleData.get("value"), 0);
      String description = (String) puzzleData.get("description");
      String effects = (String) puzzleData.get("effects");
      String target = (String) puzzleData.get("target");

      Puzzle puzzle = new Puzzle(name, active, affectsTarget, affectsPlayer, solution, value, description, effects, target);
      puzzles.put(name.toUpperCase(), puzzle);
    }
  }

  /**
   * @param monstersArray
   */
  private void loadMonsters(JSONArray monstersArray) {
    if (monstersArray == null) {
      return;
    }

    for (Object obj : monstersArray) {
      JSONObject monsterData = (JSONObject) obj;

      String name = (String) monsterData.get("name");
      boolean active = Boolean.parseBoolean((String) monsterData.get("active"));
      int damage = parseIntOrDefault(monsterData.get("damage"), 5);
      boolean canAttack = Boolean.parseBoolean((String) monsterData.get("can_attack"));
      String attackDescription = (String) monsterData.get("attack");
      String description = (String) monsterData.get("description");
      String effects = (String) monsterData.get("effects");
      int value = parseIntOrDefault(monsterData.get("value"), 0);
      String solution = (String) monsterData.get("solution");
      String target = (String) monsterData.get("target");

      Monster monster = new Monster(name, active, damage, canAttack, attackDescription, description, effects, value, solution, target);
      monsters.put(name.toUpperCase(), monster);
    }
  }

  /**
   *
   */
  private void connectRooms() {
    for (Room room : rooms.values()) {
      for (Direction dir : Direction.values()) {
        String targetRoomNumber = room.getExitRoomNumber(dir);

        // Skip if no exit in this direction (0) or if there's a puzzle/monster blocking (-n)
        if (targetRoomNumber.equals("0")) {
          continue;
        }

        // If positive, direct connection to another room
        if (Integer.parseInt(targetRoomNumber) > 0) {
          Room targetRoom = rooms.get(targetRoomNumber);
          if (targetRoom != null) {
            room.setExit(dir, targetRoom);
          }
        }
        // If negative, there's a puzzle or monster blocking; will handle this during gameplay
      }
    }
  }

  /**
   * @param value
   * @param defaultValue
   * @return
   */
  private int parseIntOrDefault(Object value, int defaultValue) {
    if (value == null) {
      return defaultValue;
    }

    try {
      if (value instanceof String) {
        return Integer.parseInt((String) value);
      } else if (value instanceof Number) {
        return ((Number) value).intValue();
      }
    } catch (NumberFormatException e) {
      // Ignore and return default
    }

    return defaultValue;
  }

  /**
   *
   * @return
   */
  public Player getPlayer() {
    return player;
  }

  /**
   *
   * @param name
   */
  public void setPlayerName(String name) {
    player.setName(name);
  }

  /**
   *
   * @param roomNumber
   * @return
   */
  public Room getRoom(String roomNumber) {
    return rooms.get(roomNumber);
  }

  /**
   *
   * @param name
   * @return
   */
  public Puzzle getPuzzleByName(String name) {
    return puzzles.get(name.toUpperCase());
  }

  /**
   *
   * @return
   */
  public String getGameName() {
    return gameName;
  }

  /**
   *
   * @param solution
   * @return
   */
  public boolean applySolution(String solution) {
    Room currentRoom = player.getCurrentRoom();

    // Check if there's a puzzle in the room
    if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
      Puzzle puzzle = currentRoom.getPuzzle();
      if (puzzle.solve(solution)) {
        // Update player score
        player.addScore(puzzle.getValue());

        // Unblock paths
        for (Direction dir : Direction.values()) {
          String exitNumber = currentRoom.getExitRoomNumber(dir);
          if (Integer.parseInt(exitNumber) < 0) {
            // Convert negative to positive to unblock
            currentRoom.setExitRoomNumber(dir, String.valueOf(Math.abs
                    (Integer.parseInt(exitNumber))));
            // Set the actual exit
            Room targetRoom = rooms.get(String.valueOf(Math.abs(Integer.parseInt(exitNumber))));
            if (targetRoom != null) {
              currentRoom.setExit(dir, targetRoom);
            }
          }
        }
        return true;
      }
    }

    // Check if there's a monster in the room
    if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
      Monster monster = currentRoom.getMonster();
      if (monster.getSolution().equalsIgnoreCase(solution)) {
        monster.defeat();
        // Update Players score
        player.addScore(monster.getValue());

        // Unblock paths
        for (Direction dir : Direction.values()) {
          String exitNumber = currentRoom.getExitRoomNumber(dir);
          if (Integer.parseInt(exitNumber) < 0) {
            // Convert negative to positive to unblock
            currentRoom.setExitRoomNumber(dir, String.valueOf(Math.abs
                    (Integer.parseInt(exitNumber))));
            // Set the actual exit
            Room targetRoom = rooms.get(String.valueOf(Math.abs(Integer.parseInt(exitNumber))));
            if (targetRoom != null) {
              currentRoom.setExit(dir, targetRoom);
            }
          }
        }
        return true;
      }
    }

    return false;
  }

  /**
   *
   * @param filename
   * @throws IOException
   */
  public void saveGame(String filename) throws IOException {
    JSONObject saveData = new JSONObject();

    // Save player data
    JSONObject playerData = new JSONobject();
    playerData.put("name", player.getName());
    playerData.put("health", player.getHealth());
    playerData.put("score", player.getScore());
    playerData.put("current_room", player.getCurrentRoom().getRoomNumber());

    // Save inventory
    JSONArray inventoryData = new JSONArray();
    for (Item item : player.getInventory()) {
      JSONObject itemData = new JSONObject();
      itemData.put("name", item.getName());
      itemData.put("uses_remaining", item.getUsesRemaining());
      inventoryData.add(itemData);
    }
    playerData.put("inventory", inventoryData);

    saveData.put("player", playerData);

    // Save room states
    JSONArray roomsData = new JSONArray();
    for (Room room : rooms.values()) {
      JSONObject roomData = new JSONObject;
      roomData.put("room_number", room.getRoomNumber());

      // Save puzzle state
      if (room.getPuzzle() != null) {
        roomData.put("puzzle_active", room.getPuzzle().isActive());
      }

      // Save monster state
      if (room.getMonster() != null) {
        roomData.put("monster_active", room.getMonster().isActive());
      }

      // Save room exits
      JSONObject exitsData = new JSONobject();
      for (Direction dir : Direction.value()) {
        exitsData.put(dir.toString(), room.getExitRoomNumber(dir));
      }
      roomData.put("exits", exitsData);

      // Save items in room
      JSONArray roomItemsData = new JSONArray();
      for (Item item : room.getItems()) {
        roomItemsData.add(item.getName());
      }
      roomData.put("items", roomItemsData);

      roomsData.add(roomData);
    }

    saveData.put("rooms", roomsData);
    saveData.put("game_name", gameName);
    saveData.put("version", version);

    try (FileWriter file = new FileWriter(filename)) {
      file.write(saveData.toJSONString());
    }
  }


}

