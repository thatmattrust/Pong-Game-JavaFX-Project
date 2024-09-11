package com.example.pong;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

/**
 * Represents the movement of a paddle in the Pong game.
 */
public class PaddleMovement implements Runnable {
    private final Rectangle paddle;
    private final double deltaY;

    /**
     * Constructs a PaddleMovement object for the given paddle and delta y.
     *
     * @param paddle The paddle to move.
     * @param deltaY The change in y-coordinate for the paddle's movement.
     */
    public PaddleMovement(Rectangle paddle, double deltaY) {
        this.paddle = paddle;
        this.deltaY = deltaY;
    }

    /**
     * The main run method for the PaddleMovement thread.
     * This method continuously moves the paddle using JavaFX's Platform.runLater method and sleeps for a specified time.
     */
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> movePaddle());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Moves the paddle vertically based on the specified delta y value.
     */
    private void movePaddle() {
        double newY = paddle.getY() + deltaY;
        //paddle.setY(newY);
    }
}
