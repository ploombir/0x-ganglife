package de.marc.ganglife.faction.police;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class akteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /akte <Spieler>");
                main.playErrorSound(player);
                return true;
            }

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(!uPlayer.getFaction().equals("Polizei")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            if(uPlayer.getDeathTime() > 0) {
                player.sendMessage(main.pre_error + "§cDu kannst nicht Bewusstlos Akten bearbeiten.");
                main.playErrorSound(player);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst deine eigene Akte nicht bearbeiten.");
                main.playErrorSound(player);
                return true;
            }
        }

        return false;
    }
}
