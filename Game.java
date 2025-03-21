package game;

import cards.Card;
import game.Game;
import cards.Deck;
import gui.GameInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// The Game class represents a game being played.
public class Game implements Serializable {
    // Each game has two players and a deck of cards.
    private Player player1;
    private Player player2;
    private Deck deck;
    // The savedGames list stores the saved states of the game.
    private List<CheckPoint> savedGames = new ArrayList<>();
    private transient GameInterface gameInterface;
	private boolean isWar; //New Flag for War initialisation
	

    // The constructor initialises the game with two players and deals the cards.
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.deck = new Deck();
        dealCards();
        this.isWar = false; //Initialise War Flag
    }

    // This method deals the cards to the players.
    public void dealCards() {
        this.deck.draw(this.player1, this.player2);
    }

    // These methods are getters and setters for the players.
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    // This method returns the list of saved games.
    public List<CheckPoint> getSavedGames() {
        return this.savedGames;
    }
    
    public boolean isWar() {
        return isWar;
    }

    public void setWar(boolean isWar) {
        this.isWar = isWar;
    }

    // This method plays a round of the game.
    public void playGame() { 	
    	
        // The game is played only if both players have cards.
        if (this.player1.hasCards() && this.player2.hasCards()) {
            // The player with the higher card takes both cards.
            if (this.player1.getCard().compareTo(this.player2.getCard()) > 0) {
                this.player1.addCard(this.player2.playCard());
                this.player1.addCard(this.player1.playCard());
                gameInterface.setWarWinner(player1.getName() + " won the previous round!"); // Set winner label for a normal round
            } else if (this.player1.getCard().compareTo(this.player2.getCard()) < 0) {
                this.player2.addCard(this.player1.playCard());
                this.player2.addCard(this.player2.playCard());
                gameInterface.setWarWinner("Opponent won the previous round!"); // Set winner label for a normal round
            }
            
            // If both players have the same card, they keep playing until one wins.
            else {
            	this.isWar = true;
            	
                ArrayList<Card> table = new ArrayList<>();
                table.add(this.player1.playCard());
                table.add(this.player2.playCard());
                
                // Display war initiation
                gameInterface.showWarStartedLabel();
                
                while (true) {
                    table.add(this.player1.playCard());
                    table.add(this.player2.playCard());
                    
                    if (this.player1.getCard().compareTo(this.player2.getCard()) == 0) {
                        table.add(this.player1.playCard());
                        table.add(this.player2.playCard());
                    } else if (this.player1.getCard().compareTo(this.player2.getCard()) > 0) {
                        table.add(this.player1.playCard());
                        table.add(this.player2.playCard());
                        this.player1.addAllCards(table);
                        gameInterface.setWarWinner(player1.getName() + " won the war!");
                        break;
                    } else {
                        table.add(this.player1.playCard());
                        table.add(this.player2.playCard());
                        this.player2.addAllCards(table);
                        gameInterface.setWarWinner("Opponent won the war!");
                        break;
                    } 

                }
                this.isWar = false;  //War is concluded
                
            }
            
        }
        if (!this.player1.hasCards()) {
            gameInterface.setWinner("Player 2");
        } else if (!this.player2.hasCards()) {
            gameInterface.setWinner("Player 1");
        }

    }

    // This method saves the current state of the game to a file.
    public void saveGame(String filePath, String name) {
        try {
            // The current state of the game is added to the list.
            savedGames.add(new CheckPoint(player1, player2, name));

            // The updated list is written back to the file.
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(savedGames);
                System.out.println("Game saved successfully.");
            }
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    // This method loads the saved games from a file.
    public List<CheckPoint> loadGames(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            savedGames = (List<CheckPoint>) ois.readObject();
            System.out.println("Games loaded successfully.");
            return savedGames;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load games: " + e.getMessage());
            return null;
        }
    }

    // This method selects a saved game from the list.
    public CheckPoint selectGame(int index) {
        // The game is selected only if the index is valid.
        if (index >= 0 && index < this.savedGames.size()) {
            CheckPoint checkpoint = this.savedGames.get(index);
            this.player1 = checkpoint.getPlayer1();
            this.player2 = checkpoint.getPlayer2();
            System.out.println("Game selected: " + checkpoint.getName());
            return checkpoint;
        } else {
            System.err.println("Invalid game index: " + index);
            return null;
        }
    }

    public void deleteCheckpoint(int index) {
        if (index >= 0 && index < this.savedGames.size()) {
            this.savedGames.remove(index);
            System.out.println("Checkpoint " + index + " deleted successfully.");
        } else {
            System.err.println("Invalid checkpoint index: " + index);
        }
    }
    
    public void restartGame() {
        // Reset player hands
        this.player1.reset();  // Assuming reset() method clears player's cards
        this.player2.reset();

        // Re-deal cards
        this.deck = new Deck();
        dealCards();

        // Inform GUI or perform any other necessary actions
        if (gameInterface != null) {
//            gameInterface.setWinner("");  // Clear any previous winner message
        }

        
    }
}
