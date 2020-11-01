package de.flamefoxes.prebuild.score;

import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Score {
    private static final PrePlayer PRE_PLAYER = SqlPrePlayerRepository.create(Mysql.connection);

    public static void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("aaa");
        if(objective == null) {
            objective = scoreboard.registerNewObjective("aaa", "bbb");
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§eVorbau-Server");
        objective.getScore("§a").setScore(11);
        objective.getScore("§8» §7Online").setScore(10);
        objective.getScore("§8» §a" + Bukkit.getOnlinePlayers().size() + "§7/" + Bukkit.getMaxPlayers()).setScore(9);
        objective.getScore("§d").setScore(8);
        objective.getScore("§8» §7Bauthema").setScore(7);
        objective.getScore("§8» §e" + PRE_PLAYER.theme(player.getName())).setScore(6);
        objective.getScore("§1").setScore(5);
        objective.getScore("§8» §7Status").setScore(4);
        objective.getScore("§8» " + status(player)).setScore(3);
        objective.getScore("§4").setScore(2);
        objective.getScore("§8§m---------------").setScore(1);
        player.setScoreboard(scoreboard);
    }

    private static String status(Player player) {
        if(PRE_PLAYER.status(player.getName()) == 0) {
            return "§cNicht Registriert";
        } else if(PRE_PLAYER.status(player.getName()) == 1) {
            return "§bIn Bearbeitung";
        } else if(PRE_PLAYER.status(player.getName()) == 2) {
            return "§aAngenommen";
        } else if(PRE_PLAYER.status(player.getName()) == 3) {
            return "§cAbgelehnt";
        }
        return "ERROR";
    }
}
