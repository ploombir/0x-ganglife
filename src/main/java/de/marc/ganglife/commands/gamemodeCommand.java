package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class gamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(player.hasPermission("system.gm")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("0")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(main.prefix + "§7Du bist nun im Survival Modus!");
                        main.playProccessSound(player);
                    } else if(args[0].equalsIgnoreCase("1")) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(main.prefix + "§7Du bist nun im Creative Modus!");
                        main.playProccessSound(player);
                    } else if(args[0].equalsIgnoreCase("2")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(main.prefix + "§7Du bist nun im Adventure Modus!");
                        main.playProccessSound(player);
                    } else if(args[0].equalsIgnoreCase("3")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(main.prefix + "§7Du bist nun im Spectator Modus!");
                        main.playProccessSound(player);
                    }
                } else if(args.length == 0) {
                    player.sendMessage(main.prefix + "§cBenutze /gm 1 2 3 <Spieler>");
                }

            } else {
                player.sendMessage(main.noperms);
            }
            if(args.length == 2) {
                if(player.hasPermission("system.gm.other")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null) {
                        if(args[0].equalsIgnoreCase("0")) {
                            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7in den Survival Modus gesetzt!");
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7in den Survival Modus gesetzt!");
                            main.playProccessSound(player);
                            main.playProccessSound(target);
                        } else if(args[0].equalsIgnoreCase("1")) {
                            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7in den Creative Modus gesetzt!");
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7in den Creative Modus gesetzt!");
                            main.playProccessSound(player);
                            main.playProccessSound(target);
                        } else if(args[0].equalsIgnoreCase("2")) {
                            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7in den Adventure Modus gesetzt!");
                            target.setGameMode(GameMode.ADVENTURE);
                            target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7in den Adventure Modus gesetzt!");
                            main.playProccessSound(player);
                            main.playProccessSound(target);
                        } else if(args[0].equalsIgnoreCase("3")) {
                            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7in den Spectator Modus gesetzt!");
                            target.setGameMode(GameMode.SPECTATOR);
                            target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7in den Spectator Modus gesetzt!");
                            main.playProccessSound(player);
                            main.playProccessSound(target);
                        }
                    } else {
                        player.sendMessage(main.notonline);
                    }
                } else {
                    player.sendMessage(main.noperms);
                }
            }
        }
        
        return false;
    }
}
