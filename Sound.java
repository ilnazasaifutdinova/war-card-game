package gui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    // Sound clips for different actions in the game
    private Clip drawCardClip;
    private Clip clickSoundClip;
    private Clip backgroundMusicClip;
    private Clip rickRollClip;

    // Constructor initializes the sound clips by loading audio files
    public Sound() {
        try {
            // Load sounds for card drawing, button clicking, and background music
            drawCardClip = loadSound("Sounds/card.wav");
            clickSoundClip = loadSound("Sounds/click.wav");
            backgroundMusicClip = loadSound("Sounds/background.wav");
            rickRollClip = loadSound("Sounds/rickroll.wav");
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an exception occurs during loading
        }
    }

    // Private method to load a sound file and return a Clip object
    private Clip loadSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(filePath); // Create a File object for the sound file
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile); // Get an audio input stream from the file
        Clip clip = AudioSystem.getClip(); // Get a Clip object for playback
        clip.open(audioInputStream); // Open the audio input stream with the clip
        return clip; // Return the loaded clip
    }

    // Method to play the card drawing sound
    public void playDrawCardSound() {
        if (drawCardClip != null) {
            drawCardClip.setFramePosition(0);  // Rewind to the beginning of the sound
            drawCardClip.start(); // Start playing the sound
        }
    }

    // Method to play the button clicking sound
    public void playClickSound() {
        if (clickSoundClip != null) {
            clickSoundClip.setFramePosition(0);  // Rewind to the beginning of the sound
            clickSoundClip.start(); // Start playing the sound
        }
    }

    // Method to play background music
    public void playBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.setFramePosition(0); // Ensure the music starts from the beginning
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            backgroundMusicClip.start(); // Start playing the music
        }
    }

    // Method to stop the background music
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop(); // Stop the music if it is currently playing
        }
    }
    
    public void playRickRollMusic() {
    	if (rickRollClip != null && !rickRollClip.isRunning()) {
    		rickRollClip.setFramePosition(0);
    		rickRollClip.loop(Clip.LOOP_CONTINUOUSLY);
    		rickRollClip.start();
    	}
    }
    
    public void stopRickRollMusic() {
        if (rickRollClip != null && rickRollClip.isRunning()) {
            rickRollClip.stop();
        }
    }
}
