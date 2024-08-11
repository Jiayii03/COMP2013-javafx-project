package com.snakegame.chaujiayi_source_code_javafx_mvn;

import javafx.geometry.Point2D;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.input.KeyCode;
import org.testfx.matcher.base.NodeMatchers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.util.List;

/**
 * JUnit 5 test class for testing the Game Scene in the Snake Game application.
 * Uses TestFX for UI testing and Awaitility for asynchronous assertions.
 *
 * @author JiaYiChau
 */
@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameSceneTest {

    private boolean conditionMet = false;
    SnakeController snakeController;
    MySnake mySnake = new MySnake(100, 100);
    private SpikeFactory spikeFactory = SpikeFactory.getInstance(); // create an instance of spikeFactory
    private Spike food;
    List<Point2D> updatedBodyPoints;

    /**
     * Setup method that initializes the JavaFX stage for testing.
     *
     * @param stage The primary stage created for the test.
     * @throws Exception If an error occurs during setup.
     */
    @Start
    private void start(Stage stage) throws Exception {

        Food.setIsTest(true);
        food = spikeFactory.getSpike("FOOD"); // get an object of Food

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SnakeView.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller associated with the SnakeView.fxml
        snakeController = fxmlLoader.getController();
        snakeController.setMySnake(mySnake);

        // Pass data to the controller
        snakeController.setPrimaryStage(stage);
        snakeController.setPlayerName("Test Player");
        snakeController.setFood(food);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Test method to verify the handling of key presses and score increase when food is eaten. Also verifies the display of UI elements after game over.
     *
     * <p>Test steps:</p>
     * <ol>
     *   <li>Press DOWN key and verify snake movement.</li>
     *   <li>Press LEFT key and verify snake movement.</li>
     *   <li>Press UP key and verify snake movement.</li>
     *   <li>Press RIGHT key and verify snake movement.</li>
     *   <li>Wait for food to be eaten and verify score increase.</li>
     *   <li>Verify UI elements after game over.</li>
     *   <li>Click on play again button and verify the start screen.</li>
     * </ol>
     *
     * @param robot The TestFX robot for UI interactions.
     */
    @Test
    @Order(1)
    void should_handle_key_press_and_increase_score_when_food_eaten(FxRobot robot) {

        // print length of coordinates of the snake
        System.out.println("The length of coordinates of the snake is: " + mySnake.getBodyPoints().size());

        sleepForAWhile(1000, 5);

        // Test on key press DOWN
        robot.press(KeyCode.DOWN);

        sleepForAWhile(1000, 5);

        // Retrieve the bodyPoints after pressing UP
        updatedBodyPoints = mySnake.getBodyPoints();

        // Check if there are at least two points in the list
        if (updatedBodyPoints.size() >= 2) {
            // Get the last and second last points
            Point2D lastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 1);
            Point2D secondLastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 2);

            // Check if the y-coordinate of the last point is 5 units smaller than the second last point
            assertTrue(lastPoint.getY() - 5 == secondLastPoint.getY());
        } else {
            fail("Not enough points in bodyPoints");
        }

        // Test on key press LEFT
        robot.press(KeyCode.LEFT);

        sleepForAWhile(1000, 5);

        updatedBodyPoints = mySnake.getBodyPoints();

        // Check if there are at least two points in the list
        if (updatedBodyPoints.size() >= 2) {
            // Get the last and second last points
            Point2D lastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 1);
            Point2D secondLastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 2);

            // Check if the x-coordinate of the last point is 5 units smaller than the second last point
            assertTrue(lastPoint.getX() + 5 == secondLastPoint.getX());
        } else {
            fail("Not enough points in bodyPoints");
        }

        // Test on key press UP
        robot.press(KeyCode.UP);

        sleepForAWhile(1000, 5);

        // Retrieve the bodyPoints after pressing UP
        updatedBodyPoints = mySnake.getBodyPoints();

        // Check if there are at least two points in the list
        if (updatedBodyPoints.size() >= 2) {
            // Get the last and second last points
            Point2D lastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 1);
            Point2D secondLastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 2);

            // Check if the y-coordinate of the last point is 5 units smaller than the second last point
            assertTrue(lastPoint.getY() + 5 == secondLastPoint.getY());
        } else {
            fail("Not enough points in bodyPoints");
        }

        // Test on key press RIGHT
        robot.press(KeyCode.RIGHT);

        updatedBodyPoints = mySnake.getBodyPoints();

        // Check if there are at least two points in the list
        if (updatedBodyPoints.size() >= 2) {
            // Get the last and second last points
            Point2D lastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 1);
            Point2D secondLastPoint = updatedBodyPoints.get(updatedBodyPoints.size() - 2);

            // Check if the x-coordinate of the last point is 5 units smaller than the second last point
            assertTrue(lastPoint.getX() - 5 == secondLastPoint.getX());
        } else {
            fail("Not enough points in bodyPoints");
        }

        // Wait for the food to be eaten
        try {
            Food.setIsTest(false);
            Awaitility.await().atMost(Duration.ofSeconds(10)).until(() -> isFoodEaten());

            System.out.println("Food has been eaten!");

            // assert that the score is increased by 521
            assertEquals(521, mySnake.getScore());

            sleepForAWhile(1000, 5);

        } catch (ConditionTimeoutException e) {
            // Handle timeout (food not eaten within the expected time)
            throw new RuntimeException("Food not eaten within the expected time");
        }

        // verify that the play again button is present
        verifyThat("#playAgainButton", NodeMatchers.isVisible());

        // verify that the quit game button is present
        verifyThat("#quitGameButton", NodeMatchers.isVisible());

        robot.clickOn("#playAgainButton");

        // Verify that the start button is present
        verifyThat("#startButton", NodeMatchers.isNotNull());

        // Verify that the instruction button is present
        verifyThat("#instructionButton", NodeMatchers.isNotNull());

        // Verify that the quit button is present
        verifyThat("#quitButton", NodeMatchers.isNotNull());
    }

    // helper function - sleep
    void sleepForAWhile(int duration, int timeout){

        // Start an asynchronous task that will set conditionMet to true after some delay
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(duration);
                conditionMet = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Use awaitility to wait for the condition to be true
        Awaitility.await().atMost(timeout, TimeUnit.SECONDS).until(() -> conditionMet);
        conditionMet = false;
    }

    private boolean isFoodEaten() {
        // Implement a method to check whether the food is eaten
        // For example, return true if the food is not present in the scene
        return !((Food) food).getIsAlive();
    }
}
