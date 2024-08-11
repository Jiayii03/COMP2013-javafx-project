package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code GameObject} class was originally named as {@code SnakeObject} class and was renamed.
 * It represents a game object in the Snake Game.
 * It serves as the base class for various game entities, e.g. snake, food, bombs, etc. providing common properties
 * and methods for objects that are part of the game world.
 *
 * @author JiaYiChau-modified
 */
public abstract class GameObject {

    private int x;
    private int y;
    private Image image;
    private int w;
    private int h;
    private boolean alive; // previously named as "l"

    public abstract void draw(GraphicsContext gc);

    public javafx.geometry.Rectangle2D getRectangle() {
        return new javafx.geometry.Rectangle2D(x, y, w, h);
    }

    public int getXCoordinate() {
        return x;
    }

    public int getYCoordinate() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image i) {
        this.image = i;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}

