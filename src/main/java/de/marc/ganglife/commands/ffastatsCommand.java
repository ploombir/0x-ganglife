package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.setFFA;
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

public class ffastatsCommand implements CommandExecutor {

    setFFA setFFA = new setFFA(main.getPlugin().getDatabaseAsync().getDataSource());

    int KILLS;
    int DEATHS;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /ffastats");
                main.playErrorSound(player);
                return true;
            }

            setFFA.getFFAKills(player.getUniqueId()).thenAccept(getKills -> {
                KILLS = getKills.get();
                setFFA.getFFADeaths(player.getUniqueId()).thenAccept(getDeaths -> {
                    DEATHS = getDeaths.get();

                    Bukkit.getScheduler().runTask(main.getPlugin(), () -> createStatsGUI(player, KILLS, DEATHS));
                });
            });
        }

        return false;
    }

    public static void createStatsGUI(Player player, int ffa_kills, int ffa_deaths) {
        Gui statsInventory = Gui.gui()
                .rows(5)
                .title(Component.text(main.prefix + "§7FFA-Statistiken:"))
                .disableAllInteractions()
                .create();

        GuiItem playerHead = ItemBuilder.from(Material.PLAYER_HEAD).name(Component.text("§a" + player.getName()))
                .setSkullOwner(player)
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                });
        GuiItem kills = ItemBuilder.from(Material.ARROW).name(Component.text("§7Deine Kills"))
                .lore(Component.text(" §f▹ §7" + ffa_kills + " Kills"))
                .asGuiItem(clickEvent -> {
                    main.playProccessSound(player);
                    player.closeInventory();
                    player.sendMessage(main.prefix + "§7Deine Kills in FFA: §6" + ffa_kills);
                });


        GuiItem deaths = ItemBuilder.from(Material.BONE).name(Component.text("§7Deine Tode"))
                .lore(Component.text(" §f▹ §7" + ffa_deaths + " Tode"))
                .asGuiItem(clickEvent -> {
                    main.playProccessSound(player);
                    player.closeInventory();
                    player.sendMessage(main.prefix + "§7Deine Tode in FFA: §6" + ffa_deaths);
                });

        if(ffa_kills == 0 || ffa_deaths == 0) {
            GuiItem killsdeathsnull = ItemBuilder.from(Material.IRON_SWORD).name(Component.text("§7Deine K/D"))
                    .lore(Component.text(" §f▹ §7 0 K/D"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.closeInventory();
                        player.sendMessage(main.prefix + "§7Deine K/D in FFA: §60");
                    });
            statsInventory.setItem(23, killsdeathsnull);
        } else {
            GuiItem killsdeaths = ItemBuilder.from(Material.IRON_SWORD).name(Component.text("§7Deine K/D"))
                    .lore(Component.text(" §f▹ §7" + ffa_kills/ffa_deaths + " K/D"))
                    .asGuiItem(clickEvent -> {
                        main.playProccessSound(player);
                        player.closeInventory();
                        player.sendMessage(main.prefix + "§7Deine K/D in FFA: §6" + ffa_kills/ffa_deaths);
                    });
            statsInventory.setItem(23, killsdeaths);
        }


        GuiItem glass = ItemBuilder.from(Material.BLACK_STAINED_GLASS).name(Component.text(" "))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                });


        statsInventory.setItem(4, playerHead);
        statsInventory.setItem(21, kills);
        statsInventory.setItem(22, deaths);
        statsInventory.setItem(1, glass);
        statsInventory.setItem(10, glass);
        statsInventory.setItem(19, glass);
        statsInventory.setItem(28, glass);
        statsInventory.setItem(37, glass);
        statsInventory.setItem(7, glass);
        statsInventory.setItem(16, glass);
        statsInventory.setItem(25, glass);
        statsInventory.setItem(34, glass);
        statsInventory.setItem(43, glass);


        statsInventory.open(player);
        main.playProccessSound(player);
    }
}