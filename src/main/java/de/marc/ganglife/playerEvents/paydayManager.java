package de.marc.ganglife.playerEvents;


import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import de.marc.ganglife.dataSetter.*;

import java.util.HashMap;
import java.util.Map;

public class paydayManager implements Listener {
    public static final Map<Player, Integer> paydayScheduler = new HashMap<>();

    setUnique setUnique = new setUnique(main.getPlugin().getDatabaseAsync().getDataSource());
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        setUnique.getUniqueID(player.getUniqueId()).thenAccept(id -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                if(id.isEmpty()) return;

                startPayDay(player);
            });
        });


    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        stopPayDay(player);
    }

    public static void startPayDay(Player player) {
        if (paydayScheduler.get(player) != null) {
            return;
        }
        if(paydayScheduler.get(player) == null) {
            player.sendMessage(main.prefix + "§aPayDay wurde gestartet.");
            main.playProccessSound(player);

            final int[] totalTime = {60};


            Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein PayDay wurde erfolgreich gestartet.");

            paydayScheduler.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
                if(!player.isOnline()) {
                    stopPayDay(player);
                }
                    totalTime[0]--;


                if (totalTime[0] >= 60) {
                        totalTime[0] = 60;

                       /* if (setActs.getHaft(player.getUniqueId()) >= 60) {
                            Bukkit.getScheduler().cancelTask(jailTimer.get(player));
                            jailTimer.remove(player);

                            player.sendMessage(main.prefix + "§aDu bist nun frei.");
                            main.playSuccessSound(player);
                            player.teleport(free);

                            setActs.setJail(player.getUniqueId(), "false");
                            setActs.setReasons(player.getUniqueId(), Arrays.asList("Leer"));
                            setActs.setHaft(player.getUniqueId(), 0);
                        } else {
                            setActs.removeHaft(player.getUniqueId(), 1);
                        }

                        */
                    }
            }, 0, 20));
        }
    }

    public static void stopPayDay(Player player) {
        if (paydayScheduler.get(player) != null) {
            Bukkit.getScheduler().cancelTask(paydayScheduler.get(player));
            paydayScheduler.remove(player);
        }
    }

}
