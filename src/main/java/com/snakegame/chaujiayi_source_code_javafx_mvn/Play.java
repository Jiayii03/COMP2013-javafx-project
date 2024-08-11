package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Snake Game application. This class extends the JavaFX Application class
 * and serves as the entry point for launching the game.
 *
 * @author JiaYiChau-modified
 */
public class Play extends Application {

    private Stage primaryStage;

    /**
     * This method is called when the JavaFX application is launched.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws IOException If an error occurs while loading the start scene.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // Create the start scene with a button
        Scene startScene = createStartScene();

        primaryStage.setTitle("Snakee Yipee");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    /**
     * Creates and returns the start scene for the Snake Game.
     * The method loads the "StartScene.fxml" file to create the start scene.
     * It sets up the controller, i.e. StartSceneController, passes the primaryStage reference, and enables accessibility features.
     *
     * @return The start scene for the Snake Game.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public Scene createStartScene() throws IOException {

        // Load the SnakeView.fxml file for the game scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Parent startScene = fxmlLoader.load();

        // Get the controller associated with the FXML file
        StartSceneController startSceneController = fxmlLoader.getController();

        // Pass the primaryStage reference to the StartScene controller
        startSceneController.setPrimaryStage(primaryStage);
        startSceneController.enableAccessibility();

        // Create the start scene
        return new Scene(startScene, 870, 560);

    }

    /**
     * The main entry point for the Snake Game application.
     * This method launches the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
