package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class checkitemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission("system.admin")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            player.sendMessage(player.getInventory().getItemInMainHand().toString());

        }

        return false;
    }
}
