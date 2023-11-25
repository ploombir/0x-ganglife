package de.marc.ganglife.playerEvents;


import de.marc.ganglife.Main.main;
import de.marc.ganglife.methods.systems;
import de.marc.ganglife.playerdatas.UPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import de.marc.ganglife.dataSetter.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class paydayManager implements Listener {
    public static final Map<Player, Integer> paydayScheduler = new HashMap<>();
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        stopPayDay(player);
    }

    public void startPayDay(Player player) {
        if(paydayScheduler.get(player) == null) {
            final int[] totalTime = {60};

            Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein PayDay wurde §agestartet.");

            paydayScheduler.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
                if(!player.isOnline()) {
                    stopPayDay(player);
                    return;
                }
                totalTime[0]--;

                if (totalTime[0] <= 0) { // Entspricht einer Minute
                        totalTime[0] = 60;
                        syncTime();

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        if(uPlayer == null) {
                            player.kick(Component.text("§cDu wurdest gekickt, bitte melde dich bei einem Admin. \n §cUPLAYER NULL"));
                            return;
                        }
                        uPlayer.setPaydayTime(uPlayer.getPaydayTime() + 1);

                        if(uPlayer.getPaydayTime() >= 60) {
                            player.sendMessage("§7=========== §9PayDay §7===========");
                            player.sendMessage(" §f▹ §7Alter Kontostand: §a" + uPlayer.getBank() + "$");
                            player.sendMessage(" §f▹ §7Zinsen: §a+" +  Math.round(uPlayer.getBank() * 0.02) + "$");
                            player.sendMessage(" §f▹ §7Steuern: §c-" + Math.round(uPlayer.getBank() * 0.015) + "$");

                            uPlayer.setBank((int) (uPlayer.getBank() + Math.round(uPlayer.getBank() * 0.02)));
                            uPlayer.setBank((int) (uPlayer.getBank() - Math.round(uPlayer.getBank() * 0.015)));

                            if (uPlayer.isPremiumAccount()) {
                                player.sendMessage(" §f▹ §7Premium Bonus: §a+200$ +50 EXP §7(§6Premium§7)");
                                uPlayer.setBank(uPlayer.getBank() + 200);
                                uPlayer.setLevelExp(uPlayer.getLevelExp() + 50);
                            }

                            if(uPlayer.isRentStorage()) {
                                player.sendMessage(" §f▹ §7Lagerkosten: §c-20$");
                                uPlayer.setBank(uPlayer.getBank() - 20);
                            }

                            player.sendMessage(" §f▹ §7PayDay Experience: §a+25 EXP");
                            uPlayer.setLevelExp(uPlayer.getLevelExp() + 25);

                            /*if(!setFaction.isInFaction(player.getUniqueId(), "Zivilist")) {
                                String getFaction = setFaction.getFaction(player.getUniqueId());
                                String getGehalt = cmd_showgehalt.getGehaltFromRank(getFaction, setFaction.getRank(player.getUniqueId()).toString());

                                if(setFBank.hasEnoughFBank(getFaction, Integer.parseInt(getGehalt))) {
                                    player.sendMessage(" §f▹ §7Fraktionsgehalt: §a+" + getGehalt + "$");
                                    setBank.addBank(player.getUniqueId(), Integer.parseInt(getGehalt));
                                    setFBank.removeFBank(getFaction, Integer.parseInt(getGehalt));
                                } else {
                                    player.sendMessage(" §f▹ §7Fraktionsgehalt: §cKonto Leer!");
                                }
                            }
                             */
                            uPlayer.setPaydayTime(0);
                            player.sendMessage(" §f▹ §7Neuer Kontostand: §a" + uPlayer.getBank() + "$");
                            systems.updateLevel(player, uPlayer);
                            main.playSuccessSound(player);
                        }
                    }
            }, 0, 20));
        }
    }

    public static void stopPayDay(Player player) {
        if (paydayScheduler.get(player) != null) {
            Bukkit.getScheduler().cancelTask(paydayScheduler.get(player));
            paydayScheduler.remove(player);
            Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein PayDay wurde §cgestoppt.");
        }
    }

    public static void syncTime() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        long time = (hours * 1000) + (minutes * 16) - 6000;

        World world = Bukkit.getWorld("0xMain");
        world.setTime(time);
    }

}
