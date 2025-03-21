package gui;

import cards.Card;
import game.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameInterface extends JFrame {
    private JLabel player1CardLabel;
    private JLabel player2CardLabel;
    private JLabel winnerLabel;
    private Game game;
    private JLabel player1CardCount;
    private JLabel player2CardCount;
    
    // Sound clips for card draw and button click
    private Clip drawCardClip;
    private Clip clickSoundClip;
    
    // Background music clip
    private Clip backgroundMusicClip;
    
    private Clip rickRollClip;
    

    // Avatar labels
    private JLabel player1AvatarLabel;
    private JLabel player2AvatarLabel;
    
    //Player's name
    private String player1Name; 
    
    // NEW LABEL FOR THE WAR IMAGES
    private JLabel player1WarCard1Label;
    private JLabel player1WarCard2Label;
    private JLabel player1WarCardLabel;
    
    private JLabel player2WarCard1Label;
    private JLabel player2WarCard2Label;
    private JLabel player2WarCardLabel;
    
    private JPanel leftContainerPanel;
    private JPanel rightContainerPanel;
    
    private JLabel WarStartedLabel;
    private JButton ShowWarCardsButton;
    
    private JLabel WarWinnerLabel;
    private JButton WarOkButton;
    JButton layOutCardButton = new JButton("Draw Card");
    // Panels for war cards
    private JPanel player1WarPanel;
    private JPanel player2WarPanel;
	

    public GameInterface(Game game, String player1Avatar) {
    	setLocationRelativeTo(null);
        this.game = game;
        
        setLayout(new BorderLayout());
        this.player1Name = game.getPlayer1().getName(); // Get the player's name from the game
        ColorScheme colorScheme = new ColorScheme();
        Sound sound = new Sound();
        JButton startGameButton = new JButton("New Game");
        JButton saveButton = new JButton("Save Game");
        JButton loadButton = new JButton("Load Save");
        JButton aboutButton = new JButton("About Game");
        JButton cheatMenuButton = new JButton("Cheat Menu");
//        JButton adjustColorButton = new JButton("Adjust Custom Color");
        JButton returnToOpeningScreenButton = createReturnToOpeningScreenButton(); // Return button to opening screen
        
        player1CardCount = new JLabel();
        player2CardCount = new JLabel();

        player1CardLabel = new JLabel();
        player2CardLabel = new JLabel();
        
        // NEW LABELS FOR THE WAR IMAGES
        player1WarCard1Label = new JLabel();
        player1WarCard2Label = new JLabel();
        player1WarCardLabel = new JLabel();
        
        player2WarCard1Label = new JLabel();
        player2WarCard2Label = new JLabel();
        player2WarCardLabel = new JLabel();

        winnerLabel = new JLabel();
/*        
        adjustColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ColorAdjuster(colorScheme);
            }
        });
*/        
        WarWinnerLabel = new JLabel();

        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                JOptionPane.showMessageDialog(null, "Game progress is reset");
                game.restartGame();
                updateCardCounts();
                displayPlayerCards();
                winnerLabel.setText("");  // Clear winner label if needed
                sound.playClickSound();
                ShowWarCardsButton.setVisible(false);
                layOutCardButton.setVisible(true);
                
            }
        });

        //Added show/hide WarStartedLabel()
        layOutCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.playGame();
                updateCardCounts();
                displayPlayerCards();
                sound.playDrawCardSound(); // Play sound when drawing a card
                
                if (game.isWar()) {
                	showWarStartedLabel();
                } /*else {
                	hideWarStartedLabel();
                	
                }*/
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                JOptionPane.showMessageDialog(null, "Game saved successfully");
                //System.out.println("Save button clicked");
                game.saveGame("game_sessions.ser", player1Name);
                sound.playClickSound();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                JOptionPane.showMessageDialog(null, "Game save loaded successfully");
                System.out.println("Load button clicked");
                List<CheckPoint> games = game.loadGames("game_sessions.ser");
                if (games != null && !games.isEmpty()) {
                    CheckPoint checkpoint = games.get(0);  // Loading the first saved game as an example
                    game.setPlayer1(checkpoint.getPlayer1());
                    game.setPlayer2(checkpoint.getPlayer2());
                    player1Name = checkpoint.getName(); // Load the saved name
                    updateCardCounts();
                    displayPlayerCards();
                    sound.playClickSound();
                }
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound.playClickSound();
                JOptionPane.showMessageDialog(null,
                		"Rules of the Card Game War:\n\n" +
                	            "1. The deck is divided evenly among the players.\n" +
                	            "2. Each player reveals the top card of their deck.\n" +
                	            "3. The player with the higher card wins both cards and places them at the bottom of their deck.\n" +
                	            "4. If the cards are of equal value, a 'war' is triggered:\n" +
                	            "   - Each player places three cards face down and one card face up.\n" +
                	            "   - The player with the higher face-up card wins all the cards.\n" +
                	            "   - If the face-up cards are again equal, the process repeats.\n" +
                	            "5. The game continues until one player has all the cards.\n" +
                	            "6. The player with all the cards wins the game.\n" + 
                	           "------------------------------------------------------------------------------------------" +
                        		"\nCreated By: \n\nIvan Rudnev :)\nFilips Voronevics :)\nArtem Ermolaev :)\nEce Esmer :)\nNeilya Burkitbayeva :)\nIlnaza Saifutdinova :)",
                        "About Game",
                        JOptionPane.INFORMATION_MESSAGE);
                sound.playClickSound();
            }
        });
        
        cheatMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.playClickSound();
				System.out.println("Cheat Menu button clicked");
				sound.playRickRollMusic();
				showGifDialog();
				sound.stopRickRollMusic();
				
			}
        	
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(colorScheme.getCustomColor()); // Set background color
        buttonPanel.add(startGameButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(cheatMenuButton);
//        buttonPanel.add(adjustColorButton);
        buttonPanel.add(returnToOpeningScreenButton); // Add return button to the panel
        //buttonPanel.add(WarWinnerLabel);
        add(buttonPanel, BorderLayout.NORTH);

        JPanel cardCountPanel = new JPanel();
        cardCountPanel.setBackground(colorScheme.getCustomColor()); // Set background color
        cardCountPanel.add(player1CardCount);
        cardCountPanel.add(player2CardCount);
        
        add(cardCountPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexibility
        centerPanel.setBackground(colorScheme.getCustomColor()); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around the button
        
        WarStartedLabel = new JLabel("The War started");
        WarStartedLabel.setForeground(Color.WHITE); // Set the text color to white
        WarStartedLabel.setVisible(false); //Initially hidden
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(WarStartedLabel, gbc);

        ShowWarCardsButton = new JButton("Show war cards");
        ShowWarCardsButton.setVisible(false); //Initially hidden
        
        ShowWarCardsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchWarCardPanel(); //Show/hide war cards after clicking on 'WarOkBotton'
				layOutCardButton.setVisible(false);
				hideWarStartedLabel(); 
			}
        }); 
        
        WarWinnerLabel.setForeground(Color.WHITE); // Set text color to white
        WarWinnerLabel.setVisible(false); // Initially hide the label
        
        WarOkButton = new JButton("OK");
        WarOkButton.setVisible(false); // Initially hide the button
        
        WarOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideWarWinnerLabel();
                switchWarCardPanel(); // Hide war cards after clicking on 'WarOkButton'
				layOutCardButton.setVisible(true);
            }
        });
        
        gbc.gridy = 1;
        centerPanel.add(ShowWarCardsButton, gbc);

        gbc.gridy = 2;
        centerPanel.add(layOutCardButton, gbc);
        
        gbc.gridy = 3;
        centerPanel.add(WarWinnerLabel, gbc);

        gbc.gridy = 4;
        centerPanel.add(WarOkButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
        
        leftContainerPanel = new JPanel();
        leftContainerPanel.setLayout(new BoxLayout(leftContainerPanel, BoxLayout.Y_AXIS));
        leftContainerPanel.setBackground(colorScheme.getCustomColor());

        JPanel player1Panel = new JPanel();
        player1Panel.setBackground(colorScheme.getCustomColor()); // Set background color
        player1AvatarLabel = new JLabel(new ImageIcon("Images/" + player1Avatar.toLowerCase().replace(" ", "") + ".png"));
        player1Panel.add(player1AvatarLabel);
        player1Panel.add(player1CardLabel);
        //add(player1Panel, BorderLayout.WEST);
        leftContainerPanel.add(player1Panel);
        
        //ILNAZA; CARDS FOR WAR FOR PLAYER_1
        player1WarPanel = new JPanel();
        player1WarPanel.setBackground(colorScheme.getCustomColor());
        
        player1WarCard1Label = new JLabel(new ImageIcon("Images/card_back.png"));
        //player1WarPanel.add(player1WarCard1Label);
        player1WarCard2Label = new JLabel(new ImageIcon("Images/card_back.png"));
        //player1WarPanel.add(player1WarCard2Label);
        player1WarCardLabel = new JLabel(new ImageIcon("Images/Ace_of_Hearts.png"));
        //player1WarPanel.add(player1WarCardLabel);
        
        player1WarPanel.add(player1WarCard1Label);
        player1WarPanel.add(player1WarCard2Label);
        player1WarPanel.add(player1WarCardLabel);
        leftContainerPanel.add(player1WarPanel);
        //leftContainerPanel.add(player1Panel);
        //false
        player1WarPanel.setVisible(false); //Set invisible at start

        // Add the container panel to the left side of the main layout
        add(leftContainerPanel, BorderLayout.WEST);
        
        rightContainerPanel = new JPanel();
        rightContainerPanel.setLayout(new BoxLayout(rightContainerPanel, BoxLayout.Y_AXIS));
        rightContainerPanel.setBackground(colorScheme.getCustomColor());

        JPanel player2Panel = new JPanel();
        player2Panel.setBackground(colorScheme.getCustomColor()); // Set background color
        player2AvatarLabel = new JLabel(new ImageIcon("Images/bot.png")); // Default avatar for Player 2
        player2Panel.add(player2AvatarLabel);
        player2Panel.add(player2CardLabel);
        //add(player2Panel, BorderLayout.EAST);
        rightContainerPanel.add(player2Panel);
        
        
        //ILNAZA; CARDS FOR WAR FOR PLAYER_2
        player2WarPanel = new JPanel();
        player2WarPanel.setBackground(colorScheme.getCustomColor());
        
        player2WarCardLabel = new JLabel(new ImageIcon("Images/Ace_of_Hearts.png"));
        //player2WarPanel.add(player2WarCardLabel);
        player2WarCard1Label = new JLabel(new ImageIcon("Images/card_back.png"));
        //player2WarPanel.add(player2WarCard1Label);
        player2WarCard2Label = new JLabel(new ImageIcon("Images/card_back.png"));
        //player2WarPanel.add(player2WarCard2Label);
        
        player2WarPanel.add(player2WarCardLabel);
        player2WarPanel.add(player2WarCard1Label);
        player2WarPanel.add(player2WarCard2Label);
        rightContainerPanel.add(player2WarPanel);
        //false
        player2WarPanel.setVisible(false); //Set invisible at start
        
        
        // Add the container panel to the left side of the main layout
        add(rightContainerPanel, BorderLayout.EAST);
        
        //=======================
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        //=======================
      
        setSize(1400, 800);
        setTitle("Card War Game");
        getContentPane().setBackground(colorScheme.getCustomColor()); // Set background color of the frame
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

	//Method to show info about war start
    public void showWarStartedLabel() {
        WarStartedLabel.setVisible(true);
        ShowWarCardsButton.setVisible(true);
        this.layOutCardButton.setVisible(false);
        
        WarWinnerLabel.setVisible(false);
        WarOkButton.setVisible(false);
    }
    
    public void hideWarStartedLabel() {
        WarStartedLabel.setVisible(false);
        ShowWarCardsButton.setVisible(false);
        
        WarWinnerLabel.setVisible(true);
        WarOkButton.setVisible(true);
    }
    
    public void showWarWinnerLabel() {
        WarWinnerLabel.setVisible(true);
        WarOkButton.setVisible(true);
    }
    
    public void setWarWinner(String winner) {
        WarWinnerLabel.setText(winner);
        WarWinnerLabel.setVisible(true);
        //WarOkButton.setVisible(true);
    }
    
    public void hideWarWinnerLabel() {
    	WarWinnerLabel.setVisible(false);
        WarOkButton.setVisible(false);
    }
    
    public void switchWarCardPanel() {
    	boolean isVisible = player1WarPanel.isVisible() && player2WarPanel.isVisible();
        player1WarPanel.setVisible(!isVisible);
        player2WarPanel.setVisible(!isVisible);
	}

    public void updateCardCounts() {
        Font bigFont = new Font("Arial", Font.BOLD, 16);
        
        String player1CountText = player1Name + "'s Cards: " + game.getPlayer1().getHandSize();
        player1CardCount.setText(player1CountText);
        player1CardCount.setFont(bigFont);
        String player2CountText = "| Opponent's Cards: " + game.getPlayer2().getHandSize();
        player2CardCount.setText(player2CountText);
        player2CardCount.setFont(bigFont);
    }

    private void displayPlayerCards() {
        Card player1Card = game.getPlayer1().getCard();
        if (player1Card != null && player1Card.getImage() != null) {
            player1CardLabel.setIcon(new ImageIcon(player1Card.getImage()));
        } else {
            player1CardLabel.setIcon(null);  // Clear icon if no card or image is null
        }

        Card player2Card = game.getPlayer2().getCard();
        if (player2Card != null && player2Card.getImage() != null) {
            player2CardLabel.setIcon(new ImageIcon(player2Card.getImage()));
        } else {
            player2CardLabel.setIcon(null);  // Clear icon if no card or image is null
        }
        
        //WAR CARD GAME FOR PLAYER_1
        Card player1WarCard = game.getPlayer1().getWarCard();  // Get third war card
        if (player1WarCard != null && player1WarCard.getImage() != null) {
            player1WarCardLabel.setIcon(new ImageIcon(player1WarCard.getImage()));
        } else {
            player1WarCardLabel.setIcon(null);  // Clear icon if no card or image is null
        }
        
      //WAR CARD GAME FOR PLAYER_2
        Card player2WarCard = game.getPlayer2().getWarCard();  // Example method to get war card
        if (player2WarCard != null && player2WarCard.getImage() != null) {
            player2WarCardLabel.setIcon(new ImageIcon(player2WarCard.getImage()));
        } else {
            player2WarCardLabel.setIcon(null);  // Clear icon if no card or image is null
        }

    }

    private JButton createReturnToOpeningScreenButton() {
        JButton returnToOpeningScreenButton = new JButton("Return to Opening Screen");
        returnToOpeningScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToOpeningScreen();
            }
        });
        return returnToOpeningScreenButton;
    }

    private void returnToOpeningScreen() {
        Sound sound = new Sound();
        sound.playClickSound();
        this.setVisible(false); // Hide the game interface
        OpeningScreen openingScreen = new OpeningScreen();
        openingScreen.setVisible(true); // Show the opening screen
        sound.playClickSound();
    }
    
    public void setWinner(String winner) {
        if (winner.equals(player1Name)) {
            new ClosingScreen("If you won or lost, you have wasted your time clicking a button. \n" + "Go outside. \n" + "Read a book. \n" + "Meet the love of your life...\n");
        } else {
            new ClosingScreen("If you won or lost, you have wasted your time clicking a button. \n" + "Go outside. \n" + "Read a book. \n" + "Meet the love of your life...\n");
        }
    }
    
    private void showGifDialog() {
        Sound sound = new Sound();
        // Create a JDialog to display the GIF
        JDialog gifDialog = new JDialog(this, "RickRoll", true);
        gifDialog.setLayout(new BorderLayout());
        
        // Add the GIF to the dialog
        JLabel gifLabel = new JLabel(new ImageIcon("Sounds/download.gif"));
        gifDialog.add(gifLabel, BorderLayout.CENTER);
        
        // Add a close button to the dialog
        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gifDialog.dispose(); // Close the dialog
                sound.stopRickRollMusic();
            }
        });
        gifDialog.add(closeButton, BorderLayout.SOUTH);
        
        
        gifDialog.setSize(500, 500); // Set the size of the dialog
        gifDialog.setLocationRelativeTo(this); // Center the dialog relative to the main frame
        gifDialog.setVisible(true); // Show the dialog
    }

    public static void main(String[] args) {
        Player player1 = new Player("Player", 1);
        Player player2 = new Player("Player 2", 2);
        Game game = new Game(player1, player2);
        GameInterface gameInterface = new GameInterface(game, "defaultAvatar");
        game.setGameInterface(gameInterface);
    }
}