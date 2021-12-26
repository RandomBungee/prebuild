package de.flamefoxes.build.command;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import de.flamefoxes.build.BuildUtil;
import de.flamefoxes.build.player.IBuildPlayer;
import de.flamefoxes.build.player.SqlBuildPlayer;
import de.flamefoxes.build.sql.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCommand implements CommandExecutor {

  private final IBuildPlayer iBuildPlayer;

  public DeleteCommand(BuildUtil buildUtil) {
    this.iBuildPlayer = SqlBuildPlayer.create(Mysql.connection, buildUtil);
  }

  @Override
  public boolean onCommand(
    CommandSender commandSender,
    Command command,
    String labels,
    String[] arguments
  ) {
    if (checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player) commandSender;
    if (arguments.length != 1) {
      player.sendMessage(BuildUtil.PREFIX + "§7Bitte benutze §a/delete <PlayerName>§7!");
      return true;
    }
    String targetName = arguments[0];
    iBuildPlayer.delete(targetName);
    MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager()
      .getPlugin("Multiverse-Core");
    MVWorldManager mvWorldManager = multiverseCore.getMVWorldManager();
    mvWorldManager.deleteWorld(targetName, true, true);
    player
      .sendMessage(BuildUtil.PREFIX
        + "§7Du hast den Berwerber §e" + targetName + " §cgelöscht§7!");
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if (!command.getName().equalsIgnoreCase("delete")) {
      return true;
    }
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    if (!commandSender.hasPermission("prebuild.command.delete")) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§7Dafür hast du §ckeine §7Rechte!");
      return true;
    }
    return false;
  }
}
