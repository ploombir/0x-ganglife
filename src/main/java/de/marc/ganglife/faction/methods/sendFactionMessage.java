package de.marc.ganglife.faction.methods;

import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class sendFactionMessage {

    public static void sendPlayerFactionMessage(Player player, String message) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        for(Player all : Bukkit.getOnlinePlayers()) {
            UPlayer uAllPlayers = UPlayer.getUPlayer(all.getUniqueId());

            if(uAllPlayers.getFaction().equals(uPlayer.getFaction())) {
                all.sendMessage(factionPrefixes.getPrefix(player) + "FUNK §f▹ " + factionPrefixes.getPrefix(player) + message);
            }
        }
    }
    public static void sendStringFactionMessage(String Faction, String message) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            UPlayer uAllPlayers = UPlayer.getUPlayer(all.getUniqueId());
            if(uAllPlayers.getFaction().equals(Faction)) {
                all.sendMessage(factionPrefixes.getStringPrefix(Faction) + "FUNK §f▹ " + factionPrefixes.getStringPrefix(Faction) + message);
            }
        }
    }
}
