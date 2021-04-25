package de.flamefoxes.build.command;

import de.flamefoxes.build.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class SetupCommand implements CommandExecutor {
  private final BuildUtil buildUtil;

  public SetupCommand(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
  }

  @Override
  public boolean onCommand(
    CommandSender commandSender,
    Command command,
    String labels,
    String[] arguments
  ) {
    if(checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player)commandSender;
    buildUtil.locations().location("spawn");
    player.sendMessage(BuildUtil.PREFIX + "§7Du hast den §eSpawn §aerfolgreich §7gesetzt!");
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if(!command.getName().equalsIgnoreCase("setspawn")) {
      return true;
    }
    if(!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    if(!commandSender.hasPermission("prebuild.command.setspawn")) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§7Dafür hast du §ckeine §7Rechte!");
      return true;
    }
    return false;
  }
}
