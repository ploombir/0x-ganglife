package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerEvents.interactionmenu;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class uncuffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /uncuff <Spieler>");
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
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber entfesseln.");
                main.playErrorSound(player);
                return true;
            }

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
            if(uPlayer.getDeathTime() >= 1) return true;

            if(interactionmenu.cuff.contains(player)) {
                player.sendMessage(main.pre_error + "§cDu kannst nicht gefesselt, andere Personen entfesseln.");
                main.playErrorSound(player);
                return true;
            }
            if(!interactionmenu.cuff.contains(target)) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht gefesselt.");
                main.playErrorSound(player);
                return true;
            }
            if(player.getLocation().distance(target.getLocation()) >= 2) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner nähe.");
                main.playErrorSound(player);
                return true;
            }

            player.sendMessage(main.pre_error + "§7Du hast §e" + target.getName() + " §7entfesselt.");
            target.sendMessage(main.pre_error + "§7Du wurdest von §e" + player.getName() + " §7entfesselt.");
            main.playProccessSound(player);
            main.playProccessSound(target);

            target.setWalkSpeed((float) 0.2);
            interactionmenu.cuff.remove(target);
        }
        return false;
    }
}
