package com.example.pong;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a player in the Pong game.
 */
public class Player{

    //Creates variables
    private String name;
    private int score;

    /**
     * Constructs a Player object with default name "Player" and initial score 0.
     */
    public Player() {
        name = "Player";
    }

    //Setters

    /**
     * Sets the name of the player.
     *
     * @param name The name to set for the player.
     */
    public void setName(String name){
        this.name = name;
    }

    //Getters
    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the score of the player.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score of the player to 0.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Increments the score of the player by 1.
     */
    public void incrementScore() {
        score++;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
