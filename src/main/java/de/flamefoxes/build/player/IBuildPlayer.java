package de.flamefoxes.build.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBuildPlayer {
  void create(BuildPlayer buildPlayer);

  void change(BuildPlayer buildPlayer);

  List<String> buildPlayers();

  Optional<BuildPlayer> find(UUID uniqueId);

  boolean exist(UUID uniqueId);
}
