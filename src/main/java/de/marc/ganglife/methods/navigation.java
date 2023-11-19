package de.marc.ganglife.methods;

import de.marc.ganglife.Main.main;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class navigation {

    public static Map<Player, Integer> navigationTimers = new HashMap<>();
    public static void navigateTo(Player player, Location targetLocation) {
        if(navigationTimers.containsKey(player)) {
            Bukkit.getScheduler().cancelTask(navigationTimers.get(player));
            navigationTimers.remove(player);
        }
        navigationTimers.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
            Location playerLocation = player.getLocation();

            Vector direction = targetLocation.toVector().subtract(playerLocation.toVector()).normalize();

            double distance = playerLocation.distance(targetLocation);
            int numberOfParticles = Math.min((int) (distance * 10), 8);

            player.sendActionBar(Component.text("§f▹ §7Noch §6" + Math.round(player.getLocation().distance(targetLocation)) + " §7Blöcke entfernt."));

            for (int i = 0; i < numberOfParticles; i++) {
                double offsetX = direction.getX() / numberOfParticles;
                double offsetY = direction.getY() / numberOfParticles;
                double offsetZ = direction.getZ() / numberOfParticles;

                player.spawnParticle(Particle.REDSTONE, playerLocation.clone().add(offsetX, offsetY + 0.75, offsetZ), 1, 0, 0, 0, 1.0, new Particle.DustOptions(Color.RED, 1));
                playerLocation.add(direction);
            }

            for (int angle = 0; angle < 360; angle += 10) {
                double radians = Math.toRadians(angle);
                double x = targetLocation.getX() + Math.cos(radians) * 1.05;
                double y = targetLocation.getY();
                double z = targetLocation.getZ() + Math.sin(radians) * 1.05;

                player.spawnParticle(Particle.REDSTONE, new Location(targetLocation.getWorld(), x, y, z), 1, 0, 0, 0, 1.0, new Particle.DustOptions(Color.WHITE, 1));
            }

            if (player.getLocation().distance(targetLocation) <= 3) {
                player.sendActionBar("§f▹ §aDu hast dein Ziel erreicht!");
                Bukkit.getScheduler().cancelTask(navigationTimers.get(player));
                navigationTimers.remove(player);
            }
        }, 0, 20));
    }
}
