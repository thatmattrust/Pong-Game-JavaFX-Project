package com.example.pong;

public class GameState {
    private String playerOneName;
    private String playerTwoName;
    private int playerOneScore;
    private int playerTwoScore;
    private int maxScore;

    private GameState(Builder builder) {
        this.playerOneName = builder.playerOneName;
        this.playerTwoName = builder.playerTwoName;
        this.playerOneScore = builder.playerOneScore;
        this.playerTwoScore = builder.playerTwoScore;
        this.maxScore = builder.maxScore;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public int getMaxScore() {
        return maxScore;
    }


    public static class Builder {
        private String playerOneName;
        private String playerTwoName;
        private int playerOneScore;
        private int playerTwoScore;
        private int maxScore;

        public Builder playerOneName(String playerOneName) {
            this.playerOneName = playerOneName;
            return this;
        }

        public Builder playerTwoName(String playerTwoName) {
            this.playerTwoName = playerTwoName;
            return this;
        }

        public Builder playerOneScore(int playerOneScore) {
            this.playerOneScore = playerOneScore;
            return this;
        }

        public Builder playerTwoScore(int playerTwoScore) {
            this.playerTwoScore = playerTwoScore;
            return this;
        }

        public Builder maxScore(int maxScore) {
            this.maxScore = maxScore;
            return this;
        }


        public GameState build() {
            return new GameState(this);
        }
    }
}
