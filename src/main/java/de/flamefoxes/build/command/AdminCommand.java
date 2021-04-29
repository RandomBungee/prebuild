package de.flamefoxes.build.command;

import de.flamefoxes.build.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class AdminCommand implements CommandExecutor {

  private final BuildUtil buildUtil;

  public AdminCommand(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
  }

  @Override
  public boolean onCommand(
    CommandSender commandSender,
    Command command,
    String label,
    String[] arguments
  ) {
    if (checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player) commandSender;
    buildUtil.inventory().createInventory("list", player);
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if (!command.getName().equalsIgnoreCase("admin")) {
      return true;
    }
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    if (!commandSender.hasPermission("prebuild.command.admin")) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§7Dafür hast du §ckeine §7Rechte!");
      return true;
    }
    return false;
  }
}
