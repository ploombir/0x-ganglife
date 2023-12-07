package de.marc.ganglife.phone.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class callCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player player) {
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /call <Nummer>");
                main.playErrorSound(player);
                return true;
            }
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(uPlayer.isPhoneFlightMode()) {
                player.sendMessage(main.pre_error + "§cDu kannst im Flugmodus keine Anrufe tätigen.");
                main.playErrorSound(player);
                return true;
            }
            if(!isInteger.isInt(args[0])) {
                player.sendMessage(main.pre_error + "§cBitte gebe eine Nummer an.");
                main.playErrorSound(player);
                return true;
            }


        }
        return false;
    }
}
