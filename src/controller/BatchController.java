package controller;

import model.GameWorld;
import util.CommandParser;
import util.CommandParser.ParsedCommand;
import util.FileIoManager;
import view.GameView;

import java.util.List;

public class BatchController extends GameController {
  private final String batchFilePath;
  private final GameView view;

  public BatchController(GameWorld world, String batchFilePath, GameView view) {
    super(world);
    this.batchFilePath = batchFilePath;
    this.view = view;
  }

  public void run() {
    List<String> commands = FileIoManager.readFile(batchFilePath);
    for (String line : commands) {
      ParsedCommand parsed = CommandParser.parse(line);
      Command command = CommandFactory.create(parsed.command, parsed.args);

      if (command != null) {
        Object world = new Object();
        command.execute(world, view);
      } else {
        view.showMessage("Invalid command in batch file: " + line);
      }
    }
  }

  private class Command {
    public void execute(Object world, GameView view) {

    }
  }

  private class CommandFactory {
    public static Command create(String command, List<String> args) {
      return null;
    }
  }
}


