package gui;

import javax.swing.*;
import java.awt.*;

public class GameRules extends JFrame {

    public GameRules() {
        setTitle("Game Rules");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea rulesText = new JTextArea();
        rulesText.setText(
            "Rules of the Card Game War:\n\n" +
            "1. The deck is divided evenly among the players.\n" +
            "2. Each player reveals the top card of their deck.\n" +
            "3. The player with the higher card wins both cards and places them at the bottom of their deck.\n" +
            "4. If the cards are of equal value, a 'war' is triggered:\n" +
            "   - Each player places three cards face down and one card face up.\n" +
            "   - The player with the higher face-up card wins all the cards.\n" +
            "   - If the face-up cards are again equal, the process repeats.\n" +
            "5. The game continues until one player has all the cards.\n" +
            "6. The player with all the cards wins the game."
        );
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(rulesText);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}