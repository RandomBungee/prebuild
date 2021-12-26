package de.flamefoxes.build.listener;

import com.onarandombox.MultiverseCore.*;
import com.onarandombox.MultiverseCore.api.*;
import de.flamefoxes.build.*;
import de.flamefoxes.build.player.*;
import de.flamefoxes.build.sql.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.scheduler.*;

public class RegisterInventoryClickListener implements Listener {

  private final BuildUtil buildUtil;
  private final IBuildPlayer iBuildPlayer;

  private final HashMap<Player, String> structure = new HashMap<>();
  private final HashMap<Player, String> plugin = new HashMap<>();
  private final HashMap<Player, String> style = new HashMap<>();

  private final List<String> structures = new ArrayList<>();
  private final List<String> plugins = new ArrayList<>();

  public RegisterInventoryClickListener(BuildUtil buildUtil) {
    this.buildUtil = buildUtil;
    this.iBuildPlayer = SqlBuildPlayer.create(Mysql.connection, buildUtil);
  }

  @EventHandler
  public void startVideoInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Schaue dir unser Video an!")) {
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              buildUtil.registerProcess(false);
              player.closeInventory();
              buildUtil.inventory().createInventory("private", player);
              buildUtil.registerProcess(true);
              cancel();
            }
          }.runTask(buildUtil.build());
        }
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void privatePolicyInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Datenschutzerklärung")) {
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              buildUtil.registerProcess(false);
              player.closeInventory();
              buildUtil.inventory().createInventory("plugin", player);
              buildUtil.registerProcess(true);
              cancel();
            }
          }.runTask(buildUtil.build());
        }
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void pluginInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Welche Plguins beherrscht du?")) {
        inventoryClickEvent.setCancelled(true);
        if (!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")
          && inventoryClickEvent.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          if (!plugins.contains(item.replaceAll("§7", ""))) {
            plugins.add(item.replaceAll("§7", ""));
            plugin.put(player, transformListToString(plugins));
            buildUtil.inventory().inventory.setItem(
              inventoryClickEvent.getSlot(),
              createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
                inventoryClickEvent.getCurrentItem().getType())
            );
          }
        }
        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              if (!plugin.isEmpty()) {
                buildUtil.registerProcess(false);
                player.closeInventory();
                buildUtil.inventory().createInventory("structure", player);
                buildUtil.registerProcess(true);
                cancel();
              } else {
                player
                  .sendMessage(BuildUtil.PREFIX + "§cDu musst mindestens ein Plugin auswählen!");
              }
            }
          }.runTask(buildUtil.build());
        }
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void structureInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Was beherrscht du gut?")) {
        inventoryClickEvent.setCancelled(true);
        if (!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")
          && inventoryClickEvent.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          if (!structures.contains(item.replaceAll("§7", ""))) {
            structures.add(item.replaceAll("§7", ""));
            structure.put(player, transformListToString(structures));
            buildUtil.inventory().inventory.setItem(
              inventoryClickEvent.getSlot(),
              createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
                inventoryClickEvent.getCurrentItem().getType())
            );
          }
        }
        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              if (!structures.isEmpty()) {
                buildUtil.registerProcess(false);
                player.closeInventory();
                buildUtil.inventory().createInventory("style", player);
                buildUtil.registerProcess(true);
                cancel();
              } else {
                player.sendMessage(
                  BuildUtil.PREFIX + "§cDu musst mindestens eine Baustruktur auswählen!");
              }
            }
          }.runTask(buildUtil.build());
        }
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void styleInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Was für Baustile beherrscht du gut?")) {
        inventoryClickEvent.setCancelled(true);
        if (!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§a>> Weiter")
          && inventoryClickEvent.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          style.put(player, item.replace("§e", ""));
          buildUtil.inventory()
            .inventory.setItem(
            inventoryClickEvent.getSlot(),
            createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
              inventoryClickEvent.getCurrentItem().getType())
          );
        }
        new BukkitRunnable() {
          @Override
          public void run() {
            buildUtil.registerProcess(false);
            player.closeInventory();
            buildUtil.inventory().createInventory("finish", player);
            buildUtil.registerProcess(true);
            cancel();
          }
        }.runTask(buildUtil.build());
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void finishInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Fertig? Bist du bereit?")) {
        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
          .equalsIgnoreCase("§aLos gehts!")) {
          buildUtil.registerProcess(false);
          player.closeInventory();
          String theme = pickRandomTheme(player);
          String structures = structure.get(player);
          String styles = style.get(player);
          String plugins = plugin.get(player);
          BuildPlayer buildPlayer = createPlayer(player, theme, plugins, styles, structures);
          iBuildPlayer.create(buildPlayer);
          MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager()
            .getPlugin("Multiverse-Core");
          MVWorldManager mvWorldManager = multiverseCore.getMVWorldManager();
          mvWorldManager.cloneWorld("build_template", player.getName());
          World world = Bukkit.getWorld(player.getName());
          MultiverseWorld multiverseWorld = mvWorldManager.getMVWorld(world);
          world.setStorm(false);
          world.setTime(1000);
          world.setGameRuleValue("randomTickSpeed", "0");
          world.setGameRuleValue("doDaylightCycle", "false");
          multiverseWorld.setGameMode(GameMode.CREATIVE);
          Location location = new Location(world, 0, 64, 0);
          player.teleport(location);
          player.sendMessage(
            BuildUtil.PREFIX + "§7Du hast die Registration abgeschlossen! Dein Bauthema ist: §e"
              + theme +
              "§7. §7Wir wünschen dir Viel Erfolg und Viel Spaß! "
          );
        }
      }
    } catch (Exception ignore) {
    }
  }

  @EventHandler
  public void abortRegistration(InventoryCloseEvent inventoryCloseEvent) {
    Player player = (Player) inventoryCloseEvent.getPlayer();
    if (buildUtil.registerProcess()) {
      player.kickPlayer("§cDu hast die Registration abgebrochen!");
    }
  }

  private String pickRandomTheme(Player player) {
    String theme;
    Random random = new Random();
    int maxThemes = buildUtil.themes().selectAllThemes(style.get(player)).size();
    theme = buildUtil.themes().selectAllThemes(style.get(player)).get(random.nextInt(maxThemes));
    return theme;
  }

  public BuildPlayer createPlayer(
    Player player,
    String theme,
    String pluginKind,
    String buildStyle,
    String structureKind
  ) {
    return BuildPlayer.newBuilder()
      .setName(player.getName())
      .setUniqueId(player.getUniqueId())
      .setApplyKey("0")
      .setSubmitted(0)
      .setPluginKind(pluginKind)
      .setBuildStyle(buildStyle)
      .setStructureKind(structureKind)
      .setTheme(theme)
      .build();
  }

  private ItemStack createItem(String displayName, Material material) {
    ItemStack itemStack = new ItemStack(material, 1);
    ItemMeta itemMeta = itemStack.getItemMeta();
    assert itemMeta != null;
    itemMeta.setDisplayName(displayName);
    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private String transformListToString(List<String> list) {
    StringBuilder structureStringBuilder = new StringBuilder();
    for (String structures : list) {
      structureStringBuilder.append(structures);
      structureStringBuilder.append(", ");
    }
    return structureStringBuilder.toString();
  }
}
