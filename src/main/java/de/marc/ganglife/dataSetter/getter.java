package de.marc.ganglife.dataSetter;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class getter {

    static int MONEY;

    public static CompletableFuture<Integer> getMoney(UUID uuid) {
        setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());

        return CompletableFuture.supplyAsync(() -> {
            try {
                return setEconomy.getMoney(uuid).get(); // Hier blockiert die .get()-Methode den Thread, um den Wert sofort zu erhalten
            } catch (Exception e) {
                e.printStackTrace();
                return 0; // Rückgabe eines Standardwerts oder einer Fehlerbehandlung
            }
        }, Bukkit.getScheduler().getMainThreadExecutor());
    }
}
