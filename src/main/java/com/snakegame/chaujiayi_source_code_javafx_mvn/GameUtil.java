package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.io.InputStream;

/**
 * The {@code GameUtil} class provides utility methods for loading and manipulating images in the Snake Game.
 *
 * @author JiaYiChau-modified
 */
public class GameUtil {

    /**
     * Loads an image from the specified imagePath.
     *
     * @param imagePath The path of the image to be loaded.
     * @return The loaded Image object.
     */
    public static Image getImage(String imagePath) {
        try (InputStream inputStream = GameUtil.class.getClassLoader().getResourceAsStream(imagePath)) {
            if (inputStream != null) {
                return new Image(inputStream);
            } else {
                System.err.println("ERROR: Image Not Found: " + imagePath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("ERROR: Exception while loading image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rotates the given image by the specified degree.
     *
     * @param image  The image to be rotated.
     * @param degree The degree by which the image should be rotated.
     * @return The rotated Image object.
     */
    public static Image rotateImage(final Image image, final int degree) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        Rotate rotation = new Rotate(degree, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        imageView.getTransforms().add(rotation);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT); // Set the background to be transparent

        // Creating a snapshot of the rotated image
        return imageView.snapshot(params, null);
    }
}
