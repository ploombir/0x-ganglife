package de.marc.ganglife.npcs.seller;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.systems;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class interactHehler implements Listener {

    public String npcname = "Hehler";

    public Integer priceGold = 1000;

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

            GuiItem sellGold = ItemBuilder.from(Material.GOLD_INGOT)
                    .name(Component.text("§eGold verkaufen.."))
                    .lore(Component.text(" §f▹ §7 1 Goldbarren ≙ " + priceGold + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if (!player.getInventory().containsAtLeast(items.GOLD_INGOT.getItem(), 1)) {
                            player.sendMessage(main.pre_error + "§cDu hast keine Goldbarren.");
                            main.playErrorSound(player);
                            return;
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein §fGoldbarren §7verkauft.");
                        player.sendMessage(" §f▹ §c- Goldbarren");
                        player.sendMessage(" §f▹ §a+" + priceGold + "$");
                        player.sendMessage(" §f▹ §a+5 EXP");
                        uPlayer.setLevelExp(uPlayer.getLevelExp() + 5);
                        uPlayer.setCash(uPlayer.getCash() + priceGold);
                        main.playSuccessSound(player);
                        player.getInventory().removeItem(items.GOLD_INGOT.getItem());
                        systems.updateLevel(player, uPlayer);
                    });

            interactInventory.setItem(13, sellGold);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
