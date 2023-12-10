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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class callCommand implements CommandExecutor {

    setMobile setMobile = new setMobile(main.getPlugin().getDatabaseAsync().getDataSource());
    public static ArrayList<Player> phoneWaitingCall = new ArrayList<>();
    public static ArrayList<Player> phoneIsInCall = new ArrayList<>();
    public static HashMap<Player, Player> phoneCaller = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player player) {
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /call <Nummer>");
                main.playErrorSound(player);
                return true;
            }

            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Anrufe zu tätigen.");
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

            setMobile.getPlayerFromNumber(args[0]).thenAccept(targetName -> {
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    if(targetName.isEmpty()) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer existiert nicht.");
                        main.playErrorSound(player);
                        return;
                    }
                    Player target = Bukkit.getPlayer(targetName.get());

                    if(target == player) {
                        player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber anrufen.");
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

                    phoneCaller.put(player, target);

                    if(phoneCaller.get(player) == null) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer ist zurzeit nicht erreichbar.");
                        main.playErrorSound(player);
                        return;
                    }

                    if(phoneWaitingCall.contains(target)) {
                        player.sendMessage(main.pre_error + "§cDiese Nummer wird bereits angerufen.");
                        main.playErrorSound(player);
                        return;
                    }

                    if(phoneIsInCall.contains(target)) {
                        player.sendMessage(main.pre_error + "§cDie Nummer ist bereits in einem Gespräch.");
                        main.playErrorSound(player);
                        return;
                    }
                    String phoneNumber = String.valueOf(uPlayer.getPhoneNumber());

                    target.sendMessage(main.prefix + "§7Die Nummer §e" + uPlayer.getPhoneNumber() +
                            " §6(" + phone.findContactNameByNumber(target, phoneNumber) + ") " +
                            "§7probiert dich anzurufen..");
                    target.sendMessage(" §f▹ §7§oNehme den Anruf mit §a/acceptcall §7an oder lehne diesen mit §c/denycall §7ab.");

                    player.sendMessage(main.prefix + "§7Du rufst nun §e" + args[0] + " §6(" + phone.findContactNameByNumber(player, args[0])+") §7an..");

                    target.playSound(target.getLocation(), Sound.MUSIC_DISC_STAL, 1, 1);

                    phoneWaitingCall.add(target);
                    phoneCaller.put(target, player);
                    main.playProccessSound(player);
                });
            });


        }
        return false;
    }
}
