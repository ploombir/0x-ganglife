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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class interactShop implements Listener {


    public String npcname = "Shop";
    public Integer priceCuffs = 20;
    public Integer priceMedicalbox = 500;
    public Integer priceBread = 2;

    public Integer priceBottle = 5;
    public Integer pricePhone = 500;
    public Integer priceBox = 100;
    public Integer priceContract = 5000;


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

            GuiItem buyBread = ItemBuilder.from(Material.BREAD)
                    .name(Component.text("§eBrot"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceBread + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceBread) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - priceBread);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein Brot erworben.");
                        player.getInventory().addItem(items.BREAD.getItem());
                        main.playProccessSound(player);
                    });

            GuiItem buyMedicalbox = ItemBuilder.from(Material.GLOWSTONE_DUST)
                    .name(Component.text("§eVerbandskasten"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceMedicalbox + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceMedicalbox) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - priceMedicalbox);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein Verbandskasten erworben.");
                        uPlayer.setMedicineAmount(uPlayer.getMedicineAmount() + 1);
                        main.playProccessSound(player);
                    });

            GuiItem buyCuffs = ItemBuilder.from(Material.LEGACY_LEASH)
                    .name(Component.text("§eSeil"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceCuffs + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceCuffs) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - priceCuffs);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein Seil erworben.");
                        player.getInventory().addItem(items.CUFFS.getItem());
                        main.playProccessSound(player);
                    });

            GuiItem buyPhone = ItemBuilder.from(Material.MUSIC_DISC_CAT)
                    .name(Component.text("§eHandy"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + pricePhone + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= pricePhone) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - pricePhone);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein Handy erworben.");
                        player.getInventory().addItem(items.PHONE.getItem());
                        main.playProccessSound(player);
                    });

            GuiItem buyWaterbottle = ItemBuilder.from(Material.POTION)
                    .name(Component.text("§eHandy"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceBottle + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceBottle) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        ItemStack bottle = new ItemStack(Material.POTION, 1);
                        ItemMeta metab = bottle.getItemMeta();
                        PotionMeta pmeta = (PotionMeta) metab;
                        PotionData pdata = new PotionData(PotionType.WATER);
                        metab.setDisplayName("§fWasserflasche");
                        pmeta.setBasePotionData(pdata);
                        bottle.setItemMeta(metab);

                        uPlayer.setCash(uPlayer.getCash() - priceBottle);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine Wasserflasche erworben.");
                        player.getInventory().addItem(bottle);
                        main.playProccessSound(player);
                    });

            GuiItem buyBox = ItemBuilder.from(Material.PRISMARINE_SHARD)
                    .name(Component.text("§eBox"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceBox + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceBox) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - priceBox);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich eine Box erworben.");
                        player.getInventory().addItem(items.BOX.getItem());
                        main.playProccessSound(player);
                    });


            GuiItem buyContract = ItemBuilder.from(Material.SLIME_BALL)
                    .name(Component.text("§eHausvertrag"))
                    .lore(Component.text(" §f▹ §7Preis: §6" + priceContract + "$"))
                    .asGuiItem(settingsClickEvent -> {
                        if(uPlayer.getCash() <= priceContract) {
                            player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                            main.playErrorSound(player);
                            return;
                        }

                        uPlayer.setCash(uPlayer.getCash() - priceContract);
                        player.sendMessage(main.prefix + "§7Du hast erfolgreich ein Hausvertrag erworben.");
                        player.getInventory().addItem(items.HOUSE_CONTRACT.getItem());
                        main.playProccessSound(player);
                    });

            interactInventory.setItem(10, buyBread);
            interactInventory.setItem(11, buyPhone);
            interactInventory.setItem(12, buyCuffs);
            interactInventory.setItem(13, buyMedicalbox);
            interactInventory.setItem(14, buyWaterbottle);
            interactInventory.setItem(15, buyBox);
            interactInventory.setItem(16, buyContract);


            interactInventory.open(player);
            main.playProccessSound(player);
        }
    }

}
