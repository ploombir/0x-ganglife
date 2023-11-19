package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.methods.isInteger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class payCommand implements CommandExecutor {



    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player player) {
            Player target = Bukkit.getPlayer(args[0]);
            String amount = args[1];
            int BlockDistance = 3;

            if(args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /pay <Spieler> <Betrag>");
                main.playErrorSound(player);
                return true;
            }
            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst dir nicht selber Geld geben.");
                main.playErrorSound(player);
                return true;
            }
            if(!isInteger.isInt(amount)) {
                player.sendMessage(main.pre_error + "§cBitte gebe eine Zahl an.");
                main.playErrorSound(player);
                return true;
            }
            if(Integer.valueOf(amount) > 0) {
                player.sendMessage(main.pre_error + "§cBitte gebe keine negative Zahl an.");
                main.playErrorSound(player);
                return true;
            }
            if(player.getLocation().distance(target.getLocation()) >= BlockDistance) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner nähe.");
                main.playErrorSound(player);
                return true;
            }


        }
        return false;
    }
}
