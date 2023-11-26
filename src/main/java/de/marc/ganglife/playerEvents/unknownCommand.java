package de.marc.ganglife.playerEvents;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import java.net.http.WebSocket;

public class unknownCommand implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onUnknown(PlayerCommandPreprocessEvent e) {
        if(!(e.isCancelled())) {
            String msg = e.getMessage().split(" ") [0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(msg);

            if(topic == null) {
                e.setCancelled(true);
            }
        }
    }
}
