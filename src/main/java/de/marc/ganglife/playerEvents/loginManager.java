package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.setUnique;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class loginManager implements Listener {
    setUnique setUnique = new setUnique(main.getPlugin().getDatabaseAsync().getDataSource());
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        setUnique.getUniqueID(player.getUniqueId()).thenAccept(unique -> {
           if(unique.isPresent()) {
               player.sendMessage(main.prefix + "Willkommen zurück.");
               main.playSuccessSound(player);

                    if(uPlayer.isPremiumAccount()) {
                        if(!player.hasPermission("system.premium")) {
                            player.sendMessage(main.prefix + "§aHerzlichen Glückwunsch, dein Account hat nun Premium.");
                            main.playSuccessSound(player);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set premium");
                        } else {
                            player.sendMessage(main.prefix + "§6Premium ist aktiviert.");
                        }
                    } else {
                        if(player.hasPermission("system.premium")) {
                            if(!player.isOp()) {
                                player.sendMessage(main.prefix + "§cDein Premium Account ist abgelaufen.");
                                main.playErrorSound(player);
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set default");
                            }
                        }
                    }
           }
        });
    }
}
