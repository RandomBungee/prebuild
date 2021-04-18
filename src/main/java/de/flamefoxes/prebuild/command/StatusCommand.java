package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor {
    private final PrePlayer prePlayer;

    public StatusCommand() {
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
            String checkKey = prePlayer.checkKey(player.getName());
            String structure = prePlayer.structure(player.getName());
            String style = prePlayer.style(player.getName());
            String plugin = prePlayer.plugin(player.getName());
            if(statusChange.equalsIgnoreCase("angenommen")) {
                prePlayer.change(targetName, theme, email, discord, submitted, 2, checkKey, structure, style, plugin);
                player.sendMessage(PreBuilding.PREFIX + "§7Du hast die Bewerbung von §e" + targetName + " §aangenommen§7!");
            } else if(statusChange.equalsIgnoreCase("abgelehnt")) {
                prePlayer.change(targetName, theme, email, discord, submitted, 3, checkKey, structure, style, plugin);
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
        if(!command.getName().equalsIgnoreCase("status")) {
            return true;
        }
        if(!(arguments.length == 2)) {
            commandSender.sendMessage(PreBuilding.PREFIX + "§7Bitte verwende §c/status <Player> <Angenommen|Abgelehnt>");
            return true;
        }
        if(!commandSender.hasPermission("prebuild.status")) {
            commandSender.sendMessage(PreBuilding.PREFIX + "§7Dafür hast du §ckeine §7Rechte!");
            return true;
        }
        return false;
    }
}
