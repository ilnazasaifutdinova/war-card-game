package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColorAdjuster extends JFrame {
	private JSlider redSlider;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private ColorScheme colorScheme;
	private JPanel colorDisplayPanel;

	public ColorAdjuster(ColorScheme colorScheme) {
		this.colorScheme = colorScheme;

		// JFrame setup
		setTitle("Adjust Custom Color");
		setSize(400, 300);
		setLayout(new GridLayout(3, 2)); // Grid layout for sliders
		setLocationRelativeTo(null); // Center the frame on monitor

		// Create sliders for RGB components
		redSlider = createSlider("Red", 255);
		greenSlider = createSlider("Green", 255);
		blueSlider = createSlider("Blue", 255);

		// Add sliders to the frame
		add(redSlider);
		add(greenSlider);
		add(blueSlider);

		// Panel to display the custom color
		colorDisplayPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(colorScheme.getCustomColor());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		add(colorDisplayPanel);

		// ChangeListener to update custom color on slider change
		ChangeListener listener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int red = redSlider.getValue();
				int green = greenSlider.getValue();
				int blue = blueSlider.getValue();
				colorScheme.setCustomColor(red, green, blue); // Update color in ColorScheme
			}
		};

		// Attach listener to sliders
		redSlider.addChangeListener(listener);
		greenSlider.addChangeListener(listener);
		blueSlider.addChangeListener(listener);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				// Optional: Handle any cleanup or saving if needed
			}
		});
		setVisible(true); // Make the frame visible

		// Listen for color changes in ColorScheme and repaint display panel
		colorScheme.addColorChangeListener(new ColorScheme.ColorChangeListener() {
			@Override
			public void colorChanged(Color newColor) {
				colorDisplayPanel.repaint(); // Repaint color display panel
//				colorScheme.setCustomColor(); // Update custom color in ColorScheme
			}
		});
	}

	// Helper method to create a slider with a label
	private JSlider createSlider(String name, int max) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(name);
		JSlider slider = new JSlider(0, max, max);
		panel.add(label, BorderLayout.WEST);
		panel.add(slider, BorderLayout.EAST);
		add(panel);
		return slider;
	}

	// Main method to demonstrate to test ColorAdjuster class
	public static void main(String[] args) {
		ColorScheme colorScheme = new ColorScheme(); // Create a ColorScheme instance

		// Create an instance of ColorAdjuster
		new ColorAdjuster(colorScheme);

		// Example JFrame to demonstrate custom color usage
		JFrame exampleFrame = new JFrame("Example Usage of Custom Color");
		exampleFrame.setSize(400, 300);
		JPanel examplePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(colorScheme.getCustomColor()); // Get custom color from ColorScheme
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		exampleFrame.add(examplePanel);
		exampleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exampleFrame.setVisible(true);

		// Listen for color changes in ColorScheme and repaint example panel
		colorScheme.addColorChangeListener(new ColorScheme.ColorChangeListener() {
			@Override
			public void colorChanged(Color newColor) {
				examplePanel.repaint(); // Repaint example panel on color change
			}
		});
	}
}
