package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chat implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        event.getRecipients().clear();
        Player player = event.getPlayer();

    }
}
