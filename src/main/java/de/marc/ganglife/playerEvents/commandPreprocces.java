package de.marc.ganglife.playerEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.net.http.WebSocket;

public class commandPreprocces implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] parts = event.getMessage().split(" ");
        if (parts[0].toLowerCase().equalsIgnoreCase("/minecraft:me")) {
            event.setCancelled(true);
            event.setMessage("/");
        } else if (parts[0].toLowerCase().equalsIgnoreCase("/minecraft:tell")) {
            event.setCancelled(true);
            event.setMessage("/");
        } else if (parts[0].toLowerCase().equalsIgnoreCase("/tell")) {
            event.setCancelled(true);
            event.setMessage("/");
        } else if (parts[0].toLowerCase().equalsIgnoreCase("/r")) {
            event.setCancelled(true);
            event.setMessage("/");
        }  else if (parts[0].toLowerCase().equalsIgnoreCase("/minecraft:msg")) {
            event.setCancelled(true);
            event.setMessage("/");
        } else if (parts[0].toLowerCase().equalsIgnoreCase("/minecraft:w")) {
            event.setCancelled(true);
            event.setMessage("/");
        } else if (parts[0].toLowerCase().equalsIgnoreCase("/minecraft:whisper")) {
            event.setCancelled(true);
            event.setMessage("/");
        }
    }
}
