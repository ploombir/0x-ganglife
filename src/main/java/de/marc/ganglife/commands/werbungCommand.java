package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.playerdatas.UPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class werbungCommand implements CommandExecutor {

    public static Map<Player, Integer> playerTimers = new HashMap<>();
    public static ArrayList<Player> blocked = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cs instanceof Player player) {
            String msg = "";
            int BlockDistance = 3;
            Location loc = new Location(player.getWorld(), 63, 67, -42);
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if (args.length >= 3) {
                for (int i = 0; i < args.length; i++) {
                    msg = msg + " " + args[i];
                }
                    if (player.getLocation().distance(loc) <= BlockDistance) {
                        if(!blocked.contains(player)) {
                            String finalMsg = msg;
                            if(uPlayer.getCash() >= 50) {
                                Bukkit.broadcast(Component.text("§bWERBUNG §f▹§7" + finalMsg + "."));
                                Bukkit.broadcast(Component.text(" §f▹ §aGeschaltet von §7" + player.getName() + " §7- §6" + uPlayer.getPhoneNumber()));

                                uPlayer.setCash(uPlayer.getCash() - 50);
                                blocked.add(player);

                                playerTimers.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                                    blocked.remove(player);
                                }, 60 * 20L));
                            } else {
                                player.sendMessage(main.prefix + "§cUm eine Werbung zu schalten benötigst du 50$.");
                                main.playErrorSound(player);
                            }
                        } else {
                            player.sendMessage(main.prefix + "§cDu kannst nur jede Minute eine Werbung schalten.");
                            main.playErrorSound(player);
                        }
                    } else {
                        player.sendMessage(main.prefix + "§cDu befindest dich nicht an der Werbungstafel.");
                        main.playErrorSound(player);
                    }
            } else {
                player.sendMessage(main.pre_error + "§cDie Werbung ist zu kurz.");
                main.playErrorSound(player);
            }
        }
        return false;
    }
}
