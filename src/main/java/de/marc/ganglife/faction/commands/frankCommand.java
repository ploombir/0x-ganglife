package de.marc.ganglife.faction.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.faction.methods.sendFactionMessage;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class frankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Fraktionen zu verwalten.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /fkick <Spieler> <Rang>");
                main.playErrorSound(player);
                return true;
            }
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(uPlayer.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }
            if(uPlayer.getFactionRank() < 5) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);

            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst dir nicht selber einen anderen Rang setzen.");
                main.playErrorSound(player);
                return true;
            }

            if(target == null) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());
            String rank = args[1];

            if(!isInteger.isInt(rank)) {
                player.sendMessage(main.pre_error + "§cBitte gebe eine Zahl an.");
                main.playErrorSound(player);
                return true;
            }
            if(Integer.parseInt(rank) > 6 || Integer.parseInt(rank) < 1) {
                player.sendMessage(main.pre_error + "§cBitte gebe einen Rang von 1 bis 6 an.");
                main.playErrorSound(player);
                return true;
            }

            uTarget.setFactionRank(Integer.parseInt(rank));
            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7auf Rang §6" + rank + " §7gesetzt.");
            target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7auf Rang §6" + rank + " §7gesetzt.");
            main.playProccessSound(player);
            main.playSuccessSound(target);
            sendFactionMessage.sendFactionMessage(player, target.getName() + " wurde auf Rang " + rank + " gesetzt.");
        }
        return false;
    }
}
