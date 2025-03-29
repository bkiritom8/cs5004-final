package scenarios;

import model.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Scenario 4: Activating a Crafting Station
 *
 * The player enters a Cave and notices an arrangement of wood and stones — this is a Crafting Station.
 * The station appears inactive until the player has both Iron and Diamond.
 * When the player interacts with the station while holding both, the items are removed,
 * and a new item, Diamond Sword, is crafted and added to inventory.
 */
public class CraftingStationScenario {

  public static void main(String[] args) {
    // Since Player now requires a starting Room, create a dummy room for the player.
    Map<Direction, String> defaultExits = new HashMap<>();
    defaultExits.put(Direction.NORTH, "0");
    defaultExits.put(Direction.SOUTH, "0");
    defaultExits.put(Direction.EAST, "0");
    defaultExits.put(Direction.WEST, "0");
    Room startingRoom = new Room("Cave Entrance", "0", "A dark cave entrance.", defaultExits, field1, field2, itemsField, field3, picture);

    // Step 1: Set up the player with a starting room.
    Player player = new Player(startingRoom);
    System.out.println("You enter a dark cave and discover a strange structure made of wood and stone.");

    // Step 2: Set up the crafting station fixture.
    // (Using the Fixture constructor that takes name, weight, and description.)
    Fixture craftingStation = new Fixture("Crafting Station", 0, "A crafting station that is inactive until you have both Iron and Diamond.");

    // Step 3: Show current inventory.
    System.out.println("Current Inventory: " + player.getInventory());

    // Step 4: Try interacting with no required items.
    System.out.println("You approach the crafting station...");
    System.out.println(craftingStation.interact(player));

    // Step 5: Add required items using addToInventory.
    player.addToInventory(Item.IRON);
    player.addToInventory(Item.DIAMOND);
    System.out.println("\nYou found Iron and Diamond!");

    // Step 6: Show updated inventory.
    System.out.println("Current Inventory: " + player.getInventory());

    // Step 7: Try interacting again.
    System.out.println("\nYou try using the crafting station again...");
    System.out.println(craftingStation.interact(player));

    // Step 8: Final inventory check.
    System.out.println("Final Inventory: " + player.getInventory());
  }
}
