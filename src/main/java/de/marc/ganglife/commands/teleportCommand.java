package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class teleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission("system.teleport")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            if(args.length != 1 || args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /teleport <Spieler> oder /teleport <Spieler> zu <Spieler>");
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


            if(args.length == 1) {
                player.teleport(target.getLocation());
                player.sendMessage(main.prefix + "§7Du hast dich zu §6" + target.getName() + " §7teleportiert.");
                main.playProccessSound(player);
            } else if(args.length == 2) {
                Player target2 = Bukkit.getPlayer(args[1]);

                if(target2 == target) {
                    player.sendMessage(main.pre_error + "§cDu kannst nicht den gleichen Spieler zu sich selber teleportieren.");
                    main.playErrorSound(player);
                    return true;
                }

                if(target2 == null) {
                    player.sendMessage(main.notonline);
                    main.playErrorSound(player);
                    return true;
                }

                target.teleport(target2.getLocation());
                player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7zu §6" + target2.getName() + "  §7teleportiert.");
                target.sendMessage(main.prefix + "§6" + player.getName() + " §7hat dich zu §6" + target2.getName() + " §7teleportiert.");
                target2.sendMessage(main.prefix + "§6" + player.getName() + " §7hat §6" + target.getName() + " §7zu dir teleportiert.");

                main.playProccessSound(player);
                main.playProccessSound(target);
                main.playProccessSound(target2);
            }

        }

        return false;
    }
}
