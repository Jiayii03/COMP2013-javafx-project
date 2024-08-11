package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Bomb} class represents a bomb spike in the Snake Game. Bombs are objects that, when touched by the snake,
 * lead to game over. This class extends {@link GameObject} and implements the {@link Spike} interface, providing
 * functionality for drawing and handling collisions with bombs.
 *
 * @author JiaYiChau
 */
public class Bomb extends GameObject implements Spike{

    private static List<Coordinate> bombCoordinates = new ArrayList<>();;
    static Image bombImage = new Image(String.valueOf(Bomb.class.getResource("images/Bomb.png")));

    /**
     * Constructs a new Bomb object with a random position.
     */
    public Bomb(){

        this.setImage(bombImage);

        this.setW((int) getImage().getWidth());
        this.setH((int) getImage().getHeight());

        this.setX((int) (Math.random() * (870 + 10)));
        this.setY((int) (Math.random() * (560 - 40)));

        bombCoordinates.add(new Coordinate(getXCoordinate(), getYCoordinate()));
    }

    /**
     * Checks for collisions between the snake and bombs. If a collision is detected, it sets the snake to not alive.
     *
     * @param mySnake The snake object to check for collisions.
     */
    public void checkCollision(MySnake mySnake){
        // Check for collisions with walls
        for (Coordinate bombCoordinate : bombCoordinates) {
            if (mySnake.getRectangle().intersects(bombCoordinate.getX(), bombCoordinate.getY(), 20, 20) && mySnake.isAlive()) {
                mySnake.setAlive(false); // snake touches the boundary of a wall, set it to not alive
                break;
            }
        }
    }

    /**
     * Draws the bomb spikes on the specified {@link GraphicsContext}.
     *
     * @param gc The {@link GraphicsContext} on which to draw the bomb spikes.
     */
    public void draw(GraphicsContext gc) {
        double scaledWidth = 40;  // Adjust the desired width
        double scaledHeight = 40;

        for (Coordinate coordinate : bombCoordinates) {
            gc.drawImage(bombImage, coordinate.getX(), coordinate.getY(), scaledWidth, scaledHeight);
        }
    }

    public static List<Coordinate> getBombCoordinates(){
        return bombCoordinates;
    }

    /**
     * Resets the list of bomb coordinates.
     */
    public static void resetBombCoordinates(){
        bombCoordinates.clear();
    }
}
