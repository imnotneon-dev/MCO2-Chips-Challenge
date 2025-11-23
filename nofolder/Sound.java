import javax.sound.sampled.*;

public class Sound {
    public static void playMusic(String filePath) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                Sound.class.getResourceAsStream(filePath)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}