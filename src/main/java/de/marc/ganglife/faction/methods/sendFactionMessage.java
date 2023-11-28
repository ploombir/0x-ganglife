package de.marc.ganglife.faction.methods;

import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class sendFactionMessage {

    public static void sendFactionMessage(Player player, String message) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        for(Player all : Bukkit.getOnlinePlayers()) {
            UPlayer uAllPlayers = UPlayer.getUPlayer(all.getUniqueId());

            if(uAllPlayers.getFaction().equals(uPlayer.getFaction())) {
                all.sendMessage(" §f▹ " + factionPrefixes.getPrefix(player) + message);
            }
        }
    }
}
