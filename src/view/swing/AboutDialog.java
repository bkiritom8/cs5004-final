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
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Dialog for displaying information about the game.
 */
public class AboutDialog extends JDialog {
  
  /**
   * Constructs a new AboutDialog.
   * 
   * @param parent The parent frame
   */
  public AboutDialog(JFrame parent) {
    super(parent, "Adventure Game - Team Scalpel", true);
    
    // Set size and location
    setSize(450, 350);
    setLocationRelativeTo(parent);
    setResizable(false);
    
    // Create components
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Logo/title panel
    JPanel titlePanel = new JPanel(new BorderLayout());
    
    // Load the logo image from resources
    String logoPath = "resources/images/ui/logo.png";
    File logoFile = new File(logoPath);
    if (logoFile.exists()) {
        ImageIcon logo = new ImageIcon(logoPath);
        // Scale the image to fit nicely in the dialog
        Image scaledImage = logo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        titlePanel.add(logoLabel, BorderLayout.WEST);
    }
    
    JLabel titleLabel = new JLabel("Adventure Game Engine", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titlePanel.add(titleLabel, BorderLayout.CENTER);
    
    // Description
    JTextArea descriptionArea = new JTextArea(
        "A text and graphical adventure game engine.\n\n" +
        "Navigate through rooms, solve puzzles, defeat monsters, and collect items " +
        "in this classic adventure game style.\n\n" +
        "Created for CS5004 - Object Oriented Design\n\n" +
        "© 2023 Adventure Game Team"
    );
    descriptionArea.setEditable(false);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setBackground(panel.getBackground());
    descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
    descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Version info
    JLabel versionLabel = new JLabel("Version 1.0", SwingConstants.CENTER);
    versionLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
    
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
    panel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(versionLabel, BorderLayout.NORTH);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(closeButton);
    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    panel.add(bottomPanel, BorderLayout.SOUTH);
    
    // Add main panel to dialog
    add(panel);
  }
} 