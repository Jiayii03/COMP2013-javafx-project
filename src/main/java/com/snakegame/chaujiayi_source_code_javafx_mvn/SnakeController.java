package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.*;

/**
 * Controller class for the Snake Game application. This class handles the game logic, user input,
 * and interactions between different game elements. It is responsible for initializing the game,
 * managing the game loop, handling level transitions, displaying the scoreboard, and more.
 *
 * @author JiaYiChau
 */
public class SnakeController extends Window {

    private Stage primaryStage;

    @FXML
    private Rectangle snakeRectangle;

    @FXML
    private Canvas canvas;

    @FXML
    private ImageView pausePopup;

    @FXML
    private Button resumeButton;

    @FXML
    private ImageView pauseButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Text levelText;

    @FXML
    private Text scoreText;

    @FXML
    private Text highestScoreText;

    @FXML
    private ImageView resumeGameText;

    @FXML
    private ImageView pauseGameText;

    private SpikeFactory spikeFactory = SpikeFactory.getInstance(); // create an instance of spikeFactory
    private MySnake mySnake = new MySnake(100,100);
    private Spike food = spikeFactory.getSpike("FOOD"); // get an object of Food
    private Spike wall = spikeFactory.getSpike("WALL");
    private Spike bomb = spikeFactory.getSpike("BOMB");
    private Spike spells = spikeFactory.getSpike("SPELLS");
    private Image fail = ImageUtil.getImages().get("game-scene-01");
    private String playerName = "";
    private boolean gameOver = false;
    private int highestScore = 0;
    private String highestPlayer = "";
    private boolean isPaused = false;
    private Timeline gameLoop;
    private static final int BOMB_INTERVAL = 150;
    private int bombIntervalCounter = 0;
    private boolean secondLevelUpSoundPlayed = false;
    private boolean thirdLevelUpSoundPlayed = false;

    /**
     * Initializes the game, setting up the necessary components and starting the game loop.
     * The game loop uses a JavaFX Timeline to update and redraw the game components at regular intervals.
     * During the game loop, it checks for the snake's state, level conditions, and handles various game elements.
     * The method has no parameters and returns nothing.
     */
    public void initialize() {
        mySnake.setSnakeController(this);
        findHighestScore();
        // Game loop using JavaFX Timeline
        gameLoop = new Timeline(new KeyFrame(Duration.millis(30), event -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            canvas.setFocusTraversable(true);
            canvas.requestFocus();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            if(!isPaused && !gameOver) {
                if (mySnake.isAlive()) {
                    mySnake.draw(gc);

                    if(mySnake.getScore() > 500 && mySnake.getScore() < 1000){ // level two
                        handleLevelTwo(gc);
                    }

                    if(mySnake.getScore() >= 1000) { // level three
                        handleLevelThree(gc);
                        bomb.draw(gc);
                        ((Bomb) bomb).checkCollision(mySnake);

                        if (((GameObject) spells).isAlive()) {
                            spells.draw(gc);
                            ((Spells) spells).eaten(mySnake);
                        } else {
                            spells = new Spells();
                        }
                    }

                    if (((GameObject) food).isAlive()) { // object food needs to be cast into type GameObject because the Spike interface doesn't have isAlive method
                        food.draw(gc);
                        ((Food) food).eaten(mySnake);
                    } else {
                        food = new Food();
                    }
                } else {
                    gc.drawImage(fail, 0, 0);
                    if (!gameOver) {
                        writeToCSV();
                        try {
                            displayScoreboard();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        gameOver = true;
                    }
                }
            }
            drawScore(gc, playerName);
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    /**
     * Handles the transition to Level 2 in the Snake Game.
     * This method updates the game elements, such as the background (grass), snake speed, and level text,
     * to reflect the transition to Level 2. It also draws the wall on the canvas and checks for collisions
     * between the snake and the wall.
     *
     * @param gc The GraphicsContext for drawing on the canvas.
     */
    public void handleLevelTwo(GraphicsContext gc){
        if(SoundManager.getSoundEnabled() && !secondLevelUpSoundPlayed) {
            System.out.println("Playing music");
            MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/level-up-sound-effect.mp3");
            secondLevelUpSoundPlayed = true;
        }

        Image levelTwoBackground = new Image(String.valueOf(getClass().getResource("images/grass-scene.jpg")));
        backgroundImageView.setImage(levelTwoBackground);

        mySnake.setSpeedXY(6);
        mySnake.setNum(mySnake.getW() / mySnake.getSpeedXY());
        levelText.setText("Level 2");

        wall.draw(gc);
        ((Wall) wall).checkCollision(mySnake);
    }

    /**
     * Handles the transition to Level 3 in the Snake Game.
     * This method updates the game elements, such as the background (deep sea), snake speed, and level text,
     * to reflect the transition to Level 3. It also draws the wall on the canvas, checks for collisions
     * between the snake and the wall, and adds bombs to the game.
     *
     * @param gc The GraphicsContext for drawing on the canvas.
     */
    public void handleLevelThree(GraphicsContext gc){
        if(SoundManager.getSoundEnabled() && !thirdLevelUpSoundPlayed) {
            System.out.println("Playing music");
            MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/level-up-sound-effect.mp3");
            thirdLevelUpSoundPlayed = true;
        }

        Image levelTwoBackground = new Image(String.valueOf(getClass().getResource("images/deep-sea.png")));
        backgroundImageView.setImage(levelTwoBackground);

        levelText.setText("Level 3");
        mySnake.setSpeedXY(6);
        mySnake.setNum(mySnake.getW() / mySnake.getSpeedXY());
        wall.draw(gc);
        ((Wall) wall).checkCollision(mySnake);

        addBomb(gc);
    }

    /**
     * Adds bomb sprites to the game at 3 seconds interval.
     * This method increments a counter to track the elapsed time and adds bomb sprites to the game
     * when the specified interval is reached. The bomb sprites are created using the SpikeFactory.
     *
     * @param gc The GraphicsContext for drawing on the canvas.
     */
    public void addBomb(GraphicsContext gc){
        bombIntervalCounter++;

        // add bomb sprites at every 3 seconds
        if (bombIntervalCounter >= BOMB_INTERVAL) {
            // Add bomb sprites here
            bomb = spikeFactory.getSpike("BOMB");

            bombIntervalCounter = 0; // Reset the counter
        }
    }

    /**
     * Writes the player's name and score to a CSV file.
     * This method appends a new line to the "scores.csv" file containing the player's name
     * and their current score in CSV format.
     */
    public void writeToCSV() {
        try (FileWriter writer = new FileWriter("scores.csv", true)) {
            System.out.println("File path: " + new File("scores.csv").getAbsolutePath());
            System.out.println("Writing");
            // Append a new line with the player's name and score
            writer.append(playerName)
                    .append(',')
                    .append(String.valueOf(mySnake.getScore()))
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the scoreboard scene in the JavaFX application.
     * This method loads the "Scoreboard.fxml" file to create the scoreboard scene and sets up
     * the necessary controllers. It then creates the scene and sets it in the primaryStage.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void displayScoreboard() throws IOException {

        // Load the SnakeView.fxml file for the game scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Scoreboard.fxml"));
        Parent scoreboardRoot = fxmlLoader.load();

        ScoreboardController scoreboardController = fxmlLoader.getController();
        scoreboardController.setPrimaryStage(this.primaryStage);

        // Create the game scene
        Scene scoreBoardScene = new Scene(scoreboardRoot, 870, 560);

        // Set the game scene
        this.primaryStage.setScene(scoreBoardScene);

        if(SoundManager.getSoundEnabled()) {
            System.out.println("Playing music");
            MusicPlayer.getMusicPlay("com/snakeGame/chaujiayi_source_code_javafx_mvn/music/game_over_you_failed.mp3");
        }
    }

    /**
     * Finds and sets the highest score and player from the "scores.csv" file.
     * This method reads the "scores.csv" file, parses each line to extract player names and scores,
     * and identifies the highest score and the corresponding player. It then sets the highest score
     * and player fields in the class for later use.
     */
    public void findHighestScore() {

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader("scores.csv"))) {
                System.out.println("File path: " + new File("scores.csv").getAbsolutePath());
                String line;
                String highestPlayer = null;
                int highestScore = Integer.MIN_VALUE;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);

                        if (score > highestScore) {
                            highestScore = score;
                            highestPlayer = playerName;
                        }
                    }
                }

                if (highestPlayer != null) {
                    System.out.println("Highest Score: " + highestScore);
                    this.highestScore = highestScore;
                    System.out.println("Player with Highest Score: " + highestPlayer);
                    this.highestPlayer = highestPlayer;
                } else {
                    System.out.println("No entries found in the CSV file.");
                }
            }

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Toggles the pause state of the game.
     * When called, this method toggles the `isPaused` flag and shows a pause pop-up
     * with a Gaussian blur effect applied to the game canvas when the game is paused.
     */
    @FXML
    public void pauseGame() {
        isPaused = !isPaused;

        // Show a pop-up when the game is paused
        if (isPaused) {
            GaussianBlur blur = new GaussianBlur(20); // You can adjust the radius as needed
            canvas.setEffect(blur);

            showPausePopup();
        }
    }

    /**
     * Displays the pause pop-up UI on the JavaFX application.
     * This method runs on the JavaFX application thread using `Platform.runLater` to ensure
     * proper UI updates. It sets the visibility of UI components related to the pause pop-up,
     * requests focus for the resumeButton, and adds an event filter to capture key events during pause.
     */
    public void showPausePopup() {
        Platform.runLater(() -> {
            pausePopup.setVisible(true);
            resumeButton.setVisible(true);
            pauseButton.setVisible(false);
            exitButton.setVisible(true);
            resumeGameText.setVisible(true);
            pauseGameText.setVisible(false);

            // Ensure focus traversal for the resumeButton
            resumeButton.setFocusTraversable(true);

            // Request focus for the resumeButton using Platform.runLater
            Platform.runLater(() -> resumeButton.requestFocus());

            // Add an event filter to the scene to capture key events
            Scene scene = resumeButton.getScene();
            scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handlePauseKeyEvent);
        });
    }

    /**
     * Handles the action to restart the game when the player chooses to play again after pausing.
     * This method loads the "StartScene.fxml" file, initializes a new StartSceneController, sets
     * the primary stage, resets bomb coordinates, creates a new game scene, and sets it as the current scene.
     *
     * @throws IOException If an error occurs while loading the "StartScene.fxml" file.
     */
    public void handlePausePlayAgain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Parent startSceneRoot = fxmlLoader.load();

        StartSceneController startSceneController = fxmlLoader.getController();
        startSceneController.setPrimaryStage(this.primaryStage);

        // reset bomb coordinates
        Bomb.resetBombCoordinates();

        // Create the game scene
        Scene startSceneScene = new Scene(startSceneRoot, 870, 560);

        // Set the game scene
        this.primaryStage.setScene(startSceneScene);
    }

    /**
     * Handles key events during the pause state of the game.
     * This method is responsible for handling key events, such as consuming the "P" key event to prevent
     * accidental presses while the game is paused, and resuming the game when the "ENTER" key is pressed.
     *
     * @param event The KeyEvent representing the key press event.
     */
    private void handlePauseKeyEvent(KeyEvent event) {
        // Check if the "P" key is pressed and consume the event
        if (isPaused && event.getCode() == KeyCode.P) {
            event.consume();
        }

        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.R) {
            resumeGame();
        }
    }

    /**
     * Resumes the game after it has been paused. This method hides the pause popup, adjusts the visibility
     * of UI elements (resumeButton, pauseButton, exitButton), sets the isPaused flag to false, removes the blur effect
     * from the canvas, and removes the event filter for key presses during the pause state.
     */
    public void resumeGame(){
        pausePopup.setVisible(false);
        resumeButton.setVisible(false);
        pauseButton.setVisible(true);
        exitButton.setVisible(false);
        resumeGameText.setVisible(false);
        pauseGameText.setVisible(true);
        isPaused = false;  // Resume the game
        canvas.setEffect(null);  // Remove the blur effect

        // Remove the event filter once the game is resumed
        Scene scene = resumeButton.getScene();
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, this::handlePauseKeyEvent);
    }

    /**
     * Handles the key pressed event during the game. This method delegates the key event to the
     * corresponding method in the MySnake instance, allowing the snake to respond to user input.
     *
     * @param e The KeyEvent representing the key press event.
     */
    @FXML
    public void handleKeyPressed(KeyEvent e) {
        mySnake.keyPressed(e);
    }

    /**
     * Draws the player's score and the highest score on the game screen. This method updates the
     * scoreText and highestScoreText UI elements with the current player's score and the highest score.
     *
     * @param gc          The GraphicsContext used for drawing on the canvas.
     * @param playerName  The name of the current player.
     */
    private void drawScore(GraphicsContext gc, String playerName) {

        scoreText.setText(playerName + "'s score : " + mySnake.getScore());

        if(highestPlayer != "") highestScoreText.setText(highestPlayer + "'s highest score : " + highestScore);
        else highestScoreText.setText("Highest Score not available yet");
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMySnake(MySnake mySnake) {
        this.mySnake = mySnake;
    }

    public void setFood(Spike food) {
        this.food = food;
    }
}
