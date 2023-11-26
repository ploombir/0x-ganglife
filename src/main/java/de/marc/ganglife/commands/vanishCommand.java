package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class vanishCommand implements CommandExecutor {
    public static ArrayList<Player> isVanish = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (cs instanceof Player player) {
            if(!player.hasPermission("system.vanish")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /vanish");
                main.playErrorSound(player);
                return true;
            }
            if(!player.isInvisible()) {
                player.sendMessage(main.prefix + "§aDu bist nun im Vanish.");
                player.setInvisible(true);
                main.playProccessSound(player);
            } else {
                player.setInvisible(false);
                main.playProccessSound(player);
                player.sendMessage(main.prefix + "§aDu bist nun §cnicht mehr §aim Vanish.");
            }
        }
        return false;
    }
}
