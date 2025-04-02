package enginedriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.SwingUtilities;

import controller.BatchController;
import controller.GameController;
import controller.SwingController;
import controller.TextController;
import model.GameWorld;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.FileIOManager;
import util.ImageLoader;

/**
 * Main entry point for the Adventure Game application.
 * Handles command-line arguments and launches the appropriate game mode.
 */
public class Main {

  /**
   * The main method that processes command-line arguments and starts the game.
   *
   * @param args Command line arguments
   *             Format: <game_file> [-text|-graphics|-batch <input_file> [output_file]]
   * @throws IOException If there is an error reading input or writing output
   */
  public static void main(String[] args) throws IOException {
    // If no arguments provided, use default behavior
    if (args.length == 0) {
      runDefaultGame();
      return;
    }

    // Process command-line arguments
    if (args.length < 2) {
      printUsage();
      return;
    }

    String gameFile = args[0];
    String mode = args[1];

    try {
      switch (mode) {
        case "-text":
          // Run in text mode
          GameEngineApp app = new GameEngineApp(gameFile, "text", null, null);
          app.start();
          break;

        case "-graphics":
          // Initialize image resources
          ImageLoader.initialize();

          // Run in graphics mode
          GameEngineApp graphicsApp = new GameEngineApp(gameFile, "graphics", null, null);
          SwingUtilities.invokeLater(() -> {
            try {
              graphicsApp.start();
            } catch (Exception e) {
              System.err.println("Error starting graphics mode: " + e.getMessage());
              e.printStackTrace();
            }
          });
          break;

        case "-batch":
          if (args.length < 3) {
            System.out.println("Batch mode requires an input file");
            printUsage();
            return;
          }
          String inputFile = args[2];
          String outputFile = (args.length > 3) ? args[3] : null;

          // Run in batch mode
          GameEngineApp batchApp = new GameEngineApp(gameFile, "batch", inputFile, outputFile);
          batchApp.start();
          break;

        default:
          System.out.println("Invalid mode: " + mode);
          printUsage();
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Runs the default "Hero's Journey" game with the original flow
   * This preserves the original functionality from the previous version
   *
   * @throws IOException If there is an error reading/writing files
   */
  private static void runDefaultGame() throws IOException {
    // Create the game data in-memory
    String gameData = createGameData();

    // Write game data to a temporary file
    Path tempFile = Files.createTempFile("heroes_journey", ".json");
    Files.writeString(tempFile, gameData);

    System.out.println("=== The Hero's Journey ===");
    System.out.println("A text adventure based on classic challenges and scenarios");
    System.out.println("Commands: (N)orth, (S)outh, (E)ast, (W)est, (T)ake, (D)rop, e(X)amine, attac(K),");
    System.out.println("          (L)ook, (U)se, (I)nventory, (A)nswer, sa(V)e, (R)estore, (Q)uit");
    System.out.println("\nYour adventure begins now...");

    // Create game engine with interactive input/output
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    GameEngineApp gameEngineApp = new GameEngineApp(tempFile.toString(), "text", null, null);

    // Start the game
    try {
      gameEngineApp.start();
    } finally {
      // Clean up temporary file
      Files.deleteIfExists(tempFile);
    }
  }

  /**
   * Prints usage information for the command-line interface.
   */
  private static void printUsage() {
    System.out.println("Usage: java -jar game_engine.jar <game_file> [-text|-graphics|-batch <input_file> [output_file]]");
    System.out.println("  <game_file>      : Path to the JSON game data file");
    System.out.println("  -text            : Run in interactive text mode");
    System.out.println("  -graphics        : Run in graphical mode using Swing UI");
    System.out.println("  -batch <in>      : Run in batch mode with commands from input file");
    System.out.println("  -batch <in> <out>: Run in batch mode with output to file");
    System.out.println("\nWith no arguments, runs the default Hero's Journey game in text mode.");
  }


  /**
   * Creates the game world data as a JSON string.
   *
   * @return A JSON string representing the game world
   */
  private static String createGameData() {
    JSONObject gameData = new JSONObject();

    // Game metadata
    gameData.put("name", "The Hero's Journey");
    gameData.put("version", "1.0.0");

    // Create rooms
    JSONArray rooms = new JSONArray();

    // Room 1: Home
    JSONObject home = new JSONObject();
    home.put("room_name", "Home");
    home.put("room_number", "1");
    home.put("description", "You wake up in your cozy cottage at the edge of town. Sunlight streams through your window as you contemplate your desire to become a warrior and protect your hometown. Looking around, you notice your simple furnishings - a bed, a small table, and a chest containing your belongings. A doorway leads outside to the town square, connected by a winding dirt path.");
    home.put("N", "0");
    home.put("S", "0");
    home.put("E", "0");
    home.put("W", "2");
    home.put("puzzle", null);
    home.put("monster", null);
    home.put("items", "Training Sword");
    home.put("fixtures", "Wooden Chest");
    home.put("picture", null);
    rooms.add(home);

    // Room 2: Town Square
    JSONObject townSquare = new JSONObject();
    townSquare.put("room_name", "Town Square");
    townSquare.put("room_number", "2");
    townSquare.put("description", "Following the dirt path from your cottage, you arrive at the bustling town square. Merchants sell their wares while townspeople chat by the central fountain. From here, several routes branch out: a cobblestone road heading north leads to the grand library, a well-worn path to the west winds toward a mysterious cave entrance, and the familiar dirt path east returns to your cottage.");
    townSquare.put("N", "7");
    townSquare.put("S", "0");
    townSquare.put("E", "1");
    townSquare.put("W", "3");
    townSquare.put("puzzle", null);
    townSquare.put("monster", null);
    townSquare.put("items", "Town Map");
    townSquare.put("fixtures", "Fountain");
    townSquare.put("picture", null);
    rooms.add(townSquare);

    // Room 3: Cave Entrance
    JSONObject caveEntrance = new JSONObject();
    caveEntrance.put("room_name", "Cave Entrance");
    caveEntrance.put("room_number", "3");
    caveEntrance.put("description", "Taking the western path, you follow its twists and turns until reaching a large cave opening carved into the hillside. Cool air drifts from within, and mineral deposits glitter in the dim light. You notice iron ore deposits near the entrance that could be useful for crafting. A narrow tunnel descends deeper into the cave system.");
    caveEntrance.put("N", "0");
    caveEntrance.put("S", "0");
    caveEntrance.put("E", "2");
    caveEntrance.put("W", "4");
    caveEntrance.put("puzzle", null);
    caveEntrance.put("monster", null);
    caveEntrance.put("items", "Iron Ore");
    caveEntrance.put("fixtures", "Glittering Deposits");
    caveEntrance.put("picture", null);
    rooms.add(caveEntrance);

    // Room 4: Crafting Chamber
    JSONObject craftingChamber = new JSONObject();
    craftingChamber.put("room_name", "Crafting Chamber");
    craftingChamber.put("room_number", "4");
    craftingChamber.put("description", "Descending through the narrow tunnel, the passage opens into a spacious underground chamber. In the center stands an arrangement of wood and stones forming what appears to be a crafting station. The ancient fixture seems dormant, but you sense it might activate with the right materials.");
    craftingChamber.put("N", "0");
    craftingChamber.put("S", "0");
    craftingChamber.put("E", "3");
    craftingChamber.put("W", "5");
    craftingChamber.put("puzzle", "Crafting Station");
    craftingChamber.put("monster", null);
    craftingChamber.put("items", null);
    craftingChamber.put("fixtures", "Crafting Station");
    craftingChamber.put("picture", null);
    rooms.add(craftingChamber);

    // Room 5: Path to Coral Cavern
    JSONObject pathToCoral = new JSONObject();
    pathToCoral.put("room_name", "Path to Coral Cavern");
    pathToCoral.put("room_number", "5");
    pathToCoral.put("description", "With your new diamond sword in hand, you discover another passage leading from the crafting chamber. Following this tunnel, you notice the air becoming more humid and hear the distant sound of dripping water. The stone walls gradually give way to glistening coral formations that emit a soft, blue glow.");
    pathToCoral.put("N", "0");
    pathToCoral.put("S", "0");
    pathToCoral.put("E", "4");
    pathToCoral.put("W", "6");
    pathToCoral.put("puzzle", null);
    pathToCoral.put("monster", null);
    pathToCoral.put("items", null);
    pathToCoral.put("fixtures", "Coral Formations");
    pathToCoral.put("picture", null);
    rooms.add(pathToCoral);

    // Room 6: Coral Cavern
    JSONObject coralCavern = new JSONObject();
    coralCavern.put("room_name", "Coral Cavern");
    coralCavern.put("room_number", "6");
    coralCavern.put("description", "The tunnel opens into a breathtaking underwater cavern filled with vibrant coral formations. Some corals form arrow-like patterns pointing deeper into the cavern. As you follow these patterns, you encounter a massive Guardian Octopus blocking your path. Its tentacles wave menacingly, but it seems to be waiting for an answer rather than attacking.");
    coralCavern.put("N", "-16");
    coralCavern.put("S", "0");
    coralCavern.put("E", "5");
    coralCavern.put("W", "0");
    coralCavern.put("puzzle", "Water Current Lock");
    coralCavern.put("monster", "Guardian Octopus");
    coralCavern.put("items", null);
    coralCavern.put("fixtures", "Ancient Coral Formation");
    coralCavern.put("picture", null);
    rooms.add(coralCavern);

    // Room 7: Library Entrance
    JSONObject libraryEntrance = new JSONObject();
    libraryEntrance.put("room_name", "Library Entrance");
    libraryEntrance.put("room_number", "7");
    libraryEntrance.put("description", "Following the cobblestone road north from the town square, you arrive at the town library. Its impressive façade features tall columns and heavy wooden doors. Inside, you find rows of bookshelves filled with knowledge. At the far end stands an ornate bookshelf that catches your attention.");
    libraryEntrance.put("N", "8");
    libraryEntrance.put("S", "2");
    libraryEntrance.put("E", "0");
    libraryEntrance.put("W", "0");
    libraryEntrance.put("puzzle", null);
    libraryEntrance.put("monster", null);
    libraryEntrance.put("items", null);
    libraryEntrance.put("fixtures", "Library Doors");
    libraryEntrance.put("picture", null);
    rooms.add(libraryEntrance);

    // Room 8: Library Main Hall
    JSONObject libraryMain = new JSONObject();
    libraryMain.put("room_name", "Library Main Hall");
    libraryMain.put("room_number", "8");
    libraryMain.put("description", "Inside the grand library, rows of bookshelves stretch into the distance. Sunlight streams through high windows, illuminating dancing dust particles. In the corner, a particularly ornate bookshelf seems to hold more than just books. You notice a riddle inscribed on one shelf: 'What has keys but can't open locks?'");
    libraryMain.put("N", "0");
    libraryMain.put("S", "7");
    libraryMain.put("E", "0");
    libraryMain.put("W", "-9");
    libraryMain.put("puzzle", "Bookshelf Riddle");
    libraryMain.put("monster", null);
    libraryMain.put("items", null);
    libraryMain.put("fixtures", "Ornate Bookshelf");
    libraryMain.put("picture", null);
    rooms.add(libraryMain);

    // Room 9: Secret Study
    JSONObject secretStudy = new JSONObject();
    secretStudy.put("room_name", "Secret Study");
    secretStudy.put("room_number", "9");
    secretStudy.put("description", "Stepping through the revealed doorway, you enter a hidden study filled with rare books and ancient scrolls. At the far end stands a heavy wooden door with an ornate lock. Approaching it, you realize it requires a special key - the Sealed Key - which you don't yet possess.");
    secretStudy.put("N", "0");
    secretStudy.put("S", "0");
    secretStudy.put("E", "8");
    secretStudy.put("W", "10");
    secretStudy.put("puzzle", "Sealed Door");
    secretStudy.put("monster", null);
    secretStudy.put("items", "Ancient Scroll");
    secretStudy.put("fixtures", "Sealed Door");
    secretStudy.put("picture", null);
    rooms.add(secretStudy);

    // Room 10: Library Basement Stairs
    JSONObject basementStairs = new JSONObject();
    basementStairs.put("room_name", "Library Basement Stairs");
    basementStairs.put("room_number", "10");
    basementStairs.put("description", "Near the hidden study, you discover a narrow staircase spiraling down into darkness. The worn stone steps suggest this passage has existed for centuries, though few seem to have used it recently. The air grows cooler and mustier as you descend.");
    basementStairs.put("N", "0");
    basementStairs.put("S", "11");
    basementStairs.put("E", "9");
    basementStairs.put("W", "0");
    basementStairs.put("puzzle", null);
    basementStairs.put("monster", null);
    basementStairs.put("items", null);
    basementStairs.put("fixtures", "Stone Staircase");
    basementStairs.put("picture", null);
    rooms.add(basementStairs);

    // Room 11: Library Basement
    JSONObject basement = new JSONObject();
    basement.put("room_name", "Library Basement");
    basement.put("room_number", "11");
    basement.put("description", "At the bottom of the stairs, you find yourself in a dark, dusty basement filled with forgotten tomes and cobwebs. Suddenly, a massive Dungeon Guardian blocks your path, protecting what appears to be an ancient doorway.");
    basement.put("N", "10");
    basement.put("S", "0");
    basement.put("E", "0");
    basement.put("W", "0");
    basement.put("puzzle", null);
    basement.put("monster", "Dungeon Guardian");
    basement.put("items", null);
    basement.put("fixtures", "Ancient Doorway");
    basement.put("picture", null);
    rooms.add(basement);

    // Room 12: Dungeon Entrance
    JSONObject dungeonEntrance = new JSONObject();
    dungeonEntrance.put("room_name", "Dungeon Entrance");
    dungeonEntrance.put("room_number", "12");
    dungeonEntrance.put("description", "Using the Sealed Key on the ornate lock, the heavy door swings open with a creak, revealing a dark passage beyond. The air is damp and carries the scent of ancient stone. Torches flicker along the walls as you step into the true dungeon entrance.");
    dungeonEntrance.put("N", "9");
    dungeonEntrance.put("S", "13");
    dungeonEntrance.put("E", "0");
    dungeonEntrance.put("W", "0");
    dungeonEntrance.put("puzzle", null);
    dungeonEntrance.put("monster", null);
    dungeonEntrance.put("items", null);
    dungeonEntrance.put("fixtures", "Wall Torches");
    dungeonEntrance.put("picture", null);
    rooms.add(dungeonEntrance);

    // Room 13: Dungeon Room
    JSONObject dungeonRoom = new JSONObject();
    dungeonRoom.put("room_name", "Dungeon Room");
    dungeonRoom.put("room_number", "13");
    dungeonRoom.put("description", "The passage opens into a large dungeon chamber with high ceilings and imposing stone architecture. A fierce Troll blocks the southern exit, which appears to lead to a treasure chamber.");
    dungeonRoom.put("N", "12");
    dungeonRoom.put("S", "-14");
    dungeonRoom.put("E", "0");
    dungeonRoom.put("W", "0");
    dungeonRoom.put("puzzle", null);
    dungeonRoom.put("monster", "Troll");
    dungeonRoom.put("items", null);
    dungeonRoom.put("fixtures", "Stone Pillars");
    dungeonRoom.put("picture", null);
    rooms.add(dungeonRoom);

    // Room 14: Treasure Chamber
    JSONObject treasureChamber = new JSONObject();
    treasureChamber.put("room_name", "Treasure Chamber");
    treasureChamber.put("room_number", "14");
    treasureChamber.put("description", "Through the southern doorway, you enter a magnificent chamber filled with gold coins, jewels, and ancient artifacts. As you explore the riches, a suspicious-looking treasure chest in the corner catches your eye.");
    treasureChamber.put("N", "13");
    treasureChamber.put("S", "0");
    treasureChamber.put("E", "15");
    treasureChamber.put("W", "0");
    treasureChamber.put("puzzle", null);
    treasureChamber.put("monster", "Treasure Chest");
    treasureChamber.put("items", "Gold Coins");
    treasureChamber.put("fixtures", null);
    treasureChamber.put("picture", null);
    rooms.add(treasureChamber);

    // Room 15: Chamber Annex
    JSONObject chamberAnnex = new JSONObject();
    chamberAnnex.put("room_name", "Chamber Annex");
    chamberAnnex.put("room_number", "15");
    chamberAnnex.put("description", "A smaller side room connected to the main treasure chamber contains additional valuable artifacts and ancient maps showing paths to other dungeons and treasure sites. You gather what you can carry before deciding to head home.");
    chamberAnnex.put("N", "0");
    chamberAnnex.put("S", "0");
    chamberAnnex.put("E", "0");
    chamberAnnex.put("W", "14");
    chamberAnnex.put("puzzle", null);
    chamberAnnex.put("monster", null);
    chamberAnnex.put("items", "Ancient Map, Jeweled Crown");
    chamberAnnex.put("fixtures", "Map Table");
    chamberAnnex.put("picture", null);
    rooms.add(chamberAnnex);

    // Room 16: Hidden Pearl Chamber
    JSONObject pearlChamber = new JSONObject();
    pearlChamber.put("room_name", "Hidden Pearl Chamber");
    pearlChamber.put("room_number", "16");
    pearlChamber.put("description", "Beyond the Water Current Lock, you discover a small chamber bathed in soft blue light. At its center rests the luminous Pearl on a stone pedestal, glowing with inner light.");
    pearlChamber.put("N", "0");
    pearlChamber.put("S", "6");
    pearlChamber.put("E", "0");
    pearlChamber.put("W", "0");
    pearlChamber.put("puzzle", null);
    pearlChamber.put("monster", null);
    pearlChamber.put("items", "Luminous Pearl");
    pearlChamber.put("fixtures", "Stone Pedestal");
    pearlChamber.put("picture", null);
    rooms.add(pearlChamber);

    // Create items
    JSONArray items = new JSONArray();

    // Training Sword
    JSONObject trainingSword = new JSONObject();
    trainingSword.put("name", "Training Sword");
    trainingSword.put("weight", "2");
    trainingSword.put("max_uses", "100");
    trainingSword.put("uses_remaining", "100");
    trainingSword.put("value", "5");
    trainingSword.put("when_used", "You swing the training sword, but it's not very effective.");
    trainingSword.put("description", "A wooden training sword. Good for practice, but not much else.");
    trainingSword.put("picture", null);
    items.add(trainingSword);

    // Town Map
    JSONObject townMap = new JSONObject();
    townMap.put("name", "Town Map");
    townMap.put("weight", "1");
    townMap.put("max_uses", "10");
    townMap.put("uses_remaining", "10");
    townMap.put("value", "5");
    townMap.put("when_used", "You study the map, noting the locations of the library and cave.");
    townMap.put("description", "A simple map showing the layout of the town and surrounding areas.");
    townMap.put("picture", null);
    items.add(townMap);

    // Iron Ore
    JSONObject ironOre = new JSONObject();
    ironOre.put("name", "Iron Ore");
    ironOre.put("weight", "3");
    ironOre.put("max_uses", "1");
    ironOre.put("uses_remaining", "1");
    ironOre.put("value", "10");
    ironOre.put("when_used", "You examine the iron ore. It could be useful for crafting.");
    ironOre.put("description", "A chunk of raw iron ore extracted from the cave wall.");
    ironOre.put("picture", null);
    items.add(ironOre);

    // Diamond
    JSONObject diamond = new JSONObject();
    diamond.put("name", "Diamond");
    diamond.put("weight", "1");
    diamond.put("max_uses", "1");
    diamond.put("uses_remaining", "1");
    diamond.put("value", "50");
    diamond.put("when_used", "You examine the sparkling diamond. It's extremely valuable.");
    diamond.put("description", "A brilliant diamond that gleams in the light. Rare and valuable.");
    diamond.put("picture", null);
    items.add(diamond);

    // Diamond Sword
    JSONObject diamondSword = new JSONObject();
    diamondSword.put("name", "Diamond Sword");
    diamondSword.put("weight", "3");
    diamondSword.put("max_uses", "100");
    diamondSword.put("uses_remaining", "100");
    diamondSword.put("value", "100");
    diamondSword.put("when_used", "You swing the diamond sword with deadly precision!");
    diamondSword.put("description", "A magnificent sword with a diamond-encrusted blade. Extremely sharp and durable.");
    diamondSword.put("picture", null);
    items.add(diamondSword);

    // Luminous Pearl
    JSONObject pearl = new JSONObject();
    pearl.put("name", "Luminous Pearl");
    pearl.put("weight", "1");
    pearl.put("max_uses", "5");
    pearl.put("uses_remaining", "5");
    pearl.put("value", "75");
    pearl.put("when_used", "The pearl glows with an otherworldly light, illuminating the area.");
    pearl.put("description", "A rare pearl that glows with an inner blue light. It seems to possess magical properties.");
    pearl.put("picture", null);
    items.add(pearl);

    // Ancient Scroll
    JSONObject scroll = new JSONObject();
    scroll.put("name", "Ancient Scroll");
    scroll.put("weight", "1");
    scroll.put("max_uses", "5");
    scroll.put("uses_remaining", "5");
    scroll.put("value", "30");
    scroll.put("when_used", "You read the scroll, learning about the dungeon guardian below.");
    scroll.put("description", "A yellowed scroll with ancient text written on it. It mentions a guardian in the library basement.");
    scroll.put("picture", null);
    items.add(scroll);

    // Sealed Key
    JSONObject sealedKey = new JSONObject();
    sealedKey.put("name", "Sealed Key");
    sealedKey.put("weight", "1");
    sealedKey.put("max_uses", "1");
    sealedKey.put("uses_remaining", "1");
    sealedKey.put("value", "50");
    sealedKey.put("when_used", "You insert the key into the ornate lock. It turns with a satisfying click.");
    sealedKey.put("description", "An ornate key with ancient runes etched into its surface. It seems to be for an important door.");
    sealedKey.put("picture", null);
    items.add(sealedKey);

    // Gold Coins
    JSONObject goldCoins = new JSONObject();
    goldCoins.put("name", "Gold Coins");
    goldCoins.put("weight", "2");
    goldCoins.put("max_uses", "1");
    goldCoins.put("uses_remaining", "1");
    goldCoins.put("value", "100");
    goldCoins.put("when_used", "You count the gold coins, feeling richer already.");
    goldCoins.put("description", "A handful of gold coins from the treasure chamber. They glint in the light.");
    goldCoins.put("picture", null);
    items.add(goldCoins);

    // Ancient Map
    JSONObject ancientMap = new JSONObject();
    ancientMap.put("name", "Ancient Map");
    ancientMap.put("weight", "1");
    ancientMap.put("max_uses", "10");
    ancientMap.put("uses_remaining", "10");
    ancientMap.put("value", "40");
    ancientMap.put("when_used", "You study the ancient map, noting locations of other dungeons and treasure sites.");
    ancientMap.put("description", "A weathered map showing the locations of other dungeons and treasure sites in the region.");
    ancientMap.put("picture", null);
    items.add(ancientMap);

    // Jeweled Crown
    JSONObject crown = new JSONObject();
    crown.put("name", "Jeweled Crown");
    crown.put("weight", "2");
    crown.put("max_uses", "1");
    crown.put("uses_remaining", "1");
    crown.put("value", "200");
    crown.put("when_used", "You place the crown on your head, feeling quite regal.");
    crown.put("description", "A beautiful crown adorned with various precious gems. Fit for royalty.");
    crown.put("picture", null);
    items.add(crown);

    // Create fixtures
    JSONArray fixtures = new JSONArray();

    // Wooden Chest
    JSONObject chest = new JSONObject();
    chest.put("name", "Wooden Chest");
    chest.put("weight", "1000");
    chest.put("puzzle", null);
    chest.put("states", null);
    chest.put("description", "A simple wooden chest containing your personal belongings.");
    chest.put("picture", null);
    fixtures.add(chest);

    // Fountain
    JSONObject fountain = new JSONObject();
    fountain.put("name", "Fountain");
    fountain.put("weight", "1000");
    fountain.put("puzzle", null);
    fountain.put("states", null);
    fountain.put("description", "A stone fountain with clear water bubbling in the center of the town square.");
    fountain.put("picture", null);
    fixtures.add(fountain);

    // Glittering Deposits
    JSONObject deposits = new JSONObject();
    deposits.put("name", "Glittering Deposits");
    deposits.put("weight", "1000");
    deposits.put("puzzle", null);
    deposits.put("states", null);
    deposits.put("description", "Mineral deposits embedded in the cave wall. You spot traces of iron ore.");
    deposits.put("picture", null);
    fixtures.add(deposits);

    // Crafting Station
    JSONObject craftingStation = new JSONObject();
    craftingStation.put("name", "Crafting Station");
    craftingStation.put("weight", "1000");
    craftingStation.put("puzzle", null);
    craftingStation.put("states", null);
    craftingStation.put("description", "An ancient arrangement of wood and stones forming what appears to be a crafting station. It seems it can combine materials to create something new.");
    craftingStation.put("picture", null);
    fixtures.add(craftingStation);

    // Coral Formations
    JSONObject coral = new JSONObject();
    coral.put("name", "Coral Formations");
    coral.put("weight", "1000");
    coral.put("puzzle", null);
    coral.put("states", null);
    coral.put("description", "Beautiful coral formations that emit a soft blue glow, illuminating the passageway.");
    coral.put("picture", null);
    fixtures.add(coral);

    // Ancient Coral Formation
    JSONObject ancientCoral = new JSONObject();
    ancientCoral.put("name", "Ancient Coral Formation");
    ancientCoral.put("weight", "1000");
    ancientCoral.put("puzzle", null);
    ancientCoral.put("states", null);
    ancientCoral.put("description", "A formation of arrow-like coral patterns pointing to hidden secrets.");
    ancientCoral.put("picture", null);
    fixtures.add(ancientCoral);

    // Library Doors
    JSONObject libraryDoors = new JSONObject();
    libraryDoors.put("name", "Library Doors");
    libraryDoors.put("weight", "1000");
    libraryDoors.put("puzzle", null);
    libraryDoors.put("states", null);
    libraryDoors.put("description", "Heavy wooden doors marking the entrance to the town library.");
    libraryDoors.put("picture", null);
    fixtures.add(libraryDoors);

    // Ornate Bookshelf
    JSONObject bookshelf = new JSONObject();
    bookshelf.put("name", "Ornate Bookshelf");
    bookshelf.put("weight", "1000");
    bookshelf.put("puzzle", null);
    bookshelf.put("states", null);
    bookshelf.put("description", "A tall bookshelf with a riddle inscribed on one shelf: 'What has keys but can't open locks?'");
    bookshelf.put("picture", null);
    fixtures.add(bookshelf);

    // Sealed Door
    JSONObject sealedDoor = new JSONObject();
    sealedDoor.put("name", "Sealed Door");
    sealedDoor.put("weight", "1000");
    sealedDoor.put("puzzle", null);
    sealedDoor.put("states", null);
    sealedDoor.put("description", "A heavy wooden door with an ornate lock. It seems to require a special key.");
    sealedDoor.put("picture", null);
    fixtures.add(sealedDoor);

    // Stone Staircase
    JSONObject staircase = new JSONObject();
    staircase.put("name", "Stone Staircase");
    staircase.put("weight", "1000");
    staircase.put("puzzle", null);
    staircase.put("states", null);
    staircase.put("description", "Worn stone steps spiraling down into darkness, leading to the library basement.");
    staircase.put("picture", null);
    fixtures.add(staircase);

    // Ancient Doorway
    JSONObject ancientDoorway = new JSONObject();
    ancientDoorway.put("name", "Ancient Doorway");
    ancientDoorway.put("weight", "1000");
    ancientDoorway.put("puzzle", null);
    ancientDoorway.put("states", null);
    ancientDoorway.put("description", "An imposing doorway of ancient stone, currently blocked by the Dungeon Guardian.");
    ancientDoorway.put("picture", null);
    fixtures.add(ancientDoorway);

    // Wall Torches
    JSONObject torches = new JSONObject();
    torches.put("name", "Wall Torches");
    torches.put("weight", "1000");
    torches.put("puzzle", null);
    torches.put("states", null);
    torches.put("description", "Flickering torches mounted on the dungeon walls, providing dim illumination in the darkness.");
    torches.put("picture", null);
    fixtures.add(torches);

    // Stone Pillars
    JSONObject pillars = new JSONObject();
    pillars.put("name", "Stone Pillars");
    pillars.put("weight", "1000");
    pillars.put("puzzle", null);
    pillars.put("states", null);
    pillars.put("description", "Massive stone pillars supporting the high ceiling of the dungeon room. Ancient carvings decorate their surfaces.");
    pillars.put("picture", null);
    fixtures.add(pillars);

    // Stone Pedestal
    JSONObject pedestal = new JSONObject();
    pedestal.put("name", "Stone Pedestal");
    pedestal.put("weight", "1000");
    pedestal.put("puzzle", null);
    pedestal.put("states", null);
    pedestal.put("description", "A stone pedestal at the center of the chamber, bathed in soft blue light. It holds the Luminous Pearl.");
    pedestal.put("picture", null);
    fixtures.add(pedestal);

    // Map Table
    JSONObject mapTable = new JSONObject();
    mapTable.put("name", "Map Table");
    mapTable.put("weight", "1000");
    mapTable.put("puzzle", null);
    mapTable.put("states", null);
    mapTable.put("description", "A large table with various maps and charts scattered across its surface. They show dungeons and treasure sites throughout the region.");
    mapTable.put("picture", null);
    fixtures.add(mapTable);

    // Create puzzles
    JSONArray puzzles = new JSONArray();

    // Crafting Station Puzzle
    JSONObject craftingPuzzle = new JSONObject();
    craftingPuzzle.put("name", "Crafting Station");
    craftingPuzzle.put("active", "true");
    craftingPuzzle.put("affects_target", "true");
    craftingPuzzle.put("affects_player", "false");
    craftingPuzzle.put("solution", "Iron Ore");
    craftingPuzzle.put("value", "100");
    craftingPuzzle.put("description", "An ancient crafting station that can combine materials to create something new.");
    craftingPuzzle.put("effects", "The crafting station stands dormant. It seems to require specific materials to activate.");
    craftingPuzzle.put("target", "4:Crafting Chamber");
    craftingPuzzle.put("picture", null);
    puzzles.add(craftingPuzzle);

    // Water Current Lock
    JSONObject waterLock = new JSONObject();
    waterLock.put("name", "Water Current Lock");
    waterLock.put("active", "true");
    waterLock.put("affects_target", "true");
    waterLock.put("affects_player", "false");
    waterLock.put("solution", "'rotate-left-right-center'");
    waterLock.put("value", "150");
    waterLock.put("description", "A mechanism that controls the water currents in the cavern.");
    waterLock.put("effects", "Strong water currents prevent you from exploring deeper into the cavern. The coral patterns suggest a specific sequence of movements to calm the waters.");
    waterLock.put("target", "6:Coral Cavern");
    waterLock.put("picture", null);
    puzzles.add(waterLock);

    // Bookshelf Riddle
    JSONObject bookshelfRiddle = new JSONObject();
    bookshelfRiddle.put("name", "Bookshelf Riddle");
    bookshelfRiddle.put("active", "true");
    bookshelfRiddle.put("affects_target", "true");
    bookshelfRiddle.put("affects_player", "false");
    bookshelfRiddle.put("solution", "'piano'");
    bookshelfRiddle.put("value", "75");
    bookshelfRiddle.put("description", "A riddle inscribed on a shelf: 'What has keys but can't open locks?'");
    bookshelfRiddle.put("effects", "The ornate bookshelf seems to hide a secret passage. A riddle is inscribed on one of the shelves: 'What has keys but can't open locks?'");
    bookshelfRiddle.put("target", "8:Library Main Hall");
    bookshelfRiddle.put("picture", null);
    puzzles.add(bookshelfRiddle);

    // Sealed Door
    JSONObject sealedDoorPuzzle = new JSONObject();
    sealedDoorPuzzle.put("name", "Sealed Door");
    sealedDoorPuzzle.put("active", "true");
    sealedDoorPuzzle.put("affects_target", "true");
    sealedDoorPuzzle.put("affects_player", "false");
    sealedDoorPuzzle.put("solution", "Sealed Key");
    sealedDoorPuzzle.put("value", "100");
    sealedDoorPuzzle.put("description", "A heavy wooden door with an ornate lock.");
    sealedDoorPuzzle.put("effects", "A heavy wooden door blocks the way forward. It has an ornate lock that seems to require a special key.");
    sealedDoorPuzzle.put("target", "9:Secret Study");
    sealedDoorPuzzle.put("picture", null);
    puzzles.add(sealedDoorPuzzle);

    // Create monsters
    JSONArray monsters = new JSONArray();

    // Guardian Octopus
    JSONObject octopus = new JSONObject();
    octopus.put("name", "Guardian Octopus");
    octopus.put("active", "true");
    octopus.put("affects_target", "true");
    octopus.put("affects_player", "true");
    octopus.put("solution", "'coral'");
    octopus.put("value", "100");
    octopus.put("description", "A massive octopus with glowing blue patterns on its tentacles.");
    octopus.put("effects", "A massive Guardian Octopus blocks your path with its tentacles spread wide. It seems to be waiting for an answer rather than attacking.");
    octopus.put("damage", "15");
    octopus.put("target", "6:Coral Cavern");
    octopus.put("can_attack", "true");
    octopus.put("attack", "wraps a tentacle around you, squeezing tightly!");
    octopus.put("picture", null);
    monsters.add(octopus);

    // Dungeon Guardian
    JSONObject guardian = new JSONObject();
    guardian.put("name", "Dungeon Guardian");
    guardian.put("active", "true");
    guardian.put("affects_target", "true");
    guardian.put("affects_player", "true");
    guardian.put("solution", "Training Sword");
    guardian.put("value", "150");
    guardian.put("description", "A fearsome guardian of the dungeon, clad in ancient armor.");
    guardian.put("effects", "A massive Dungeon Guardian blocks your path, protecting the ancient doorway. It readies its weapon as you approach.");
    guardian.put("damage", "20");
    guardian.put("target", "11:Library Basement");
    guardian.put("can_attack", "true");
    guardian.put("attack", "swings its ancient weapon at you!");
    guardian.put("picture", null);
    monsters.add(guardian);

    // Troll
    JSONObject troll = new JSONObject();
    troll.put("name", "Troll");
    troll.put("active", "true");
    troll.put("affects_target", "true");
    troll.put("affects_player", "true");
    troll.put("solution", "Diamond Sword");
    troll.put("value", "200");
    troll.put("description", "A fierce troll with green skin and a large club.");
    troll.put("effects", "A fierce Troll guards the southern exit, swinging a massive club menacingly.");
    troll.put("damage", "25");
    troll.put("target", "13:Dungeon Room");
    troll.put("can_attack", "true");
    troll.put("attack", "swings its club at you with tremendous force!");
    troll.put("picture", null);
    monsters.add(troll);

    // Treasure Chest Monster
    JSONObject chestMonster = new JSONObject();
    chestMonster.put("name", "Treasure Chest");
    chestMonster.put("active", "true");
    chestMonster.put("affects_target", "true");
    chestMonster.put("affects_player", "true");
    chestMonster.put("solution", "Luminous Pearl");
    chestMonster.put("value", "125");
    chestMonster.put("description", "What appeared to be a treasure chest is actually a monster in disguise!");
    chestMonster.put("effects", "A suspicious-looking treasure chest sits in the corner. As you approach, it seems to move slightly...");
    chestMonster.put("damage", "15");
    chestMonster.put("target", "14:Treasure Chamber");
    chestMonster.put("can_attack", "true");
    chestMonster.put("attack", "snaps at you with razor-sharp teeth!");
    chestMonster.put("picture", null);
    monsters.add(chestMonster);

    // Add all elements to the game data
    gameData.put("rooms", rooms);
    gameData.put("items", items);
    gameData.put("fixtures", fixtures);
    gameData.put("puzzles", puzzles);
    gameData.put("monsters", monsters);

    return gameData.toJSONString();
  }
}