package de.marc.ganglife.npcs.worker;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.process;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class interactWood implements Listener {

    public String npcname = "Holzverarbeiter";

    public Integer rawAmount = 20;
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
                    .name(Component.text("§eHolz verarbeiten.."))
                    .lore(Component.text(" §f▹ §7" + rawAmount + " Holz ≙ " + finishAmount + " Holzpalette"))
                    .asGuiItem(settingsClickEvent -> {
                        player.closeInventory();
                        main.playProccessSound(player);
                        Location npcLocation = new Location(player.getWorld(), -201, 68, -193);

                        process.startProcess(player, items.WOOD_LOG, rawAmount, items.WOOD_PLANKS, finishAmount, npcLocation);
                    });

            interactInventory.setItem(13, interactItem);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
