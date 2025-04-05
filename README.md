Project Title: Data-Driven Adventure Game Engine (HW9 Update)
Course: CS5004 - Object Oriented Design
Team Members:
- Theresa Coleman (coleman.t@northeastern.edu)
- Zuzu Nyirakanyange (nyirakanyange.z@northeastern.edu)
- Nishnath Kandarpa (kandarpa.n@northeastern.edu)
- Bhargav Pamidighantam (pamidighantam.b@northeastern.edu)
- Kaige Zheng (zheng.kaig@northeastern.edu)

------------------------------------------------------------
Overview:
This project implements a modular, extensible text-based adventure game engine in Java. In HW9, we have further enhanced the engine by incorporating advanced features, refining game mechanics, and improving overall scalability and performance. The game continues to leverage JSON configuration files to dynamically define game worlds, ensuring that new adventures can be created without altering the underlying code.

------------------------------------------------------------
Key Components (Updated):
- **Player**: Tracks health, score, inventory, and active status effects. Now supports dynamic health regeneration events.
- **Room**: Represents locations with exits and contains fixtures, items, puzzles, monsters, and now NPCs.
- **Item**: Collectible objects with properties such as weight, usage limits, and special effects. New item types include magic and utility items.
- **Fixture**: Non-movable objects for player interaction (e.g., crafting stations, clue providers) that now include interactive environmental elements.
- **Monster**: Adversaries that block paths and can be defeated using specific items. Enhanced AI behavior introduces new combat dynamics.
- **Puzzle**: Logic-based challenges that modify room configurations or grant access to new areas. Added support for timed puzzles and multi-step challenges.
- **NPC (New)**: Non-Player Characters that provide dialogue, quests, and hints, enriching the narrative experience.

------------------------------------------------------------
Design Evolution from HW8 to HW9:
Building upon the solid foundation of HW8, the following major enhancements have been made in HW9:

1. **Refactored and Modular Codebase:**
   - Improved separation of concerns with clearer module boundaries.
   - Enhanced abstraction layers to simplify future extensions.

2. **Enhanced JSON Integration:**
   - Updated JSON schemas to support additional game entities (e.g., NPCs, dynamic events).
   - Implemented improved error handling and validation for game configuration files.

3. **Advanced Game Mechanics:**
   - Introduced an NPC dialogue and quest system to add narrative depth.
   - Implemented dynamic, timed events that affect room states and puzzles.
   - Expanded the command parser to handle more complex player inputs.

4. **Performance and User Experience Improvements:**
   - Optimized the game loop and state management for smoother gameplay.
   - Refined room navigation logic to support dynamic exit conditions.
   - Enhanced inventory and item management with additional interactions.

5. **Enhanced Save/Restore Functionality:**
   - Upgraded persistence mechanisms to support more granular save options and session histories.

6. **Testing and Documentation:**
   - Expanded unit and integration tests to cover the new features.
   - Improved inline documentation and updated user guides for easier onboarding and maintenance.

------------------------------------------------------------
Usage Instructions:
1. Open the project in IntelliJ IDEA.
2. Ensure you are using Java 17 or higher.
3. Run the `GameEngineApp` class to start the game.
4. The game will automatically load the world from JSON files located in the `resources/` directory.
5. Use the following command syntax to play:
   - `go [direction]` (e.g., `go north`)
   - `pickup [item]`
   - `use [item] on [target]`
   - `solve puzzle [answer]`
   - `talk [NPC name]` to interact with non-player characters
   - `inventory`
   - `look`
   - `save` or `load` to persist or resume a session

------------------------------------------------------------
Implemented Design Scenarios (Updated):
- **Monster Loot Drops Key:** Defeating a monster drops an item needed to solve a downstream puzzle.
- **Dynamic Monster Encounter:** A monster blocks an exit and requires specific item usage to defeat.
- **Puzzle Unlocks Hidden Room:** Solving a puzzle dynamically updates room connections.
- **Fixture-based Crafting System:** Combining specific items at a fixture yields a new item required to progress.
- **NPC Quest System (New):** Interaction with NPCs introduces quests and dialogue options that influence game progression.
- **Timed Events (New):** Certain puzzles and room events are now time-sensitive, adding urgency to gameplay.

------------------------------------------------------------
Assumptions:
- The game world is fully defined via JSON configuration files before gameplay begins.
- A single player instance is active throughout the game session.
- Game entities such as items, puzzles, monsters, and NPC logic are predefined and not dynamically generated during gameplay.

------------------------------------------------------------
Acknowledgments:
- Diagrams created using PlantUML and draw.io.
- References: Visual Paradigm, GeeksForGeeks, Creately, Game Programming Patterns.
- Special thanks to the course staff for their ongoing support and guidance.

