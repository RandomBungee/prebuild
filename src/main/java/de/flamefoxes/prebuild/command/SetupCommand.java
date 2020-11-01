package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {
    private final PreBuilding preBuilding;

    public SetupCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
    }

    public boolean onCommand(
            CommandSender commandSender,
            Command command,
            String label,
            String[] arguments) {
        if(!checkCommandComponents(commandSender, command)) {
            Player player = (Player)commandSender;
            preBuilding.locations().setLocation("spawn", player.getLocation());
            player.sendMessage(PreBuilding.PREFIX + "§7Du hast die Location für den §aSpawn §7gesetzt!");
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
        if(!command.getName().equalsIgnoreCase("setspawn")) {
            return true;
        }
        return false;
    }
}
