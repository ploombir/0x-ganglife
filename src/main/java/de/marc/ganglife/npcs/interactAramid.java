package de.marc.ganglife.npcs;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.process;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class interactAramid implements Listener {
    public String npcname = "Schneiderei";

    public Integer rawAmount = 50;
    public Integer finishAmount = 1;

    @EventHandler
    public void handleInteractEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!(event.getRightClicked() instanceof Entity)) return;

        Entity npc = event.getRightClicked();

        Gui interactInventory = Gui.gui()
                .rows(3)
                .title(Component.text(main.prefix + "§7" + npcname))
                .disableAllInteractions()
                .create();

        if (npc.getCustomName() != null && npc.getCustomName().equals(npcname) && event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);

            GuiItem interactItem = ItemBuilder.from(Material.MAGENTA_DYE)
                    .name(Component.text("§eAramid verarbeiten.."))
                    .lore(Component.text(" §f▹ §7" + rawAmount + " Aramid ≙ " + finishAmount + " Westenkiste"))
                    .asGuiItem(settingsClickEvent -> {
                        player.closeInventory();
                        main.playProccessSound(player);
                        Location npcLocation = new Location(player.getWorld(), 1, 1,1);

                        ItemStack rawItem = items.ARAMID.getItem();
                        ItemStack finishItem = items.BULLETPROOF.getItem();

                        process.startProcess(player, rawItem, rawAmount, finishItem, finishAmount, npcLocation);
                    });

            interactInventory.setItem(13, interactItem);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
