package de.flamefoxes.prebuild.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                    = connection.prepareStatement("UPDATE player_data SET theme = ?, email = ?, discord = ?, submitted = ?, status = ? WHERE player_name = ?");
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

    public int status(String name) {
        return 0;
    }

    private int statusStatement() {
        return 0;
    }

    public int submitted(String name) {
        return 0;
    }

    private int submittedStatement() {
        return 0;
    }

    private void updateAndCloseStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static SqlPrePlayerRepository create(Connection connection) {
        return new SqlPrePlayerRepository(connection);
    }
}
