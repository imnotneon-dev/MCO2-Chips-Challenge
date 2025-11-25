import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class SoundManager {
    private static Clip backgroundMusic;
    private static boolean isMuted = false;
    private static float originalVolume = 1.0f;

    public static void playMusic(String filePath) {
        if (isMuted) return;
        
        try {
            stopMusic(); // Stop any existing music
            
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                SoundManager.class.getResourceAsStream(filePath)
            );
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audio);
            
            // Set volume control
            setVolume(originalVolume);
            
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    public static void toggleMute() {
        isMuted = !isMuted;
        
        if (backgroundMusic != null) {
            if (isMuted) {
                backgroundMusic.stop();
            } else {
                backgroundMusic.start();
                setVolume(originalVolume);
            }
        }
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void setVolume(float volume) {
        if (backgroundMusic != null) {
            try {
                FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
                originalVolume = volume;
            } catch (Exception e) {
                System.out.println("Volume control not supported");
            }
        }
    }

    // Create a reusable mute button
    public static JButton createMuteButton() {
        JButton muteButton = new JButton();
        updateMuteButtonIcon(muteButton);
        
        muteButton.setBounds(630, 10, 50, 50); // Top-right corner
        muteButton.setFocusable(false);
        muteButton.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        muteButton.setOpaque(true);
        muteButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        muteButton.addActionListener(e -> {
            toggleMute();
            updateMuteButtonIcon(muteButton);
        });
        
        return muteButton;
    }

    // Update the button icon based on mute state
    public static void updateMuteButtonIcon(JButton muteButton) {
        if (isMuted()) {
            muteButton.setText("🔇"); // Muted icon
            muteButton.setToolTipText("Unmute Sound");
            muteButton.setForeground(Color.RED);
        } else {
            muteButton.setText("🔊"); // Sound icon
            muteButton.setToolTipText("Mute Sound");
            muteButton.setForeground(Color.GREEN);
        }
        
        // Style the button
        muteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        muteButton.setFocusPainted(false);
    }
}