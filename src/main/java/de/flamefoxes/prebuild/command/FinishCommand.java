package de.flamefoxes.prebuild.command;

import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import de.flamefoxes.prebuild.util.Algorithm;
import java.util.List;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
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
            if(arguments.length == 0) {
                int submitted = prePlayer.submitted(player.getName());
                if(submitted != 1) {
                    if(!confirm.contains(player)) {
                        confirm.add(player);
                        player.sendMessage(PreBuilding.PREFIX + "§7Bist du sicher das du dein Plot abgeben möchtest?");
                        player.sendMessage(PreBuilding.PREFIX + "§7Wenn ja, dann gebe §c/finish confirm §7ein");
                    } else {
                        player.sendMessage(PreBuilding.PREFIX + "§7Bitte gebe §c/finish confirm §7ein!");
                    }
                }
            } else if(arguments.length == 1) {
                if(arguments[0].equalsIgnoreCase("confirm")) {
                    if(confirm.contains(player)) {
                        confirm.remove(player);
                        String theme = prePlayer.theme(player.getName());
                        String discord = prePlayer.discord(player.getName());
                        String email = prePlayer.email(player.getName());
                        String checkKey = Algorithm.generate(10);
                        String structure = prePlayer.structure(player.getName());
                        String style = prePlayer.style(player.getName());
                        String plugin = prePlayer.plugin(player.getName());
                        prePlayer.change(player.getName(), theme, email, discord, 1, 1, checkKey, structure, style, plugin);
                        player.sendMessage(PreBuilding.PREFIX + "§aDu hast dein Plot abgeben!");
                        TextComponent textComponent  = new TextComponent(PreBuilding.PREFIX + "§aDeine ApplyID ist: " + checkKey);
                        textComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Klicke hier um deine ApplyID zu kopieren").create()));
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, checkKey));
                        player.spigot().sendMessage(textComponent);
                    } else {
                        player.sendMessage(PreBuilding.PREFIX + "§7Bitte gebe §c/finish §7ein!");
                    }
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
