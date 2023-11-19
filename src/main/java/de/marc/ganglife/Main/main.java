package de.marc.ganglife.Main;

import de.marc.ganglife.commands.gamemodeCommand;
import de.marc.ganglife.commands.giveitemCommand;
import de.marc.ganglife.commands.naviCommand;
import de.marc.ganglife.dataSetter.mySQLConnection;
import de.marc.ganglife.playerEvents.loginManager;
import de.marc.ganglife.playerEvents.registerAccounts;
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
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new registerAccounts(), this);
        getServer().getPluginManager().registerEvents(new loginManager(), this);
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
