package com.snakegame.chaujiayi_source_code_javafx_mvn;

/**
 * The {@code Coordinate} class represents a point in a two-dimensional space defined by its x and y coordinates.
 * It is used to represent the position of various game elements, such as bombs and walls, in the Snake Game.
 *
 * @author JiaYiChau
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructs a new Coordinate object with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

