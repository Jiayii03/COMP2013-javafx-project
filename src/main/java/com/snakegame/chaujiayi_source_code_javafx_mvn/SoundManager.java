package com.snakegame.chaujiayi_source_code_javafx_mvn;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

/**
 * The {@code SoundManager} class is responsible for managing sound in the Snake Game. It provides methods to play
 * audio, control sound state, and check whether a sound has been played. This class utilizes JavaFX's {@link MediaPlayer}
 * for handling audio playback.
 *
 * @author JiaYiChau
 */
public class SoundManager {
    private static boolean soundEnabled = true;
    private static boolean popSoundPlayed = false;
    private MediaPlayer mediaPlayer;

    /**
     * Constructs a new SoundManager with the specified audio file path.
     *
     * @param audioFilePath The path to the audio file.
     */
    public SoundManager(String audioFilePath){
        Media sound = new Media(StartSceneController.class.getClassLoader().getResource(audioFilePath).toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    /**
     * Plays the audio. If the sound has already been played, it stops the current playback and starts a new one.
     */
    public void playSound() {
        // Play the sound
        popSoundPlayed = false;
        mediaPlayer.stop();
        mediaPlayer.play();
        popSoundPlayed = true;
    }

    public static boolean popSoundPlayed() {
        return popSoundPlayed;
    }

    public static void setSoundEnabled(boolean soundEnabled){
        SoundManager.soundEnabled = soundEnabled;
    }

    public static boolean getSoundEnabled() {
        return SoundManager.soundEnabled;
    }
}
