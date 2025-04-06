package view.swing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    // You could add an image here if available
    // String iconPath = isWin ? "resources/images/ui/victory.png" : "resources/images/ui/defeat.png";
    // JLabel iconLabel = new JLabel(new ImageIcon(iconPath));
    // titlePanel.add(iconLabel, BorderLayout.WEST);
    
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
    
    // Stats (could be added later)
    JPanel statsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    statsPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
    
    // Example stats (replace with actual game stats)
    JLabel timeLabel = new JLabel("Time played:");
    JLabel timeValue = new JLabel("00:00:00");
    JLabel roomsLabel = new JLabel("Rooms visited:");
    JLabel roomsValue = new JLabel("0");
    JLabel itemsLabel = new JLabel("Items collected:");
    JLabel itemsValue = new JLabel("0");
    
    statsPanel.add(timeLabel);
    statsPanel.add(timeValue);
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