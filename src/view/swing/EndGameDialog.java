package view.swing;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import controller.SwingController;
import model.Player;

/**
 * Dialog for displaying end game messages (win or lose).
 */
public class EndGameDialog extends JDialog {
  
  /**
   * Constructs a new EndGameDialog.
   * 
   * @param parent The parent frame
   * @param isWin true if player won, false if player lost
   * @param message The end game message
   */
  public EndGameDialog(JFrame parent, boolean isWin, String message) {
    this(parent, isWin, message, null);
  }
  
  /**
   * Constructs a new EndGameDialog with player statistics.
   * 
   * @param parent The parent frame
   * @param isWin true if player won, false if player lost
   * @param message The end game message
   * @param controller The game controller with access to player data
   */
  public EndGameDialog(JFrame parent, boolean isWin, String message, SwingController controller) {
    super(parent, isWin ? "Victory!" : "Game Over", true);
    
    // Set size and location
    setSize(450, 350);
    setLocationRelativeTo(parent);
    setResizable(false);
    
    // Create components
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Title with icon
    JPanel titlePanel = new JPanel(new BorderLayout(10, 10));
    
    // Load the appropriate image based on win/lose condition
    String iconPath = isWin ? "resources/images/ui/victory.png" : "resources/images/ui/defeat.png";
    File imageFile = new File(iconPath);
    if (imageFile.exists()) {
        ImageIcon icon = new ImageIcon(iconPath);
        // Scale the image to fit nicely in the dialog
        Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        titlePanel.add(iconLabel, BorderLayout.WEST);
    }
    
    JLabel titleLabel = new JLabel(
        isWin ? "Congratulations! You Won!" : "Game Over",
        SwingConstants.CENTER
    );
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    
    // Set color based on win/lose
    titleLabel.setForeground(isWin ? new Color(0, 128, 0) : new Color(192, 0, 0));
    
    titlePanel.add(titleLabel, BorderLayout.CENTER);
    
    // Message
    JTextArea messageArea = new JTextArea(message);
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
    messageArea.setBackground(panel.getBackground());
    messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Stats panel
    JPanel statsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    statsPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
    
    // Default values for statistics
    String itemCount = "0";
    String health = "Unknown";
    String roomNumber = "Unknown";
    
    // Add statistics labels
    JLabel healthLabel = new JLabel("Health:");
    JLabel healthValue = new JLabel(health);
    JLabel roomsLabel = new JLabel("Room:");
    JLabel roomsValue = new JLabel(roomNumber);
    JLabel itemsLabel = new JLabel("Items:");
    JLabel itemsValue = new JLabel(itemCount);
    
    statsPanel.add(healthLabel);
    statsPanel.add(healthValue);
    statsPanel.add(roomsLabel);
    statsPanel.add(roomsValue);
    statsPanel.add(itemsLabel);
    statsPanel.add(itemsValue);
    
    // Close button
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    
    // Add components
    panel.add(titlePanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
    
    JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
    bottomPanel.add(statsPanel, BorderLayout.NORTH);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(closeButton);
    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    panel.add(bottomPanel, BorderLayout.SOUTH);
    
    // Add main panel to dialog
    add(panel);
  }
} 