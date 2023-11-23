package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.methods.logs;
import de.marc.ganglife.playerdatas.UPlayer;
import de.marc.ganglife.playerdatas.playerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.Random;

public class registerAccounts implements Listener {

    private final playerManager playerManager;
    public registerAccounts(playerManager playerManager) {
        this.playerManager = playerManager;
    }
    setUnique setUnique = new setUnique(main.getPlugin().getDatabaseAsync().getDataSource());

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        setUnique.getUniqueID(player.getUniqueId()).thenAccept(id -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                if(id.isEmpty()) {



                    Bukkit.getConsoleSender().sendMessage(main.log + "§aPlayerAccount für §6" + player.getName() + " §awurde erfolgreich angelegt. §7IP: " + player.getAddress().getHostString());
                    logs.sendTeamLog(player, "ist ein neuer Spieler.");
                    Location locnew = new Location(Bukkit.getWorld("0xMain"),-175, 71, -268);

                    player.teleport(locnew);
                    player.sendMessage(" ");
                    player.sendMessage(main.prefix + "§aHallo und Herzlich Willkommen auf §bOx-GangLife§a! §aDu befindest dich im moment am Flughafen, bitte begebe dich aus dem Flugzeug und folge den grünen Teppichen auf dem Boden. Spreche mit dem Einreiseamt und beantrage einen Ausweis.");
                    main.playSuccessSound(player);

                    Location loc = new Location(player.getWorld(), -156, 68, -239);
                    // cmd_navi.navigateTo(player, loc);

                    UPlayer uPlayer = UPlayer.getUPlayer(event.getPlayer().getUniqueId());
                    if (uPlayer == null) {
                        uPlayer = new UPlayer(event.getPlayer(), this.playerManager);
                    }
                    playerManager.createPlayer(player.getUniqueId());

                    uPlayer.loadData();

                } else {
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §aist beigetreten. §7IP: " + player.getAddress().getHostString());
                    logs.sendTeamLog(player, "ist dem Server beigetreten.");

                    UPlayer uPlayer = UPlayer.getUPlayer(event.getPlayer().getUniqueId());
                    if (uPlayer == null) {
                        uPlayer = new UPlayer(event.getPlayer(), this.playerManager);
                    }

                    uPlayer.loadData();
                }
            });
        });
    }
    public static int generateRandomFourDigitNumber() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }
}
