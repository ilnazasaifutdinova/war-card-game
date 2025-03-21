package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorScheme {
    private Color darkGreen;
    private Color white;
    private Color customColor;
    
    // List to hold color change listeners
    private List<ColorChangeListener> listeners = new ArrayList<>();
    
    // Constructor initializes predefined colors and sets default custom color
    public ColorScheme() {
        this.darkGreen = new Color(0, 100, 0); // Dark green color
        this.white = Color.WHITE; // White color
        this.customColor = new Color(0, 100, 0); // Default custom color (dark green)
    }

    // Getter for dark green color
    public Color getDarkGreen() {
        return darkGreen;
    }

    // Getter for white color
    public Color getWhite() {
        return white;
    }
    
    // Getter for custom color (dark green by default)
    public Color getCustomColor() {
        return customColor;
    }
    
    // Setter for custom color, notifies listeners of the change
    public void setCustomColor(int r, int g, int b) {
        this.customColor = new Color(r, g, b); // Create new Color object with RGB values
        notifyColorChangeListeners(); // Notify listener about the color change
    }
    
    // Method to add a color change listener
    public void addColorChangeListener(ColorChangeListener listener) {
        listeners.add(listener);
    }

    // Method to notify listener about a color change
    private void notifyColorChangeListeners() {
        for (ColorChangeListener listener : listeners) {
            listener.colorChanged(customColor); // Notify listener with the new custom color
        }
    }

    // Interface for color change listener
    public interface ColorChangeListener {
        void colorChanged(Color newColor); // Method called when color changes
    }
}
