package scenarios;

import enginedriver.GameEngineApp;
import model.*;

/*
scenario 6: unlocking the pearl
updates the coral cavern with a fixture, monster, puzzle, and pearl item
*/
public class UnlockPearlScenario implements Scenario {

    private final GameWorld gameWorld;
    
    public UnlockPearlScenario(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    
    @Override
    public void runScenario() {
        // get the coral cavern room (ensure the id matches your JSON)
        Room cavern = gameWorld.getRoom("CoralCavern");
        if (cavern == null) return;
        
        // add the ancient coral formation fixture
        Fixture coral = new Fixture("ancient coral formation", 500, "arrow-like coral patterns");
        cavern.addFixture(coral);
        
        // set the guardian octopus monster with a riddle answer
        Monster octopus = new Monster("guardian octopus", true, 10, true,
            "the octopus lashes out", 
            "a giant octopus blocks your way",
            "it retreats when you answer correctly", 
            50, "'coral'", "path");
        cavern.setMonster(octopus);
        
        // set the water current lock puzzle
        Puzzle lockPuzzle = new Puzzle("water current lock", true, true, false,
            "rotate-left-right-center", 100,
            "rotate the seashell wheels in the correct order", 
            "you hear a click as a hidden chamber opens", "chamber");
        cavern.setPuzzle(lockPuzzle);
        
        // add the luminous pearl item if not already present
        boolean pearlExists = false;
        for (Item item : cavern.getItems()) {
            if (item.getName().equalsIgnoreCase("luminous pearl")) {
                pearlExists = true;
                break;
            }
        }
        if (!pearlExists) {
            Item pearl = new Item("luminous pearl", 1, 1, 1, 200,
                "the pearl glows brightly", "a glowing pearl");
            cavern.addItem(pearl);
        }
        
        // update room description
        cavern.setDescription(cavern.getDescription() + " The cavern glows with hints of hidden treasure.");
    }
}