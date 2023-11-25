package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.methods.logs;
import de.marc.ganglife.methods.navigation;
import de.marc.ganglife.methods.systems;
import de.marc.ganglife.playerdatas.UPlayer;
import de.marc.ganglife.playerdatas.playerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static de.marc.ganglife.playerdatas.UPlayer.cachedUPlayers;

public class registerAccounts implements Listener {

    private final playerManager playerManager;

    public registerAccounts(playerManager playerManager) {
        this.playerManager = playerManager;
    }

    getLastID getLastID = new getLastID(main.getPlugin().getDatabaseAsync().getDataSource());
    setUnique setUnique = new setUnique(main.getPlugin().getDatabaseAsync().getDataSource());
    paydayManager paydayManager = new paydayManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(""));

        setUnique.getUniqueID(player.getUniqueId()).thenAccept(id -> {
            if (id.isEmpty()) {
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    Bukkit.getConsoleSender().sendMessage(main.log + "§aPlayerAccount für §6" + player.getName() + " §awurde erfolgreich angelegt. §7IP: " + player.getAddress().getHostString());
                    logs.sendTeamLog(player, "ist ein neuer Spieler.");
                    Location locnew = new Location(Bukkit.getWorld("0xMain"), -175, 71, -268);

                    player.teleport(locnew);
                    player.sendMessage(" ");
                    player.sendMessage(main.prefix + "§aHallo und Herzlich Willkommen auf §bOx-GangLife§a! §aDu befindest dich im moment am Flughafen, bitte begebe dich aus dem Flugzeug und folge den grünen Teppichen auf dem Boden. Spreche mit dem Einreiseamt und beantrage einen Ausweis.");
                    main.playSuccessSound(player);

                    Location loc = new Location(player.getWorld(), -156, 68, -239);
                    navigation.navigateTo(player, loc);

                    playerManager.createPlayer(player.getUniqueId());

                    getLastID.getLastId().thenAccept(lastId -> {
                        if (lastId.isPresent()) {
                            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                                getLastID.setPhoneNumber(player.getUniqueId(), lastId.get() + 5000);

                                UPlayer uPlayer = UPlayer.getUPlayer(event.getPlayer().getUniqueId());

                                if (uPlayer == null) {
                                    uPlayer = new UPlayer(event.getPlayer(), this.playerManager);
                                }
                                uPlayer.loadData();
                                paydayManager.startPayDay(player);
                                systems.updateLevel(player, uPlayer);
                            });
                        }
                    });
                });
            } else {
                UPlayer uPlayer = UPlayer.getUPlayer(event.getPlayer().getUniqueId());

                if (uPlayer == null) {
                    uPlayer = new UPlayer(event.getPlayer(), this.playerManager);
                }
                uPlayer.loadData();
                paydayManager.startPayDay(player);

                player.sendMessage(main.prefix + "§7Willkommen zurück.");
                //check premium
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    UPlayer uPlayer2 = UPlayer.getUPlayer(event.getPlayer().getUniqueId());
                    systems.updateLevel(player, uPlayer2);
                    if (uPlayer2.isPremiumAccount()) {
                        if (!player.hasPermission("system.premium")) {
                            player.sendMessage(main.prefix + "§aHerzlichen Glückwunsch, dein Account hat nun Premium.");
                            main.playSuccessSound(player);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set premium");
                        } else {
                            player.sendMessage(main.prefix + "§6Premium ist aktiviert.");
                        }
                    } else {
                        if (player.hasPermission("system.premium")) {
                            if (!player.isOp()) {
                                player.sendMessage(main.prefix + "§cDein Premium Account ist abgelaufen.");
                                main.playErrorSound(player);
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set default");
                            }
                        }
                    }
                });
            }
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
