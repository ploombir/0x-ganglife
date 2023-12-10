package de.marc.ganglife.phone.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class auflegenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) cs;

        if(args.length == 0) {
            if(callCommand.phoneIsInCall.contains(player)) {
                player.sendMessage(main.prefix + "§cDu hast aufgelegt.");
                main.playProccessSound(player);
                Player target = callCommand.phoneCaller.get(player);

                target.sendMessage(main.prefix + "§cDer Anruf wurde beendet.");
                main.playProccessSound(target);
                callCommand.phoneIsInCall.remove(player);
                callCommand.phoneIsInCall.remove(target);
            } else {
                player.sendMessage(main.pre_error + "§cDu bist in keinem Anruf.");
                main.playErrorSound(player);
            }
        } else {
            player.sendMessage(main.pre_error + "§cBitte benutze /auflegen");
            main.playErrorSound(player);
        }

        return false;
    }
}
