package de.marc.ganglife.phone.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.phone;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class acceptcallCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /acceptcall");
                main.playErrorSound(player);
                return true;
            }
            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Anrufe anzunehmen.");
                main.playErrorSound(player);
                return true;
            }

            if(!callCommand.phoneWaitingCall.contains(player)) {
                player.sendMessage(main.pre_error + "§cDu wirst nicht angerufen.");
                main.playErrorSound(player);
                return true;
            }
            if(callCommand.phoneIsInCall.contains(player)) {
                player.sendMessage(main.pre_error + "§cDu bist bereits in einem Anruf.");
                main.playErrorSound(player);
                return true;
            }
            Player caller = callCommand.phoneCaller.get(player);

            if(caller == null) {
                player.sendMessage(main.pre_error + "§cDiese Nummer ist nicht mehr erreichbar.");
                main.playErrorSound(player);
                return true;
            }

            callCommand.phoneIsInCall.add(player);
            callCommand.phoneIsInCall.add(caller);
            callCommand.phoneWaitingCall.remove(player);

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
            UPlayer uTarget = UPlayer.getUPlayer(caller.getUniqueId());


            player.sendMessage(main.prefix + "§aDu bist nun in einem Anruf mit §e" + uTarget.getPhoneNumber()
                    + " §6(" + phone.findContactNameByNumber(player, String.valueOf(uTarget.getPhoneNumber())) + ")§a:");
            player.sendMessage(" §f▹ §7§oUm aufzulegen gebe §c/auflegen §7§o in den Chat ein.");

            caller.sendMessage(main.prefix + "§aDu bist nun in einem Anruf mit §6" + uPlayer.getPhoneNumber() + " §7(" +
                    phone.findContactNameByNumber(caller, String.valueOf(uPlayer.getPhoneNumber())) + ")§a:");
            caller.sendMessage(" §f▹ §7§oUm aufzulegen gebe §c/auflegen §7§o in den Chat ein.");

            main.playSuccessSound(player);
            main.playSuccessSound(caller);
            player.stopSound(Sound.MUSIC_DISC_STAL);
        }

        return false;
    }
}
