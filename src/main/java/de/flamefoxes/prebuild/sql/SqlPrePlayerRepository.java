package de.flamefoxes.prebuild.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlPrePlayerRepository implements PrePlayer {
    private final Connection connection;

    private SqlPrePlayerRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(
            String name,
            String theme,
            String email,
            String discord,
            int submitted,
            int status,
      String checkKey
    ) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("INSERT INTO player_data (player_name,theme,email,discord,submitted,status,check_key) VALUES (?,?,?,?,?,?,?)");
            createStatement(preparedStatement, name, theme, email, discord, submitted, status, checkKey);
            updateAndCloseStatement(preparedStatement);
        } catch (SQLException cantCreatePlayer) {
            System.err.println("Can´t create Player in SQL: " + cantCreatePlayer.getMessage());
        }
    }

    private void createStatement(
            PreparedStatement preparedStatement,
            String name,
            String theme,
            String email,
            String discord,
            int submitted,
            int status,
            String checkKey
    ) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, theme);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, discord);
        preparedStatement.setInt(5, submitted);
        preparedStatement.setInt(6, status);
        preparedStatement.setString(7, checkKey);
    }

    public void change(
            String name,
            String theme,
            String email,
            String discord,
            int submitted,
            int status,
             String checkKey
    ) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement(
                            "UPDATE player_data SET theme = ?, email = ?, discord = ?, submitted = ?, status = ?, check_key WHERE player_name = ?"
            );
            updateStatement(preparedStatement, name, theme, email, discord, submitted, status, checkKey);
            updateAndCloseStatement(preparedStatement);
        } catch (SQLException cantCreatePlayer) {
            System.err.println("Can´t update Player in SQL: " + cantCreatePlayer.getMessage());
        }
    }

    private void updateStatement(
            PreparedStatement preparedStatement,
            String name,
            String theme,
            String email,
            String discord,
            int submitted,
            int status,
            String checkKey
    ) throws SQLException {
        preparedStatement.setString(1, theme);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, discord);
        preparedStatement.setInt(4, submitted);
        preparedStatement.setInt(5, status);
        preparedStatement.setString(6, name);
        preparedStatement.setString(7, checkKey);
    }

    public int submitted(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return submittedStatement(preparedStatement, name);
        } catch (SQLException cantCatchStatus) {
            System.err.println("Can´t getting the Submitted: " + cantCatchStatus.getMessage());
        }
        return 0;
    }

    private int submittedStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("submitted");
        }
        return 0;
    }

    public int status(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return statusStatement(preparedStatement, name);
        } catch (SQLException cantCatchStatus) {
            System.err.println("Can´t getting the Status: " + cantCatchStatus.getMessage());
        }
        return 0;
    }

    private int statusStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("status");
        }
        return 0;
    }

    public String checkKey(String name) {
        try {
            PreparedStatement preparedStatement
              = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return checkKeyStatement(preparedStatement, name);
        } catch (SQLException cantCatchStatus) {
            System.err.println("Can´t getting the Status: " + cantCatchStatus.getMessage());
        }
        return "";
    }

    private String checkKeyStatement(
      PreparedStatement preparedStatement,
      String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("check_key");
        }
        return "";
    }

    public String theme(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return themeStatement(preparedStatement, name);
        } catch (SQLException cantCatchTheme) {
            System.err.println("Can´t getting Theme: " + cantCatchTheme.getMessage());
        }
        return "no Theme";
    }

    private String themeStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString("theme");
        }
        return "";
    }

    public String discord(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return discordStatement(preparedStatement, name);
        } catch (SQLException cantCatchTheme) {
            System.err.println("Can´t getting Theme: " + cantCatchTheme.getMessage());
        }
        return "no Discord";
    }

    private String discordStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString("discord");
        }
        return "";
    }

    public String email(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return emailStatement(preparedStatement, name);
        } catch (SQLException cantCatchTheme) {
            System.err.println("Can´t getting Theme: " + cantCatchTheme.getMessage());
        }
        return "no E-Mail";
    }

    private String emailStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString("email");
        }
        return "";
    }

    public List<String> players() {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data");
            return playersStatement(preparedStatement);
        } catch (SQLException cantCatchAllPlayers)  {
            System.err.println("Can´t get all Player from Database: " + cantCatchAllPlayers.getMessage());
        }
        return null;
    }

    private List<String> playersStatement(PreparedStatement preparedStatement) throws SQLException {
        List<String> players = new ArrayList<String>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            players.add(resultSet.getString("player_name"));
        }
        return players;
    }

    public boolean exist(String name) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM player_data WHERE player_name = ?");
            return existStatement(preparedStatement, name);
        } catch (SQLException cantCheckExisting) {
            System.err.println("Can´t Check if Player exist: " + cantCheckExisting.getMessage());
        }
        return false;
    }

    private boolean existStatement(
            PreparedStatement preparedStatement,
            String name
    ) throws SQLException {
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return true;
        }
        return false;
    }

    private void updateAndCloseStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static SqlPrePlayerRepository create(Connection connection) {
        return new SqlPrePlayerRepository(connection);
    }
}
