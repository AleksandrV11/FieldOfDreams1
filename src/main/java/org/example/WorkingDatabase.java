package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class WorkingDatabase implements PlayerDao {
    @Override
    public void entryDatabase(HashMap<Long, Player> map) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FieldofDreams1", "postgres", "111");
            String query = "INSERT INTO fieldofdreams (iduser,name,guessLetters,guessLetterPositive,guessWords,guessWordsPositive,percentageLettersGuessedPositive,percentageWordsGuessedPositive) VALUES (?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                for (Map.Entry<Long, Player> entry : map.entrySet()) {
                    Long userId = entry.getKey();
                    Player player = entry.getValue();
                    preparedStatement.setLong(1, userId);
                    preparedStatement.setString(2, player.getName());
                    preparedStatement.setDouble(3, player.getGuessLetters());
                    preparedStatement.setDouble(4, player.getGuessLetterPositive());
                    preparedStatement.setDouble(5, player.getGuessWords());
                    preparedStatement.setDouble(6, player.getGuessWordsPositive());
                    preparedStatement.setDouble(7, player.getPercentageOfLettersGuessed());
                    preparedStatement.setDouble(8, player.getPercentageOfWordsGuessed());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Map<Long, Player> readFromDatabase() {
        Map<Long, Player> resultMap = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FieldofDreams1", "postgres", "111")) {
            String selectQuery = "SELECT iduser,name,guessLetters,guessLetterPositive,guessWords,guessWordsPositive,percentageLettersGuessedPositive,percentageWordsGuessedPositive FROM fieldofdreams";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("iduser");
                    String name = resultSet.getString("name");
                    double guessLetters = resultSet.getDouble("guessLetters");
                    double guessLetterPositive = resultSet.getDouble("guessLetterPositive");
                    double guessWords = resultSet.getDouble("guessWords");
                    double guessWordsPositive = resultSet.getDouble("guessWordsPositive");
                    double percentageLettersGuessedPositive = resultSet.getDouble("percentageLettersGuessedPositive");
                    double percentageWordsGuessedPositive = resultSet.getDouble("percentageWordsGuessedPositive");
                    Player player = new Player(name, guessLetters, guessLetterPositive, guessWords, guessWordsPositive,
                            percentageLettersGuessedPositive, percentageWordsGuessedPositive);
                    resultMap.put(userId, player);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading from database", e);
        }
        return resultMap;
    }
    @Override
    public void clearDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FieldofDreams1", "postgres", "111")) {
            String deleteQuery = "DELETE FROM fieldofdreams";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(deleteQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error clearing database", e);
        }
    }
}
