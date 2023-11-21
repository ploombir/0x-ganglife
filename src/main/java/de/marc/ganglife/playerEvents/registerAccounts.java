package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.methods.logs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.Random;

public class registerAccounts implements Listener {

    setActs setActs = new setActs(main.getPlugin().getDatabaseAsync().getDataSource());
    setBank setBank = new setBank(main.getPlugin().getDatabaseAsync().getDataSource());
    setDeads setDeads = new setDeads(main.getPlugin().getDatabaseAsync().getDataSource());
    setDiscordverify setDiscordverify = new setDiscordverify(main.getPlugin().getDatabaseAsync().getDataSource());
    setDrink setDrink = new setDrink(main.getPlugin().getDatabaseAsync().getDataSource());
    setDrugs setDrugs = new setDrugs(main.getPlugin().getDatabaseAsync().getDataSource());
    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    setFaction setFaction = new setFaction(main.getPlugin().getDatabaseAsync().getDataSource());
    setFFA setFFA = new setFFA(main.getPlugin().getDatabaseAsync().getDataSource());
    setGarbageLevel setGarbageLevel = new setGarbageLevel(main.getPlugin().getDatabaseAsync().getDataSource());
    setHouse setHouse = new setHouse(main.getPlugin().getDatabaseAsync().getDataSource());
    setLager setLager = new setLager(main.getPlugin().getDatabaseAsync().getDataSource());
    setLevel setLevel = new setLevel(main.getPlugin().getDatabaseAsync().getDataSource());
    setLicences setLicences = new setLicences(main.getPlugin().getDatabaseAsync().getDataSource());
    setMobile setMobile = new setMobile(main.getPlugin().getDatabaseAsync().getDataSource());
    setPayDays setPayDays = new setPayDays(main.getPlugin().getDatabaseAsync().getDataSource());
    setPersonalausweis setPersonalausweis = new setPersonalausweis(main.getPlugin().getDatabaseAsync().getDataSource());
    setPlayertime setPlayertime = new setPlayertime(main.getPlugin().getDatabaseAsync().getDataSource());
    setUnique setUnique = new setUnique(main.getPlugin().getDatabaseAsync().getDataSource());
    setVotes setVotes = new setVotes(main.getPlugin().getDatabaseAsync().getDataSource());


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        setUnique.getUniqueID(player.getUniqueId()).thenAccept(id -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                if(id.isEmpty()) {
                    setUnique.insertUniqueIdIntoDatabase(player.getUniqueId(), player.getName());
                    setLicences.setDriveFile(player.getUniqueId(), "false");
                    setLicences.setGunFile(player.getUniqueId(), "false");
                    setLicences.setFirstKitFile(player.getUniqueId(), "false");
                    setPersonalausweis.setPerso(player.getUniqueId(), "false");
                    setActs.setJail(player.getUniqueId(), "false");
                    setBank.setBank(player.getUniqueId(), 0);
                    setLevel.setLevelSystem(player.getUniqueId(), 1);
                    setEconomy.setMoney(player.getUniqueId(), 500);
                    setLevel.setExpSystem(player.getUniqueId(), 1);
                    setFaction.setFaction(player.getUniqueId(), "Zivilist");
                    setFaction.setRank(player.getUniqueId(), 1);
                    setHouse.setHouseSystem(player.getUniqueId(), 0);
                    setHouse.setHouseInventory(player.getUniqueId(), "[]");
                    setLager.setLagerInventory(player.getUniqueId(), "[]");
                    setLager.setHasLager(player.getUniqueId(), "false");
                    setActs.setHaft(player.getUniqueId(), 0);
                    setActs.setReasons(player.getUniqueId(), Arrays.asList("Leer"));
                    setActs.setJail(player.getUniqueId(), "false");
                    setGarbageLevel.setLevelSystem(player.getUniqueId(), 1);
                    setGarbageLevel.setExpSystem(player.getUniqueId(), 0);
                    setDrugs.setCocain(player.getUniqueId(), 0);
                    setDrugs.setWeed(player.getUniqueId(), 0);
                    setDrugs.setMeth(player.getUniqueId(), 0);
                    setDrugs.setMedizin(player.getUniqueId(), 0);
                    setDrugs.setSchutzweste(player.getUniqueId(), 0);
                    setDrugs.setPoliceSchutzweste(player.getUniqueId(), 0);
                    setPayDays.setPayDay(player.getUniqueId(), 0);
                    setDrink.setDrink(player.getUniqueId(), 10);
                    setDeads.setDead(player.getUniqueId(), "false");
                    setPlayertime.setPlayertime(player.getUniqueId(), 0);
                    setFFA.setFFA(player.getUniqueId(), "false");
                    setFFA.setFFAInventory(player.getUniqueId(), "[]");
                    setFFA.setFFAKills(player.getUniqueId(), 0);
                    setFFA.setFFADeaths(player.getUniqueId(), 0);
                    setFFA.setFFAInventory(player.getUniqueId(), "[]");
                    setDiscordverify.setDiscordVerify(player.getUniqueId(), generateRandomFourDigitNumber());
                    setMobile.addPlayers(1);
                    setVotes.setVotes(player.getUniqueId(), 0);
                    setMobile.setContacts(player.getUniqueId(), "[]");
                    setMobile.setFlugmodus(player.getUniqueId(), "false");
                    setMobile.getPlayers().thenAccept(registredPlayers -> {
                        Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                            if(registredPlayers.isPresent()) {
                                setMobile.setNumber(player.getUniqueId(), registredPlayers.get() + 5000);
                            }
                        });
                    });
                    Bukkit.getConsoleSender().sendMessage(main.log + "§aPlayerAccount für §6" + player.getName() + " §awurde erfolgreich angelegt. §7IP: " + player.getAddress().getHostString());
                    logs.sendTeamLog(player, "ist ein neuer Spieler.");
                    Location locnew = new Location(Bukkit.getWorld("0xMain"),-175, 71, -268);

                    player.teleport(locnew);
                    player.sendMessage(" ");
                    player.sendMessage(main.prefix + "§aHallo und Herzlich Willkommen auf §bOx-GangLife§a! §aDu befindest dich im moment am Flughafen, bitte begebe dich aus dem Flugzeug und folge den grünen Teppichen auf dem Boden. Spreche mit dem Einreiseamt und beantrage einen Ausweis.");
                    main.playSuccessSound(player);

                    Location loc = new Location(player.getWorld(), -156, 68, -239);
                    // cmd_navi.navigateTo(player, loc);

                } else {
                    Bukkit.getConsoleSender().sendMessage(main.log + "§6" + player.getName() + " §aist beigetreten. §7IP: " + player.getAddress().getHostString());
                    logs.sendTeamLog(player, "ist dem Server beigetreten.");

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
