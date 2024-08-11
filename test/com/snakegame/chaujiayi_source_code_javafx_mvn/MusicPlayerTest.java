package com.snakegame.chaujiayi_source_code_javafx_mvn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * JUnit 5 test class for testing the MusicPlayer functionality in the Snake Game application.
 * Focuses on playing music without encountering exceptions.
 *
 * @author JiaYiChau
 */
class MusicPlayerTest {

    @Test
    void should_play_music_without_exceptions() {

        /**
         * Test method to verify that the MusicPlayer can play music without exceptions.
         *
         * <p>Uses assertDoesNotThrow to ensure that playing music does not throw any exceptions.</p>
         */
        assertDoesNotThrow(() -> MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/frogger.mp3"));

        // Add some sleep to allow the music to start playing before completing the test
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
