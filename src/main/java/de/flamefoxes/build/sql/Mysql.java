package de.flamefoxes.build.sql;

import java.sql.*;

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
  }

  public void connect() {
    try {
      connection
        = DriverManager
        .getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database
            + "?autoReconnect0true",
          this.user, this.password);
      System.out.println("[MySQL] Connected!");
    } catch (SQLException failureToConnect) {
      System.err.println("Can´t connect to Mysql-Database: " + failureToConnect.getMessage());
    }
  }

  public void closeConnection() {
    if (connection == null) {
      return;
    }
    try {
      connection.close();
    } catch (SQLException cantCloseConnection) {
      System.err.println("Can´t close Mysql-Connection: " + cantCloseConnection.getMessage());
    }
  }

  public void createTable() {
    if (connection == null) {
      return;
    }
    try {
      connection
        .prepareStatement("CREATE TABLE IF NOT EXISTS player_data(player_name VARCHAR(16)," +
          "unique_id VARCHAR(100)," +
          "theme VARCHAR(100)," +
          "submitted INT," +
          "check_key VARCHAR(100)," +
          "structure TEXT," +
          "build_style TEXT," +
          "plugin TEXT)")
        .executeUpdate();
      System.out.println("[MySQL] Table was created!");
    } catch (SQLException createTableFailure) {
      System.err.println("Can´t create Table: " + createTableFailure.getMessage());
    }
  }
}
