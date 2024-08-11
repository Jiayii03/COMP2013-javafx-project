package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

/**
 * The {@code Food} class represents the food (spike) in the Snake Game. It extends {@link GameObject} and implements
 * {@link Spike}, defining the characteristics and behavior of the food object.
 *
 * @author JiaYiChau-modified
 */
public class Food extends GameObject implements Spike {

    private static boolean isTest = false;

    /**
     * Constructs a new Food object. Initializes its position, image, and other attributes.
     */
    public Food() {
        this.setAlive(true);

        Random random = new Random();
        int randomIndex = random.nextInt(10);
        this.setImage(ImageUtil.getImages().get(String.valueOf(randomIndex)));

        this.setW((int) getImage().getWidth());
        this.setH((int) getImage().getHeight());

        if (!isTest){
            this.setX((int) (Math.random() * (870 - getW() + 10)));
            this.setY((int) (Math.random() * (560 - getH() - 40)));
        }
        else{
            this.setX(370);
            this.setY(105);
        }

    }

    /**
     * Handles the event when the food is eaten by the snake. Updates the score and the state of the food.
     *
     * @param mySnake The snake that ate the food.
     */
    public void eaten(MySnake mySnake) {

        if (mySnake.getRectangle().intersects(this.getRectangle()) && this.isAlive() && mySnake.isAlive()) {
            setAlive(false); // food is eaten by snake
            mySnake.changeLength(mySnake.getLength() + 1); // increase length of snake by 1
            mySnake.setScore(mySnake.getScore() + 521); // increase the score by 521
        }
    }

    /**
     * Draws the food on the game canvas using the specified GraphicsContext.
     *
     * @param gc The GraphicsContext used for drawing.
     */
    @Override
    public void draw(GraphicsContext gc) {
        // draw the fruit
        gc.drawImage(getImage(), getXCoordinate(), getYCoordinate());
    }

    public static void setIsTest(boolean isTest) {
        Food.isTest = isTest;
    }

    public static boolean getIsTest() {
        return isTest;
    }

    public boolean getIsAlive() {
        return this.isAlive();
    }
}