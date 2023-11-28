package de.marc.ganglife.faction.methods;

import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.entity.Player;

public class factionPrefixes {

    public static String getPrefix(Player player) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        String colorCode = "";

        switch (uPlayer.getFaction()) {
            case "Polizei" -> {
                colorCode = "§9";
            }
            case "Medics" -> {
                colorCode = "§c";
            }
            case "Ballas" -> {
                colorCode = "§d";
            }
            case "Mafia" -> {
                colorCode = "§7";
            }
        }
        return colorCode;
    }
}
