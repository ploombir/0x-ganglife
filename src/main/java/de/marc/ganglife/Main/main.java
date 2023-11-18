package de.marc.ganglife.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§afetter nega hat geladen");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
