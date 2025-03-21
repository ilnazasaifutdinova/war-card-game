package game;

import cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

// The Player class represents a player in the game.
public class Player implements Serializable {
    // Each player has a name, an id, and a hand of cards.
    private String name;
    private int id;
    private Queue<Card> hand;

    // The constructor initializes the player with a name and id, and an empty hand.
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.hand = new LinkedList<>();
    }

    // This method sets the hand of the player.
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

    // This method returns the name of the player.
    public String getName() {
        return this.name;
    }

    // This method returns the id of the player.
    public int getId() {
        return this.id;
    }

    public int getHandSize() {
        return this.hand.size();
    }

    // This method returns the top card of the player's hand without removing it.
    public Card getCard(){
        return this.hand.peek();
    }

    // This method adds a card to the player's hand.
    public void addCard(Card card) {
        this.hand.add(card);
    }

    // This method adds multiple cards to the player's hand.
    public void addAllCards(ArrayList<Card> cards) {
        this.hand.addAll(cards);
    }

    // This method removes and returns the top card of the player's hand.
    public Card playCard() {
        return this.hand.poll();
    }

    // This method checks if the player has any cards left in their hand.
    public boolean hasCards() {
        return !this.hand.isEmpty();
    }
    
    // Method to retrieve the third card from the player's hand
    public Card getWarCard() {
        // Convert the queue to a list
        List<Card> handList = new ArrayList<>(hand);

        // Check if there are at least three cards in hand
        if (handList.size() >= 3) {
            return handList.get(2); // Return the third card (index 2)
        } else {
            return null; // Return null if there are not enough cards in hand
        }
    }
    
    public void reset() {
        this.hand.clear();  // Clear the player's hand
        // Reset any other attributes if needed
    }

}