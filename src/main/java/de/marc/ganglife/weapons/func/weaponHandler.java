package de.marc.ganglife.weapons.func;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.playerEvents.interactionmenu;
import de.marc.ganglife.weapons.guns.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class weaponHandler implements Listener {

    private ItemStack m4 = new ItemStack(items.M4.getItem());
    private ItemStack ak47 = new ItemStack(items.AK47.getItem());
    private ItemStack pdw = new ItemStack(items.PDW.getItem());
    private ItemStack uzi = new ItemStack(items.UZI.getItem());
    private ItemStack pistole = new ItemStack(items.PISTOL.getItem());
    private ItemStack jagdflinte = new ItemStack(items.RIFLE.getItem());
    private ItemStack sniper = new ItemStack(items.AWP.getItem());
    private ItemStack lasergun = new ItemStack(items.LASERGUN.getItem());
    public boolean enableGun;

    public ArrayList<weapon> weapons;

    public static ArrayList<String> ammo = new ArrayList<>();


    public weaponHandler(main plugin) {
        enableGun = true;
        weapons = new ArrayList<>();
        weapons.add(new M4(plugin, m4, 5,20*3, 30, 6));
        weapons.add(new AK47(plugin, ak47, 4, 20*3, 30, 6));
        weapons.add(new PDW(plugin, pdw, 6, 20*3, 30, 5));
        weapons.add(new UZI(plugin , uzi, 3, 20*3, 30, 5));
        weapons.add(new Pistole(plugin, pistole, 3, 20*2, 7, 13));
        weapons.add(new Jagdflinte(plugin, jagdflinte,12,20*5,2, 20*0));
        weapons.add(new Sniper(plugin, sniper,18,20*5,1, 20*0));
        weapons.add(new Lasergun(plugin, lasergun, 20,20*3,100,20*0));
    }



    @EventHandler
    public void handleGunShot(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        if (e.getItem() == null) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            weapon gun = checkGun(e.getItem());
            if (gun != null) {
                if (gun.getAmmo(p) > 1) {
                    if(enableGun == false) return;
                    if(interactionmenu.cuff.contains(p)) return;
                    //if(setDeads.isDead(p.getUniqueId(), "true")) return;
                    if(weapon.reloadGun.contains(p.getName())) return;

                    addNbtTagToItemStack(e.getItem(), "GunTagKey", "" + gun.getAmmo(p));

                    gun.shoot(p);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(main.prefix + "§7" + gun.getAmmo(p) + ""));
                } else {
                    if(enableGun == false) return;
                    if(interactionmenu.cuff.contains(p)) return;
                    //if(setDeads.isDead(p.getUniqueId(), "true")) return;
                    gun.shoot(p);
                    gun.reload(p);
                }
            }
        } else {
            weapon gun = checkGun(e.getItem());
            if (gun != null) {
                if(!gun.getMaterial().equals(Material.IRON_HOE)) return;
                scope(p);
            }
        }
    }
    private void addNbtTagToItemStack(ItemStack itemStack, String key, String value) {
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey nbtKey = getKey(key);
            dataContainer.set(nbtKey, PersistentDataType.STRING, value);
            itemStack.setItemMeta(itemMeta);
        }
    }

    private void scope(Player p) {
        if(!isScoping(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999,100));
        } else {
            p.removePotionEffect(PotionEffectType.SLOW);
        }
        p.getLocation().getWorld().playSound(p.getLocation(),Sound.BLOCK_DISPENSER_FAIL,1,1);
    }
    private boolean isScoping(Player p) {
        if(p.hasPotionEffect(PotionEffectType.SLOW)){
            if(p.getPotionEffect(PotionEffectType.SLOW).getAmplifier() == 100)
                return true;
        }
        return false;
    }

    @EventHandler
    public void handleGunSlotSwitch(PlayerItemHeldEvent e) {
        if(isScoping(e.getPlayer())) scope(e.getPlayer());
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()) == null) return;
        weapon gun = checkGun(e.getPlayer().getInventory().getItem(e.getNewSlot()));
        if(gun != null) {
            Player p = e.getPlayer();
            if(!gun.isReloading(p)) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(main.prefix + "§7"+gun.getAmmo(p)+""));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(main.prefix + "§7Waffe lädt nach.."));
            }
        }
    }

    @EventHandler
    public void cancelGunSwap(PlayerSwapHandItemsEvent e) {
        weapon gunMainHand = checkGun(e.getMainHandItem());
        weapon gunOffHand = checkGun(e.getOffHandItem());
        if(gunMainHand != null || gunOffHand != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelGunDrop(PlayerDropItemEvent e){
        weapon gun = checkGun(e.getItemDrop().getItemStack());
        if(gun != null) {
            e.setCancelled(true);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_FLINTANDSTEEL_USE,1,1);
            gun.reload(e.getPlayer());
        }
    }

    @EventHandler
    public void projectileBounce(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow) {
            Projectile projectile = e.getEntity();
            if(!(projectile.getShooter() instanceof Player)) return;
            if(e.getHitBlock() == null) return;
            Player p = (Player) projectile.getShooter();
            weapon gun = checkGun(p.getInventory().getItemInMainHand());
            if(gun != null) {
                // e.getEntity().getLocation().getWorld().spawnParticle(Particle.SNOWBALL, e.getEntity().getLocation(),10);
            }
        }
    }

    @EventHandler
    public void handleGunDamage(EntityDamageByEntityEvent e) {
        if(enableGun == false) e.setDamage(0);
        if(!(e.getDamager() instanceof Projectile)) return;
        Projectile projectile = (Projectile) e.getDamager();
        if(!(projectile.getShooter() instanceof Player)) return;
        Player p = (Player) projectile.getShooter();
        weapon gun = checkGun(p.getInventory().getItemInMainHand());
        if(gun != null) {
            if(e.getEntity().getName().equals((p.getName()))) return;
            e.setDamage(gun.getDamage());
        }
    }

    private weapon checkGun(ItemStack istack) {
        for(weapon current : weapons) {
            if(current.getMaterial() == istack.getType() && current.getIstack().getItemMeta().getDisplayName().equals(istack.getItemMeta().getDisplayName())) return current;
        }
        return null;
    }
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(main.getPlugin(), key);
    }
}
