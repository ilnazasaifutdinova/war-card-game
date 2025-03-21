package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ClosingScreen extends JFrame {
    
    public ClosingScreen(String message) {
        // Set the title of the JFrame
        setTitle("Game Over");
        
        // Set the size of the JFrame
        setSize(1400, 800);
        
        // Set BorderLayout as the layout manager for the JFrame
        setLayout(new BorderLayout());
        
        // Set default close operation to exit the application when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel to display the message, centered horizontally
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        // Set font of the message label to Arial, bold, size 24
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // Add the message label to the center of the JFrame
        add(messageLabel, BorderLayout.CENTER);

        // Create a JButton labeled "Close"
        JButton closeButton = new JButton("Close");
        // Add an ActionListener to handle button clicks
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application when the button is clicked
                System.exit(0);
            }
        });

        // Create a JPanel to hold the closeButton
        JPanel buttonPanel = new JPanel();
        // Add the closeButton to the buttonPanel
        buttonPanel.add(closeButton);
        // Add the buttonPanel to the bottom (South) of the JFrame
        add(buttonPanel, BorderLayout.SOUTH);

        // Center the JFrame on the screen
        setLocationRelativeTo(null);
        
        // Make the JFrame visible
        setVisible(true);
    }
}
