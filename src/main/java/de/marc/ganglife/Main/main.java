package de.marc.ganglife.Main;

import de.marc.ganglife.commands.*;
import de.marc.ganglife.dataSetter.mySQLConnection;
import de.marc.ganglife.faction.commands.fkickCommand;
import de.marc.ganglife.faction.commands.setgehaltCommand;
import de.marc.ganglife.faction.commands.showgehaltCommand;
import de.marc.ganglife.faction.commands.inviteCommand;
import de.marc.ganglife.phone.events.openPhone;
import de.marc.ganglife.playerEvents.*;
import de.marc.ganglife.playerdatas.playerManager;
import de.marc.ganglife.playerdatas.quitListener;
import de.marc.ganglife.utils.inventoryCancel;
import de.marc.ganglife.weapons.func.weaponHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    public static String log = "§9LOG §8▹ §a";
    public static String prefix = "§b0§3x §f▹ §7";
    public static String pre_error = "§c§lERROR §f▹ §c";
    public static String noperms = pre_error + "§cDazu hast du keine Rechte!";
    public static String notonline = prefix + "§cDieser Spieler ist nicht Online.";

    public static main plugin;
    mySQLConnection mySQL;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin laedt..");
        mySQL = new mySQLConnection();
        plugin = this;
        try {
            registerEvents();
            registerCommands();

            // bank robs
            //closeIronDoorsAfterReload();
            //setTresorBlocksBack();

        } catch(Exception e1) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§4§lDas Plugin konnte nicht gestartet werden! \n" + e1.toString() + "\n" + e1.getMessage());
        }
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDas Plugin wurde §3erfolgreich §ageladen!");

        if (mySQL.isConnected()) {
            Bukkit.getConsoleSender().sendMessage(main.log + "§aDatenbankverbindung erfolgreich hergestellt.");
        } else {
            Bukkit.getConsoleSender().sendMessage(main.log + "§cFehler beim Herstellen der Datenbankverbindung.");
        }
    }

    @Override
    public void onDisable() {
        if(mySQL.getConnection() != null) {
            mySQL.disconnect();
        }
    }

    private void registerCommands() {
        getCommand("giveitem").setExecutor(new giveitemCommand());
        getCommand("gamemode").setExecutor(new gamemodeCommand());
        getCommand("navigation").setExecutor(new naviCommand());
        getCommand("werbung").setExecutor(new werbungCommand());
        getCommand("aduty").setExecutor(new adutyCommand());
        getCommand("build").setExecutor(new buildCommand());
        getCommand("blockfarming").setExecutor(new blockfarmingCommand());
        getCommand("heal").setExecutor(new healCommand());
        getCommand("broadcast").setExecutor(new broadcastCommand());
        getCommand("clear").setExecutor(new clearCommand());
        getCommand("registeratm").setExecutor(new registeratmCommand());
        getCommand("dropgun").setExecutor(new dropgunCommand());
        getCommand("pay").setExecutor(new payCommand());
        getCommand("durchsuchen").setExecutor(new durchsuchenCommand());
        getCommand("ffastats").setExecutor(new ffastatsCommand());
        getCommand("profil").setExecutor(new profileCommand());
        getCommand("fraklager").setExecutor(new fraklagerCommand());
        getCommand("uncuff").setExecutor(new uncuffCommand());
        getCommand("teleport").setExecutor(new teleportCommand());
        getCommand("tphere").setExecutor(new tphereCommand());
        getCommand("vanish").setExecutor(new vanishCommand());
        getCommand("verify").setExecutor(new verifyCommand());
        getCommand("arevive").setExecutor(new entityDamageClass());
        getCommand("showgehalt").setExecutor(new showgehaltCommand());
        getCommand("setgehalt").setExecutor(new setgehaltCommand());
        getCommand("invite").setExecutor(new inviteCommand());
        getCommand("fkick").setExecutor(new fkickCommand());
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new registerAccounts(new playerManager(getDatabaseAsync().getDataSource())), this);

        getServer().getPluginManager().registerEvents(new quitListener(), this);
        getServer().getPluginManager().registerEvents(new interactionmenu(), this);
        getServer().getPluginManager().registerEvents(new chat(), this);
        getServer().getPluginManager().registerEvents(new weaponHandler(this), this);
        getServer().getPluginManager().registerEvents(new inventoryCancel(), this);
        getServer().getPluginManager().registerEvents(new paydayManager(), this);
        getServer().getPluginManager().registerEvents(new commandPreprocces(), this);
        getServer().getPluginManager().registerEvents(new unknownCommand(), this);
        getServer().getPluginManager().registerEvents(new buildListener(), this);
        getServer().getPluginManager().registerEvents(new cancelInteracts(), this);
        getServer().getPluginManager().registerEvents(new entityDamageClass(), this);
        getServer().getPluginManager().registerEvents(new openPhone(), this);
    }

    public mySQLConnection getDatabaseAsync() {
        return mySQL;
    }

    public static void playErrorSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
    }
    public static void playProccessSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
    }
    public static void playSuccessSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }
    public static main getPlugin() {
        return plugin;
    }
}
