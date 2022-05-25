package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Class representing the primary sound effect player for the Wof game.
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class SoundEffectPlayer {

    // Sound file paths for easy access.
    public final static String GUESS_CORRECT = "/resources/sounds/guess_correct.wav";
    public final static String THEME_MUSIC = "/resources/sounds/music.wav";
    public final static String WHEEL_SPIN = "/resources/sounds/wheel_spin.wav";
    public final static String GOOD_SPIN = "/resources/sounds/good_spin.wav";
    public final static String BAD_SPIN = "/resources/sounds/bad_spin.wav";
    public final static String SOLVED = "/resources/sounds/solved.wav";
    public final static String GUESS_INCORRECT = "/resources/sounds/wrong_guess.wav";
    public final static String CONGRATS = "/resources/sounds/congrats.wav";
    public final static String GUESSING_TIME = "/resources/sounds/guessing_time.wav";
    private Clip clip;

    /**
     * Sets and opens the current file for the sound player.
     * @param filePath sound file path
     */
    private void setFile(String filePath) {
        try {
            InputStream file = getClass().getResourceAsStream(filePath);
            assert file != null;
            InputStream buffered = new BufferedInputStream(file);
            AudioInputStream sound = AudioSystem.getAudioInputStream(buffered);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the sound file starting from beginning.
     * @param filePath sound file path
     */
    public void play(String filePath) {
        play(filePath, 0);
    }

    /**
     * Plays the sound file starting at specific position
     * @param filePath sound file path
     * @param position position to start sound at
     */
    public void play(String filePath, int position) {
        setFile(filePath);
        clip.setFramePosition(position);
        clip.start();
    }

    /**
     * Sets volume of sound player
     * @param volume volume to set sound player between 0 and 1
     */
    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * Stops all sound being currently played.
     */
    public void stop() {
        clip.stop();
    }


}
