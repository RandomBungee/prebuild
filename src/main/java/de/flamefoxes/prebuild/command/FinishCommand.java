package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FinishCommand implements CommandExecutor {
    private final PreBuilding preBuilding;
    private final ArrayList<Player> confirm;
    private final PrePlayer prePlayer;

    public FinishCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
        this.confirm = new ArrayList<Player>();
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    public boolean onCommand(
            CommandSender commandSender,
            Command command,
            String label,
            String[] arguments
    ) {
        if(!checkCommandComponents(commandSender, command)) {
            Player player = (Player)commandSender;
            if(arguments.length == 1) {
                if(!confirm.contains(player)) {
                    confirm.add(player);
                    player.sendMessage(PreBuilding.PREFIX + "§7Bist du sicher das du dein Plot abgeben möchtest?");
                    player.sendMessage(PreBuilding.PREFIX + "§7Wenn ja, dann gebe §c/finish confirm ein");
                    return true;
                }
                player.sendMessage(PreBuilding.PREFIX + "§7Bitte gebe §c/finish confirm §7ein!");
                return true;
            }
            if(arguments.length == 2) {
                if(confirm.contains(player)) {
                    confirm.remove(player);
                    String theme = prePlayer.theme(player.getName());
                    String discord = prePlayer.discord(player.getName());
                    String email = prePlayer.email(player.getName());
                    prePlayer.change(player.getName(), theme, email, discord, 1, 0);
                    player.sendMessage(PreBuilding.PREFIX + "§aDu hast dein Plot abgeben!");
                }
            }
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
        if(!command.getName().equalsIgnoreCase("finish")) {
            return true;
        }
        return false;
    }
}
