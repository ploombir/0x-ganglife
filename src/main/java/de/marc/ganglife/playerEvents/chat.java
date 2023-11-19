package de.marc.ganglife.playerEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chat implements Listener {

    @EventHandler
    public void ChatRadius(AsyncPlayerChatEvent event) {
        event.getRecipients().clear();
    }
}
