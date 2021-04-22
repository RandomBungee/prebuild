package de.flamefoxes.build.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    preparedStatement.setString(2, buildPlayer.getUniqueId());
    preparedStatement.setString(3, buildPlayer.getTheme());
    preparedStatement.setInt(4, buildPlayer.getSubmitted());
    preparedStatement.setInt(5, buildPlayer.getApplyKey());
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
    preparedStatement.setInt(4, buildPlayer.getApplyKey());
    preparedStatement.setString(5, buildPlayer.getStructureKind());
    preparedStatement.setString(6, buildPlayer.getBuildStyle());
    preparedStatement.setString(7, buildPlayer.getPluginKind());
    preparedStatement.setString(8, buildPlayer.getUniqueId());
    updateAndCloseStatement(preparedStatement);
  }

  @Override
  public List<String> buildPlayersUnique() {
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
  public Optional<BuildPlayer> find(String uniqueId) {
    return Optional.empty();
  }

  private void updateAndCloseStatement(PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.executeUpdate();
    preparedStatement.close();
  }

  public static SqlBuildPlayer create(Connection connection) {
    return new SqlBuildPlayer(connection);
  }
}
