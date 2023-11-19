package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class blockfarmingCommand implements CommandExecutor {
    public static boolean allowFarming = true;

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(cs instanceof Player player) {
            if(player.hasPermission("system.admin")) {
                if(allowFarming == true) {
                    player.sendMessage(main.prefix + "§cFarming wurde nun geblockt.");
                    allowFarming = false;
                    main.playProccessSound(player);
                } else {
                    player.sendMessage(main.prefix + "§aFarming wurde nun aktiviert.");
                    allowFarming = true;
                    main.playProccessSound(player);
                }
            } else {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
            }
        } else if(cs instanceof ConsoleCommandSender) {
            if(allowFarming == true) {
                Bukkit.getConsoleSender().sendMessage(main.prefix + "§cFarming wurde nun geblockt.");
                allowFarming = false;
            } else {
                Bukkit.getConsoleSender().sendMessage(main.prefix + "§aFarming wurde nun aktiviert.");
                allowFarming = true;
            }
        }
        return false;
    }
}
