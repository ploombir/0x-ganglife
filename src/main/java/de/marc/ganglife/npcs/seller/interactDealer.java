package de.marc.ganglife.npcs.seller;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.process;
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

public class interactDealer implements Listener {

    public String npcname = "Dealer";

    public Integer priceCocain = 400;
    public Integer priceMeth = 200;
    public Integer priceWeed = 100;

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

            GuiItem sellCocain = ItemBuilder.from(Material.FEATHER)
                    .name(Component.text("§ePulverkiste verkaufen.."))
                    .lore(Component.text(" §f▹ §7 1 Pulverkiste ≙ " + priceCocain + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if (!player.getInventory().containsAtLeast(items.COCAINE_CHEST.getItem(), 1)) {
                            player.sendMessage(main.pre_error + "§cDu hast keine Pulverkiste.");
                            main.playErrorSound(player);
                            return;
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine §fPulverkiste §7verkauft.");
                        player.sendMessage(" §f▹ §c- Pulverkiste");
                        player.sendMessage(" §f▹ §a+" + priceCocain + "$");
                        player.sendMessage(" §f▹ §a+5 EXP");
                        uPlayer.setLevelExp(uPlayer.getLevelExp() + 5);
                        uPlayer.setCash(uPlayer.getCash() + priceCocain);
                        main.playSuccessSound(player);
                        player.getInventory().removeItem(items.COCAINE_CHEST.getItem());
                        systems.updateLevel(player, uPlayer);
                    });
            GuiItem sellMeth = ItemBuilder.from(Material.FLINT)
                    .name(Component.text("§eKristallkiste verkaufen.."))
                    .lore(Component.text(" §f▹ §7 1 Kristallkiste ≙ " + priceMeth + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if (!player.getInventory().containsAtLeast(items.METH_CHEST.getItem(), 1)) {
                            player.sendMessage(main.pre_error + "§cDu hast keine Kristallkiste.");
                            main.playErrorSound(player);
                            return;
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine §fKristallkiste §7verkauft.");
                        player.sendMessage(" §f▹ §c- Kristallkiste");
                        player.sendMessage(" §f▹ §a+" + priceMeth + "$");
                        player.sendMessage(" §f▹ §a+5 EXP");
                        uPlayer.setLevelExp(uPlayer.getLevelExp() + 5);
                        uPlayer.setCash(uPlayer.getCash() + priceMeth);
                        main.playSuccessSound(player);
                        player.getInventory().removeItem(items.METH_CHEST.getItem());
                        systems.updateLevel(player, uPlayer);
                    });
            GuiItem sellWeed = ItemBuilder.from(Material.NETHER_BRICK)
                    .name(Component.text("§eZigarettenkiste verkaufen.."))
                    .lore(Component.text(" §f▹ §7 1 Zigarettenkiste ≙ " + priceWeed + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if (!player.getInventory().containsAtLeast(items.WEED_CHEST.getItem(), 1)) {
                            player.sendMessage(main.pre_error + "§cDu hast keine Zigarettenkiste.");
                            main.playErrorSound(player);
                            return;
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine §fZigarettenkiste §7verkauft.");
                        player.sendMessage(" §f▹ §c- Zigarettenkiste");
                        player.sendMessage(" §f▹ §a+" + priceWeed + "$");
                        player.sendMessage(" §f▹ §a+5 EXP");
                        uPlayer.setLevelExp(uPlayer.getLevelExp() + 5);
                        uPlayer.setCash(uPlayer.getCash() + priceWeed);
                        main.playSuccessSound(player);
                        player.getInventory().removeItem(items.WEED_CHEST.getItem());
                        systems.updateLevel(player, uPlayer);
                    });

            interactInventory.setItem(11, sellWeed);
            interactInventory.setItem(13, sellCocain);
            interactInventory.setItem(15, sellMeth);
            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }
}
