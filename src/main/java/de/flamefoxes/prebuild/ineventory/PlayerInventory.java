package de.flamefoxes.prebuild.ineventory;

import de.flamefoxes.prebuild.sql.Mysql;
import de.flamefoxes.prebuild.sql.PrePlayer;
import de.flamefoxes.prebuild.sql.SqlPrePlayerRepository;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory {
    private final PrePlayer prePlayer;
    private final Inventory adminInventory;
    private final Inventory introductionInv;
    private final Inventory privacyInv;
    public final Inventory pluginInv;
    public final Inventory structurInv;
    public final Inventory styleIn;
    public final Inventory finishInv;

    public PlayerInventory() {
        this.prePlayer = SqlPrePlayerRepository.create(Mysql.connection);
        adminInventory = Bukkit.createInventory(null, InventoryType.CHEST, "§7Spieler-Liste");
        introductionInv = Bukkit.createInventory(null, InventoryType.CHEST, "§7Schaue dir unser Video an!");
        privacyInv = Bukkit.createInventory(null, InventoryType.CHEST, "§7Datenschutzerklärung");
        pluginInv = Bukkit.createInventory(null, InventoryType.CHEST, "§7Welche Plguins beherrscht du?");
        structurInv = Bukkit.createInventory(null, InventoryType.CHEST, "§7Was beherrscht du gut?");
        styleIn = Bukkit.createInventory(null, InventoryType.CHEST, "§7Was für Baustile beherrscht du gut?");
        finishInv = Bukkit.createInventory(null, InventoryType.CHEST, "§7Fertig? Bist du bereit?");
    }

    public void createAdminInventory(Player player) {
        adminInventory.clear();
        for(String players : prePlayer.players()) {
            adminInventory.addItem(createPlayerHead("§8» " + players, players, lore(players)));
        }
        player.openInventory(adminInventory);
    }

    public void createIntroductionInventory(Player player) {
        introductionInv.setItem(13, createItem("§ewww.flamefoxes.de/go/buildervideo", Material.ITEM_FRAME));
        introductionInv.setItem(19, createPlayerHead("§c<< Abbrechen", "MHF_ArrowLeft"));
        introductionInv.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
        player.openInventory(introductionInv);
    }

    public void createPrivatePolicyInventory(Player player) {
        privacyInv.setItem(13, createItem("§ewww.flamefoxes.de/go/information", Material.PAPER));
        //privacyInv.setItem(19, createPlayerHead("§c<< Zurück", "MHF_ArrowLeft"));
        privacyInv.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
        player.openInventory(privacyInv);
    }

    public void createPluginInventory(Player player) {
       // pluginInv.setItem(19, createPlayerHead("§c<< Zurück", "MHF_ArrowLeft"));
        pluginInv.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
        pluginInv.setItem(2, createItem("§7WorldEdit", Material.WOODEN_AXE));
        pluginInv.setItem(4, createItem("§7HeadDatabase", Material.ITEM_FRAME));
        pluginInv.setItem(6, createItem("§7GoBrush", Material.FLINT));
        pluginInv.setItem(11, createItem("§7GoPaint", Material.FEATHER));
        pluginInv.setItem(13, createItem("§7ArmorStandTools", Material.ARMOR_STAND));
        pluginInv.setItem(15, createItem("§7VoxelSniper", Material.ARROW));
        player.openInventory(pluginInv);
    }

    public void createStructurInventory(Player player) {
       // structurInv.setItem(19, createPlayerHead("§c<< Zurück", "MHF_ArrowLeft"));
        structurInv.setItem(25, createPlayerHead("§a>> Weiter", "MHF_ArrowRight"));
        structurInv.setItem(11, createItem("§7Architektonische Strukturen", Material.PAPER));
        structurInv.setItem(13, createItem("§7Organische Strukturen", Material.LEATHER));
        structurInv.setItem(15, createItem("§7Terra/Landschaft", Material.GRASS_BLOCK));
        player.openInventory(structurInv);
    }

    public void createStyleInventory(Player player) {
        styleIn.setItem(3, createItem("§eFantasie", Material.SLIME_BALL));
        styleIn.setItem(4, createItem("§eIdylisch", Material.MELON));
        styleIn.setItem(5, createItem("§eModerne", Material.CHEST));
        styleIn.setItem(12, createItem("§ePost-Apokalyptisch", Material.WITHER_SKELETON_SKULL));
        styleIn.setItem(13, createItem("§eAltmodisch", Material.CAKE));
        styleIn.setItem(14, createItem("§eNaturell", Material.CACTUS));
        player.openInventory(styleIn);
    }

    public void createFinishInventory(Player player) {
        finishInv.setItem(13, createItem("§aLos gehts!", Material.LIME_DYE));
        player.openInventory(finishInv);
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

    private List<String> lore(String name) {
        List<String> lores = new ArrayList<String>();
        lores.add("§7Thema §8» §a" + prePlayer.theme(name));
        lores.add("§7Plotstatus §8» §a" + submitted(name));
        lores.add("§7Bewerbungsstatus §8» §a" + status(name));
        return lores;
    }

    private String status(String name) {
        if(prePlayer.status(name) == 0) {
            return "§cNicht Registriert";
        } else if(prePlayer.status(name) == 1) {
            return "§bIn Bearbeitung";
        } else if(prePlayer.status(name) == 2) {
            return "§aAngenommen";
        } else if(prePlayer.status(name) == 3) {
            return "§cAbgelehnt";
        }
        return "ERROR";
    }

    private String submitted(String name) {
        if(prePlayer.submitted(name) == 1) {
            return "§aAbgegeben";
        } else {
            return "§cNicht abgegeben";
        }
    }
}