package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the instruction scene of the Snake Game. Handles user interactions
 * for navigating back to the start scene.
 *
 * @author JiaYiChau
 */
public class InstructionSceneController {

    private Stage primaryStage;

    /**
     * Sets the primary stage for the InstructionSceneController.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Navigates back to the start scene from the instruction scene. Loads the StartScene.fxml file,
     * retrieves the associated controller, passes the primaryStage reference, enables accessibility features,
     * creates the start scene, and sets the primaryStage's scene to the newly created start scene.
     */
    public void instructionPageBackToStartScene(){
        try{
            // Load the SnakeView.fxml file for the game scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
            Parent startSceneLayout = fxmlLoader.load();

            // Get the controller associated with the StartScene.fxml
            StartSceneController startSceneController = fxmlLoader.getController();

            // Pass the primaryStage reference to the StartScene controller
            startSceneController.setPrimaryStage(this.primaryStage);
            startSceneController.enableAccessibility();

            // Create the game scene
            Scene startScene = new Scene(startSceneLayout, 870, 560);

            // Set the game scene
            this.primaryStage.setScene(startScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
