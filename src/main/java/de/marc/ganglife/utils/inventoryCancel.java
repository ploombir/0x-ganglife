package de.marc.ganglife.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;

public class inventoryCancel implements Listener {

    public static ArrayList<Player> inventoryFreeze = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (inventoryFreeze.contains(player)) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onInv(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (inventoryFreeze.contains(player)) {
            inventoryFreeze.remove(player);
        }
    }
}
