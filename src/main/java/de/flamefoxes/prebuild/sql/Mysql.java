package de.flamefoxes.prebuild.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    private final String host;
    private final String user;
    private final String password;
    private final String database;
    private final int port;
    public static Connection connection;

    public Mysql(
            String host,
            String user,
            String password,
            String database,
            int port
    ) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
        connect();
    }

    private void connect() {
        try {
            connection
                    = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect0true",
                    this.user, this.password);
        } catch (SQLException failureToConnect) {
            System.err.println("Can´t connect to Mysql-Database: " + failureToConnect.getMessage());
        }
    }

    public void createTable() {
        if(connection == null) { return; }
        try {
            connection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS player_data(player_name VARCHAR(16)," +
                            " theme VARCHAR(100)," +
                            " email VARCHAR(100)," +
                            " discord VARCHAR(100)," +
                            " submitted INT," +
                            " status INT)");
        } catch (SQLException createTableFailure) {
            System.err.println("Can´t create Table: " + createTableFailure.getMessage());
        }
    }
}
