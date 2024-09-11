package com.example.pong;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class for the Pong game. Extends JavaFX Application to create a JavaFX application.
 */

public class PongGame extends Application {

    /**
     * The main method of the Pong game.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Override of the start method from the Application class.
     * This method is called when the JavaFX application is launched.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        StartView startView = new StartView();
        SettingsView settingsView = new SettingsView();
        GameView gameView = new GameView();

        GameController controller = new GameController(primaryStage, startView, settingsView, gameView);

        controller.startGame();
    }
}
