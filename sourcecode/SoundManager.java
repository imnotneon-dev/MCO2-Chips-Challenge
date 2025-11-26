import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * This class features the sound part of the game, the game features the playing of Chip's Challenge background music from the file
 * This class also has a mute button in case the player wants to mute the game
 * 
 * @author Jenrick Lim, Ryan Malapitan (S16)
 */
public class SoundManager {
    private static Clip backgroundMusic;
    private static boolean isMuted = false;
    private static float originalVolume = 1.0f;

    /**
     * This is the method to start the BGMusic of the game, it gets the music file and loops it continuously
     * @param filePath the file path of the music file
     */
    public static void playMusic(String filePath) {
        if (isMuted) return;
        
        try {
            stopMusic();
            
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                SoundManager.class.getResourceAsStream(filePath)
            );
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audio);
            
            setVolume(originalVolume);
            
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Manually stops any music playing if there is any
     */
    public static void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    /**
     * Helps to handle the mute function of the game, wherein when the player clicks the mute button,
     * the music will be unheard until the player clicks it again
     */
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

    /**
     * This method helps to determine whether the state of the music is muted or not
     * @return {@code true} if it is muted
     *         {@code false} if it is not muted
     */
    public static boolean isMuted() {
        return isMuted;
    }

    /**
     * This method helps to handle the volume part of the music, it sets the volume of the game , which for now, only sets it to the original volume
     */
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

    /**
     * This method is mainly for creating a new mute button that the player can use for them to mute the BGMusic
     * This features the styling of the button and the action listener
     * @return muteButton the button to be used for the mute function
     */
    public static JButton createMuteButton() {
        JButton muteButton = new JButton();
        updateMuteButtonIcon(muteButton);
        
        muteButton.setBounds(630, 10, 50, 50); 
        muteButton.setFocusable(false);
        muteButton.setBackground(new Color(0, 0, 0, 100)); 
        muteButton.setOpaque(true);
        muteButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        muteButton.addActionListener(e -> {
            toggleMute();
            updateMuteButtonIcon(muteButton);
        });
        
        return muteButton;
    }

    /**
     * This method is for setting the icon of the mute buttom if it's muted or not, green if not muted, red if muted
     * 
     * @param muteButton helps to determine if sound is muted already or not
     */
    public static void updateMuteButtonIcon(JButton muteButton) {
        if (isMuted()) {
            muteButton.setText("🔇");
            muteButton.setToolTipText("Unmute Sound");
            muteButton.setForeground(Color.RED);
        } else {
            muteButton.setText("🔊");
            muteButton.setToolTipText("Mute Sound");
            muteButton.setForeground(Color.GREEN);
        }
        
        muteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        muteButton.setFocusPainted(false);
    }
}