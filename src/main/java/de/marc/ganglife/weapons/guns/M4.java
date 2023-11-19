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

public class M4 extends weapon {
    public static Map<Player, Integer> playerTimers = new HashMap<>();
    public M4(main plugin, ItemStack istack, double damage, long reloadTime, int ammo, long rpmTime) {
        super(plugin, istack, damage, reloadTime, ammo, rpmTime);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("deprecation")
    @Override
    public void shootEffects(Player p) {
        Arrow projectile = p.launchProjectile(Arrow.class);
        projectile.setVelocity(projectile.getVelocity().multiply(1.3));
        projectile.setDamage(0);
        projectile.setCritical(false);
        projectile.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        projectile.setSilent(false);
        projectile.setGravity(false);

        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_PIG_HURT ,(float)0.2, 1);
        p.updateInventory();

        playerTimers.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
            projectile.remove();
        }, 20*3));
    }
}
