package com.example.pong;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Represents the ball in the Pong game.
 */

public class Ball extends Rectangle {
    private double speedX;
    private double speedY;

    /**
     * Constructs a ball with the given size and color.
     *
     * @param size  The size of the ball (both width and height).
     * @param color The color of the ball.
     */
    public Ball(double size, Color color) {
        super(size, size, color);

        Random random = new Random();
        speedX = random.nextBoolean() ? 2 : -2;
        speedY = random.nextBoolean() ? 2 : -2;

    }

    //Setters

    /**
     * Sets the horizontal speed of the ball.
     *
     * @param speedX The horizontal speed of the ball.
     */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     * Sets the vertical speed of the ball.
     *
     * @param speedY The vertical speed of the ball.
     */
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    //Getters
    /**
     * Returns the vertical speed of the ball.
     *
     * @return The vertical speed of the ball.
     */
    public double getSpeedY() {
        return speedY;
    }

    /**
     * Returns the horizontal speed of the ball.
     *
     * @return The horizontal speed of the ball.
     */
    public double getSpeedX() {
        return speedX;
    }

    /**
     * Returns the size of the ball.
     *
     * @return The size of the ball (both width and height).
     */
    public double getSize(){
        return super.getHeight();
    }

}
