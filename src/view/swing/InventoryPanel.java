package view.swing;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;

import controller.SwingController;
import model.Item;

/**
 * The InventoryPanel class provides a UI panel for interacting with the inventory.
 */
public class InventoryPanel extends JPanel {
  JList<String> inventoryList;
  DefaultListModel<String> inventoryListModel;
  SwingController controller;

  /**
   * InventoryPanel class constructor using SwingController for handling GUI events.
   *
   * @param controller {@link SwingController} used to manage user interactions
   */
  public InventoryPanel(SwingController controller) {
    this.controller = controller;

    // Set layout and border
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Inventory"));

    // Initialize list model and list
    inventoryListModel = new DefaultListModel<>();
    inventoryList = new JList<>(inventoryListModel);

    // Add components
    add(new JScrollPane(inventoryList), BorderLayout.CENTER);

    // Add buttons
    JPanel buttonPanel = new JPanel();
    JButton useButton = new JButton("Use");
    JButton dropButton = new JButton("Drop");
    JButton examineButton = new JButton("Examine");

    useButton.addActionListener(e -> useSelectedItem());
    dropButton.addActionListener(e -> dropSelectedItem());
    examineButton.addActionListener(e -> examineSelectedItem());

    buttonPanel.add(useButton);
    buttonPanel.add(dropButton);
    buttonPanel.add(examineButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Integrates the player's selected items (and remaining uses) to be updated with the
   * inventory display.
   *
   * @param items List of items to display in the inventory
   */
  public void updateInventory(List<Item> items) {
    inventoryListModel.clear();
    for (Item item : items) {
      inventoryListModel.addElement(item.getName()
              + " ("
              + item.getUsesRemaining()
              + " uses)");
    }
  }

  /**
   * Integrates the player's selection with the use command.
   */
  public void useSelectedItem() {
    String selected = inventoryList.getSelectedValue();

    if (selected != null) {
      // Extract item name from display string
      String itemName = selected.split(" ")[0];
      controller.executeCommand("use " + itemName);
    }
  }

  /**
   * Integrates the player's selection with the drop command.
   */
  public void dropSelectedItem() {
    String selected = inventoryList.getSelectedValue();

    if (selected != null) {
      String itemName = selected.split(" ")[0];
      controller.executeCommand("drop " + itemName);
    }
  }

  /**
   * Integrates the player's selection with the examine command.
   */
  public void examineSelectedItem() {
    String selected = inventoryList.getSelectedValue();

    if (selected != null) {
      String itemName = selected.split(" ")[0];
      controller.executeCommand("examine " + itemName);
    }
  }
}
