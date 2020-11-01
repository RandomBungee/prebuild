package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ContactCommand implements CommandExecutor {
    private final PrePlayer prePlayer;

    public ContactCommand() {
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    public boolean onCommand(
            CommandSender commandSender,
            Command command,
            String label,
            String[] arguments
    ) {
        if(!checkCommandComponents(commandSender, command, arguments)) {
            Player player = (Player)commandSender;
            String specificationType = arguments[0];
            String specification = "";
            for(int i = 1; i < arguments.length; i++) {
                specification += arguments[i] + " ";
            }
            if(specificationType.equalsIgnoreCase("Mail")) {
                String theme = prePlayer.theme(player.getName());
                int status = prePlayer.status(player.getName());
                int submitted = prePlayer.submitted(player.getName());
                prePlayer.change(player.getName(), theme, specification, null, submitted, status);
                player.sendMessage(PreBuilding.PREFIX + "§7Deine E-Mail wurde geändert!");
            }
            if(specificationType.equalsIgnoreCase("Discord")) {
                String theme = prePlayer.theme(player.getName());
                int status = prePlayer.status(player.getName());
                int submitted = prePlayer.submitted(player.getName());
                prePlayer.change(player.getName(), theme, null, specification, submitted, status);
                player.sendMessage(PreBuilding.PREFIX + "§7Dein Discord wurde geändert!");
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
        if(!command.getName().equalsIgnoreCase("start")) {
            return true;
        }
        if(!(arguments.length > 2)) {
            commandSender.sendMessage(PreBuilding.PREFIX + "§7Bitte verwende §c/contact <Mail|Discord> <Name>");
            return true;
        }
        return false;
    }
}
