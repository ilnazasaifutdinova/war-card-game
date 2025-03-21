package game;

import cards.Deck;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckPoint implements Serializable {
    private final Player player1;
    private final Player player2;
    private final LocalDateTime timestamp;
    private final String name;

    public CheckPoint(Player player1, Player player2, String name) {
        this.player1 = player1;
        this.player2 = player2;
        this.timestamp = LocalDateTime.now();
        this.name = name;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}