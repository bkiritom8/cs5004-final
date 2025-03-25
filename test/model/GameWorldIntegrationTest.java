package model;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Integration test for the game world.
 */
public class GameWorldIntegrationTest {

  @Test
  void testLoadEmptyRoomsGame() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/empty_rooms.json");
    assertEquals("Empty Rooms", gameWorld.getGameName());

    Room startRoom = gameWorld.getPlayer().getCurrentRoom();
    assertEquals("1", startRoom.getRoomNumber());
    assertEquals("Hallway 1", startRoom.getName());
  }

}
