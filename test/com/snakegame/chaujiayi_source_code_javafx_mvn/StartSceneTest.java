package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isFocused;

/**
 * JUnit 5 test class for testing the Start Scene in the Snake Game application.
 * Uses TestFX for UI testing and focuses on interactions with buttons and scene transitions.
 *
 * <p>Make sure to set up the necessary FXML and resources for the test scenarios.</p>
 *
 * @author Your Name
 * @version 1.0
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class   StartSceneTest {

    private StartSceneController startSceneController;
    private static boolean soundManagerOriginalState;
    private Stage primaryStage;

    @Start
    private void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Parent root = fxmlLoader.load();
        startSceneController = fxmlLoader.getController(); // Get reference to the controller
        startSceneController.setPrimaryStage(stage); // Pass the primaryStage reference to the controller
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        soundManagerOriginalState = SoundManager.getSoundEnabled(); // Store original state
    }

    @Test
    @Order(1)
    void should_have_three_buttons(FxRobot robot) {
        // Verify that the start button is present
        verifyThat("#startButton", NodeMatchers.isNotNull());

        // Verify that the instruction button is present
        verifyThat("#instructionButton", NodeMatchers.isNotNull());

        // Verify that the quit button is present
        verifyThat("#quitButton", NodeMatchers.isNotNull());

        // Verify that volumeOn ImageView is present
        verifyThat("#volumeOn", NodeMatchers.isNotNull());

        // Verify that volumeOff ImageView is present
        verifyThat("#volumeOff", NodeMatchers.isNotNull());

        // Verify that the backdrop ImageView is present
        verifyThat("#startSceneBackdrop", NodeMatchers.isNotNull());
    }

    @Test
    @Order(2)
    void should_enable_accessibility_and_play_sound_on_focus(FxRobot robot) {
        // Call the method to enable accessibility
        robot.interact(() -> startSceneController.enableAccessibility());

        // Verify the start button is focused
        verifyThat("#startButton", isFocused());

        // Simulate TAB key presses to navigate through buttons
        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Move to instruction
        robot.sleep(1000); // Sleep to wait for the focus change event

        // Verify that startButton gained focus and sound was played
        verifyThat("#instructionButton", isFocused());

        if (soundManagerOriginalState) {
            // If sound was originally enabled, verify that sound was played
            assertTrue(SoundManager.popSoundPlayed(), "Pop sound should have been played");
        }

        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Move to Quit button
        robot.sleep(1000); // Sleep to wait for the focus change event

        // Verify that startButton gained focus and sound was played
        verifyThat("#quitButton", isFocused());

        if (soundManagerOriginalState) {
            // If sound was originally enabled, verify that sound was played
            assertTrue(SoundManager.popSoundPlayed(), "Pop sound should have been played");
        }

        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Move to startButton
        robot.sleep(1000); // Sleep to wait for the focus change event

        // Verify that startButton gained focus and sound was played
        verifyThat("#startButton", isFocused());

        if (soundManagerOriginalState) {
            // If sound was originally enabled, verify that sound was played
            assertTrue(SoundManager.popSoundPlayed(), "Pop sound should have been played");
        }
    }

    @Test
    @Order(3)
    void should_show_instruction_scene_when_instruction_button_clicked(FxRobot robot) {

        // Click the instruction button
        robot.clickOn("#instructionButton");

        // Verify that the instructionSceneBackdrop is present
        verifyThat("#instructionSceneBackdrop", NodeMatchers.isNotNull());

        // Verify that the back button is present
        verifyThat("#backButton", NodeMatchers.isNotNull());

        // Click the back button
        robot.clickOn("#backButton");

        // Verify that the startSceneBackdrop is present
        verifyThat("#startSceneBackdrop", NodeMatchers.isNotNull());

    }

    @Test
    void should_start_game_when_start_button_clicked(FxRobot robot) {

        StartSceneController.setTest(true);

        // Click the start button
        robot.clickOn("#startButton");

        // Run the dialog code on the JavaFX application thread
        Platform.runLater(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Your Name");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter your name:");

            // Apply CSS styling
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("css/CustomDialog.css").toExternalForm());

            // Show and wait for the result
            Optional<String> result = dialog.showAndWait();

            // Check if the dialog was closed with a result
            if (result.isPresent()) {
                String enteredName = result.get();

                // Perform actions based on the entered name, e.g., start the game
                startSceneController.startGame(enteredName);

                // Verify that the game scene is present
                verifyThat("#backgroundImageView", NodeMatchers.isNotNull());

                // Verify that the pause button is present
                verifyThat("#pauseButton", NodeMatchers.isNotNull());
            }
        });

        // You may need to add some delay here to allow the dialog to appear before proceeding with verification
        robot.sleep(500);

        robot.write("Test Player");
    }
}

