package com.example.pong;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Represents the view of the Pong game.
 */
public class GameView {
    private final Pane pane;
    private final Scene scene;
    private Rectangle paddleOne;
    private Rectangle paddleTwo;
    private Ball ball;
    private final int PADDING = 20;
    private double newHeight;
    private double newWidth;

    private int maxScore;
    private double speedIncrease;
    private int bounceCount;
    private boolean paused;


    private Player playerOne = new Player();
    private Player playerTwo = new Player();

    private boolean paddleOneMovingUp = false;
    private boolean paddleOneMovingDown = false;
    private boolean paddleTwoMovingUp = false;
    private boolean paddleTwoMovingDown = false;

    private boolean gameActive = false;
    private Text scoreText;

    private Text playerOneText;
    private Text playerTwoText;

    private GameController controller;

    /**
     * Constructs a GameView object with default settings.
     */
    public GameView() {
        pane = new Pane();
        scene = new Scene(pane, 800, 600);
        pane.setStyle("-fx-background-color: black;");


        paddleOne = new Rectangle(10, 100, Color.WHITE);
        paddleTwo = new Rectangle(10, 100, Color.WHITE);
        paddleOne.resize(12,120);
        ball = new Ball(15, Color.WHITE);

        paddleOne.setX(PADDING);
        paddleOne.setY(newHeight / 2 - paddleOne.getHeight() / 2);

        paddleTwo.setX(newWidth - paddleTwo.getWidth() - PADDING);
        paddleTwo.setY(newHeight / 2 - paddleTwo.getHeight() / 2);

        playerOne.setName("Player 1");
        playerTwo.setName("Player 2");

        pane.getChildren().addAll(paddleOne, paddleTwo, ball);
        addSizeChangeListener();

        playerOneText = new Text(playerOne.getName() + ": " + playerOne.getScore());
        playerOneText.setFill(Color.WHITE);
        playerOneText.setFont(Font.font(20));
        pane.getChildren().add(playerOneText);

        playerTwoText = new Text(playerTwo.getName() + ": " + playerTwo.getScore());
        playerTwoText.setFill(Color.WHITE);
        playerTwoText.setFont(Font.font(20));
        pane.getChildren().add(playerTwoText);

        paddleOneMovingUp = false;
        paddleOneMovingDown = false;
        paddleTwoMovingUp = false;
        paddleTwoMovingDown = false;

        scoreText = new Text();
        scoreText.setFill(Color.GREY);
        scoreText.setFont(Font.font(30));
        scoreText.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(scoreText);

        scene.setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event.getCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        scene.setOnKeyReleased(event -> handleKeyReleased(event.getCode()));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePaddles();
                moveBall();
            }
        };
        timer.start();
    }

    private void handleKeyPressed(KeyCode code) throws IOException {
        switch (code) {
            case W:
                paddleOneMovingUp = true;
                break;
            case S:
                paddleOneMovingDown = true;
                break;
            case UP:
                paddleTwoMovingUp = true;
                break;
            case DOWN:
                paddleTwoMovingDown = true;
                break;
            case R:
                resetGame();
                break;
            case P:
                gameActive = false;
                paused = true;
                break;
            case U:
                if(paused){
                    gameActive = true;
                    paused = false;
                }
                break;

            case ENTER:
                gameActive = false;
                paused = true;

                saveGame();
                gameActive = true;
                paused = false;
                resetGame();
                controller.startScreen();
                break;

            case ESCAPE:
                resetGame();
                controller.startScreen();
                break;

            case SPACE:
                resetGame();
                gameActive = true;
                break;
            default:
                break;
        }
    }

    private void handleKeyReleased(KeyCode code) {
        switch (code) {
            case W:
                paddleOneMovingUp = false;
                break;
            case S:
                paddleOneMovingDown = false;
                break;
            case UP:
                paddleTwoMovingUp = false;
                break;
            case DOWN:
                paddleTwoMovingDown = false;
                break;
            default:
                break;
        }
    }

    private void movePaddles() {
        if (paddleOneMovingUp) {
            movePaddle(paddleOne, -10);
        }
        if (paddleOneMovingDown) {
            movePaddle(paddleOne, 10);
        }
        if (paddleTwoMovingUp) {
            movePaddle(paddleTwo, -10);
        }
        if (paddleTwoMovingDown) {
            movePaddle(paddleTwo, 10);
        }
    }

    /**
     * Retrieves the scene of the game view.
     *
     * @return The scene of the game view.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * handles positioning of scene objects when window is resized
     */

    public void setPositions() {
        newWidth = scene.getWidth();
        newHeight = scene.getHeight();

        playerOneText.relocate(PADDING, PADDING);
        playerTwoText.relocate(newWidth - playerTwoText.getLayoutBounds().getWidth() - PADDING, PADDING);

        paddleOne.setX(PADDING);
        paddleOne.setY(newHeight / 2 - paddleOne.getHeight() / 2);

        paddleTwo.setX(newWidth - paddleTwo.getWidth() - PADDING);
        paddleTwo.setY(newHeight / 2 - paddleTwo.getHeight() / 2);

        ball.setX(newWidth / 2 - ball.getWidth() / 2);
        ball.setY(newHeight / 2 - ball.getWidth() / 2);
    }

    /**
     * Adds change listeners for window resizing.
     */
    private void addSizeChangeListener() {
        pane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                setPositions();
            }
        });

        pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                setPositions();
            }
        });
    }

    /**
     * Moves a paddle vertically.
     *
     * @param paddle The paddle to move.
     * @param deltaY The amount to move the paddle vertically.
     */
    private void movePaddle(Rectangle paddle, double deltaY) {
        double newY = paddle.getY() + deltaY;
        if(newY > 0 && newY < newHeight - paddle.getHeight()) {
            paddle.setY(newY);
        }
    }

    /**
     * Retrieves the first paddle.
     *
     * @return The first paddle.
     */
    public Rectangle getPaddleOne() {
        return paddleOne;
    }

    /**
     * Retrieves the second paddle.
     *
     * @return The second paddle.
     */
    public Rectangle getPaddleTwo() {
        return paddleTwo;
    }

    /**
     * Retrieves the ball.
     *
     * @return The ball.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Sets the player one.
     *
     * @param playerOne The player one to set.
     */
    public void setPlayerOne(Player playerOne){
        this.playerOne = playerOne;
        updateScore();
    }

    /**
     * Sets the player two.
     *
     * @param playerTwo The player two to set.
     */
    public void setPlayerTwo(Player playerTwo){
        this.playerTwo = playerTwo;
        updateScore();
    }

    /**
     * Sets the game status.
     *
     * @param gameActive The game status to set.
     */
    public void gameStatus(boolean gameActive){
        this.gameActive = gameActive;
    }

    /**
     * Updates player scores on the screen.
     */
    public void updateScore() {
        playerOneText.setText(playerOne.getName() + ": " + playerOne.getScore());
        playerTwoText.setText(playerTwo.getName() + ": " + playerTwo.getScore());
    }

    /**
     * Moves the ball based on game logic.
     */
    private void moveBall() {
        if(paused){
            showScoreMessage("PAUSED\nPress U to Play", 0.01);
        }
        playerOneText.relocate(PADDING, PADDING);
        playerTwoText.relocate(newWidth - playerTwoText.getLayoutBounds().getWidth() - PADDING, PADDING);

        double newX = ball.getX() + ball.getSpeedX();
        double newY = ball.getY() + ball.getSpeedY();

        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        if(gameActive){
            if(newX <= PADDING){
                handleScore(playerTwo);
            }

            if(newX >= sceneWidth - ball.getWidth() - PADDING){
                handleScore(playerOne);
            }

            if(newX <= PADDING || newX >= sceneWidth - ball.getWidth() - PADDING){
                newY = newHeight/2;
                newX = newWidth/2;
            }


            if (newY <= 0 || newY >= sceneHeight - ball.getHeight()) {
                ball.setSpeedY(-ball.getSpeedY());
                newY = ball.getY() + ball.getSpeedY();
            }

            if (newX <= paddleOne.getX() + paddleOne.getWidth() && newY + ball.getHeight() >= paddleOne.getY() && newY <= paddleOne.getY() + paddleOne.getHeight()) {
                ball.setSpeedX(-ball.getSpeedX());
                newX = ball.getX() + ball.getSpeedX();
                if(ball.getSpeedX() <= paddleOne.getWidth() && bounceCount % 3 == 0) {
                    ball.setSpeedX(ball.getSpeedX() + speedIncrease);
                }
                bounceCount++;
            }

            if (newX + ball.getWidth() >= paddleTwo.getX() && newY + ball.getHeight() >= paddleTwo.getY() && newY <= paddleTwo.getY() + paddleTwo.getHeight()) {
                ball.setSpeedX(-ball.getSpeedX());
                newX = ball.getX() + ball.getSpeedX();
                if(ball.getSpeedX() <= paddleTwo.getWidth() && bounceCount % 3 == 0) {
                    ball.setSpeedX(ball.getSpeedX() - speedIncrease);
                }
                bounceCount++;
            }

            ball.setX(newX);
            ball.setY(newY);
        }
    }

    /**
     * Handles scoring logic.
     *
     * @param player The player who scored.
     */
    private void handleScore(Player player){
        player.incrementScore();
        updateScore();
        bounceCount = 0;
        if(player.getScore() < maxScore){
            showScoreMessage(player.getName() + " Scored!", 1.5);
            ball.setSpeedX(-ball.getSpeedX()*3/ball.getSpeedX());
            ball.setSpeedY(-ball.getSpeedY());
        }else{
            showScoreMessage(player.getName().toUpperCase() + " WINS!!!!\nPress Space To Start Again", 10);
            gameActive = false;
        }

    }

    /**
     * Displays a score message for a specified duration.
     *
     * @param message The message to display.
     * @param duration The duration for which the message should be displayed.
     */
    public void showScoreMessage(String message, double duration) {
        scoreText.setText(message);
        scoreText.setVisible(true);
        scoreText.relocate((newWidth - scoreText.getLayoutBounds().getWidth())/2, newHeight/2);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(duration),
                event -> scoreText.setVisible(false)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame(){
        playerOne.resetScore();
        playerTwo.resetScore();
        showScoreMessage("", 0);

        updateScore();
        setPositions();
    }

    /**
     * Sets the maximum score.
     *
     * @param maxScore The maximum score to set.
     */
    public void setMaxScore(int maxScore){
        this.maxScore = maxScore;
    }


    /**
     * Sets the speed increase.
     *
     * @param speedIncrease The speed increase to set.
     */
    public void setSpeedIncrease(double speedIncrease){
        this.speedIncrease = speedIncrease;
    }

    public boolean checkBallPaddleCollision(Ball ball, Rectangle paddle) {
        double ballCenterX = ball.getX() + ball.getWidth() / 2;
        double ballCenterY = ball.getY() + ball.getHeight() / 2;

        double paddleLeft = paddle.getX();
        double paddleRight = paddle.getX() + paddle.getWidth();
        double paddleTop = paddle.getY();
        double paddleBottom = paddle.getY() + paddle.getHeight();

        if (ballCenterX >= paddleLeft && ballCenterX <= paddleRight && ballCenterY >= paddleTop && ballCenterY <= paddleBottom) {
            return true;
        }

        return false;
    }

    public void setController(GameController controller){
        this.controller = controller;
    }

    private void saveGame(){
        TextInputDialog dialog = new TextInputDialog("Game Name");
        dialog.setHeaderText("Save Game");
        dialog.setContentText("Enter game name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                String player1Name = playerOne.getName();
                String player2Name = playerTwo.getName();
                int player1Score = playerOne.getScore();
                int player2Score = playerTwo.getScore();
                int gameLimit = maxScore;
                GameDAO.saveGame(name, player1Name, player2Name, player1Score, player2Score, gameLimit);
                GameController.showAlert("Game saved successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
