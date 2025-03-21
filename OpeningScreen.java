package gui;

import game.Game;
import game.Player;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpeningScreen extends JFrame {

    private String[] avatarOptions = {"Chicken", "Deer", "Fire", "Dog", "Bush"};
    private JComboBox<String> avatarComboBox;
    private JLabel avatarImageLabel;
    
    private Clip clickSoundClip; // SOUND
    private Clip backgroundMusicClip; // NEWCODE: Music clip
    private GameInterface gameInterface; // NEWCODE: Reference to GameInterface
    private Clip rickRollClip;

    public OpeningScreen() {
    	setLocationRelativeTo(null);
        setTitle("Welcome to the Card Game");
        setSize(400, 400); // Increased size for more avatars
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ColorScheme colorScheme = new ColorScheme();
        // Set background color of the content pane
        getContentPane().setBackground(colorScheme.getDarkGreen());

        JLabel welcomeLabel = new JLabel("Welcome to the Card Game!", SwingConstants.CENTER);
        // Add white border around the label
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        welcomeLabel.setForeground(Color.WHITE); 
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // FONT

        // Avatar selection panel
        JPanel avatarPanel = new JPanel();
        avatarPanel.setBackground(colorScheme.getDarkGreen());
        JLabel chooseAvatarLabel = new JLabel("Choose your avatar:");
        chooseAvatarLabel.setForeground(Color.WHITE); //WHITE AND CENTRALIZATION
        chooseAvatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        avatarPanel.add(chooseAvatarLabel);

        avatarComboBox = new JComboBox<>(avatarOptions);
        avatarPanel.add(avatarComboBox);

        // Avatar image display
        avatarImageLabel = new JLabel();
        avatarImageLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarImageLabel.setVerticalAlignment(JLabel.CENTER);
        avatarPanel.add(avatarImageLabel);

        // Container panel for centering
        Box containerBox = Box.createVerticalBox(); // Create a Box container for vertical alignment
        containerBox.setBackground(colorScheme.getDarkGreen());
        containerBox.add(Box.createVerticalGlue()); // Add vertical space at the top
        containerBox.add(welcomeLabel); // Add welcome label to container
        containerBox.add(Box.createVerticalStrut(20)); // Add vertical space between components
        containerBox.add(avatarPanel); // Add avatar panel to container
        containerBox.add(Box.createVerticalGlue()); // Add vertical space at the bottom

        add(containerBox, BorderLayout.CENTER); 

        // Update avatar image on selection
        avatarComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAvatar = (String) avatarComboBox.getSelectedItem();
                updateAvatarImage(selectedAvatar);
            }
        });

        // Initialize with the first avatar image
        updateAvatarImage(avatarOptions[0]);

        JButton startGameButton = new JButton("Start Game");

        // NEWCODE: PADDING
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(colorScheme.getDarkGreen());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around the button

        // Music control buttons
        JButton startMusicButton = new JButton("Start Music"); // NEWCODE: Start Music button
        JButton stopMusicButton = new JButton("Stop Music"); // NEWCODE: Stop Music button

        startMusicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Music button clicked"); // Debugging output
                playBackgroundMusic();
            }
        });

        stopMusicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopBackgroundMusic();
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound(); // NEWCODE: Play click sound
                System.out.println("Start Game button clicked");
                String selectedAvatar = (String) avatarComboBox.getSelectedItem(); // Ensure the selected avatar is passed
                String playerName = askUserName(); //Ask for the usr's name
                Player player1 = new Player(playerName, 1);
                Player player2 = new Player("Opponent", 2);
                Game game = new Game(player1, player2);
                gameInterface = new GameInterface(game, selectedAvatar); // Pass the selected avatar
                game.setGameInterface(gameInterface);

                setVisible(false); // Hide the opening screen
                gameInterface.setVisible(true); // Show the game interface
            }
        });

        buttonPanel.add(startMusicButton, gbc); // Add Start Music button
        buttonPanel.add(stopMusicButton, gbc); // Add Stop Music button
        buttonPanel.add(startGameButton, gbc);
        add(buttonPanel, BorderLayout.SOUTH); 

        setVisible(true);

        loadClickSound(); // SOUND
        loadBackgroundMusic(); // NEWCODE: Load background music
    }
    
    private String askUserName() {
        String name = JOptionPane.showInputDialog(this, "What is your name?", "Enter Name", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        } else {
            return "Player";
        }
    }

    public void setGameInterface(GameInterface gameInterface) { // NEWCODE: Setter for GameInterface
        this.gameInterface = gameInterface;
    }

    private void updateAvatarImage(String avatarName) {
        String imagePath = "Images/" + avatarName.toLowerCase().replace(" ", "") + ".png";
        ImageIcon avatarIcon = new ImageIcon(imagePath);
        avatarImageLabel.setIcon(avatarIcon);
    }

    // LOAD SOUND
    private void loadClickSound() {
        try {
            File soundFile = new File("sounds/click.wav"); // Ensure the file path is correct
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clickSoundClip = AudioSystem.getClip();
            clickSoundClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Ensure stack trace is printed
        }
    }

    // SOUND
    private void playClickSound() {
        if (clickSoundClip != null) {
            clickSoundClip.stop(); 
            clickSoundClip.setFramePosition(0); 
            clickSoundClip.start(); 
        }
    }

    // NEWCODE: Load background music
    private void loadBackgroundMusic() {
        try {
            File soundFile = new File("sounds/background.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Ensure stack trace is printed
        }
    }

    // NEWCODE: Play background music
    private void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.setFramePosition(0); // Ensure to start from the beginning
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            backgroundMusicClip.start();
        }
    }

    // NEWCODE: Stop background music
    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    public static void main(String[] args) {
        new OpeningScreen();
    }
}