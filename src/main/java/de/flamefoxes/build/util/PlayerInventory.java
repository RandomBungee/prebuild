package de.flamefoxes.build.util;

import de.flamefoxes.build.player.*;
import de.flamefoxes.build.sql.Mysql;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class PlayerInventory {
  private final IBuildPlayer iBuildPlayer;
  public Inventory inventory;

  public PlayerInventory() {
    this.iBuildPlayer = SqlBuildPlayer.create(Mysql.connection);
  }

  public void createInventory(
    String kind,
    Player player
  ) {
    if(kind.equals("list")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Spieler-List");
      for(String players : iBuildPlayer.buildPlayers()) {
        String name = UUIDFetcher.getName(UUID.fromString(players));
        inventory.addItem(createPlayerHead("§8» " + name, name, lore(UUID.fromString(players))));
      }
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("video")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Schaue dir unser Video an!");
      setDefaultItems(inventory);
      inventory.setItem(13, createItem("§ewww.flamefoxes.de/go/buildervideo", Material.ITEM_FRAME));
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("private")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Datenschutzerklärung");
      setDefaultItems(inventory);
      inventory.setItem(13, createItem("§ewww.flamefoxes.de/go/information", Material.PAPER));
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("plugin")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Welche Plugins beherrscht du gut?");
      setDefaultItems(inventory);
      inventory.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
      inventory.setItem(2, createItem("§7WorldEdit", Material.WOODEN_AXE));
      inventory.setItem(4, createItem("§7HeadDatabase", Material.ITEM_FRAME));
      inventory.setItem(6, createItem("§7GoBrush", Material.FLINT));
      inventory.setItem(11, createItem("§7GoPaint", Material.FEATHER));
      inventory.setItem(13, createItem("§7ArmorStandTools", Material.ARMOR_STAND));
      inventory.setItem(15, createItem("§7VoxelSniper", Material.ARROW));
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("structure")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Welche Strukutren kannst du gut?");
      setDefaultItems(inventory);
      inventory.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
      inventory.setItem(11, createItem("§7Architektonische Strukturen", Material.PAPER));
      inventory.setItem(13, createItem("§7Organische Strukturen", Material.LEATHER));
      inventory.setItem(15, createItem("§7Terra/Landschaft", Material.GRASS_BLOCK));
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("style")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Wähle dein Themen gebiet?");
      setDefaultItems(inventory);
      inventory.setItem(3, createItem("§eFantasie", Material.SLIME_BALL));
      inventory.setItem(4, createItem("§eIdylisch", Material.MELON));
      inventory.setItem(5, createItem("§eModerne", Material.CHEST));
      inventory.setItem(12, createItem("§ePost-Apokalyptisch", Material.WITHER_SKELETON_SKULL));
      inventory.setItem(13, createItem("§eAltmodisch", Material.CAKE));
      inventory.setItem(14, createItem("§eNaturell", Material.CACTUS));
      player.openInventory(inventory);
      return;
    }
    if(kind.equals("finish")) {
      inventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Wähle dein Themen gebiet?");
      inventory.setItem(13, createItem("§aLos gehts!", Material.LIME_DYE));
      player.openInventory(inventory);
    }
  }

  private void setDefaultItems(Inventory inventory) {
    for(int i = 0; i < 27; i++) {
      inventory.setItem(i, createItem(" ", Material.BLACK_STAINED_GLASS_PANE));
    }
    inventory.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
  }

  private ItemStack createPlayerHead(String displayName, String owner, List<String> lore) {
    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
    SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
    assert skullMeta != null;
    skullMeta.setOwner(owner);
    skullMeta.setDisplayName(displayName);
    skullMeta.setLore(lore);
    itemStack.setItemMeta(skullMeta);
    return itemStack;
  }

  private ItemStack createPlayerHead(String displayName, String owner) {
    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
    SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
    assert skullMeta != null;
    skullMeta.setOwner(owner);
    skullMeta.setDisplayName(displayName);
    itemStack.setItemMeta(skullMeta);
    return itemStack;
  }

  private ItemStack createItem(String displayName, Material material) {
    ItemStack itemStack = new ItemStack(material, 1);
    ItemMeta itemMeta = itemStack.getItemMeta();
    assert itemMeta != null;
    itemMeta.setDisplayName(displayName);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private List<String> lore(UUID uniqueId) {
    Optional<BuildPlayer> optionalBuildPlayer = iBuildPlayer.find(uniqueId);
    BuildPlayer buildPlayer = optionalBuildPlayer.orElse(noSuchPlayer());
    List<String> lores = new ArrayList<String>();
    lores.add("§7Thema §8» §a" + buildPlayer.getTheme());
    lores.add("§7Plotstatus §8» §a" + ((buildPlayer.getSubmitted() != 0) ? "§cNich abgegeben" : "§aAbgegeben"));
    return lores;
  }

  public BuildPlayer noSuchPlayer() {
    return BuildPlayer.newBuilder()
      .setName("")
      .setUniqueId(UUID.randomUUID())
      .setApplyKey(0)
      .setSubmitted(0)
      .setPluginKind("")
      .setBuildStyle("")
      .setStructureKind("")
      .setTheme("")
      .build();
  }
}
