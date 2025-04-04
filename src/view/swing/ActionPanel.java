package view.swing;

import java.awt.*;

import javax.swing.*;

import controller.SwingController;

/**
 * The ActionPanel class provides a UI panel for performing actions within the game.
 */
public class ActionPanel extends JPanel {
  SwingController controller;

  /**
   * ActionPanel class constructor using SwingController for handling GUI events.
   *
   * @param controller {@link SwingController} used to manage user interactions
   */
  public ActionPanel(SwingController controller) {
    this.controller = controller;

    // Set layout and border
    setLayout(new FlowLayout());
    setBorder(BorderFactory.createTitledBorder("Actions"));

    // Create buttons
    JButton lookButton = new JButton("Look");
    JButton takeButton = new JButton("Take");
    JButton attackButton = new JButton("Attack");
    JButton answerButton = new JButton("Answer");

    // Add action listeners
    lookButton.addActionListener(e -> controller.executeCommand("look"));
    takeButton.addActionListener(e -> showTakeDialog());
    attackButton.addActionListener(e -> controller.executeCommand("attack"));
    answerButton.addActionListener(e -> showAnswerDialog());

    // Add buttons to panel
    add(lookButton);
    add(takeButton);
    add(attackButton);
    add(answerButton);
  }

  /**
   * Displays a dialog box of items where the player can choose from.
   */
  public void showTakeDialog() {
    // Display dialog to select item to take
    String itemName = JOptionPane.showInputDialog(this, "What would you like to take?");

    if (itemName != null && !itemName.isEmpty()) {
      controller.executeCommand("take " + itemName);
    }
  }

  /**
   * Displays a dialog box answer where the player can enter their puzzle answer.
   */
  public void showAnswerDialog() {
    // Display dialog to input puzzle answer
    String answer = JOptionPane.showInputDialog(this, "Enter your answer:");

    if (answer != null && !answer.isEmpty()) {
      controller.executeCommand("answer " + answer);
    }
  }
}
