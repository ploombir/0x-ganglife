package de.marc.ganglife.weapons.func;

import de.marc.ganglife.Main.main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

;

public abstract class weapon {

    private main plugin;
    private ItemStack istack;
    private long rpmTime;
    private double damage;
    private long reloadTime;
    private int ammo;
    public static ArrayList<String> shotGun, reloadGun;
    private HashMap<String,Integer> playerAmmo;
    public static Map<Player, Integer> playerTimers = new HashMap<>();

    public weapon(main plugin, ItemStack istack, double damage, long reloadTime, int ammo, long rpmTime) {
        this.plugin = plugin;
        this.istack = istack;
        this.rpmTime = rpmTime;
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.ammo = ammo;

        shotGun = new ArrayList<>();
        reloadGun = new ArrayList<>();
        playerAmmo = new HashMap<>();
        for(Player current : Bukkit.getOnlinePlayers()) {
            playerAmmo.put(current.getName(), this.ammo);
        }
    }

    public void resetAmmo(Player p) {
        playerAmmo.put(p.getName(), this.ammo);

    }

    public void shoot(Player p) {
        if(!shotGun.contains(p.getName()) && !playerAmmo.containsKey(p.getName()) && !reloadGun.contains(p.getName())){
            reload(p);
        }
        if (!shotGun.contains(p.getName()) && playerAmmo.containsKey(p.getName()) && !(playerAmmo.get(p.getName()) <= 0)) {
            playerAmmo.put(p.getName(), playerAmmo.get(p.getName()) - 1);
            if(reloadGun.contains(p.getName())){
                reloadGun.remove(p.getName());
            }
            shotGun.add(p.getName());

            shootEffects(p);
            playerTimers.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    shotGun.remove(p.getName());
                }
            }, rpmTime));
        } else if(!shotGun.contains(p.getName()) && playerAmmo.containsKey(p.getName()) && playerAmmo.get(p.getName()) <= 0) {
            reload(p);
        }
    }
    public void reload(Player p ) {
        if (!reloadGun.contains(p.getName())) {

            reloadGun.add(p.getName());
            p.getLocation().getWorld().playSound(p.getLocation(),Sound.ITEM_FLINTANDSTEEL_USE,1,1);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(main.prefix + "§7Waffe lädt nach.."));
            playerTimers.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), new Runnable() {
                @Override
                @Deprecated
                public void run() {
                    if(!reloadGun.contains(p.getName())) return;
                    reloadGun.remove(p.getName());
                    setAmmo(p, ammo);
                    p.updateInventory();
                    p.playSound(p.getLocation(),Sound.BLOCK_DISPENSER_DISPENSE,1,1);
                    if(p.getInventory().getItemInMainHand().getType().equals(istack.getType())) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(main.prefix + "§7" + getAmmo(p) + ""));
                    }
                }
            },reloadTime));
        } else {
            p.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE,1,1);
        }
    }

    public ItemStack getIstack() {
        return istack;
    }

    public long getRpmTime() {
        return rpmTime;
    }

    public Material getMaterial() {
        return istack.getType();
    }

    public double getDamage() {
        return damage;
    }

    public void setAmmo(Player p, int ammo) {
        playerAmmo.put(p.getName(),ammo);
    }

    public int getAmmo(Player p) {
        if(playerAmmo.containsKey(p.getName())) {return playerAmmo.get(p.getName());}
        return 0;
    }

    public void setReloading(Player p, boolean reloading){
        if(reloading == true){
            reloadGun.add(p.getName());
        } else {
            reloadGun.remove(p.getName());
        }

    }

    public double getReloadTime() {
        return reloadTime;
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean isReloading(Player p){
        if(reloadGun.contains(p.getName())) return true;
        return false;
    }

    public abstract void shootEffects(Player p);

}
