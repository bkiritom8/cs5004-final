package controller;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
dummy environment for testing game.
*/
class TestGameWorld extends GameWorld {
    private Player player;
    private Room room;

    public TestGameWorld() throws IOException {
        super("./resources/empty_rooms.json"); // Call the actual constructor

        // Then override what we need
        Map<Direction, String> exits = new HashMap<>();
        exits.put(Direction.NORTH, "0");
        exits.put(Direction.SOUTH, "0");
        exits.put(Direction.EAST, "0");
        exits.put(Direction.WEST, "0");

        Room room = new Room("test room", "1", "a simple test room", exits);
        this.player = new Player(room);
    }


    @Override
    public String getGameName() {
        return "integration test game";
    }
    
    @Override
    public void setPlayerName(String name) {
        player.setName(name);
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }
    
    @Override
    public boolean applySolution(String solution) {
        return false;
    }
    
    @Override
    public void saveGame(String filename) throws IOException {
        // do nothing
    }
    
    @Override
    public void loadGame(String filename) throws IOException {
        // do nothing
    }
}

/*
test gamecontroller.
name + basic commands.
*/
public class GameControllerTest {

    @Test
    public void testQuitCommand() throws IOException {
        // simulate: enter name then quit
        String input = "testplayer\nq\n";
        StringReader reader = new StringReader(input);
        StringWriter writer = new StringWriter();
        TestGameWorld gameWorld = new TestGameWorld();

        GameController controller = new GameController(gameWorld, reader, writer);
        controller.play();

        String output = writer.toString();
        assertTrue(output.contains("welcome to integration test game!"), "display welcome message");
        assertTrue(output.contains("hello, testplayer!"), "display greeting");
        assertTrue(output.contains("game over"), "display game over message");
    }

    @Test
    public void testLookAndInventory() throws IOException {
        // simulate: enter name, look, check inventory, then quit
        String input = "testplayer\nl\ni\nq\n";
        StringReader reader = new StringReader(input);
        StringWriter writer = new StringWriter();
        TestGameWorld gameWorld = new TestGameWorld();

        GameController controller = new GameController(gameWorld, reader, writer);
        controller.play();

        String output = writer.toString();
        assertTrue(output.contains("a simple test room"), "display room description");
        assertTrue(output.contains("your inventory is empty"), "display empty inventory");
    }
}
