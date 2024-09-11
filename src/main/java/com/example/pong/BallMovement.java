package com.example.pong;

/**
 * Represents the movement of the ball in the Pong game.
 */
public class BallMovement implements Runnable {
    private final Ball ball;
    private final double speedX;
    private final double speedY;

    /**
     * Constructs a BallMovement object for the given ball.
     *
     * @param ball The ball to move.
     */
    public BallMovement(Ball ball) {
        this.ball = ball;
        this.speedX = ball.getSpeedX();
        this.speedY = ball.getSpeedY();
    }

    /**
     * The main run method for the BallMovement thread.
     * This method continuously moves the ball and sleeps for a specified time.
     */
    @Override
    public void run() {
        while (true) {
            moveBall();
            try {
                Thread.sleep(50); // Adjust the delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Moves the ball based on its current speed.
     */
    private void moveBall() {
        double newX = ball.getX() + ball.getSpeedX();
        double newY = ball.getY() + ball.getSpeedY();
    }
}
