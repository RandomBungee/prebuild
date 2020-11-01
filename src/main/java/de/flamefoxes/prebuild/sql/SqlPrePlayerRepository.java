package de.flamefoxes.prebuild.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            int status
    ) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("INSERT INTO player_data (player_name,theme,email,discord,submitted,status) VALUES (?,?,?,?,?,?)");
            createStatement(preparedStatement, name, theme, email, discord, submitted, status);
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
            int status
    ) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, theme);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, discord);
        preparedStatement.setInt(5, submitted);
        preparedStatement.setInt(6, status);
    }

    public void change(
            String name,
            String theme,
            String email,
            String discord,
            int submitted,
            int status
    ) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement(
                            "UPDATE player_data SET theme = ?, email = ?, discord = ?, submitted = ?, status = ? WHERE player_name = ?"
            );
            createStatement(preparedStatement, name, theme, email, discord, submitted, status);
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
            int status
    ) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, theme);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, discord);
        preparedStatement.setInt(5, submitted);
        preparedStatement.setInt(6, status);
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

    private void updateAndCloseStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static SqlPrePlayerRepository create(Connection connection) {
        return new SqlPrePlayerRepository(connection);
    }
}
