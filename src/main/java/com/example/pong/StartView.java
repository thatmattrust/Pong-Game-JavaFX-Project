package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Represents the start view of the Pong game.
 */
public class StartView {
    private final VBox layout;
    private final Button startButton;
    private final Button loadButton;
    private final Button settingsButton;
    private final Button exitButton;
    private final Scene scene;

    /**
     * Constructs a StartView object with default settings.
     */
    public StartView() {
        layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("PONG");
        titleLabel.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 75;");
        layout.getChildren().add(titleLabel);

        startButton = new Button("START");
        loadButton = new Button("LOAD");
        settingsButton = new Button("SETTINGS");
        exitButton = new Button("EXIT");

        startButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 35;");
        loadButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25;");
        settingsButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25;");
        exitButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25;");

        layout.getChildren().addAll(startButton, loadButton, settingsButton, exitButton);

        scene = new Scene(layout, 800, 600);
    }

    /**
     * Gets the scene of the start view.
     *
     * @return The scene of the start view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets the start button of the start view.
     *
     * @return The start button of the start view.
     */
    public Button getStartButton() {
        return startButton;
    }

    /**
     * Gets the settings button of the start view.
     *
     * @return The settings button of the start view.
     */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /**
     * Gets the exit button of the start view.
     *
     * @return The exit button of the start view.
     */
    public Button getExitButton() {
        return exitButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }
}
