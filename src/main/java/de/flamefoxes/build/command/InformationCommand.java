package de.flamefoxes.build.command;

import de.flamefoxes.build.BuildUtil;
import de.flamefoxes.build.player.BuildPlayer;
import de.flamefoxes.build.player.IBuildPlayer;
import de.flamefoxes.build.player.SqlBuildPlayer;
import de.flamefoxes.build.sql.Mysql;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InformationCommand implements CommandExecutor {
  private final BuildUtil buildUtil;
  private final IBuildPlayer iBuildPlayer = SqlBuildPlayer.create(Mysql.connection);

  public InformationCommand(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
  }

  @Override
  public boolean onCommand(
    @NotNull CommandSender commandSender,
    @NotNull Command command,
    @NotNull String label,
    @NotNull String[] arguments
  ) {
    if(checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player)commandSender;
    Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(player.getUniqueId());
    BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
    String theme = buildPlayer.getTheme();
    String submitted =  (buildPlayer.getSubmitted() != 0) ? "§cNich abgegeben" : "§aAbgegeben";
    String applyKey = String.valueOf(buildPlayer.getApplyKey());
    player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
    player.sendMessage(BuildUtil.PREFIX + "§7Bauthema: " + theme);
    player.sendMessage(BuildUtil.PREFIX + "§7Status: " + submitted);
    player.sendMessage(BuildUtil.PREFIX + "§7Apply-Key: " + applyKey);
    player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if(!command.getName().equalsIgnoreCase("info")) {
      return true;
    }
    if(!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    return false;
  }

  public BuildPlayer noSuchPlayer() {
    return BuildPlayer.newBuilder()
      .setName("")
      .setUniqueId(UUID.randomUUID())
      .setApplyKey(0)
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
