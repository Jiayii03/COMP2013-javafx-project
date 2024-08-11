package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ImageUtil} class provides a utility for loading and storing images used in the Snake Game.
 *
 * @author JiaYiChau-modified
 */
public class ImageUtil {

    private static Map<String, Image> images = new HashMap<>();

    public static Map<String, Image> getImages() {
        return images;
    }

    /**
     * Static block to load images into the map.
     */
    static {
        // snake
        images.put("snake-head-right", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/snake-head-right.png"));
        images.put("snake-body", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/snake-body.png"));
        // obstacles
        images.put("0", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-kiwi.png"));
        images.put("1", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-lemon.png"));
        images.put("2", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-litchi.png"));
        images.put("3", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-mango.png"));
        images.put("4", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-apple.png"));
        images.put("5", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-banana.png"));
        images.put("6", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-blueberry.png"));
        images.put("7", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-cherry.png"));
        images.put("8", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-durian.png"));
        images.put("9", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-grape.png"));
        images.put("10", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-grapefruit.png"));
        images.put("11", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-peach.png"));
        images.put("12", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-pear.png"));
        images.put("13", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-orange.png"));
        images.put("14", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-pineapple.png"));
        images.put("15", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-strawberry.png"));
        images.put("16", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/food-watermelon.png"));
        images.put("UI-background", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/UI-background.png"));
        images.put("game-scene-01", GameUtil.getImage("com/snakeGame/chaujiayi_source_code_javafx_mvn/images/game-scene-01.jpg"));
    }
}

