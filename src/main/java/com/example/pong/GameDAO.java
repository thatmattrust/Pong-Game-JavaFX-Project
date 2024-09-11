package com.example.pong;

import java.sql.*;

public class GameDAO {
    public static void saveGame(String gameName, String playerOneName, String playerTwoName, int playerOneScore, int playerTwoScore, int gameLimit) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Game (game_name, player1_name, player2_name, player1_score, player2_score, game_limit) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, playerOneName);
            preparedStatement.setString(3, playerTwoName);
            preparedStatement.setInt(4, playerOneScore);
            preparedStatement.setInt(5, playerTwoScore);
            preparedStatement.setInt(6, gameLimit);
            preparedStatement.executeUpdate();
        }
    }

    public static GameState loadLatestGame() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Game ORDER BY id DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String gameName = resultSet.getString("game_name");
                String playerOneName = resultSet.getString("player1_name");
                String playerTwoName = resultSet.getString("player2_name");
                int playerOneScore = resultSet.getInt("player1_score");
                int playerTwoScore = resultSet.getInt("player2_score");
                int gameLimit = resultSet.getInt("game_limit");

                return new GameState.Builder()
                        .playerOneName(playerOneName)
                        .playerTwoName(playerTwoName)
                        .playerOneScore(playerOneScore)
                        .playerTwoScore(playerTwoScore)
                        .maxScore(gameLimit)
                        .build();
            }
        }
        return null;
    }
}
