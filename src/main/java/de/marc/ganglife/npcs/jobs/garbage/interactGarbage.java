package de.marc.ganglife.npcs.jobs.garbage;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
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

import java.util.ArrayList;

public class interactGarbage implements Listener {

    public static ArrayList<Player> garbageJob = new ArrayList<>();

    public String npcname = "Müllmann";
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

            GuiItem sellGold = ItemBuilder.from(Material.BLUE_DYE)
                    .name(Component.text("§eJob starten.."))
                    .lore(Component.text(" §f▹ §7§oSammel Müll und gebe es beim Mülldepot ab."))
                    .asGuiItem(settingsClickEvent -> {
                        if(!garbageJob.contains(player)) {
                            garbageJob.add(player);
                            player.sendMessage(main.prefix + "§aDu hast den Müllmann Job gestartet.");
                            player.sendMessage(" §f▹ §7Begebe dich nun an verschiende Häuser, klicke die Türen an und entleere die Mülleimer.");
                            clickDoor.paper.put(player, 0);
                            clickDoor.glass.put(player, 0);
                            clickDoor.wood.put(player, 0);
                            clickDoor.total.put(player, 0);
                            player.closeInventory();
                            main.playSuccessSound(player);
                        } else {
                            player.sendMessage(main.prefix + "§cDu hast den Müllmann Job abgebrochen.");
                            garbageJob.remove(player);
                        }
                    });

            interactInventory.setItem(13, sellGold);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
