package de.marc.ganglife.weapons.guns;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.weapons.func.weapon;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Pistole extends weapon {
    public static Map<Player, Integer> playerTimers = new HashMap<>();
    public Pistole(main plugin, ItemStack istack, double damage, long reloadTime, int ammo, long rpmTime) {
        super(plugin, istack, damage, reloadTime, ammo, rpmTime);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("deprecation")
    @Override
    public void shootEffects(Player player) {
        Arrow projectile = player.launchProjectile(Arrow.class);
        projectile.setVelocity(projectile.getVelocity().multiply(1.3));
        //  projectile.setBounce(false);
        projectile.setCritical(false);
        projectile.setGravity(false);
        projectile.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        projectile.setSilent(false);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_HURT,(float)0.2,(float) 0.9);
        player.updateInventory();

        playerTimers.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
            projectile.remove();
        }, 20*3));
    }
}