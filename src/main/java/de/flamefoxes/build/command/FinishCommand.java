package de.flamefoxes.build.command;

import de.flamefoxes.build.*;
import de.flamefoxes.build.player.*;
import de.flamefoxes.build.sql.*;
import de.flamefoxes.build.util.*;
import java.util.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class FinishCommand implements CommandExecutor {

  private final ArrayList<Player> confirm = new ArrayList<>();
  private final IBuildPlayer iBuildPlayer;

  public FinishCommand(BuildUtil buildUtil) {
    iBuildPlayer = SqlBuildPlayer.create(Mysql.connection, buildUtil);
  }

  @Override
  public boolean onCommand(
    CommandSender commandSender,
    Command command,
    String label,
    String[] arguments
  ) {
    if (checkCommandComponents(commandSender, command)) {
      return true;
    }
    Player player = (Player) commandSender;
    if (arguments.length == 0) {
      Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(player.getUniqueId());
      BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
      if (buildPlayer.getSubmitted() != 1) {
        if (!confirm.contains(player)) {
          confirm.add(player);
          player.sendMessage(
            BuildUtil.PREFIX + "§7Bist du dir sicher das du deine Welt ageben willst?");
          player.sendMessage(
            BuildUtil.PREFIX + "§7Solltest du dir sicher sein, so gebe §c/finish confirm §7ein!");
        } else {
          player.sendMessage(BuildUtil.PREFIX + "§7Bitte gebe §c/finish confirm §7ein!");
        }
      } else {
        player.sendMessage(BuildUtil.PREFIX + "§cDu hast deine Welt bereits agegeben!");
      }
      return true;
    }

    if (arguments[0].equals("confirm")) {
      if (confirm.contains(player)) {
        Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(player.getUniqueId());
        BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
        confirm.remove(player);
        String applyKey = Algorithm.generate(10);
        iBuildPlayer.change(changedPlayer(buildPlayer, applyKey));
        TextComponent textComponent = new TextComponent(
          BuildUtil.PREFIX + "§aDeine ApplyID ist: " + applyKey);
        textComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,
          new ComponentBuilder("Klicke hier um deine ApplyID zu kopieren").create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, applyKey));
        player.spigot().sendMessage(textComponent);
      } else {
        player.sendMessage(BuildUtil.PREFIX + "§cBitte gebe zuerset /finish ein!");
      }
    }
    return false;
  }

  private boolean checkCommandComponents(
    CommandSender commandSender,
    Command command
  ) {
    if (!command.getName().equalsIgnoreCase("finish")) {
      return true;
    }
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage(BuildUtil.PREFIX + "§cDu musst ein Spieler sein!");
      return true;
    }
    return false;
  }

  private BuildPlayer changedPlayer(
    BuildPlayer player,
    String applyKey
  ) {
    return BuildPlayer.newBuilder()
      .setName(player.getName())
      .setUniqueId(player.getUniqueId())
      .setTheme(player.getTheme())
      .setStructureKind(player.getStructureKind())
      .setBuildStyle(player.getBuildStyle())
      .setPluginKind(player.getPluginKind())
      .setSubmitted(1)
      .setApplyKey(applyKey)
      .build();
  }

  public BuildPlayer noSuchPlayer() {
    return BuildPlayer.newBuilder()
      .setName("")
      .setUniqueId(UUID.randomUUID())
      .setApplyKey("0")
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
