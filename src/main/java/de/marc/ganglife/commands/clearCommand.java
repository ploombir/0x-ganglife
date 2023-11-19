package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class clearCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission("system.clear")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            if(args.length == 0) {
                player.getInventory().clear();
                player.sendMessage(main.prefix + "§7Du hast erfolgreich dein Inventar geleert.");
                main.playProccessSound(player);
            } else if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(main.notonline);
                    main.playErrorSound(player);
                    return true;
                }

                player.sendMessage(main.prefix + "§7Du hast das Inventar von §6" + target.getName() + " §7geleert.");
                target.getInventory().clear();
                target.sendMessage(main.prefix + "§7Dein Inventar wurde von §6" + player.getName() + " §7geleert.");
                main.playProccessSound(player);
                main.playErrorSound(target);
            } else {
                player.sendMessage(main.prefix + "§cBenutze /clear <Player>");
                main.playErrorSound(player);
            }
        }
        return false;
    }
}
