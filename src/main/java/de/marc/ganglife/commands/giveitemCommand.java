package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.playerdatas.UPlayer;
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

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (cs instanceof Player player) {
            if (player.hasPermission("system.admin")) {
                UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                Gui inv = Gui.gui()
                        .rows(5)
                        .title(Component.text(main.prefix + "§7Admin Items"))
                        .disableAllInteractions()
                        .create();

                inv.setItem(0, ItemBuilder.from(items.M4.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.M4.getItem());
                }));
                inv.setItem(1, ItemBuilder.from(items.AK47.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.AK47.getItem());
                }));

                inv.setItem(2, ItemBuilder.from(items.PDW.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.PDW.getItem());
                }));
                inv.setItem(3, ItemBuilder.from(items.UZI.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.UZI.getItem());
                }));
                inv.setItem(4, ItemBuilder.from(items.PISTOL.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.PISTOL.getItem());
                }));
                inv.setItem(5, ItemBuilder.from(items.RIFLE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.RIFLE.getItem());
                }));
                inv.setItem(6, ItemBuilder.from(items.AWP.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.AWP.getItem());
                }));
                inv.setItem(7, ItemBuilder.from(items.LASERGUN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.LASERGUN.getItem());
                }));
                inv.setItem(8, ItemBuilder.from(items.CUFFS.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.CUFFS.getItem());
                }));
                inv.setItem(9, ItemBuilder.from(items.BULLETPROOF.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setBulletproofAmount(uPlayer.getBulletproofAmount() + 5);
                }));

                inv.setItem(10, ItemBuilder.from(items.POLICE_BULLETPROOF.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setPoliceBulletproofAmount(uPlayer.getPoliceBulletproofAmount() + 5);
                }));
                inv.setItem(11, ItemBuilder.from(items.POLICE_CUFF.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.POLICE_CUFF.getItem());
                }));

                inv.setItem(12, ItemBuilder.from(items.PHONE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.PHONE.getItem());
                }));

                inv.setItem(13, ItemBuilder.from(items.HOUSE_CONTRACT.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.HOUSE_CONTRACT.getItem());
                }));

                inv.setItem(14, ItemBuilder.from(items.COCAIN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setCocaineAmount(uPlayer.getCocaineAmount() + 5);
                }));

                inv.setItem(15, ItemBuilder.from(items.WEED.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setWeedAmount(uPlayer.getWeedAmount() + 5);
                }));

                inv.setItem(16, ItemBuilder.from(items.METH.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setMethAmount(uPlayer.getMethAmount() + 5);
                }));

                inv.setItem(17, ItemBuilder.from(items.MEDIZIN.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    uPlayer.setMedicineAmount(uPlayer.getMedicineAmount() + 5);
                }));

                inv.setItem(18, ItemBuilder.from(items.IRON_INGOT.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.IRON_INGOT.getItem());
                }));

                inv.setItem(19, ItemBuilder.from(items.WOOD_LOG.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.WOOD_LOG.getItem());
                }));

                inv.setItem(20, ItemBuilder.from(items.DRILL.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.DRILL.getItem());
                }));

                inv.setItem(21, ItemBuilder.from(items.C4.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.C4.getItem());
                }));

                inv.setItem(22, ItemBuilder.from(items.COCAINE_LEAVES.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.COCAINE_LEAVES.getItem());
                }));

                inv.setItem(23, ItemBuilder.from(items.FROG.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.FROG.getItem());
                }));

                inv.setItem(24, ItemBuilder.from(items.WEED_LEAVES.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.WEED_LEAVES.getItem());
                }));

                inv.setItem(25, ItemBuilder.from(items.ARAMID.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.ARAMID.getItem());
                }));

                inv.setItem(26, ItemBuilder.from(items.CAGE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.CAGE.getItem());
                }));

                inv.setItem(27, ItemBuilder.from(items.PICKAXE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.PICKAXE.getItem());
                }));

                inv.setItem(28, ItemBuilder.from(items.AXE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.AXE.getItem());
                }));

                inv.setItem(29, ItemBuilder.from(items.HOE.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.HOE.getItem());
                }));

                inv.setItem(30, ItemBuilder.from(items.GOLD_INGOT.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.GOLD_INGOT.getItem());
                }));

                inv.setItem(31, ItemBuilder.from(items.WOOD_PLANKS.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.WOOD_PLANKS.getItem());
                }));

                inv.setItem(32, ItemBuilder.from(items.IRON_INGOT.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.IRON_INGOT.getItem());
                }));


                inv.setItem(33, ItemBuilder.from(items.COCAINE_CHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.COCAINE_CHEST.getItem());
                }));

                inv.setItem(34, ItemBuilder.from(items.METH_CHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.METH_CHEST.getItem());
                }));

                inv.setItem(35, ItemBuilder.from(items.WEED_CHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.WEED_CHEST.getItem());
                }));

                inv.setItem(36, ItemBuilder.from(items.BULLETPROOF_CHEST.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.BULLETPROOF_CHEST.getItem());
                }));

                inv.setItem(37, ItemBuilder.from(items.BOX.getItem()).asGuiItem(event -> {
                    main.playProccessSound(player);
                    Bukkit.getConsoleSender().sendMessage(main.log + "§e" + player.getName() + " §9hat sich eine §f" + event.getCurrentItem().getItemMeta().getDisplayName() + " §9gegeben.");
                    player.getInventory().addItem(items.BOX.getItem());
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
