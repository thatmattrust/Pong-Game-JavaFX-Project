package com.example.pong;

import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

/**
 * Controls the flow and behavior of the Pong game.
 */
public class GameController {
    private final Stage primaryStage;
    private final StartView startView;
    private static SettingsView settingsView;
    private final GameView gameView;
    private static final Player playerOne = new Player();
    private static final Player playerTwo = new Player();

    /**
     * Constructs a GameController object with the specified views and stage.
     *
     * @param primaryStage The primary stage of the application.
     * @param startView     The start view of the game.
     * @param settingsView  The settings view of the game.
     * @param gameView      The game view of the game.
     */
    public GameController(Stage primaryStage, StartView startView, SettingsView settingsView, GameView gameView) {
        this.primaryStage = primaryStage;
        this.startView = startView;
        this.settingsView = settingsView;
        this.gameView = gameView;

        gameView.setController(this);

        setupStartScreen();
        setupSettingsScreen();

        PaddleMovement paddleOneMovement = new PaddleMovement(gameView.getPaddleOne(), 0);
        Thread paddleOneThread = new Thread(paddleOneMovement);
        paddleOneThread.setDaemon(true);
        paddleOneThread.start();

        PaddleMovement paddleTwoMovement = new PaddleMovement(gameView.getPaddleTwo(), 0);
        Thread paddleTwoThread = new Thread(paddleTwoMovement);
        paddleTwoThread.setDaemon(true);
        paddleTwoThread.start();

        BallMovement ballMovement = new BallMovement(gameView.getBall());
        Thread ballThread = new Thread(ballMovement);
        ballThread.start();

    }

    /**
     * Sets up the start screen of the game and handles button actions.
     */
    private void setupStartScreen() {
        startView.getStartButton().setOnAction(event -> {
            gameSetup();
        });

        startView.getSettingsButton().setOnAction(event -> {
            primaryStage.setScene(settingsView.getScene());
        });



        startView.getLoadButton().setOnAction(event -> {
            GameState loadedGameState = null;
            try {
                loadedGameState = GameDAO.loadLatestGame();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            settingsView.loadSettingsFromGameState(loadedGameState);
            playerOne.setScore(loadedGameState.getPlayerOneScore());
            playerTwo.setScore(loadedGameState.getPlayerTwoScore());
            gameSetup();
        });


        startView.getExitButton().setOnAction(event -> {
            primaryStage.close();
        });
    }

    /**
     * Sets up the settings screen of the game and handles button actions.
     */
    private void setupSettingsScreen() {
        settingsView.getBackButton().setOnAction(event -> {

            primaryStage.setScene(startView.getScene());
        });
    }

    /**
     * Starts the game by setting the primary stage scene to the start view and showing the stage.
     */
    public void startGame() {
        primaryStage.setScene(startView.getScene());
        primaryStage.show();
    }

    public void startScreen(){
        primaryStage.setScene(startView.getScene());
    }


    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void gameSetup(){
        primaryStage.setScene(gameView.getScene());
        gameView.gameStatus(true);
        gameView.setPositions();
        Rectangle paddleOne = gameView.getPaddleOne();
        Rectangle paddleTwo = gameView.getPaddleTwo();

        gameView.setPlayerOne(playerOne);
        gameView.setPlayerTwo(playerTwo);

        playerOne.setName(settingsView.getPlayerOneName());
        playerTwo.setName(settingsView.getPlayerTwoName());

        gameView.setMaxScore(settingsView.getMaxScoreChoice());

        gameView.updateScore();

        if (settingsView.getPlayerOnePaddleSizeChoice().equals("Small")){
            paddleOne.setWidth(7);
            paddleOne.setHeight(70);
        } else if (settingsView.getPlayerOnePaddleSizeChoice().equals("Medium")) {
            paddleOne.setWidth(10);
            paddleOne.setHeight(100);
        }else if (settingsView.getPlayerOnePaddleSizeChoice().equals("Large")) {
            paddleOne.setWidth(12);
            paddleOne.setHeight(120);
        }

        if (settingsView.getPlayerTwoPaddleSizeChoice().equals("Small")){
            paddleTwo.setWidth(7);
            paddleTwo.setHeight(70);
        } else if (settingsView.getPlayerTwoPaddleSizeChoice().equals("Medium")) {
            paddleTwo.setWidth(10);
            paddleTwo.setHeight(100);
        }else if (settingsView.getPlayerTwoPaddleSizeChoice().equals("Large")) {
            paddleTwo.setWidth(12);
            paddleTwo.setHeight(120);
        }

        if (settingsView.getSpeedIncreaseChoice().equals("Slow")){
            gameView.setSpeedIncrease(0.3);
        } else if (settingsView.getSpeedIncreaseChoice().equals("Average")) {
            gameView.setSpeedIncrease(0.45);
        }else if (settingsView.getSpeedIncreaseChoice().equals("Fast")) {
            gameView.setSpeedIncrease(0.7);
        }
    }
}
