package de.marc.ganglife.npcs;

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

public class interactMeth implements Listener {

    public String npcname = "Laborant";

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
                    .name(Component.text("§eKristalle verarbeiten.."))
                    .lore(Component.text(" §f▹ §7" + rawAmount + " Kristalle ≙ " + finishAmount + " Kristallkiste"))
                    .asGuiItem(settingsClickEvent -> {
                        player.closeInventory();
                        main.playProccessSound(player);
                        Location npcLocation = new Location(player.getWorld(), -387, 89, -234);

                        process.startProcess(player, items.FROG, rawAmount, items.METH_CHEST, finishAmount, npcLocation);
                    });

            interactInventory.setItem(13, interactItem);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
