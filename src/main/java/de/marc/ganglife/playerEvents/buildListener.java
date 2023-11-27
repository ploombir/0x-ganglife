package de.marc.ganglife.playerEvents;

import de.marc.ganglife.commands.buildCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.net.http.WebSocket;

public class buildListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(!buildCommand.allowed.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!buildCommand.allowed.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
