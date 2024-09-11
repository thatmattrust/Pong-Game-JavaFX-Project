package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the settings view of the Pong game.
 */
public class SettingsView {
    private final VBox layout;
    private final ChoiceBox<String> playerOnePaddleSizeChoice;
    private final ChoiceBox<String> playerTwoPaddleSizeChoice;
    private final ChoiceBox<Integer> maxScoreChoice;
    private final ChoiceBox<String> speedIncreaseChoice;
    private final TextField playerOneNameField;
    private final TextField playerTwoNameField;
    private final Button backButton;
    private final Scene scene;

    /**
     * Constructs a SettingsView object with default settings.
     */
    public SettingsView() {
        layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("SETTINGS");
        titleLabel.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 75;");
        layout.getChildren().add(titleLabel);

        // Player 1 Settings
        HBox playerOneSettings = new HBox(10);
        playerOneSettings.setAlignment(Pos.CENTER);
        playerOneNameField = new TextField("Player 1");
        playerOnePaddleSizeChoice = createChoiceBox("Paddle Size", new String[]{"Small", "Medium", "Large"});
        playerOneSettings.getChildren().addAll(new Label("Player 1 Name"), playerOneNameField, new Label("Paddle Size"), playerOnePaddleSizeChoice);

        // Player 2 Settings
        HBox playerTwoSettings = new HBox(10);
        playerTwoSettings.setAlignment(Pos.CENTER);
        playerTwoNameField = new TextField("Player 2");
        playerTwoPaddleSizeChoice = createChoiceBox("Paddle Size", new String[]{"Small", "Medium", "Large"});
        playerTwoSettings.getChildren().addAll(new Label("Player 2 Name"), playerTwoNameField, new Label("Paddle Size"), playerTwoPaddleSizeChoice);

        // Game Settings
        HBox gameSettings = new HBox(10);
        gameSettings.setAlignment(Pos.CENTER);
        maxScoreChoice = createChoiceBox("Max Score", new Integer[]{5, 10, 25});
        speedIncreaseChoice = createChoiceBox("Speed Increase", new String[]{"Slow", "Average", "Fast"});

        String choiceBoxStyle = "-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 15; -fx-border-color: white; -fx-border-width: 1; -fx-label-text-fill: white;";
        maxScoreChoice.setStyle(choiceBoxStyle);
        speedIncreaseChoice.setStyle(choiceBoxStyle);

        Label maxScoreLabel = new Label("Max Score");
        maxScoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 15;");

        Label speedIncreaseLabel = new Label("Speed Increase");
        speedIncreaseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 15;");

        gameSettings.getChildren().addAll(maxScoreLabel, maxScoreChoice, speedIncreaseLabel, speedIncreaseChoice);

        backButton = new Button("Confirm");
        backButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25;");

        layout.getChildren().addAll(playerOneSettings, playerTwoSettings, gameSettings, backButton);

        scene = new Scene(layout, 800, 600);
    }

    /**
     * Gets the scene of the settings view.
     *
     * @return The scene of the settings view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets the selected paddle size choice for player one.
     *
     * @return The selected paddle size choice for player one.
     */
    public String getPlayerOnePaddleSizeChoice() {
        return playerOnePaddleSizeChoice.getValue();
    }

    /**
     * Gets the selected paddle size choice for player two.
     *
     * @return The selected paddle size choice for player two.
     */
    public String getPlayerTwoPaddleSizeChoice() {
        return playerTwoPaddleSizeChoice.getValue();
    }

    /**
     * Gets the selected maximum score choice.
     *
     * @return The selected maximum score choice.
     */
    public int getMaxScoreChoice() {
        return maxScoreChoice.getValue();
    }

    /**
     * Gets the selected speed increase choice.
     *
     * @return The selected speed increase choice.
     */
    public String getSpeedIncreaseChoice() {
        return speedIncreaseChoice.getValue();
    }

    /**
     * Gets the name entered for player one.
     *
     * @return The name entered for player one.
     */
    public String getPlayerOneName() {
        return playerOneNameField.getText();
    }

    /**
     * Gets the name entered for player two.
     *
     * @return The name entered for player two.
     */
    public String getPlayerTwoName() {
        return playerTwoNameField.getText();
    }

    /**
     * Gets the back button of the settings view.
     *
     * @return The back button of the settings view.
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Helper method to create a choice box
     *
     * @return A choice box containing the needed options.
     */
    private <T> ChoiceBox<T> createChoiceBox(String label, T[] options) {
        ChoiceBox<T> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(options);
        choiceBox.setValue(options[0]);
        choiceBox.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 15; -fx-border-color: white; -fx-border-width: 1; -fx-label-text-fill: white;");
        return choiceBox;
    }

    public void loadSettingsFromGameState(GameState gameState) {
        playerOneNameField.setText(gameState.getPlayerOneName());
        playerTwoNameField.setText(gameState.getPlayerTwoName());
        maxScoreChoice.setValue(gameState.getMaxScore());
    }

}

