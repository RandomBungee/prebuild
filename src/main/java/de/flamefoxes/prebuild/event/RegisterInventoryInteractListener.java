package de.flamefoxes.prebuild.event;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import de.flamefoxes.prebuild.PreBuilding;
import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class RegisterInventoryInteractListener implements Listener {
  private final PreBuilding preBuilding;
  private final PrePlayer prePlayer;
  private final HashMap<Player, String> structure;
  private final HashMap<Player, String> plugin;
  private final HashMap<Player, String> style;
  private final List<String> strucurures = new ArrayList<>();
  private final List<String> plugins = new ArrayList<>();

  public RegisterInventoryInteractListener(PreBuilding preBuilding) {
    this.preBuilding = preBuilding;
    this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
    this.structure = new HashMap<>();
    this.plugin = new HashMap<>();
    this.style = new HashMap<>();
  }

  @EventHandler
  public void startVideoInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Schaue dir unser Video an!")) {
        inventoryClickEvent.setCancelled(true);
        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c<< Abbrechen")) {
          player.kickPlayer("§cDu hast die Registration abgebrochen!");
          return;
        }
        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              preBuilding.registerProcess(false);
              player.closeInventory();
              preBuilding.playerInventory().createPrivatePolicyInventory(player);
              preBuilding.registerProcess(true);
              cancel();
            }
          }.runTask(preBuilding);
        }
      }
    } catch (Exception ignore) {}
  }

  @EventHandler
  public void privatePolicyInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Datenschutzerklärung")) {
        inventoryClickEvent.setCancelled(true);
        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              preBuilding.registerProcess(false);
              player.closeInventory();
              preBuilding.playerInventory().createPluginInventory(player);
              preBuilding.registerProcess(true);
              cancel();
            }
          }.runTask(preBuilding);
        }
      }
    } catch (Exception ignore) {}
  }

  @EventHandler
  public void pluginInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Welche Plguins beherrscht du?")) {
        inventoryClickEvent.setCancelled(true);
        if(!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          plugins.add(item.replace("§7", ""));
          plugin.put(player, pluginString(plugins));
          preBuilding.playerInventory()
            .pluginInv.setItem(
            inventoryClickEvent.getSlot(), createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
              inventoryClickEvent.getCurrentItem().getType())
          );
        }
        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              if(!plugin.isEmpty()) {
                preBuilding.registerProcess(false);
                player.closeInventory();
                preBuilding.playerInventory().createStructurInventory(player);
                preBuilding.registerProcess(true);
                cancel();
              } else {
                player.sendMessage(PreBuilding.PREFIX + "§cDu musst mindestens ein Plugin auswählen!");
              }
            }
          }.runTask(preBuilding);
        }
      }
    } catch (Exception ignore) {}
  }

  @EventHandler
  public void structureInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Was beherrscht du gut?")) {
        inventoryClickEvent.setCancelled(true);
        if(!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          strucurures.add(item.replace("§7", ""));
          structure.put(player, structureString(strucurures));
          preBuilding.playerInventory()
            .structurInv.setItem(
            inventoryClickEvent.getSlot(), createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
              inventoryClickEvent.getCurrentItem().getType())
          );
        }
        if(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          new BukkitRunnable() {
            @Override
            public void run() {
              if(!structure.isEmpty()) {
                preBuilding.registerProcess(false);
                player.closeInventory();
                preBuilding.playerInventory().createStyleInventory(player);
                preBuilding.registerProcess(true);
                cancel();
              } else {
                player.sendMessage(PreBuilding.PREFIX + "§cDu musst mindestens eine Baustruktur wählen!");
              }
            }
          }.runTask(preBuilding);
        }
      }
    } catch (Exception ignore) {}
  }

  @EventHandler
  public void styleInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player) inventoryClickEvent.getWhoClicked();
    try {
      if (inventoryClickEvent.getView().getTitle()
        .equalsIgnoreCase("§7Was für Baustile beherrscht du gut?")) {
        inventoryClickEvent.setCancelled(true);
        if(!inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a>> Weiter")) {
          String item = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
          style.put(player, item.replace("§e", ""));
          preBuilding.playerInventory()
            .styleIn.setItem(
            inventoryClickEvent.getSlot(), createItem(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName(),
              inventoryClickEvent.getCurrentItem().getType())
          );
        }
        new BukkitRunnable() {
          @Override
          public void run() {
            preBuilding.registerProcess(false);
            player.closeInventory();
            preBuilding.playerInventory().createFinishInventory(player);
            preBuilding.registerProcess(true);
            cancel();
          }
        }.runTask(preBuilding);
      }
    } catch (Exception ignore) {}
  }

  @EventHandler
  public void finishInventory(InventoryClickEvent inventoryClickEvent) {
    Player player = (Player)inventoryClickEvent.getWhoClicked();
    if (inventoryClickEvent.getView().getTitle()
      .equalsIgnoreCase("§7Fertig? Bist du bereit?")) {
      preBuilding.registerProcess(false);
      String theme = pickRandomTheme(player);
      prePlayer.create(
        player.getName(),
        theme,
        "",
        "",
        0,
        1,
        "",
        structure.get(player),
        style.get(player),
        plugin.get(player)
      );
      player.closeInventory();
      MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
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
        PreBuilding.PREFIX + "§7Du hast die Registration abgeschlossen! Dein Bauthema ist: §e"
          + theme +
          "§7. §7Wir wünschen dir Viel Erfolg und Viel Spaß! "
      );
    }
  }

  @EventHandler
  public void cancelInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
    Player player = (Player)inventoryCloseEvent.getPlayer();
    if(preBuilding.registerProcess()) {
      player.kickPlayer("§cDu hast die Registration abgebrochen!");
    }
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

  private String pickRandomTheme(Player player) {
    String theme;
    Random random = new Random();
    if(style.get(player).equalsIgnoreCase("Fantasie")) {
      int maxThemes = preBuilding.themes().themesFantasyFromFile().size() - 1;
      theme = preBuilding.themes().themesFantasyFromFile().get(random.nextInt(maxThemes));
      return theme;
    } else if(style.get(player).equalsIgnoreCase("Idylisch")) {
      int maxThemes = preBuilding.themes().themesIdyllicFromFile().size() - 1;
      theme = preBuilding.themes().themesIdyllicFromFile().get(random.nextInt(maxThemes));
      return theme;
    } else if(style.get(player).equalsIgnoreCase("Moderne")) {
      int maxThemes = preBuilding.themes().themesModernFromFile().size() - 1;
      theme = preBuilding.themes().themesModernFromFile().get(random.nextInt(maxThemes));
      return theme;
    } else if(style.get(player).equalsIgnoreCase("Post-Apokalyptisch")) {
      int maxThemes = preBuilding.themes().themesPostFromFile().size() - 1;
      theme = preBuilding.themes().themesPostFromFile().get(random.nextInt(maxThemes));
      return theme;
    } else if(style.get(player).equalsIgnoreCase("Altmodisch")) {
      int maxThemes = preBuilding.themes().themesOldFromFile().size() - 1;
      theme = preBuilding.themes().themesOldFromFile().get(random.nextInt(maxThemes));
      return theme;
    } else if(style.get(player).equalsIgnoreCase("Naturell")) {
      int maxThemes = preBuilding.themes().themesNaturalFromFile().size() - 1;
      theme = preBuilding.themes().themesNaturalFromFile().get(random.nextInt(maxThemes));
      return theme;
    }
    return "§cEin Fehler ist aufgetreten. Bitte kontaktiere ein Admin!";
  }

  private String pluginString(List<String> plugin) {
    StringBuilder pluginStringBuilder = new StringBuilder();
    for(String plusins : plugin) {
      pluginStringBuilder.append(plusins);
      pluginStringBuilder.append(", ");
    }
    return pluginStringBuilder.toString();
  }

  private String structureString(List<String> plugin) {
    StringBuilder structureStringBuilder = new StringBuilder();
    for(String structures : plugin) {
      structureStringBuilder.append(structures);
      structureStringBuilder.append(", ");
    }
    return structureStringBuilder.toString();
  }
}