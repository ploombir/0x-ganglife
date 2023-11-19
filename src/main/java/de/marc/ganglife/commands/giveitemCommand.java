package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setDrugs;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class giveitemCommand implements CommandExecutor {


    setDrugs setDrugs = new setDrugs(main.getPlugin().getDatabaseAsync().getDataSource());

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (cs instanceof Player player) {
            if (player.hasPermission("system.admin")) {
                Gui inv = Gui.gui()
                        .rows(5)
                        .title(Component.text(main.prefix + "§7Admin Items"))
                        .disableAllInteractions()
                        .create();

                inv.setItem(0, ItemBuilder.from(items.M4.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(1, ItemBuilder.from(items.AK47.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(2, ItemBuilder.from(items.PDW.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(3, ItemBuilder.from(items.SMG.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(4, ItemBuilder.from(items.PISTOLE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(5, ItemBuilder.from(items.JAGDFLINTE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(6, ItemBuilder.from(items.SNIPER.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(7, ItemBuilder.from(items.LASERGUN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(8, ItemBuilder.from(items.FESSELN_IN_INV.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(9, ItemBuilder.from(items.CHESTPLATECHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addSchutzweste(player.getUniqueId(), 1);
                }));

                inv.setItem(10, ItemBuilder.from(items.POLICE_CHESTPLATE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addPoliceSchutzweste(player.getUniqueId(), 1);
                }));
                inv.setItem(11, ItemBuilder.from(items.POLICE_CUFF.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(12, ItemBuilder.from(items.PHONE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(13, ItemBuilder.from(items.HAUSVERTRAG.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(14, ItemBuilder.from(items.COCAIN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addCocain(player.getUniqueId(), 1);
                }));

                inv.setItem(15, ItemBuilder.from(items.WEED.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addWeed(player.getUniqueId(), 1);
                }));

                inv.setItem(16, ItemBuilder.from(items.METH.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addMeth(player.getUniqueId(), 1);
                }));

                inv.setItem(17, ItemBuilder.from(items.MEDIZIN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addMedizin(player.getUniqueId(), 1);
                }));

                inv.setItem(18, ItemBuilder.from(items.IRON.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(19, ItemBuilder.from(items.WOOD.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(20, ItemBuilder.from(items.BOHRER.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(21, ItemBuilder.from(items.SPRENGSTOFF.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(22, ItemBuilder.from(items.COCAINPAP.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(23, ItemBuilder.from(items.KRÖTEN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(24, ItemBuilder.from(items.WEEDPAP.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(25, ItemBuilder.from(items.ARAMID.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(26, ItemBuilder.from(items.FANGKÄFIG.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(27, ItemBuilder.from(items.SPITZHACKE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(28, ItemBuilder.from(items.AXT.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(29, ItemBuilder.from(items.SICHEL.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(30, ItemBuilder.from(items.GOLD.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(31, ItemBuilder.from(items.WOOD_FINISH.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(32, ItemBuilder.from(items.IRON_FINISH.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));


                inv.setItem(33, ItemBuilder.from(items.COCAINCHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(34, ItemBuilder.from(items.METHCHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(35, ItemBuilder.from(items.WEEDCHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(36, ItemBuilder.from(items.CHESTPLATECHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(37, ItemBuilder.from(items.BOX.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));


                main.playProccessSound(player);
                inv.open(player);
            } else {
                main.playErrorSound(player);
                player.sendMessage(main.pre_error);
            }
        }
        return false;
    }
}
