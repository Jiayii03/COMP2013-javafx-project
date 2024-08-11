package com.snakegame.chaujiayi_source_code_javafx_mvn;

import java.io.BufferedInputStream;
import java.net.URL;

import javazoom.jl.player.Player;

/**
 * The {@code MusicPlayer} class is responsible for playing background music in the Snake Game.
 * It extends the Thread class to allow concurrent music playback.
 *
 * @author JiaYiChau-modified
 */
public class MusicPlayer extends Thread
{
    private String filename;
    private Player player;

    /**
     * Constructs a new {@code MusicPlayer} with the specified filename of the audio file.
     *
     * @param filename The filename of the audio file to be played.
     */
    public MusicPlayer(String filename)
    {
        this.filename = filename;
    }

    /**
     * Plays the specified audio file in a separate thread.
     */
    public void play()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                super.run();
                try
                {
                    URL audioFileURL = MusicPlayer.class.getClassLoader().getResource(filename);

                    if (audioFileURL != null) {
                        player = new Player(new BufferedInputStream(audioFileURL.openStream()));
                        player.play();
                    } else {
                        System.out.println("Audio file not found.");
                    }

                } catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }.start();
    }

    /**
     * Static method to start playing background music.
     *
     * @param filename The filename of the audio file to be played.
     */
    public static void getMusicPlay(String filename)
    {
        MusicPlayer musicPlayer = new MusicPlayer(filename);
        musicPlayer.play();
    }
}
