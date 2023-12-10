package de.marc.ganglife.phone.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class denycallCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) cs;

        if(args.length == 0) {
            if(callCommand.phoneWaitingCall.contains(player)) {
                if(callCommand.phoneCaller.get(player) != null) {
                    player.sendMessage(main.prefix + "§aDu hast den Anruf §cabgelehnt.");
                    main.playProccessSound(player);

                    Player caller = callCommand.phoneCaller.get(player);

                    caller.sendMessage(main.prefix + "§aDein Anruf wurde §cabgelehnt.");
                    main.playProccessSound(caller);
                    callCommand.phoneWaitingCall.remove(player);
                    player.stopSound(Sound.MUSIC_DISC_STAL);
                } else {
                    player.sendMessage(main.pre_error + "§cDiese Nummer ist nicht mehr erreichbar.");
                    callCommand.phoneWaitingCall.remove(player);
                }
            } else {
                player.sendMessage(main.pre_error + "§cDu hast keinen eingehenden Anruf.");
                main.playErrorSound(player);
            }
        } else {
            player.sendMessage(main.pre_error + "§cBitte benutze /denycall");
            main.playErrorSound(player);
        }
        return false;
    }
}
