package de.marc.ganglife.phone.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setMobile;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.methods.phone;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class smsCommand implements CommandExecutor {

    setMobile setMobile = new setMobile(main.getPlugin().getDatabaseAsync().getDataSource());
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Nachrichten zu versenden.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length <= 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /call <Nummer> <Nachricht>.");
                main.playErrorSound(player);
                return true;
            }

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(uPlayer.isPhoneFlightMode()) {
                player.sendMessage(main.pre_error + "§cDu kannst im Flugmodus keine Nachrichten versenden.");
                main.playErrorSound(player);
                return true;
            }

            if(!isInteger.isInt(args[0])) {
                player.sendMessage(main.pre_error + "§cBitte gebe eine Zahl an.");
                main.playErrorSound(player);
                return true;
            }

            setMobile.getPlayerFromNumber(args[0]).thenAccept(targetName -> {
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    if(targetName.isEmpty()) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer existiert nicht.");
                        main.playErrorSound(player);
                        return;
                    }
                    Player target = Bukkit.getPlayer(targetName.get());

                    if(target == player) {
                        player.sendMessage(main.pre_error + "§cDu kannst dir nicht selber eine Nachricht schreiben.");
                        main.playErrorSound(player);
                        return;
                    }

                    if(target == null) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer ist zurzeit nicht erreichbar.");
                        main.playErrorSound(player);
                        return;
                    }
                    UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());

                    if(uTarget.isPhoneFlightMode()) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer ist zurzeit nicht erreichbar.");
                        main.playErrorSound(player);
                        return;
                    }

                    boolean foundTarget = Arrays.stream(target.getInventory().getContents())
                            .filter(item -> item != null && item.getType() != Material.AIR)
                            .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                            .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

                    if(!foundTarget) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer ist zurzeit nicht erreichbar.");
                        main.playErrorSound(player);
                        return;
                    }
                    String message = "";
                    for (int i = 1; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    player.sendMessage(main.prefix + "§aDu hast eine SMS an §e" + args[0] + " §7(" + phone.findContactNameByNumber(player, args[0]) + ") §aversendet.");
                    player.playSound(player.getLocation(), Sound.MUSIC_DISC_CAT, 1, 1);

                    String numberToFind = String.valueOf(uPlayer.getPhoneNumber());
                    target.sendMessage("§bSMS von §e" + uPlayer.getPhoneNumber() + " §7(" + phone.findContactNameByNumber(target, numberToFind) + ") " + "§f▹ §6" + message);
                    target.playSound(target.getLocation(), Sound.MUSIC_DISC_CAT, 1, 1);
                });
            });
        }

        return false;
    }
}
