package cards;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

// The Card class represents a playing card.
public class Card implements Comparable<Card>, Serializable {
    // Each card has a suit and a rank.
    private final String suit;
    private final String rank;
    private final String imagePath;
    private transient Image image;
    private final int order;

    // The constructor initializes the card with a suit, rank, and order.
    public Card(String suit, String rank, int order) {
        this.suit = suit;
        this.rank = rank;
        this.order = order;
        this.imagePath = "Images/" + rank + "_of_" + suit + ".png";
    }

    // This method loads the image from the file system.
    private Image loadImage() {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load image for card: " + rank + " of " + suit);
            return null;
        }
    }

    // This method returns the image of the card.
    public Image getImage() {
        if (this.image == null) {
            this.image = loadImage();
        }
        return this.image;
    }

    // This method returns the suit of the card.
    public String getSuit() {
        return suit;
    }

    // This method returns the rank of the card.
    public String getRank() {
        return rank;
    }

    public int getOrder() {
        return order;
    }

    // This method compares this card to another card based on their ranks.
    @Override
    public int compareTo(Card c) {
        return this.order - c.getOrder();
    }

    // This method returns a string representation of the card.
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
