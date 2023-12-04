package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class buildCommand implements CommandExecutor {
    public static ArrayList<Player> allowed = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("system.build")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            if (args.length == 0) {
                if (allowed.contains(player)) {
                    allowed.remove(player);
                    player.sendMessage(main.prefix + "Du kannst nun §cnicht §7mehr bauen.");
                    main.playErrorSound(player);
                } else {
                    allowed.add(player);
                    player.sendMessage(main.prefix + "Du kannst nun bauen.");
                    main.playProccessSound(player);
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (allowed.contains(target)) {
                    allowed.remove(target);
                    player.sendMessage(main.prefix + "§e" + target.getName() + " §7kann nun §cnicht §7mehr bauen.");
                    main.playErrorSound(target);
                    main.playProccessSound(player);
                } else {
                    allowed.add(target);
                    player.sendMessage(main.prefix + "§e" + target.getName() + " §7kann nun bauen.");
                    main.playProccessSound(player);
                    main.playProccessSound(target);
                }
            }
        }
        return false;
    }
}
