package de.flamefoxes.build.player;

import java.util.List;
import java.util.Optional;

public interface IBuildPlayer {
  void create(BuildPlayer buildPlayer);

  void change(BuildPlayer buildPlayer);

  List<String> buildPlayersUnique();

  Optional<BuildPlayer> find(String uniqueId);
}
