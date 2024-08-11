package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the snake in the Snake Game. Handles the snake's movement, direction, and collision detection.
 * Extends the GameObject class and implements the Movable interface.
 *
 * @author JiaYiChau-modified
 */
public class MySnake extends GameObject implements Movable {

    private SnakeController snakeController;

    private int speedXY;
    private int length;
    private int num;
    private int score = 0;
    private static final Image IMG_SNAKE_HEAD = ImageUtil.getImages().get("snake-head-right");
    private List<Point2D> bodyPoints = new LinkedList<>();
    private static Image newImgSnakeHead;
    private boolean up, down, left, right = true;

    /**
     * Constructs a new MySnake object with the specified initial coordinates.
     *
     * @param x The initial x-coordinate of the snake.
     * @param y The initial y-coordinate of the snake.
     */
    public MySnake(int x, int y){
        this.setAlive(true);
        this.setX(x);
        this.setY(y);
        this.setImage(ImageUtil.getImages().get("snake-body"));
        this.setW((int) this.getImage().getWidth());
        this.setH((int) this.getImage().getHeight());
        this.speedXY = 5;
        this.length = 1;
        this.num = this.getW() / this.speedXY;
        this.newImgSnakeHead = IMG_SNAKE_HEAD;
    };

    /**
     * Gets the current length of the snake.
     *
     * @return The length of the snake.
     */
    public int getLength() {
        return length;
    }

    /**
     * Changes the length of the snake.
     *
     * @param length The new length of the snake.
     */
    public void changeLength(int length) {
        this.length = length;
    }

    /**
     * Handles keyboard input for controlling the snake's movement.
     *
     * @param e The KeyEvent representing the pressed key.
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                if (!down) {
                    up = true;
                    down = false;
                    left = false;
                    right = false;

                    newImgSnakeHead =  GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);
                }
                break;

            case DOWN:
                if (!up) {
                    up = false;
                    down = true;
                    left = false;
                    right = false;

                    newImgSnakeHead = GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
                }
                break;

            case LEFT:
                if (!right) {
                    up = false;
                    down = false;
                    left = true;
                    right = false;

                    newImgSnakeHead = GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);
                }
                break;

            case RIGHT:
                if (!left) {
                    up = false;
                    down = false;
                    left = false;
                    right = true;

                    newImgSnakeHead = IMG_SNAKE_HEAD;
                }
                break;

            // pause game
            case P:
                snakeController.pauseGame();

            default:
                break;
        }
    }

    /**
     * Moves the snake based on its current direction.
     */
    public void move()
    {
        if (up)
        {
            this.setY(this.getYCoordinate() - speedXY); // speed_XY is 5 by default, basically means how much it moves per movement
        } else if (down)
        {
            this.setY(this.getYCoordinate() + speedXY);
        } else if (left)
        {
            this.setX(this.getXCoordinate() - speedXY);
        } else if (right)
        {
            this.setX(this.getXCoordinate() + speedXY);
        }

    }

    /**
     * Draws the snake on the graphics context.
     *
     * @param gc The GraphicsContext on which to draw the snake.
     */
    @Override
    public void draw(GraphicsContext gc) {
        // check if the snake has hit the edges of the game area
        outOfBounds();

        // check if the snake has collided with its own body
        eatBody();

        // add new point into bodyPoints list every 30 ms
        bodyPoints.add(new Point2D(getXCoordinate(), getYCoordinate()));

        // doing housekeeping, when size of bodyPoints reached a maximum, remove the oldest point from the list
        // e.g. length = 1, bodyPoints.size() == 10, remove index 0, then bodyPoints.size() = 9
        if (bodyPoints.size() == (this.length + 1) * num) {
            bodyPoints.remove(0);
        }

        gc.drawImage(newImgSnakeHead, getXCoordinate(), getYCoordinate());
        drawBody(gc);

        move();
    }

    /**
     * Checks if the snake collides with itself.
     */
    public void eatBody()
    {
        for (Point2D point : bodyPoints)
        {
            for (Point2D point2 : bodyPoints)
            {
                if (point.equals(point2) && point != point2)
                {
                    this.setAlive(false);
                }
            }
        }
    }

    /**
     * Draws the body of the snake on the graphics context.
     *
     * @param g The GraphicsContext on which to draw the snake's body.
     */
    public void drawBody(GraphicsContext g)
    {

        int length = bodyPoints.size() - 1 - num;

        // step = 5 points every loop
        for (int i = length; i >= num; i -= num)
        {
//				System.out.println("Draw");
            Point2D point = bodyPoints.get(i);
            g.drawImage(this.getImage(), point.getX(), point.getY());
        }
    }

    /**
     * Checks if the snake is out of bounds (hits the edges of the game area).
     */
    public void outOfBounds()
    {
        boolean xOut = (this.getXCoordinate() <= 0 || this.getXCoordinate() >= (870 - this.getW())); // means the snake must always leave out a size of its width at the edge to stay alive
        boolean yOut = (this.getYCoordinate() <= 0 || this.getYCoordinate() >= (560 - this.getH()));
        if (xOut || yOut)
        {
            this.setAlive(false);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSpeedXY(int speed){
        this.speedXY = speed;
    }

    public int getSpeedXY(){
        return this.speedXY;
    }

    public void setNum(int num){
        this.num = num;
    }

    public List<Point2D> getBodyPoints() {
        return bodyPoints;
    }

    // Setter method to set the snakeController
    public void setSnakeController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

}

