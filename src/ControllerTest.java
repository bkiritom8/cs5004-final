package controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tests for the controller class
 */
class ControllerTest {
    // test objects we'll need
    private MockGameWorld gameWorld;
    private StringWriter outputWriter;
    private Controller controller;
    
    /**
     * set up before each test
     */
    @BeforeEach
    public void setUp() {
        // step 1: create mock game world
        gameWorld = new MockGameWorld();
        
        // step 2: set up input/output
        outputWriter = new StringWriter();
        
        // step 3: create controller with our test objects
        controller = new Controller(gameWorld, null, outputWriter);
    }
    
    /**
     * test displaying room info with a puzzle
     */
    @Test
    public void testShowLocationWithPuzzle() throws IOException {
        // step 1: create a room with a puzzle
        MockRoom room = new MockRoom("Puzzle Room", "1", "Room description");
        Puzzle puzzle = new Puzzle(
            "Test Puzzle",
            true,
            true,
            false,
            "'answer'",
            10,
            "Puzzle description",
            "Puzzle effects",
            "test"
        );
        room.setPuzzle(puzzle);
        
        // step 2: set as current room
        gameWorld.setCurrentRoom(room);
        
        // step 3: call the method to display room info
        controller.showLocation();
        
        // step 4: check the output contains puzzle effects
        String output = outputWriter.toString();
        assertTrue(output.contains("Puzzle effects"));
    }
    
    /**
     * test displaying room info with a monster
     */
    @Test
    public void testShowLocationWithMonster() throws IOException {
        // step 1: create a room with a monster
        MockRoom room = new MockRoom("Monster Room", "1", "Room description");
        MockMonster monster = new MockMonster("Troll", true);
        room.setMonster(monster);
        
        // step 2: set as current room
        gameWorld.setCurrentRoom(room);
        
        // step 3: call the method to display room info
        controller.showLocation();
        
        // step 4: check the output contains monster message
        String output = outputWriter.toString();
        assertTrue(output.contains("monster") && output.contains("Troll"));
    }
    
    /**
     * test moving in a valid direction
     */
    @Test
    public void testMoveValid() throws IOException {
        // step 1: create two connected rooms
        MockRoom room1 = new MockRoom("Start Room", "1", "Starting point");
        MockRoom room2 = new MockRoom("Next Room", "2", "Next room");
        
        // step 2: set up the exit connection
        room1.setExitRoomNumber(Direction.NORTH, "2");
        room1.setExit(Direction.NORTH, room2);
        
        // step 3: set current room
        gameWorld.setCurrentRoom(room1);
        
        // step 4: try to move north
        controller.tryMove(Direction.NORTH);
        
        // step 5: verify player moved to the new room
        assertEquals(room2, gameWorld.player.getCurrentRoom());
        
        // step 6: check for success message
        String output = outputWriter.toString();
        assertTrue(output.contains("move north"));
    }
    
    /**
     * test moving toward a wall
     */
    @Test
    public void testMoveToWall() throws IOException {
        // step 1: create a room with a wall exit
        MockRoom room = new MockRoom("Walled Room", "1", "Room with walls");
        room.setExitRoomNumber(Direction.EAST, "0");  // 0 = wall
        
        // step 2: set current room
        gameWorld.setCurrentRoom(room);
        
        // step 3: try to move east
        controller.tryMove(Direction.EAST);
        
        // step 4: verify player didn't move
        assertEquals(room, gameWorld.player.getCurrentRoom());
        
        // step 5: check for wall message
        String output = outputWriter.toString();
        assertTrue(output.contains("wall"));
    }
    
    /**
     * test moving toward a blocked path
     */
    @Test
    public void testMoveToBlockedPath() throws IOException {
        // step 1: create a room with a blocked exit and a puzzle
        MockRoom room = new MockRoom("Blocked Room", "1", "Room with blocked exit");
        room.setExitRoomNumber(Direction.SOUTH, "-2");  // negative = blocked
        
        Puzzle puzzle = new Puzzle("Lock", true, true, false,
                                  "key", 10, "A locked door", 
                                  "The door is locked", "door");
        room.setPuzzle(puzzle);
        
        // step 2: set current room
        gameWorld.setCurrentRoom(room);
        
        // step 3: try to move south
        controller.tryMove(Direction.SOUTH);
        
        // step 4: verify player didn't move
        assertEquals(room, gameWorld.player.getCurrentRoom());
        
        // step 5: check for blocked message
        String output = outputWriter.toString();
        assertTrue(output.contains("blocked") && output.contains("puzzle"));
    }
    
    /**
     * test displaying inventory
     */
    @Test
    public void testShowInventory() throws IOException {
        // step 1: add items to player's inventory
        MockItem item1 = new MockItem("lantern", 2);
        MockItem item2 = new MockItem("rope", 1);
        gameWorld.player.addItem(item1);
        gameWorld.player.addItem(item2);
        
        // step 2: show inventory
        controller.showInventory();
        
        // step 3