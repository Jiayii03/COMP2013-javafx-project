package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.IOException;

/**
 * Controller class for the start scene of the Snake Game. Handles user interactions
 * for starting the game, viewing instructions, adjusting sound settings, and quitting the game.
 * Initializes UI elements, sound effects, and provides methods to transition to other scenes.
 *
 * @author JiaYiChau
 */
public class StartSceneController {

    @FXML
    private Button startButton;

    @FXML
    private Button instructionButton;

    @FXML
    private Button quitButton;

    @FXML
    private ImageView volumeOn;

    @FXML
    private ImageView volumeOff;

    private Stage primaryStage;
    private SoundManager popSound;
    private SoundManager startSound;
    private static boolean isTest = false;

    /**
     * Initializes the StartSceneController by setting the initial focus to the startButton,
     * adding focus event handlers to UI elements, and initializing MediaPlayer objects for
     * popSound and startSound with sound files. It also adjusts the visibility of volume control
     * icons based on the initial sound state (enabled or disabled).
     */
    @FXML
    public void initialize(){
        // Set the initial focus to button1
        startButton.requestFocus();

        // Add focus event handlers
        addFocusEventHandlers(startButton);
        addFocusEventHandlers(instructionButton);
        addFocusEventHandlers(quitButton);

        // Initialize the MediaPlayer with a sound file
        String popSoundFilePath = "com/snakeGame/chaujiayi_source_code_javafx_mvn/music/popSound.mp3";
        popSound = new SoundManager(popSoundFilePath);

        String startSoundFilePath = "com/snakeGame/chaujiayi_source_code_javafx_mvn/music/startSound.mp3";
        startSound = new SoundManager(startSoundFilePath);

        if(SoundManager.getSoundEnabled()){
            volumeOn.setVisible(true);
            volumeOff.setVisible(false);
        }
        else{
            volumeOn.setVisible(false);
            volumeOff.setVisible(true);
        }
    }

    /**
     * Enables sound in the application by setting the SoundManager's sound state to true.
     * Also updates the visibility of volume control icons accordingly.
     */
    public void soundOn(){
        SoundManager.setSoundEnabled(true);
        volumeOn.setVisible(true);
        volumeOff.setVisible(false);
    }

    /**
     * Disables sound in the application by setting the SoundManager's sound state to false.
     * Also updates the visibility of volume control icons accordingly.
     */
    public void soundOff(){
        SoundManager.setSoundEnabled(false);
        volumeOn.setVisible(false);
        volumeOff.setVisible(true);
    }

    /**
     * Sets the primary stage for the StartSceneController.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Enables accessibility features for UI elements by setting focus traversable and accessible text.
     * This method is called during the initialization of the StartSceneController.
     */
    public void enableAccessibility(){
        startButton.setFocusTraversable(true);
        startButton.setAccessibleText("Start");
        instructionButton.setAccessibleText("Instruction");
        instructionButton.setFocusTraversable(true);
        quitButton.setAccessibleText("Quit");
        quitButton.setFocusTraversable(true);
    }

    /**
     * Adds focus event handlers to a given Button, providing visual and auditory feedback
     * when the button gains or loses focus.
     *
     * @param button The Button to which focus event handlers are added.
     */
    public void addFocusEventHandlers(Button button) {
        // Add focus gained event handler
        button.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #f7f1cb;");
                if(SoundManager.getSoundEnabled()) popSound.playSound();

            } else {
                // Set the original background color when focus is lost
                button.setStyle("-fx-background-color: #F7E05A;");
            }
        });
    }

    /**
     * Enters the game, initializing the game setup. If in test mode, it returns without further actions.
     * If sound is enabled, it plays the start sound. It then blurs the current scene, prompts the user to enter their name
     * using a TextInputDialog, and calls the startGame method with the entered playerName. After the dialog is closed,
     * it removes the blur effect from the scene.
     */
    public void enterGame(){
        System.out.println(isTest);

        if(isTest){
            return;
        }

        if(SoundManager.getSoundEnabled()) startSound.playSound();

        // Blur the current scene
        GaussianBlur blur = new GaussianBlur(10);
        primaryStage.getScene().getRoot().setEffect(blur);

        // Create a TextInputDialog for user input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Your Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your name:");

        // Apply CSS styling
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("css/CustomDialog.css").toExternalForm());

        // Show and wait for the result
        dialog.initStyle(StageStyle.UTILITY);
        dialog.showAndWait().ifPresent(this::startGame);

        // Remove the blur after the dialog is closed
        primaryStage.getScene().getRoot().setEffect(null);
    }

    /**
     * Starts the game by loading the SnakeView.fxml file for the game scene, initializing the SnakeController with the
     * playerName and the primaryStage reference, creating the game scene, and setting the primaryStage's scene to the
     * newly created game scene. If sound is enabled, it may also play additional music or sound effects.
     *
     * @param playerName The name entered by the player.
     */
    public void startGame(String playerName){
        try{
            // Load the SnakeView.fxml file for the game scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SnakeView.fxml"));
            Parent gameRoot = fxmlLoader.load();

            SnakeController snakeController = fxmlLoader.getController();
            snakeController.setPlayerName(playerName);
            snakeController.setPrimaryStage(this.primaryStage);

            // Create the game scene
            Scene gameScene = new Scene(gameRoot, 870, 560);

            // Set the game scene
            this.primaryStage.setScene(gameScene);
            if(SoundManager.getSoundEnabled()) MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/frogger.mp3");

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Shows the instruction scene, initializing the setup. If sound is enabled, it plays the start sound.
     * It loads the "InstructionScene.fxml" file for the instruction scene, retrieves the associated controller,
     * passes the primaryStage reference, creates the instruction scene, and sets the primaryStage's scene to the
     * newly created instruction scene.
     */
    public void showInstruction(){
        try{
            if(SoundManager.getSoundEnabled()) startSound.playSound();

            // Load the SnakeView.fxml file for the game scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InstructionScene.fxml"));

            Parent instructionLayout = fxmlLoader.load();

            // Get the controller associated with the InstructionScene.fxml
            InstructionSceneController instructionController = fxmlLoader.getController();

            // Pass the primaryStage reference to the InstructionScene controller
            instructionController.setPrimaryStage(this.primaryStage);

            // Create the game scene
            Scene instructionScene = new Scene(instructionLayout, 870, 560);

            // Set the game scene
            this.primaryStage.setScene(instructionScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Quits the game by closing the primaryStage.
     */
    public void quitGame(){
        this.primaryStage.close();
    }

    public static void setTest(boolean isT){
        isTest = isT;
    }

}
