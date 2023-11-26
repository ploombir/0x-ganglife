package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tphereCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission("system.admin")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /tphere <Spieler>");
                main.playErrorSound(player);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage(main.notonline);
                main.playErrorSound(player);
                return true;
            }
            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht zu dir selber teleportieren.");
                main.playErrorSound(player);
                return true;
            }

            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7zu dir teleportiert.");
            target.teleport(player.getLocation());
            target.sendMessage(main.prefix + "§7Du wurdest zu §6" + player.getName() + " §7teleportiert.");
            main.playProccessSound(player);
            main.playProccessSound(target);

        }

        return false;
    }
}
