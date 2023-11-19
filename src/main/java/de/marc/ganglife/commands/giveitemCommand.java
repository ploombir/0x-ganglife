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

                inv.setItem(0, ItemBuilder.from(items.m4).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(1, ItemBuilder.from(items.ak47).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));

                inv.setItem(2, ItemBuilder.from(items.pdw).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(3, ItemBuilder.from(items.smg).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(4, ItemBuilder.from(items.pistole).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(5, ItemBuilder.from(items.jagdflinte).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(6, ItemBuilder.from(items.sniper).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(7, ItemBuilder.from(items.lasergun).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(8, ItemBuilder.from(items.FesselnInInv).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                inv.setItem(9, ItemBuilder.from(items.chestplate).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addSchutzweste(player.getUniqueId(), 1);
                }));

                inv.setItem(10, ItemBuilder.from(items.police_chestplate).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addPoliceSchutzweste(player.getUniqueId(), 1);
                }));
                inv.setItem(11, ItemBuilder.from(items.police_cuff).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(12, ItemBuilder.from(items.phone).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(13, ItemBuilder.from(items.hausvertrag).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(14, ItemBuilder.from(items.cocain).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addCocain(player.getUniqueId(), 1);
                }));
                ;
                inv.setItem(15, ItemBuilder.from(items.weed).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addWeed(player.getUniqueId(), 1);
                }));
                ;
                inv.setItem(16, ItemBuilder.from(items.meth).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addMeth(player.getUniqueId(), 1);
                }));
                ;
                inv.setItem(17, ItemBuilder.from(items.medizin).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    setDrugs.addMedizin(player.getUniqueId(), 1);
                }));
                ;
                inv.setItem(18, ItemBuilder.from(items.iron).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;

                inv.setItem(19, ItemBuilder.from(items.wood).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(20, ItemBuilder.from(items.bohrer).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(21, ItemBuilder.from(items.sprengstoff).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(22, ItemBuilder.from(items.cocainpap).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(23, ItemBuilder.from(items.kröten).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(24, ItemBuilder.from(items.weedpap).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(25, ItemBuilder.from(items.aramid).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(26, ItemBuilder.from(items.fangkäfig).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(27, ItemBuilder.from(items.spitzhacke).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;

                inv.setItem(28, ItemBuilder.from(items.axt).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(29, ItemBuilder.from(items.sichel).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(30, ItemBuilder.from(items.gold).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(31, ItemBuilder.from(items.wood_finish).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(32, ItemBuilder.from(items.iron_finish).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;

                inv.setItem(33, ItemBuilder.from(items.cocainchest).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(34, ItemBuilder.from(items.methchest).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(35, ItemBuilder.from(items.weedchest).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(36, ItemBuilder.from(items.chestplatechest).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;
                inv.setItem(37, ItemBuilder.from(items.box).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(event.getCurrentItem());
                }));
                ;

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
