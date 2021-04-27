package de.flamefoxes.build.player;

import java.sql.*;
import java.util.*;

public class SqlBuildPlayer implements IBuildPlayer {
  private final Connection connection;

  private SqlBuildPlayer(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(BuildPlayer buildPlayer) {
    try {
      PreparedStatement preparedStatement
        = connection.prepareStatement(
          "INSERT INTO player_data (player_name,unique_id,theme,submitted,check_key,structure,build_style,plugin) VALUES (?,?,?,?,?,?,?,?)"
      );
      createStatement(preparedStatement, buildPlayer);
    } catch (SQLException cantCreatePlayer) {
      System.err.println("Can*t create Player: " + cantCreatePlayer.getMessage());
    }
  }

  private void createStatement(
    PreparedStatement preparedStatement,
    BuildPlayer buildPlayer
  ) throws SQLException {
    preparedStatement.setString(1, buildPlayer.getName());
    preparedStatement.setString(2, buildPlayer.getUniqueId().toString());
    preparedStatement.setString(3, buildPlayer.getTheme());
    preparedStatement.setInt(4, buildPlayer.getSubmitted());
    preparedStatement.setString(5, buildPlayer.getApplyKey());
    preparedStatement.setString(6, buildPlayer.getStructureKind());
    preparedStatement.setString(7, buildPlayer.getBuildStyle());
    preparedStatement.setString(8, buildPlayer.getPluginKind());
    updateAndCloseStatement(preparedStatement);
  }

  @Override
  public void change(BuildPlayer buildPlayer) {
    try {
      PreparedStatement preparedStatement
        = connection.prepareStatement(
          "UPDATE player_data SET player_name = ?,theme = ?,submitted = ?,check_key = ?,structure = ?,build_style = ?,plugin = ? WHERE unique_id = ?"
      );
      changeStatement(preparedStatement, buildPlayer);
    } catch (SQLException cantUpdatePlayer) {
      System.err.println("Can´t update Player");
    }
  }

  private void changeStatement(
    PreparedStatement preparedStatement,
    BuildPlayer buildPlayer
  ) throws SQLException {
    preparedStatement.setString(1, buildPlayer.getName());
    preparedStatement.setString(2, buildPlayer.getTheme());
    preparedStatement.setInt(3, buildPlayer.getSubmitted());
    preparedStatement.setString(4, buildPlayer.getApplyKey());
    preparedStatement.setString(5, buildPlayer.getStructureKind());
    preparedStatement.setString(6, buildPlayer.getBuildStyle());
    preparedStatement.setString(7, buildPlayer.getPluginKind());
    preparedStatement.setString(8, buildPlayer.getUniqueId().toString());
    updateAndCloseStatement(preparedStatement);
  }

  @Override
  public List<String> buildPlayers() {
    try {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM player_data");
      return listStatement(preparedStatement);
    } catch (SQLException cantCatchAllPlayers) {
      System.err.println("Can´t catch all Players: " + cantCatchAllPlayers.getMessage());
    }
    return null;
  }

  private List<String> listStatement(PreparedStatement preparedStatement) throws SQLException {
    ResultSet resultSet = preparedStatement.executeQuery();
    List<String> players = new ArrayList<>();
    while (resultSet.next()) {
      String uniqueId = resultSet.getString("unique_id");
      players.add(uniqueId);
    }
    return players;
  }

  @Override
  public Optional<BuildPlayer> find(UUID uniqueId) {
    try {
      PreparedStatement preparedStatement
        = connection.prepareStatement("SELECT * FROM player_data WHERE unique_id = ?");
      return findStatement(preparedStatement, uniqueId);
    } catch (SQLException cantCatchPlayer) {
      System.err.println("Can´t catch Player: " + cantCatchPlayer.getMessage());
    }
    return Optional.empty();
  }

  private Optional<BuildPlayer> findStatement(
    PreparedStatement preparedStatement,
    UUID uniqueId
  ) throws SQLException {
    preparedStatement.setString(1, uniqueId.toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()) {
      String name = resultSet.getString("player_name");
      String theme = resultSet.getString("theme");
      String structureKind = resultSet.getString("structure");
      String buildStyle = resultSet.getString("build_style");
      String pluginKind = resultSet.getString("plugin");
      int submitted = resultSet.getInt("submitted");
      String applyKey = resultSet.getString("check_key");
      BuildPlayer buildPlayer = BuildPlayer.newBuilder()
        .setName(name)
        .setUniqueId(uniqueId)
        .setTheme(theme)
        .setStructureKind(structureKind)
        .setBuildStyle(buildStyle)
        .setPluginKind(pluginKind)
        .setSubmitted(submitted)
        .setApplyKey(applyKey)
        .build();
      return Optional.of(buildPlayer);
    }
    return Optional.empty();
  }

  @Override
  public boolean exist(UUID uniqueId) {
    try {
      PreparedStatement preparedStatement
        = connection.prepareStatement("SELECT * FROM player_data WHERE unique_id = ?");
      return existStatement(preparedStatement, uniqueId);
    } catch (SQLException cantCheckPlayerExist) {
      System.err.println("Can´t check if Player exist: " + cantCheckPlayerExist.getMessage());
    }
    return false;
  }

  private boolean existStatement(
    PreparedStatement preparedStatement,
    UUID uniqueId
  ) throws SQLException {
    preparedStatement.setString(1, uniqueId.toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    return resultSet.next();
  }

  @Override
  public void delete(String playerName) {
    try {
      PreparedStatement preparedStatement
        = connection.prepareStatement("DELETE FROM player_data WHERE player_name = ?");
      deleteStatement(preparedStatement, playerName);
    } catch (SQLException cantDeletePlayer) {
      System.err.println("Can´t delete Player: " + cantDeletePlayer.getMessage());
    }
  }

  private void deleteStatement(
    PreparedStatement preparedStatement,
    String playerName
  ) throws SQLException {
    preparedStatement.setString(1, playerName);
    updateAndCloseStatement(preparedStatement);
  }

  private void updateAndCloseStatement(PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.executeUpdate();
    preparedStatement.close();
  }

  public static SqlBuildPlayer create(Connection connection) {
    return new SqlBuildPlayer(connection);
  }
}
