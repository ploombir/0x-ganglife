package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class profileCommand implements CommandExecutor {
    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    setLevel setLevel = new setLevel(main.getPlugin().getDatabaseAsync().getDataSource());
    setPayDays setPayDays = new setPayDays(main.getPlugin().getDatabaseAsync().getDataSource());
    setFaction setFaction = new setFaction(main.getPlugin().getDatabaseAsync().getDataSource());
    setPlayertime setPlayertime = new setPlayertime(main.getPlugin().getDatabaseAsync().getDataSource());
    setHouse setHouse = new setHouse(main.getPlugin().getDatabaseAsync().getDataSource());
    setDrink setDrink = new setDrink(main.getPlugin().getDatabaseAsync().getDataSource());


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 0) {
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

            setEconomy.getMoney(player.getUniqueId()).thenAccept(money -> {
                GuiItem playerMoney = ItemBuilder.from(Material.GOLD_INGOT).name(Component.text("§7Dein Bargeld:"))
                        .lore(Component.text(" §f▹ §6" + money.get() + "$"))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Dein Bargeld: §6" + money.get() + "$");
                            player.closeInventory();
                        });
                profileInventory.setItem(21, playerMoney);
            });
            AtomicInteger requiredEXP = new AtomicInteger();

            setLevel.getLevelSystem(player.getUniqueId()).thenAccept(level -> {
                GuiItem playerLevel = ItemBuilder.from(Material.EXPERIENCE_BOTTLE).name(Component.text("§7Dein Level:"))
                        .lore(Component.text(" §f▹ §6"  + level.get()))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Dein Level: §6" + level.get());
                            player.closeInventory();
                        });
                profileInventory.setItem(22, playerLevel);

                requiredEXP.set(500 + (level.get() - 1) * 250);
            });

            setLevel.getExpSystem(player.getUniqueId()).thenAccept(exp -> {
                GuiItem playerEXP = ItemBuilder.from(Material.DRAGON_BREATH).name(Component.text(" §f▹ §7Deine Experience:"))
                        .lore(Component.text(" §f▹ §6" + exp.get() + "§7/§6" + requiredEXP))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Deine Experience: §6" + exp.get() + "§7/§6" + requiredEXP);
                            player.closeInventory();
                        });
                profileInventory.setItem(23, playerEXP);
            });

            setPayDays.getPayDay(player.getUniqueId()).thenAccept(payday -> {
                GuiItem playerPayDay = ItemBuilder.from(Material.EMERALD).name(Component.text("§7Dein PayDay:"))
                        .lore(Component.text(" §f▹ §6" + payday.get() + "§7/§660"))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Dein PayDay: §6" + payday.get() + "§7/§660");
                            player.closeInventory();
                        });
                profileInventory.setItem(30, playerPayDay);
            });
            setFaction.getFaction(player.getUniqueId()).thenAccept(faction -> {
                setFaction.getFaction(player.getUniqueId()).thenAccept(rank -> {
                    GuiItem playerFaction = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Fraktion:"))
                            .lore(Component.text(" §f▹ §6" + faction.get() + " §7- §6" + rank.get()))
                            .asGuiItem(clickEvent -> {
                                main.playProccessSound(player);
                                player.sendMessage(main.prefix + "§7Deine Fraktion: §6" + faction.get() + " §7- §6" + rank.get());
                                player.closeInventory();
                            });
                    profileInventory.setItem(31, playerFaction);
                        });
            });
            setHouse.getHouseSystem(player.getUniqueId()).thenAccept(house -> {
                GuiItem playerHouse = ItemBuilder.from(Material.BRICK).name(Component.text("§7Deine Hausnummer:"))
                        .lore(Component.text(" §f▹ §6" + house.get()))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Deine Hausnummer: §6" + house.get());
                            player.closeInventory();
                        });

                GuiItem playerHouseNo = ItemBuilder.from(Material.BRICK).name(Component.text("§7Deine Hausnummer:"))
                        .lore(Component.text(" §f▹ §6Obdachlos"))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Du besitzt kein Haus.");
                            player.closeInventory();
                        });

                if(setHouse.hasHouse(player.getUniqueId(), 0)) {
                    profileInventory.setItem(32, playerHouseNo);
                } else {
                    profileInventory.setItem(32, playerHouse);
                }
            });
            setPlayertime.getPlayetime(player.getUniqueId()).thenAccept(time -> {
                GuiItem playerTime = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Spielzeit:"))
                        .lore(Component.text(" §f▹ §6" + time.get() + " Stunden"))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Deine Spielzeit: §6" + time.get());
                            player.closeInventory();
                        });
                profileInventory.setItem(39, playerTime);
            });

            setDrink.getDrink(player.getUniqueId()).thenAccept(drink -> {
                GuiItem playerDrink = ItemBuilder.from(Material.BONE).name(Component.text("§7Dein Durst:"))
                        .lore(Component.text(" §f▹ §6" + drink.get() + "§7/§610"))
                        .asGuiItem(clickEvent -> {
                            main.playProccessSound(player);
                            player.sendMessage(main.prefix + "§7Dein Durst : §6" + drink.get());
                            player.closeInventory();
                        });
                profileInventory.setItem(40, playerDrink);
            });


            profileInventory.setItem(4, playerHead);
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

            profileInventory.open(player);
            main.playProccessSound(player);
        }
        return false;
    }
}
