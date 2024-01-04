package de.marc.ganglife.npcs.jobs.garbage;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.methods.systems;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class clickDoor implements Listener {

    public static final Map<Player, Integer> glass = new HashMap<>();
    public static final Map<Player, Integer> wood = new HashMap<>();
    public static final Map<Player, Integer> paper = new HashMap<>();
    public static final Map<Player, Integer> total = new HashMap<>();
    public static final List<Integer> playerBlockedHouses = new ArrayList<>();

    public static final Map<Player, Integer> houseTimer = new HashMap<>();
    private static final Map<Player, Integer> limit = new HashMap<>();
    int adder = 0;

    public static String customTag;


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType().toString().contains("DOOR")) {
            for (int x = -2; x <= 2; x++) {
                for (int y = -2; y <= 2; y++) {
                    for (int z = -2; z <= 2; z++) {
                        Block relativeBlock = clickedBlock.getRelative(x, y, z);
                        if (relativeBlock.getState() instanceof Sign) {
                            Sign sign = (Sign) relativeBlock.getState();

                            if (sign.getPersistentDataContainer().has(getKey("CustomTag"), PersistentDataType.STRING)) {
                                customTag = sign.getPersistentDataContainer().get(getKey("CustomTag"), PersistentDataType.STRING);

                                if (!customTag.equals(0)) {
                                    if(interactGarbage.garbageJob.contains(player)) {
                                        Gui interactInventory = Gui.gui()
                                                .rows(3)
                                                .title(Component.text(main.prefix + "§7Müll entleeren"))
                                                .disableAllInteractions()
                                                .create();

                                        GuiItem grabTrash = ItemBuilder.from(Material.GUNPOWDER)
                                                .name(Component.text("§aMüll von Haus " + customTag + " entleeren.."))
                                                .lore(Component.text(" §f▹ §7Klicke um den Müll zu entleeren."))
                                                .asGuiItem(settingsClickEvent -> {


                                                });

                                        interactInventory.setItem(13, grabTrash);
                                        interactInventory.open(player);
                                        main.playProccessSound(player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(main.getPlugin(), key);
    }

}
