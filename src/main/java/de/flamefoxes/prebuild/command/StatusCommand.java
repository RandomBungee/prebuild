package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor {
    private final PreBuilding preBuilding;
    private final PrePlayer prePlayer;

    public StatusCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    public boolean onCommand(
            CommandSender commandSender,
            Command command,
            String label,
            String[] arguments) {
        if(!checkCommandComponents(commandSender, command, arguments)) {
            Player player = (Player)commandSender;
            String targetName = arguments[0];
            String statusChange = arguments[1];
            String theme = prePlayer.theme(targetName);
            String email = prePlayer.email(targetName);
            String discord = prePlayer.discord(targetName);
            int submitted = prePlayer.submitted(targetName);
            if(statusChange.equalsIgnoreCase("angenommen")) {
                prePlayer.change(targetName, theme, email, discord, submitted, 2);
                player.sendMessage(PreBuilding.PREFIX + "§7Du hast die Bewerbung von §e" + targetName + " §aangenommen§7!");
            } else if(statusChange.equalsIgnoreCase("abgelehnt")) {
                prePlayer.change(targetName, theme, email, discord, submitted, 3);
                player.sendMessage(PreBuilding.PREFIX + "§7Du hast die Bewerbung von §e" + targetName + " §cabgelehnt§7!");
            }
        }
        return false;
    }

    private boolean checkCommandComponents(
            CommandSender commandSender,
            Command command,
            String[] arguments
    ) {
        if(!(commandSender instanceof Player)) {
            return true;
        }
        if(!command.getName().equalsIgnoreCase("contact")) {
            return true;
        }
        if(!(arguments.length == 3)) {
            commandSender.sendMessage(PreBuilding.PREFIX + "§7Bitte verwende §c/status <Player> <Angenommen|Abgelehnt>");
            return true;
        }
        return false;
    }
}