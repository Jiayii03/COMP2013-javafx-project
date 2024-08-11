package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code Spells} class represents a type of spike in the Snake Game. Spells can be collected by the snake to
 * trigger special effects, such as increasing the length of the snake by 3 units.
 *
 * @author JiaYiChau
 */
public class Spells extends GameObject implements Spike{

    static Image spellsImage = new Image(String.valueOf(Bomb.class.getResource("images/spells.png")));

    /**
     * Constructs a new {@code Spells} object. Sets its initial state, including being alive and its position.
     */
    public Spells(){

        this.setAlive(true);
        this.setImage(spellsImage);

        this.setW((int) getImage().getWidth());
        this.setH((int) getImage().getHeight());

        this.setX((int) (Math.random() * (870 + 10)));
        this.setY((int) (Math.random() * (560 - 40)));

    }

    /**
     * Handles the action when the spells spike is eaten by the snake. In this case, it increases the length of the snake by 3 units.
     *
     * @param mySnake The snake object that consumes the spells spike.
     */
    public void eaten(MySnake mySnake) {

        if (mySnake.getRectangle().intersects(this.getXCoordinate(), this.getYCoordinate(), 20, 20) && mySnake.isAlive())  {
            if(SoundManager.getSoundEnabled()) {
                MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/spells-taken.mp3");
            }
            setAlive(false);
            mySnake.changeLength(mySnake.getLength() + 3); // increase length of snake by 1
            mySnake.setScore(mySnake.getScore() + 1043);
        }
    }

    /**
     * Draws the spells spike on the game canvas.
     *
     * @param gc The graphics context on which the spells spike is drawn.
     */
    @Override
    public void draw(GraphicsContext gc) {
        double scaledWidth = 40;  // Adjust the desired width
        double scaledHeight = 40;
        gc.drawImage(getImage(), getXCoordinate(), getYCoordinate(), scaledWidth, scaledHeight);
    }
}
