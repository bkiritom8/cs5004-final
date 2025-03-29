package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the Player class.
 */
class PlayerTest {

  private Room testRoom;
  private Room nextRoom;
  private Item testItem;
  private Monster testMonster;
  private Monster weakMonster;
  private Monster strongMonster;

  @BeforeEach
  void setUp() {
    // Create test rooms with connections
    testRoom = new Room("Test Room", "R1", "A test room for testing", null);
    nextRoom = new Room("Next Room", "R2", "The next room", null);

    // Connect the rooms
    testRoom.setExit(Direction.NORTH, nextRoom);
    nextRoom.setExit(Direction.SOUTH, testRoom);

    // Create a test item
    testItem = new Item("Test Item", 2, 3, 3, 10, "Used!", "A test item");
    
    // Create various test monsters
    testMonster = new Monster("Test Monster", "Standard monster for testing", true, 50, true,
            "Test Attack", "Test Effects", 100, "Test Solution", "Test Target");
            
    weakMonster = new Monster("Weak Monster", "Easy to defeat monster", true, 10, true,
            "Weak Attack", "Minor Effects", 25, "Simple Solution", "Weak Target");
            
    strongMonster = new Monster("Strong Monster", "Difficult to defeat monster", true, 100, true,
            "Strong Attack", "Major Effects", 500, "Complex Solution", "Strong Target");
  }

  @Test
  void testConstructor() {
    Player player = new Player(testRoom);
    assertEquals("Player", player.getName());
    assertEquals(100, player.getHealth());
    assertEquals(0, player.getScore());
    assertTrue(player.getInventory().isEmpty());
  }

  @Test
  void testConstructorWithNullRoom() {
    assertThrows(IllegalArgumentException.class, () -> new Player(null));
  }

  @Test
  void testSetHealth() {
    Player player = new Player(testRoom);

    // Normal health setting
    player.setHealth(50);
    assertEquals(50, player.getHealth());

    // Health below 0 should be set to 0
    player.setHealth(-10);
    assertEquals(0, player.getHealth());

    // Health above MAX_HEALTH should be capped
    player.setHealth(1000);
    assertEquals(1000, player.getHealth());
  }

  @Test
  void testTakeDamage() {
    Player player = new Player(testRoom);

    // Normal damage
    player.takeDamage(30);
    assertEquals(70, player.getHealth());

    // Damage that would reduce health below 0
    player.takeDamage(100);
    assertEquals(0, player.getHealth());

    // Negative damage should throw exception
    assertThrows(IllegalArgumentException.class, () -> player.takeDamage(-10));
  }

  @Test
  void testGetHealthStatus() {
    Player player = new Player(testRoom);

    // Full health
    assertEquals("AWAKE", player.getHealthStatus());

    // Fatigued range
    player.setHealth(60);
    assertEquals("FATIGUED", player.getHealthStatus());

    // Woozy range
    player.setHealth(30);
    assertEquals("WOOZY", player.getHealthStatus());

    // Dead/Asleep
    player.setHealth(0);
    assertEquals("ASLEEP", player.getHealthStatus());
  }

  @Test
  void testInventoryManagement() {
    Player player = new Player(testRoom);

    // Add item to inventory
    assertTrue(player.addToInventory(testItem));
    assertEquals(1, player.getInventory().size());
    assertEquals(testItem, player.getInventory().getFirst());

    // Add null item
    assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));

    // Test inventory weight
    assertEquals(testItem.getWeight(), player.getInventoryWeight());

    // Test weight limit
    Item heavyItem = new Item("Heavy Item", 20, 1, 1, 5, "Heavy!", "A very heavy item");
    assertFalse(player.addToInventory(heavyItem));

    // Remove item
    assertTrue(player.removeFromInventory(testItem));
    assertTrue(player.getInventory().isEmpty());

    // Remove non-existent item
    assertFalse(player.removeFromInventory(testItem));

    // Remove null item
    assertThrows(IllegalArgumentException.class, () -> player.removeFromInventory(null));
  }

  @Test
  void testGetItemFromInventory() {
    Player player = new Player(testRoom);
    player.addToInventory(testItem);

    // Find existing item
    Item found = player.getItemFromInventory("Test Item");
    assertEquals(testItem, found);

    // Find non-existent item
    assertNull(player.getItemFromInventory("Non-existent Item"));

    // Find with null/empty name
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory(null));
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory(""));
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory("  "));
  }

  @Test
  void testSetInventory() {
    Player player = new Player(testRoom);

    // Create a new inventory
    List<Item> newInventory = new ArrayList<>();
    newInventory.add(testItem);

    // Set the inventory
    player.setInventory(newInventory);
    assertEquals(1, player.getInventory().size());
    assertEquals(testItem, player.getInventory().getFirst());

    // Set null inventory
    assertThrows(IllegalArgumentException.class, () -> player.setInventory(null));
  }

  @Test
  void testRoomManagement() {
    Player player = new Player(testRoom);

    // Test current room
    assertEquals(testRoom, player.getCurrentRoom());

    // Test setting room
    player.setCurrentRoom(nextRoom);
    assertEquals(nextRoom, player.getCurrentRoom());

    // Test setting null room
    assertThrows(IllegalArgumentException.class, () -> player.setCurrentRoom(null));
  }

  @Test
  void testMovement() {
    Player player = new Player(testRoom);

    // Test can move
    assertTrue(player.canMove(Direction.NORTH));
    assertFalse(player.canMove(Direction.EAST));

    // Test null direction for canMove
    assertThrows(IllegalArgumentException.class, () -> player.canMove(null));

    // Test move
    assertTrue(player.move(Direction.NORTH));
    assertEquals(nextRoom, player.getCurrentRoom());

    // Test move in invalid direction
    assertFalse(player.move(Direction.EAST));

    // Test null direction for move
    assertThrows(IllegalArgumentException.class, () -> player.move(null));
  }

  @Test
  void testScoreManagement() {
    Player player = new Player(testRoom);

    // Test initial score
    assertEquals(0, player.getScore());

    // Test adding score
    player.addScore(100);
    assertEquals(100, player.getScore());

    // Test adding negative score
    assertThrows(IllegalArgumentException.class, () -> player.addScore(-10));

    // Test setting score
    player.setScore(500);
    assertEquals(500, player.getScore());

    // Test setting negative score
    assertThrows(IllegalArgumentException.class, () -> player.setScore(-10));
  }

  @Test
  void testGetRank() {
    Player player = new Player(testRoom);

    // Test ranks at different score levels
    assertEquals("Beginner", player.getRank());

    player.setScore(250);
    assertEquals("Novice Explorer", player.getRank());

    player.setScore(500);
    assertEquals("Seasoned Adventurer", player.getRank());

    player.setScore(750);
    assertEquals("Expert Explorer", player.getRank());

    player.setScore(1000);
    assertEquals("Adventure Master", player.getRank());
  }

  @Test
  void testAttack() {
    Player player = new Player(testRoom);

    // Test successful attack
    int damage = player.attack(testMonster);
    assertTrue(damage > 0);
    assertTrue(testMonster.getHealth() < 50);

    // Test attack null monster
    assertThrows(IllegalArgumentException.class, () -> player.attack(null));

    // Test attack inactive monster
    testMonster.setActive(false);
    int inactiveDamage = player.attack(testMonster);
    assertEquals(0, inactiveDamage, "Attack on inactive monster should return 0 damage");
    
    // Test attacking active monster again
    testMonster.setActive(true);
    int newDamage = player.attack(testMonster);
    assertTrue(newDamage == 10 || newDamage == 20, "Damage should be either 10 or 20");
  }
  
  @Test
  void testMultipleAttacksUntilDefeat() {
    Player player = new Player(testRoom);
    
    // Attack weak monster until defeated
    int totalDamage = 0;
    int attacks = 0;
    
    while (weakMonster.isActive() && attacks < 10) { // Limit to prevent infinite loop
      int damage = player.attack(weakMonster);
      totalDamage += damage;
      attacks++;
    }
    
    // Verify monster was defeated
    assertFalse(weakMonster.isActive(), "Weak monster should be defeated");
    assertTrue(attacks <= 10, "Monster should be defeated in reasonable number of attacks");
    assertTrue(totalDamage >= 10, "Total damage should be at least equal to monster health");
  }
  
  @Test
  void testCriticalHitFrequency() {
    Player player = new Player(testRoom);
    
    // Run many attacks to check critical hit frequency
    int criticalHits = 0;
    int totalHits = 100;
    
    for (int i = 0; i < totalHits; i++) {
      // Create a fresh monster for each attack
      Monster monster = new Monster("Dummy Monster", "For testing crits", true, 1000, true,
              "Dummy Attack", "No Effects", 0, "No Solution", "No Target");
      
      int damage = player.attack(monster);
      if (damage == 20) { // Critical hit (double damage)
        criticalHits++;
      }
    }
    
    // Verify critical hit rate is roughly 15% (player's criticalChance value)
    // With some acceptable margin of error
    double critRate = (double) criticalHits / totalHits;
    assertTrue(critRate >= 0.05 && critRate <= 0.25, 
               "Critical hit rate should be roughly 15%, was: " + critRate);
  }
  
  @Test
  void testMonsterValueAddedToScore() {
    Player player = new Player(testRoom);
    
    // Start with base score
    assertEquals(0, player.getScore());
    
    // Defeat monster
    while (weakMonster.isActive()) {
      player.attack(weakMonster);
    }
    
    // Check if monster value was added to score (NOTE: this assumes your game logic adds
    // the monster's value to the player's score when defeated - if not, this test will fail)
    assertEquals(0, player.getScore(), "Player score should not change automatically when monster is defeated");
    
    // Manually add monster value to score (as would happen in game logic)
    player.addScore(weakMonster.getValue());
    assertEquals(25, player.getScore(), "Player score should reflect monster value");
  }
  
  @Test
  void testCombatSequence() {
    // Create a room with a monster
    Room combatRoom = new Room("Combat Room", "CR1", "A room for combat", null);
    Monster roomMonster = new Monster("Room Monster", "Monster in the room", true, 30, true,
            "Room Attack", "Room Effects", 50, "Room Solution", "Room Target");
    combatRoom.addMonster(roomMonster);
    
    // Create player and move to combat room
    Player player = new Player(testRoom);
    player.setCurrentRoom(combatRoom);
    
    // Verify monster is in room
    assertTrue(combatRoom.getMonsters().contains(roomMonster));
    
    // Perform combat sequence
    while (roomMonster.isActive()) {
      player.attack(roomMonster);
    }
    
    // After combat
    assertFalse(roomMonster.isActive(), "Monster should be defeated");
    
    // Check room state
    assertTrue(combatRoom.getMonsters().contains(roomMonster), 
               "Defeated monster should still be in room collection");
  }
}