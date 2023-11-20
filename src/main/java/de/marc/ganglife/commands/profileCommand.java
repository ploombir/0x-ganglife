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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class profileCommand implements CommandExecutor {
    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    setLevel setLevel = new setLevel(main.getPlugin().getDatabaseAsync().getDataSource());
    setPayDays setPayDays = new setPayDays(main.getPlugin().getDatabaseAsync().getDataSource());
    setFaction setFaction = new setFaction(main.getPlugin().getDatabaseAsync().getDataSource());
    setPlayertime setPlayertime = new setPlayertime(main.getPlugin().getDatabaseAsync().getDataSource());
    setHouse setHouse = new setHouse(main.getPlugin().getDatabaseAsync().getDataSource());
    setDrink setDrink = new setDrink(main.getPlugin().getDatabaseAsync().getDataSource());

    setFBank setFBank = new setFBank(main.getPlugin().getDatabaseAsync().getDataSource());

    int MONEY;
    int LEVEL;
    int EXP;
    int PAYDAY;
    String FACTION;
    int RANK;
    int PLAYERTIME;
    int HOUSE;
    int DRINK;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /profil");
                main.playErrorSound(player);
                return true;
            }

            setDrink.getDrink(player.getUniqueId()).thenAccept(drink -> {
                DRINK = drink.get();
            });
            setEconomy.getMoney(player.getUniqueId()).thenAccept(money -> {
                MONEY = money.get();
            });
            setLevel.getLevelSystem(player.getUniqueId()).thenAccept(level -> {
                LEVEL = level.get();
            });
            setLevel.getExpSystem(player.getUniqueId()).thenAccept(exp -> {
                EXP = exp.get();
            });
            setPayDays.getPayDay(player.getUniqueId()).thenAccept(payday -> {
                PAYDAY = payday.get();
            });
            setFaction.getRank(player.getUniqueId()).thenAccept(rank -> {
                RANK = rank.get();
            });
            setFaction.getFaction(player.getUniqueId()).thenAccept(faction -> {
                FACTION = faction.get();
            });
            setHouse.getHouseSystem(player.getUniqueId()).thenAccept(house -> {
                HOUSE = house.get();
            });
            setPlayertime.getPlayetime(player.getUniqueId()).thenAccept(time -> {
                PLAYERTIME = time.get();
            });

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

            GuiItem playerMoney = ItemBuilder.from(Material.GOLD_INGOT).name(Component.text("§7Dein Bargeld:"))
                    .lore(Component.text(" §f▹ §6" + MONEY + "$"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein Bargeld: §6" + MONEY + "$");
                        player.closeInventory();
                    });

            GuiItem playerLevel = ItemBuilder.from(Material.EXPERIENCE_BOTTLE).name(Component.text("§7Dein Level:"))
                    .lore(Component.text(" §f▹ §6"  + LEVEL))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein Level: §6" + LEVEL);
                        player.closeInventory();
                    });


            int requiredEXP = 500 + (LEVEL - 1) * 250;

            GuiItem playerEXP = ItemBuilder.from(Material.DRAGON_BREATH).name(Component.text(" §f▹ §7Deine Experience:"))
                    .lore(Component.text(" §f▹ §6" + EXP + "§7/§6" + requiredEXP))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Experience: §6" + EXP + "§7/§6" + requiredEXP);
                        player.closeInventory();
                    });


            GuiItem playerPayDay = ItemBuilder.from(Material.EMERALD).name(Component.text("§7Dein PayDay:"))
                    .lore(Component.text(" §f▹ §6" + PAYDAY + "§7/§660"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Dein PayDay: §6" + PAYDAY + "§7/§660");
                        player.closeInventory();
                    });

            GuiItem playerFaction = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Fraktion:"))
                    .lore(Component.text(" §f▹ §6" + FACTION + " §7- §6" + RANK))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Fraktion: §6" + FACTION + " §7- §6" + RANK);
                        player.closeInventory();
                    });
            GuiItem playerNoFaction = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Fraktion:"))
                    .lore(Component.text(" §f▹ §6Zivilist"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Fraktion: §6" + FACTION);
                        player.closeInventory();
                    });

            GuiItem playerHouse = ItemBuilder.from(Material.BRICK).name(Component.text("§7Deine Hausnummer:"))
                    .lore(Component.text(" §f▹ §6" + HOUSE))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Hausnummer: §6" + HOUSE);
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
                    .lore(Component.text(" §f▹ §6" + PLAYERTIME + " Stunden"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.sendMessage(main.prefix + "§7Deine Spielzeit: §6" + PLAYERTIME);
                        player.closeInventory();
                    });


            GuiItem playerDrink = ItemBuilder.from(Material.POTION).name(Component.text("§7Dein Durst:"))
                    .lore(Component.text(" §f▹ §6" + DRINK + "§7/§61ß"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.closeInventory();
                    });

            profileInventory.setItem(4, playerHead);
            profileInventory.setItem(21, playerMoney);
            profileInventory.setItem(22, playerLevel);
            profileInventory.setItem(23, playerEXP);
            profileInventory.setItem(30, playerPayDay);

            if(setFaction.isInFaction(player.getUniqueId(), "Zivilist")) {
                profileInventory.setItem(31, playerNoFaction);
            } else {
                profileInventory.setItem(31, playerFaction);
            }

            if(setHouse.hasHouse(player.getUniqueId(), 0)) {
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
