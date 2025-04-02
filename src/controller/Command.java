package controller;

/**
 * Interface for the Command pattern.
 * All game commands implement this interface.
 */
public interface Command {

  /**
   * Executes the command.
   */
  void execute();
}