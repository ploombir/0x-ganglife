package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.getter;
import de.marc.ganglife.dataSetter.setEconomy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class healCommand implements CommandExecutor {

    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player player) {
            if(player.hasPermission("system.heal")) {
                if(args.length == 0) {
                    player.setHealth(player.getMaxHealth());
                    player.setFoodLevel(20);
                    player.sendMessage(main.prefix + "§7Du hast dich erfolgreich geheilt!");
                    main.playSuccessSound(player);
                    player.sendMessage("" + getter.getMoney(player.getUniqueId()));
                }
            } else {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
            }
            if(args.length == 1) {
                if(player.hasPermission("system.heal.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        player.sendMessage(main.prefix + "Du hast §6" + target.getName() + " §7geheilt!");
                        target.sendMessage(main.prefix + "Du wurdest von §6" + player.getName() + " §7geheilt!");
                        target.setHealth(target.getMaxHealth());
                        target.setFoodLevel(20);
                        main.playSuccessSound(target);
                    } else {
                        player.sendMessage(main.notonline);
                        main.playErrorSound(player);
                    }
                } else {
                    player.sendMessage(main.noperms);
                    main.playErrorSound(player);
                }
            }
        }
        return false;
    }
}
