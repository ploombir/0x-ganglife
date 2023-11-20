package de.marc.ganglife.methods;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class logs {

    public static void sendTeamLog(Player player, String message) {
        for(Player a : Bukkit.getOnlinePlayers()) {
            if(a.hasPermission("system.team")) {
                a.sendMessage("§cTEAM-LOG §f▹ §6" + player.getName() + " §7" + message);
                main.playProccessSound(a);
            }
        }
    }
}
