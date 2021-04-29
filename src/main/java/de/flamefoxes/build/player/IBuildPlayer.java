package de.flamefoxes.build.player;

import java.util.*;

public interface IBuildPlayer {

  void create(BuildPlayer buildPlayer);

  void change(BuildPlayer buildPlayer);

  List<String> buildPlayers();

  Optional<BuildPlayer> find(UUID uniqueId);

  boolean exist(UUID uniqueId);

  void delete(String playerName);
}
