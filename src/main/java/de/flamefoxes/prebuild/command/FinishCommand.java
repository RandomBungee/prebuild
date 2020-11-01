package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FinishCommand implements CommandExecutor  {
    private final PreBuilding preBuilding;

    public FinishCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
