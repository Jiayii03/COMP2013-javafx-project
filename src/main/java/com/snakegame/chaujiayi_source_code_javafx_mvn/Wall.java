package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Wall} class represents a wall spike in the Snake Game. Walls are stationary obstacles that, when touched
 * by the snake, lead to game over. This class extends {@link GameObject} and implements the {@link Spike} interface,
 * providing functionality for drawing and handling collisions with walls.
 *
 * @author JiaYiChau
 */
public class Wall extends GameObject implements Spike {

    private static List<Coordinate> wallCoordinates = new ArrayList<>();;

    /**
     * Constructs a new Wall object and initializes the wall coordinates.
     */
    public Wall() {
        Image wallImage = new Image(String.valueOf(getClass().getResource("images/wall.png")));
        this.setImage(wallImage);

        wallCoordinates = new ArrayList<>();
        wallCoordinates.add(new Coordinate(50, 50));
        wallCoordinates.add(new Coordinate(130, 340));
        wallCoordinates.add(new Coordinate(200, 220));
        wallCoordinates.add(new Coordinate(300, 450));
        wallCoordinates.add(new Coordinate(400, 350));
        wallCoordinates.add(new Coordinate(500, 100));
        wallCoordinates.add(new Coordinate(550, 399));
        wallCoordinates.add(new Coordinate(650, 250));
        wallCoordinates.add(new Coordinate(700, 340));
        wallCoordinates.add(new Coordinate(800, 120));
    }

    /**
     * Draws the wall spikes on the specified {@link GraphicsContext}.
     *
     * @param gc The {@link GraphicsContext} on which to draw the wall spikes.
     */
    @Override
    public void draw(GraphicsContext gc) {
        double scaledWidth = 30;  // Adjust the desired width
        double scaledHeight = 30;

        for (Coordinate coordinate : wallCoordinates) {
            gc.drawImage(this.getImage(), coordinate.getX(), coordinate.getY(), scaledWidth, scaledHeight);
        }
    }

    /**
     * Checks for collisions between the snake and walls. If a collision is detected, it sets the snake to not alive.
     *
     * @param mySnake The snake object to check for collisions.
     */
    public void checkCollision(MySnake mySnake) {
        // Check for collisions with walls
        for (Coordinate wallCoordinate : wallCoordinates) {
            if (mySnake.getRectangle().intersects(wallCoordinate.getX(), wallCoordinate.getY(), 30, 30) && mySnake.isAlive()) {
                mySnake.setAlive(false); // snake touches the boundary of a wall, set it to not alive
                break;
            }
        }
    }
}
