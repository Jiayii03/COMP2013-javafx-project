package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;

/**
 * The {@code Spike} interface represents a spike object in the Snake Game that can be drawn on the game canvas.
 * Classes implementing this interface should provide an implementation for the {@link #draw(GraphicsContext)} method.
 *
 * @author JiaYiChau
 */
public interface Spike {

    /**
     * Draws the spike on the specified {@link GraphicsContext}.
     *
     * @param gc The {@link GraphicsContext} on which to draw the spike.
     */
    void draw(GraphicsContext gc);
}
