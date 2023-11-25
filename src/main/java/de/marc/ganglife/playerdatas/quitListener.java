package de.marc.ganglife.playerdatas;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class quitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        UPlayer uPlayer = UPlayer.getUPlayer(event.getPlayer().getUniqueId());
        if (uPlayer != null) {
            uPlayer.saveData();
        }

        event.setQuitMessage(null);
    }
}
