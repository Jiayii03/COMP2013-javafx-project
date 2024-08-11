package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Controller class for the scoreboard scene of the Snake Game. Manages the display of top three
 * player scores, provides an option to play again, and allows quitting the game.
 *
 * @author JiaYiChau
 */
public class ScoreboardController {

    private Stage primaryStage;
    private Map<String, Integer> playerScores = new HashMap<>();

    @FXML
    private Text player1Name;
    @FXML
    private Text player2Name;
    @FXML
    private Text player3Name;
    @FXML
    private Text player4Name;
    @FXML
    private Text player1Score;
    @FXML
    private Text player2Score;
    @FXML
    private Text player3Score;
    @FXML
    private Text player4Score;

    /**
     * Initializes the ScoreboardController by fetching scores and displaying the top three scores.
     */
    public void initialize(){
        getScoreboard();
    }

    /**
     * Resets the bomb coordinates and returns to the start scene. Invoked when the "Play Again" button is pressed.
     *
     * @throws IOException If an error occurs while loading the StartScene.fxml file.
     */
    @FXML
    public void playAgain() throws IOException {
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
     * Retrieves player scores from the "scores.csv" file and updates the playerScores map.
     */
    public void getScoreboard(){
        String filePath = "scores.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    // Update the score if a higher score is found for the same player
                    playerScores.put(playerName, Math.max(playerScores.getOrDefault(playerName, 0), score));
                }
            }

            displayTopFourScores();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the top three player scores on the scoreboard UI.
     */
    public void displayTopFourScores(){
        // Use a PriorityQueue to get the top three scores
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue())
        );

        pq.addAll(playerScores.entrySet());

        // Peek at the first entry without removing it
        Map.Entry<String, Integer> topEntry = pq.poll();
        if (topEntry != null) {
            player1Name.setText(topEntry.getKey());
            player1Score.setText(String.valueOf(topEntry.getValue()));
        }

        // Peek at the second entry without removing it
        Map.Entry<String, Integer> secondTopEntry = pq.poll();
        if (secondTopEntry != null) {
            player2Name.setText(secondTopEntry.getKey());
            player2Score.setText(String.valueOf(secondTopEntry.getValue()));
        }

        // Peek at the third entry without removing it
        Map.Entry<String, Integer> thirdTopEntry = pq.poll();
        if (thirdTopEntry != null) {
            player3Name.setText(thirdTopEntry.getKey());
            player3Score.setText(String.valueOf(thirdTopEntry.getValue()));
        }
        // Peek at the third entry without removing it
        Map.Entry<String, Integer> fourthTopEntry = pq.poll();
        if (fourthTopEntry != null) {
            player4Name.setText(thirdTopEntry.getKey());
            player4Score.setText(String.valueOf(thirdTopEntry.getValue()));
        }
    }

    /**
     * Quits the game by closing the primaryStage. Invoked when the "Quit Game" button is pressed.
     */
    @FXML
    public void quitGame(){
        this.primaryStage.close();
    }

    /**
     * Sets the primary stage for the ScoreboardController.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
