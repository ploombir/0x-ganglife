package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class profileCommand implements CommandExecutor {
    getLastID getLastID = new getLastID(main.getPlugin().getDatabaseAsync().getDataSource());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if (args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /profil");
                main.playErrorSound(player);
                return true;
            }
            Gui profileInventory = Gui.gui()
                    .rows(6)
                    .title(Component.text(main.prefix + "§7Dein Profil:"))
                    .disableAllInteractions()
                    .create();

            GuiItem glass = ItemBuilder.from(Material.BLACK_STAINED_GLASS).name(Component.text(" "))
                    .asGuiItem(clickEvent -> {
                        player.closeInventory();
                    });

            GuiItem playerHead = ItemBuilder.from(Material.PLAYER_HEAD).name(Component.text("§a" + player.getName()))
                    .setSkullOwner(player)
                    .asGuiItem(clickEvent -> {
                        player.closeInventory();
                    });

            GuiItem playerHeadPremium = ItemBuilder.from(Material.PLAYER_HEAD).name(Component.text("§a" + player.getName() + " §7(§6Premium§7)"))
                    .setSkullOwner(player)
                    .asGuiItem(clickEvent -> {
                        player.closeInventory();
                    });

            GuiItem playerMoney = ItemBuilder.from(Material.GOLD_INGOT).name(Component.text("§7Dein Bargeld:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getCash() + "$"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein Bargeld: §6" + uPlayer.getCash() + "$");
                        player.closeInventory();
                    });

            GuiItem playerLevel = ItemBuilder.from(Material.EXPERIENCE_BOTTLE).name(Component.text("§7Dein Level:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getLevel()))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein Level: §6" + uPlayer.getLevel());
                        player.closeInventory();
                    });


            int requiredEXP = 500 + (uPlayer.getLevel() - 1) * 250;

            GuiItem playerEXP = ItemBuilder.from(Material.DRAGON_BREATH).name(Component.text(" §f▹ §7Deine Experience:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getLevelExp() + "§7/§6" + requiredEXP))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Experience: §6" + uPlayer.getLevelExp() + "§7/§6" + requiredEXP);
                        player.closeInventory();
                    });


            GuiItem playerPayDay = ItemBuilder.from(Material.EMERALD).name(Component.text("§7Dein PayDay:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getPaydayTime() + "§7/§660"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein PayDay: §6" + uPlayer.getPaydayTime() + "§7/§660");
                        player.closeInventory();
                    });

            GuiItem playerFaction = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Fraktion:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getFaction() + " §7- §6" + uPlayer.getFactionRank()))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Fraktion: §6" + uPlayer.getFaction() + " §7- §6" + uPlayer.getFactionRank());
                        player.closeInventory();
                    });
            GuiItem playerNoFaction = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Fraktion:"))
                    .lore(Component.text(" §f▹ §6Zivilist"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§cDu bist in keiner Fraktion.");
                        player.closeInventory();
                    });

            GuiItem playerHouse = ItemBuilder.from(Material.BRICK).name(Component.text("§7Deine Hausnummer:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getHouseNumber()))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Hausnummer: §6" + uPlayer.getHouseNumber());
                        player.closeInventory();
                    });

            GuiItem playerHouseNo = ItemBuilder.from(Material.BRICK).name(Component.text("§7Deine Hausnummer:"))
                    .lore(Component.text(" §f▹ §6Obdachlos"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Du besitzt kein Haus.");
                        player.closeInventory();
                    });

            GuiItem playerTime = ItemBuilder.from(Material.CLOCK).name(Component.text("§7Deine Spielzeit:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getPlayTime() + " Stunden"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Spielzeit: §6" + uPlayer.getPlayTime());
                        player.closeInventory();
                    });


            GuiItem playerDrink = ItemBuilder.from(Material.POTION).name(Component.text("§7Dein Durst:"))
                    .lore(Component.text(" §f▹ §6" + uPlayer.getDrink() + "§7/§610"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.closeInventory();
                    });


            if(uPlayer.isPremiumAccount()) {
                profileInventory.setItem(4, playerHeadPremium);
            } else {
                profileInventory.setItem(4, playerHead);
            }


            profileInventory.setItem(21, playerMoney);
            profileInventory.setItem(22, playerLevel);
            profileInventory.setItem(23, playerEXP);
            profileInventory.setItem(30, playerPayDay);

            if (uPlayer.getFaction().equals("Zivilist")) {
                profileInventory.setItem(31, playerNoFaction);
            } else {
                profileInventory.setItem(31, playerFaction);
            }

            if (uPlayer.getHouseNumber() == 0) {
                profileInventory.setItem(32, playerHouseNo);
            } else {
                profileInventory.setItem(32, playerHouse);
            }

            profileInventory.setItem(39, playerTime);
            profileInventory.setItem(40, playerDrink);

            profileInventory.setItem(1, glass);
            profileInventory.setItem(10, glass);
            profileInventory.setItem(19, glass);
            profileInventory.setItem(28, glass);
            profileInventory.setItem(37, glass);
            profileInventory.setItem(46, glass);
            profileInventory.setItem(7, glass);
            profileInventory.setItem(16, glass);
            profileInventory.setItem(25, glass);
            profileInventory.setItem(34, glass);
            profileInventory.setItem(43, glass);
            profileInventory.setItem(52, glass);

            main.playProccessSound(player);
            profileInventory.open(player);

        }
        return false;
    }
}
