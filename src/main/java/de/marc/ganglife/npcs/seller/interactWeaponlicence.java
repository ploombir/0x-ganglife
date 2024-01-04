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

public class interactWeaponlicence implements Listener {

    public String npcname = "Waffenlizenz";

    public Integer priceLicence = 10000;

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
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            GuiItem sellGold = ItemBuilder.from(Material.PAPER)
                    .name(Component.text("§eWaffenlizenz erwerben.."))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceLicence + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceLicence) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine §fWaffenlizenz §erworben.");
                        player.sendMessage(" §f▹ §c- " + priceLicence + "$");
                        player.sendMessage(" §f▹ §a+250 EXP");
                        uPlayer.setLevelExp(uPlayer.getLevelExp() + 250);
                        uPlayer.setCash(uPlayer.getCash() - priceLicence);
                        uPlayer.setWeaponLicence(true);
                        main.playSuccessSound(player);
                        systems.updateLevel(player, uPlayer);
                    });

            interactInventory.setItem(13, sellGold);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
