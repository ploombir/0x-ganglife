package de.marc.ganglife.faction.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setFaction;
import de.marc.ganglife.faction.methods.factionPrefixes;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class membersCommand implements CommandExecutor {

    setFaction setFaction = new setFaction(main.getPlugin().getDatabaseAsync().getDataSource());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um die Members zu verwalten.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /members.");
                main.playErrorSound(player);
                return true;
            }
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(uPlayer.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }

            main.playProccessSound(player);

            setFaction.getFactionMembers(uPlayer.getFaction()).thenAccept(members -> {
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    for (String member : members) {
                        Player players = Bukkit.getPlayer(member);

                        if(players != null) {
                            UPlayer uPlayers = UPlayer.getUPlayer(players.getUniqueId());
                            player.sendMessage(" §f▹ §a" + member + " §7- §6" + uPlayers.getFactionRank());
                        } else {
                            setFaction.getOfflineFactionRank(member).thenAccept(ranks -> {
                                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                                    player.sendMessage(" §f▹ §8" + member + "  §7- §6" + ranks.get());
                                });
                            });
                        }
                    }
                });
                player.sendMessage(main.prefix + "§7Gesamte Spieler in Fraktion: §7(" +factionPrefixes.getPrefix(player)
                        + uPlayer.getFaction() + " §f- §e" + members.size() + "§7/§e10§7)");
            });

        }

        return false;
    }
}
