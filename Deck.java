package cards;

import game.Player;

import java.util.*;
import java.util.LinkedList;

// The Deck class represents a deck of playing cards.
public class Deck {
    // The deck is a list of cards.
    private ArrayList<Card> cards;

    // The constructor initializes the deck with all 52 cards and shuffles them.
    public Deck() {
        this.cards = new ArrayList<>();
        String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        Map<String, Integer> rankValues = new HashMap<>();
        for (int i = 0; i < ranks.length; i++) {
            rankValues.put(ranks[i], i);
        }

        // For each suit and each rank, a new card is created and added to the deck.
        for (String rank : ranks) {
             for (String suit : suits){
                cards.add(new Card(suit, rank, rankValues.get(rank)));
            }
        }
        // The deck is shuffled.
        shuffle();
    }

    // This method shuffles the deck.
    private void shuffle() {
        Collections.shuffle(this.cards);
    }

    // This method deals the cards to two players.
    public void draw(Player player1, Player player2) {
        // The deck is split in half.
        int halfSize = this.cards.size() / 2;
        // The first half of the deck is given to player 1.
        player1.setHand(new LinkedList<>(new ArrayList<>(this.cards.subList(0, halfSize))));
        // The second half of the deck is given to player 2.
        player2.setHand(new LinkedList<>(new ArrayList<>(this.cards.subList(halfSize, cards.size()))));
    }
}