Project Title: Data-Driven Adventure Game Engine (HW9 Final Update)
Course: CS5004 - Object Oriented Design
Team Members:
- Theresa Coleman (coleman.t@northeastern.edu)
- Zuzu Nyirakanyange (nyirakanyange.z@northeastern.edu)
- Nishnath Kandarpa (kandarpa.n@northeastern.edu)
- Bhargav Pamidighantam (pamidighantam.b@northeastern.edu)
- Kaige Zheng (zheng.kaig@northeastern.edu)

------------------------------------------------------------
Overview:
This project implements a modular, extensible text-based and GUI-based adventure game engine in Java.
In HW9, we introduced significant enhancements to game interaction, UI, and robustness.
The game supports both **terminal and Swing-based graphical interfaces** and continues to load game worlds from JSON configurations.

------------------------------------------------------------
Key Components (Updated):
- **Player**: Tracks health, score, inventory, and status effects. Supports timed events and quest progress.
- **Room**: Represents locations containing exits, items, puzzles, monsters, fixtures, and NPCs.
- **Item**: Enhanced item types include magical, combinable, and quest-triggering items.
- **Fixture**: Objects like crafting stations now offer visual interaction in Swing.
- **Monster**: Enhanced monster logic includes item-based defeat and loot drop triggers.
- **Puzzle**: Puzzles can be multi-step or timed, affecting room exits and item availability.
- **NPC**: Non-Player Characters provide quests, backstory, and aid progression.
- **ImageLoader (Updated)**: Now handles intelligent fallback logic and integrates with Swing UI.
- **SaveLoadManager (Updated)**: Updated for UI compatibility and robust file handling.

------------------------------------------------------------
Design Evolution from HW8 to HW9 (Finalized):
1. **GUI Integration via Swing**:
   - Introduced visual representation of rooms and items.
   - Implemented image loading with error fallback and blank rendering support.

2. **Robust Error Handling and Defaults**:
   - Fallback support for missing image assets and JSON files.
   - Added placeholder images to prevent runtime UI failures.

3. **Batch & Console Mode Enhancements**:
   - `TextController` and `BatchController` now support testable logic with stubbed game worlds.
   - Prevent unnecessary file access during unit tests.

4. **Test Suite Expansion**:
   - Coverage for failure modes: missing files, malformed inputs, image resolution.
   - Javadoc limit compliance (under 100 characters) and Checkstyle compatibility.

------------------------------------------------------------
Usage Instructions:
1. Open the project in IntelliJ IDEA.
2. Use **Java 17 or later**.
3. To launch the game:
   - Console mode: Run `GameEngineApp` with no args or `game.json`.
   - GUI mode: Run with `-graphics` flag.
     ```
     java -jar team-project-cs5004.jar game.json -graphics
     ```
4. Available Commands:
   - `go [direction]`
   - `pickup [item]`
   - `use [item] on [target]`
   - `solve puzzle [answer]`
   - `talk [NPC name]`
   - `inventory`, `look`
   - `save`, `load`, `quit`

------------------------------------------------------------
Implemented Design Scenarios (Updated):
- Monster blocks path, drops key on defeat.
- Puzzle reveals hidden room after solving.
- Crafting items at fixture yields progression tools.
- NPCs initiate quests affecting puzzles and monster interactions.
- Timed puzzle events increase gameplay urgency.
- GUI reflects state changes including image-based feedback.

------------------------------------------------------------
New in HW9 Final:
- Integrated `ImageLoader` into all views with robust fallbacks.
- Integrated `SaveLoadManager` for session persistence.
- Prevented game crashes from missing resource files.
- Separated test mode game world construction from file dependencies.

------------------------------------------------------------
Assumptions:
- JSON files define the world prior to game start.
- Game uses preloaded images for all visual assets (with fallback support).
- Single-player session model, no real-time multiplayer.

------------------------------------------------------------
Acknowledgments:
- Diagrams created using PlantUML and draw.io.
- Resources: Visual Paradigm, GeeksForGeeks, Game Programming Patterns.
- Thanks to CS5004 staff for continuous support.
