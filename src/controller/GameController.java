package controller;

import model.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * handles player commands and game display
 */
public class GameController {
  // what we need to run the game
    private GameWorld game;
    private Scanner scanner;
    private Appendable output;
    private boolean running;
    
    /**
     * set up the controller
     */
    public Controller(GameWorld game, Readable input, Appendable output) {
        // connect to game and input/output
        this.game = game;
        this.scanner = new Scanner(input);
        this.output = output;
        this.running = false;
    }
    
    /**
     * start the game
     */
    public void play() throws IOException {
        // start running
        running = true;
        
        // get player name
        print("Welcome to " + game.getGameName() + "!\n");
        print("Enter your name: ");
        String name = scanner.nextLine().trim();
        game.setPlayerName(name);
        print("Hello, " + name + "! Your adventure begins.\n\n");
        
        // main game loop
        while (running && scanner.hasNextLine()) {
            // show the room
            showRoom();
            
            // check if player died
            if (game.getPlayer().getHealth() <= 0) {
                print("\nYou've fallen asleep. Game over!\n");
                running = false;
                return;
            }
            
            // show commands and get input
            showCommands();
            String command = scanner.nextLine().trim().toLowerCase();
            
            // handle the command
            handleCommand(command);
        }
    }
    
    /**
     * show the current room
     */
    public void showRoom() throws IOException {
        // get player and room
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();
        
        // show health
        print("Health: " + player.getHealth() + " (" + player.getHealthStatus() + ")\n");
        
        // show room name
        print("You are in the " + room.getName().toUpperCase() + "\n");
        
        // show description based on what's in the room
        Puzzle puzzle = room.getPuzzle();
        Monster monster = room.getMonster();
        
        if (puzzle != null && puzzle.isActive() && puzzle.affectsTarget()) {
            // puzzle is affecting the room
            print(puzzle.getEffects() + "\n");
        } else if (monster != null && monster.isActive()) {
            // monster is in the room
            print("A monster " + monster.getName() + " is here!\n");
            
            // monster attacks if it can
            if (monster.canAttack()) {
                int damage = monster.attack(player);
                if (damage > 0) {
                    print("The monster attacks! You take " + damage + " damage!\n");
                }
            }
        } else {
            // normal room
            print(room.getDescription() + "\n");
        }
        
        // list items in the room
        if (!room.getItems().isEmpty()) {
            print("Items here: ");
            for (Item item : room.getItems()) {
                print(item.getName().toUpperCase() + " ");
            }
            print("\n");
        }
    }
    
    /**
     * show available commands
     */
    private void showCommands() throws IOException {
        print("\nCommands: (N)orth, (S)outh, (E)ast, (W)est\n");
        print("          (I)nventory, (L)ook, (U)se [item]\n");
        print("          (T)ake [item], (D)rop [item], e(X)amine [thing]\n");
        print("          (A)nswer [text], (Q)uit\n");
        print("What do you want to do? ");
    }
    
    /**
     * handle a player command
     */
    public void handleCommand(String command) throws IOException {
        // ignore empty commands
        if (command.isEmpty()) {
            return;
        }
        
        // movement
        if (command.equals("n") || command.equals("north")) {
            move(Direction.NORTH);
        } else if (command.equals("s") || command.equals("south")) {
            move(Direction.SOUTH);
        } else if (command.equals("e") || command.equals("east")) {
            move(Direction.EAST);
        } else if (command.equals("w") || command.equals("west")) {
            move(Direction.WEST);
        } 
        // other commands
        else if (command.equals("i") || command.equals("inventory")) {
            showInventory();
        } else if (command.equals("l") || command.equals("look")) {
            showRoom();
        } else if (command.startsWith("t ") || command.startsWith("take ")) {
            // get item name after "take "
            String itemName = command.contains(" ") ? command.substring(command.indexOf(" ") + 1) : "";
            takeItem(itemName);
        } else if (command.startsWith("d ") || command.startsWith("drop ")) {
            // get item name after "drop "
            String itemName = command.contains(" ") ? command.substring(command.indexOf(" ") + 1) : "";
            dropItem(itemName);
        } else if (command.startsWith("x ") || command.startsWith("examine ")) {
            // get target name after "examine "
            String target = command.contains(" ") ? command.substring(command.indexOf(" ") + 1) : "";
            examine(target);
        } else if (command.startsWith("u ") || command.startsWith("use ")) {
            // get item name after "use "
            String itemName = command.contains(" ") ? command.substring(command.indexOf(" ") + 1) : "";
            useItem(itemName);
        } else if (command.startsWith("a ") || command.startsWith("answer ")) {
            // get answer after "answer "
            String answer = command.contains(" ") ? command.substring(command.indexOf(" ") + 1) : "";
            answer(answer);
        } else if (command.equals("save")) {
            saveGame();
        } else if (command.equals("restore")) {
            loadGame();
        } else if (command.equals("q") || command.equals("quit")) {
            print("Thanks for playing!\n");
            running = false;
        } else {
            print("I don't understand that command.\n");
        }
    }
    
    /**
     * try to move in a direction
     */
    public void move(Direction direction) throws IOException {
        // get current room
        Room currentRoom = game.getPlayer().getCurrentRoom();
        String exitNumber = currentRoom.getExitRoomNumber(direction);
        
        // check for wall (0)
        if (exitNumber.equals("0")) {
            print("You can't go that way.\n");
            return;
        }
        
        // check for blocked path (negative number)
        if (Integer.parseInt(exitNumber) < 0) {
            // find what's blocking the path
            if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
                print("A puzzle blocks your way: " + currentRoom.getPuzzle().getDescription() + "\n");
            } else if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
                print("A monster blocks your way!\n");
                // monster may attack
                Monster monster = currentRoom.getMonster();
                if (monster.canAttack()) {
                    int damage = monster.attack(game.getPlayer());
                    if (damage > 0) {
                        print("The monster attacks! You take " + damage + " damage!\n");
                    }
                }
            } else {
                print("Something blocks your way.\n");
            }
            return;
        }
        
        // move to the new room
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            game.getPlayer().setCurrentRoom(nextRoom);
            print("You move " + direction.toString().toLowerCase() + ".\n");
        } else {
            print("You can't go that way right now.\n");
        }
    }
    
    /**
     * show what's in inventory
     */
    public void showInventory() throws IOException {
        // get inventory
        Player player = game.getPlayer();
        
        // show header with weight
        print("Your inventory (" + player.getInventoryWeight() + "/" + player.getMaxWeight() + "):\n");
        
        // check if empty
        if (player.getInventory().isEmpty()) {
            print("You're not carrying anything.\n");
            return;
        }
        
        // list all items
        for (Item item : player.getInventory()) {
            print("- " + item.getName() + " (uses left: " + item.getUsesRemaining() + ")\n");
        }
    }
    
    /**
     * take an item from the room
     */
    public void takeItem(String itemName) throws IOException {
        // check if item name is empty
        if (itemName.isEmpty()) {
            print("What do you want to take?\n");
            return;
        }
        
        // find the item in the room
        Room currentRoom = game.getPlayer().getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        
        // check if item exists
        if (item == null) {
            print("There's no " + itemName + " here.\n");
            return;
        }
        
        // try to pick it up
        if (game.getPlayer().addToInventory(item)) {
            currentRoom.removeItem(item);
            print("You pick up the " + item.getName() + ".\n");
        } else {
            print("You can't carry any more.\n");
        }
    }
    
    /**
     * drop an item from inventory
     */
    public void dropItem(String itemName) throws IOException {
        // check if item name is empty
        if (itemName.isEmpty()) {
            print("What do you want to drop?\n");
            return;
        }
        
        // find the item in inventory
        Player player = game.getPlayer();
        Item item = player.getItemFromInventory(itemName);
        
        // check if player has the item
        if (item == null) {
            print("You don't have a " + itemName + ".\n");
            return;
        }
        
        // try to drop it
        if (player.removeFromInventory(item)) {
            player.getCurrentRoom().addItem(item);
            print("You drop the " + item.getName() + ".\n");
        } else {
            print("You can't drop that for some reason.\n");
        }
    }
    
    /**
     * examine something
     */
    public void examine(String target) throws IOException {
        // check if target is empty
        if (target.isEmpty()) {
            print("What do you want to examine?\n");
            return;
        }
        
        // check inventory first
        Player player = game.getPlayer();
        Item inventoryItem = player.getItemFromInventory(target);
        
        if (inventoryItem != null) {
            print(inventoryItem.getDescription() + "\n");
            return;
        }
        
        // check room items
        Room currentRoom = player.getCurrentRoom();
        Item roomItem = currentRoom.getItem(target);
        
        if (roomItem != null) {
            print(roomItem.getDescription() + "\n");
            return;
        }
        
        // check fixtures
        Fixture fixture = currentRoom.getFixture(target);
        
        if (fixture != null) {
            print(fixture.getDescription() + "\n");
            return;
        }
        
        // nothing found
        print("You don't see a " + target + " here.\n");
    }
    
    /**
     * use an item
     */
    public void useItem(String itemName) throws IOException {
        // check if item name is empty
        if (itemName.isEmpty()) {
            print("What do you want to use?\n");
            return;
        }
        
        // find item in inventory
        Player player = game.getPlayer();
        Item item = player.getItemFromInventory(itemName);
        
        // check if player has the item
        if (item == null) {
            print("You don't have a " + itemName + ".\n");
            return;
        }
        
        // check if it has uses left
        if (item.getUsesRemaining() <= 0) {
            print("The " + item.getName() + " can't be used anymore.\n");
            return;
        }
        
        // try to solve puzzle or defeat monster with it
        Room currentRoom = player.getCurrentRoom();
        boolean solved = game.applySolution(item.getName());
        
        // handle result
        if (solved) {
            if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isActive()) {
                print("You used the " + item.getName() + " to solve the puzzle!\n");
                print(currentRoom.getPuzzle().getEffects() + "\n");
                print("You earned " + currentRoom.getPuzzle().getValue() + " points!\n");
            } else if (currentRoom.getMonster() != null && !currentRoom.getMonster().isActive()) {
                print("You used the " + item.getName() + " to defeat the monster!\n");
                print("You earned " + currentRoom.getMonster().getValue() + " points!\n");
            }
        } else {
            // just use it normally
            print("You use the " + item.getName() + ".\n");
            print(item.getWhenUsed() + "\n");
        }
        
        // reduce uses left
        item.use();
    }
    
    /**
     * answer a puzzle
     */
    public void answer(String answer) throws IOException {
        // check if answer is empty
        if (answer.isEmpty()) {
            print("What's your answer?\n");
            return;
        }
        
        // check if there's a puzzle to solve
        Room currentRoom = game.getPlayer().getCurrentRoom();
        Puzzle puzzle = currentRoom.getPuzzle();
        
        if (puzzle == null || !puzzle.isActive()) {
            print("There's no puzzle here to solve.\n");
            return;
        }
        
        // check if it's a text puzzle
        if (!puzzle.getSolution().startsWith("'")) {
            print("This puzzle needs an item, not an answer.\n");
            return;
        }
        
        // try to solve it
        boolean solved = game.applySolution(answer);
        
        // handle result
        if (solved) {
            print("Correct! " + puzzle.getEffects() + "\n");
            print("You earned " + puzzle.getValue() + " points!\n");
        } else {
            print("That's not right. Try something else.\n");
        }
    }
    
    /**
     * save the game
     */
    public void saveGame() throws IOException {
        try {
            game.saveGame("saved_game.json");
            print("Game saved!\n");
        } catch (IOException e) {
            print("Couldn't save the game: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * load a saved game
     */
    public void loadGame() throws IOException {
        try {
            game.loadGame("saved_game.json");
            print("Game loaded!\n");
        } catch (Exception e) {
            print("Couldn't load the game: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * print a message
     */
    private void print(String message) throws IOException {
        output.append(message);
    }
}
}
