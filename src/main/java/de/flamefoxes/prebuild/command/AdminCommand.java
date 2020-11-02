package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    private final PreBuilding preBuilding;

    public AdminCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
    }

    public boolean onCommand(
            CommandSender commandSender,
            Command command,
            String Label,
            String[] arguments
    ) {
        if(!checkCommandComponents(commandSender, command)) {
            Player player = (Player)commandSender;
            preBuilding.adminInventory().createInventory(player);
        }
        return false;
    }

    private boolean checkCommandComponents(
            CommandSender commandSender,
            Command command
    ) {
        if(!(commandSender instanceof Player)) {
            return true;
        }
        if(!command.getName().equalsIgnoreCase("admin")) {
            return true;
        }
        if(!commandSender.hasPermission("prebuild.admingui")) {
            return true;
        }
        return false;
    }
}
