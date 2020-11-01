package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class StartCommand implements CommandExecutor {
    private final PreBuilding preBuilding;
    private final PrePlayer prePlayer;

    private StartCommand(PreBuilding preBuilding) {
        this.preBuilding = preBuilding;
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    }

    public boolean onCommand(CommandSender commandSender,
                             Command command,
                             String label,
                             String[] arguments
    ) {
        if(!checkCommandComponents(commandSender, command)) {
            Player player = (Player)commandSender;
            String randomTheme = pickRandomTheme();
            preBuilding.getServer().dispatchCommand(player, "p auto");
            prePlayer.create(player.getName(), randomTheme, null, null, 0, 0);
            player.sendRawMessage(PreBuilding.PREFIX + "§7Dein Thema ist: " + randomTheme);
            player.sendMessage(PreBuilding.PREFIX + "§7Wenn du fertig bist gebe §c/finish §7ein um dein Plot abzugeben");
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
        if(!command.getName().equalsIgnoreCase("start")) {
            return true;
        }
        return false;
    }

    private String pickRandomTheme() {
        String theme;
        Random random = new Random();
        int maxThemes = preBuilding.themes.themesFromFile().size() - 1;
        theme = preBuilding.themes.themesFromFile().get(random.nextInt(maxThemes));
        return theme;
    }
}
