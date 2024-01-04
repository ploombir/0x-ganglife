package de.marc.ganglife.npcs.other;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.systems;
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

public class interactSecurity implements Listener {

    public String npcname = "Security";
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
                    .name(Component.text("§eEinreisen.."))
                    .lore(Component.text(" §f▹ §7§oKlicke um einzureisen."))
                    .asGuiItem(settingsClickEvent -> {
                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        if (uPlayer.getFirstName().equals("")) {
                            player.sendMessage(main.pre_error + "§cBentrage zuerst einen Personalausweis.");
                            main.playErrorSound(player);
                            return;
                        }

                        Location loc = new Location(player.getWorld(), -140, 68, -236);
                        loc.setYaw((float) 270);
                        player.teleport(loc);

                        player.sendMessage(main.prefix + "§7Du bist erfolgreich eingereist. Viel Spaß auf 0x-Ganglife.");
                        main.playSuccessSound(player);
                    });

            interactInventory.setItem(13, sellGold);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
