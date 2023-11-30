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

public class fkickCommand implements CommandExecutor {

    setFaction setFaction = new setFaction(main.getPlugin().getDatabaseAsync().getDataSource());
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

            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /fkick <Spieler>");
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
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber aus deiner Fraktion kicken.");
                main.playErrorSound(player);
                return true;
            }


            if(target == null) {
                setFaction.getOfflineFaction(args[0]).thenAccept(faction -> {
                    Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                        if(faction.isEmpty()) {
                            player.sendMessage(main.pre_error + "§cDieser Spieler existiert nicht.");
                            main.playErrorSound(player);
                            return;
                        }
                        if(!faction.get().equals(uPlayer.getFaction())) {
                            player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner Fraktion.");
                            main.playErrorSound(player);
                            return;
                        }

                        setFaction.getOfflineFactionRank(args[0]).thenAccept(rank -> {
                            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                                    if(rank.isEmpty()) {
                                        player.sendMessage(main.pre_error + "§cDieser Spieler existiert nicht.");
                                        main.playErrorSound(player);
                                        return;
                                    }
                                    if(rank.get() == 6) {
                                        player.sendMessage(main.pre_error + "§cDu kannst diesen Spieler nicht aus deiner Fraktion kicken.");
                                        main.playErrorSound(player);
                                        return;
                                    }
                                    setFaction.setOfflineFaction("Zivilist", args[0]);
                                    setFaction.setOfflineFactionRank(0, args[0]);
                                    player.sendMessage(main.prefix + "§7Du hast §6" + args[0] + " §7aus der Fraktion " + factionPrefixes.getPrefix(player) + uPlayer.getFaction() + " §7geworfen.");
                                    main.playProccessSound(player);
                                });
                            });
                        });

                    });
                });
                return true;
            }

            UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());

            if(!uTarget.getFaction().equals(uPlayer.getFaction())) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }
            if(uTarget.getFactionRank() == 6) {
                player.sendMessage(main.pre_error + "§cDu kannst diesen Spieler nicht aus deiner Fraktion kicken.");
                main.playErrorSound(player);
                return true;
            }

            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7aus der Fraktion " + factionPrefixes.getPrefix(player) + uPlayer.getFaction() + " §7geworfen.");
            main.playProccessSound(player);
            target.sendMessage(main.prefix + "§7Du wurdest aus der Fraktion " + factionPrefixes.getPrefix(player) + uPlayer.getFaction() + " §7geworfen.");
            main.playProccessSound(target);
            uTarget.setFaction("Zivilist");
            uTarget.setFactionRank(0);
        }
        return false;
    }
}
