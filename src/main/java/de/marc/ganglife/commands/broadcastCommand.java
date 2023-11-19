package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class broadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(cs instanceof Player player) {
            if(!player.hasPermission("system.broadcast")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            String msg = "";

            if (args.length >= 1) {
                for (int i = 0; i < args.length; i++) {
                    msg = msg + " " + args[i];
                }
                Bukkit.broadcast(Component.text("§e§lAnkündigung §f▹ " + player.getName() + "§a" + msg));
                main.playProccessSound(player);
            } else {
                player.sendMessage(main.prefix + "§aBenutze /broadcast <Nachricht>");
                main.playErrorSound(player);
            }

        }
        return false;
    }
}
