package de.marc.ganglife.phone.events;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class openPhone implements Listener {

    @EventHandler
    public void openPhone(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Gui phoneInventory = Gui.gui()
                .rows(6)
                .title(Component.text(main.prefix + "§7Handy"))
                .disableAllInteractions()
                .create();

        GuiItem closePhoneGUI = ItemBuilder.from(Material.BARRIER).name(Component.text("§cAusschalten"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem contactApp = ItemBuilder.from(Material.BOOK).name(Component.text("§aKontakte"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem bankingApp = ItemBuilder.from(Material.GOLD_BLOCK).name(Component.text("§eBank"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem emergencyApp = ItemBuilder.from(Material.TRIPWIRE_HOOK).name(Component.text("§9Notruf"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem settingsApp = ItemBuilder.from(Material.REDSTONE).name(Component.text("§7Einstellungen"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem navigationApp = ItemBuilder.from(Material.COMPASS).name(Component.text("§aNavigation"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem factionApp = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§6Fraktion"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem businessApp = ItemBuilder.from(Material.CLAY_BALL).name(Component.text("§eBusiness"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem placeHolder = ItemBuilder.from(Material.BLACK_STAINED_GLASS).name(Component.text(""))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        phoneInventory.setItem(13, navigationApp);
        phoneInventory.setItem(21, emergencyApp);
        phoneInventory.setItem(22, bankingApp);
        phoneInventory.setItem(23, settingsApp);
        phoneInventory.setItem(39, businessApp);
        phoneInventory.setItem(40, contactApp);
        phoneInventory.setItem(41, factionApp);
        phoneInventory.setItem(49, closePhoneGUI);

        phoneInventory.setItem(2, placeHolder);
        phoneInventory.setItem(11, placeHolder);
        phoneInventory.setItem(20, placeHolder);
        phoneInventory.setItem(29, placeHolder);
        phoneInventory.setItem(38, placeHolder);
        phoneInventory.setItem(47, placeHolder);

        phoneInventory.setItem(6, placeHolder);
        phoneInventory.setItem(15, placeHolder);
        phoneInventory.setItem(24, placeHolder);
        phoneInventory.setItem(33, placeHolder);
        phoneInventory.setItem(42, placeHolder);
        phoneInventory.setItem(51, placeHolder);


        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getMaterial() == Material.MUSIC_DISC_CAT) {
                phoneInventory.open(player);
                main.playProccessSound(player);
            }
        }

    }
}
