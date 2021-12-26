package de.flamefoxes.build.command;

import de.flamefoxes.build.BuildUtil;
import de.flamefoxes.build.player.BuildPlayer;
import de.flamefoxes.build.player.IBuildPlayer;
import de.flamefoxes.build.player.SqlBuildPlayer;
import de.flamefoxes.build.sql.Mysql;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InformationCommand implements CommandExecutor {

  private final IBuildPlayer iBuildPlayer;

  public InformationCommand(BuildUtil buildUtil) {
    this.iBuildPlayer = SqlBuildPlayer.create(Mysql.connection, buildUtil);
  }

  @Override
  public boolean onCommand(
    @NotNull CommandSender commandSender,
    @NotNull Command command,
    @NotNull String label,
    @NotNull String[] arguments
  ) {
    if (checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player) commandSender;
    if (arguments.length == 0) {
      Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(player.getUniqueId());
      BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
      String theme = buildPlayer.getTheme();
      String submitted = (buildPlayer.getSubmitted() != 0) ? "§aAbgegeben" : "§cNicht abgegeben";
      String applyKey = buildPlayer.getApplyKey();
      player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
      player.sendMessage(BuildUtil.PREFIX + "§7Bauthema: " + theme);
      player.sendMessage(BuildUtil.PREFIX + "§7Status: " + submitted);
      player.sendMessage(BuildUtil.PREFIX + "§7Apply-Key: " + applyKey);
      player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
      return true;
    }
    if (arguments.length == 1) {
      if (!player.hasPermission("prebuild.command.info")) {
        player.sendMessage(BuildUtil.PREFIX + "§7Dafür hast du §ckeine §7Rechte!");
        return true;
      }
      try {
        String playerName = arguments[0];
        UUID uuid = Bukkit.getPlayer(playerName).getUniqueId();
        Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(uuid);
        BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
        String theme = buildPlayer.getTheme();
        String submitted = (buildPlayer.getSubmitted() != 0) ? "§aAbgegeben" : "§cNicht abgegeben";
        String pluginKind = buildPlayer.getPluginKind();
        String structureKind = buildPlayer.getStructureKind();
        String buildStyle = buildPlayer.getBuildStyle();
        player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
        player.sendMessage(BuildUtil.PREFIX + "§7Player: " + playerName);
        player.sendMessage(BuildUtil.PREFIX + "§7Bauthema: " + theme);
        player.sendMessage(BuildUtil.PREFIX + "§7Status: " + submitted);
        player.sendMessage(BuildUtil.PREFIX + "§7Plugins: " + pluginKind);
        player.sendMessage(BuildUtil.PREFIX + "§7Strukturen: " + structureKind);
        player.sendMessage(BuildUtil.PREFIX + "§7Baustyle: " + buildStyle);
        player.sendMessage("§7---------------<§eInformationen§7>§7---------------");
      } catch (Exception ignore) {
      }
    }
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if (!command.getName().equalsIgnoreCase("info")) {
      return true;
    }
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    return false;
  }

  public BuildPlayer noSuchPlayer() {
    return BuildPlayer.newBuilder()
      .setName("")
      .setUniqueId(UUID.randomUUID())
      .setApplyKey("0")
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
