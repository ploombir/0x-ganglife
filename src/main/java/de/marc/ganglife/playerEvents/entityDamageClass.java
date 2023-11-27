package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class entityDamageClass implements CommandExecutor, Listener {

    public static final HashMap<String, UUID> headhash = new HashMap<>();
    public static final Map<Player, Integer> deadTimer = new HashMap<>();
    public static final Map<Player, Location> playerLocations = new HashMap<>();
    public static final Map<Player, ArmorStand> armorHash = new HashMap<>();

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Player killer = player.getKiller();
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if (uPlayer.getDeathTime() > 0) return;
            if (!(event.getDamage() >= player.getHealth())) return;

            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());

            ItemStack head = ItemBuilder.from(Material.PLAYER_HEAD)
                    .setSkullOwner(player)
                    .build();

            Item leiche = player.getLocation().getWorld().dropItem(player.getLocation(), head);

            leiche.setPickupDelay(2000 * 1000);
            leiche.setCustomNameVisible(true);
            leiche.setCustomName("§7" + player.getName());

            player.setGameMode(GameMode.SPECTATOR);
            player.setFlySpeed(0);
            main.playErrorSound(player);
            generateArmorStand(player);

            if (killer != null) {
                main.playSuccessSound(killer);
            }

            playerLocations.put(player, player.getLocation());
            headhash.put(player.getName(), leiche.getUniqueId());

            if (uPlayer.isFFA()) {
                if (killer == null) {
                    player.sendMessage(main.prefix + "§7Du bist gestorben.");
                    main.playErrorSound(player);
                    uPlayer.setFfaDeaths(uPlayer.getFfaDeaths() + 1);
                    player.setHealth(player.getMaxHealth());
                }

                if (killer != null) {
                    UPlayer uKiller = UPlayer.getUPlayer(killer.getUniqueId());
                    player.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getKiller().getName() + " §7getötet.");
                    killer.sendMessage(main.prefix + "§7Du hast §6" + player.getName() + " §7getötet.");

                    uPlayer.setFfaDeaths(uPlayer.getFfaDeaths() + 1);
                    uKiller.setFfaKills(uKiller.getFfaKills() + 1);

                    main.playErrorSound(player);
                    main.playSuccessSound(killer);
                    player.setHealth(player.getMaxHealth());
                    ((LivingEntity) killer).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 5));
                }

                deadTimer.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                    player.setFlySpeed((float) 0.2);
                    if (headhash.containsKey(player.getName())) {
                        if (Bukkit.getEntity(headhash.get(player.getName())) != null)
                            Bukkit.getEntity(headhash.get(player.getName())).remove();
                        deleteArmorStand(player);
                    }
                    //ffaInventory.tpFFA(p);
                    player.setGameMode(GameMode.SURVIVAL);
                }, 5 * 20L));
            } else {
                startDeathTimer(player);
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("system.revive")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            if (args.length == 0) {
                UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                if (uPlayer.getDeathTime() <= 0) {
                    player.sendMessage(main.pre_error + "§cDu bist nicht tot.");
                    main.playErrorSound(player);
                    return true;
                }

                deleteArmorStand(player);
                player.sendMessage("revived");
                stopDeathTimer(player);
                player.setGameMode(GameMode.SURVIVAL);
            }

        }

        return false;
    }

    public static void startDeathTimer(Player player) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (uPlayer == null) {
            player.kick(Component.text("§cDu wurdest gekickt, bitte melde dich bei einem Admin. \n §cUPLAYER NULL - DEAD"));
            return;
        }
        uPlayer.setDeathTime(5);
        player.sendMessage(main.prefix + "§7Du bist nun für 5 Minuten bewusstlos..");
        main.playErrorSound(player);

        final int[] totalTime = {10};

        Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein DeahTimer wurde §agestartet.");

        deadTimer.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
            if (!player.isOnline()) {
                stopDeathTimer(player);
                return;
            }
            totalTime[0]--;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.prefix + "§7Noch §6" + uPlayer.getDeathTime() + ":" + totalTime[0] + " §7Minuten bewusstlos.."));

            if (totalTime[0] <= 0) {
                totalTime[0] = 10;

                uPlayer.setDeathTime(uPlayer.getDeathTime() - 1);

                if (uPlayer.getDeathTime() <= 1) {
                    player.sendMessage(main.prefix + "§aDu lebst nun wieder.");
                    stopDeathTimer(player);
                }
            }
        }, 0, 20));
    }

    public static void stopDeathTimer(Player player) {
        if (deadTimer.get(player) != null) {
            Bukkit.getScheduler().cancelTask(deadTimer.get(player));
            deadTimer.remove(player);
            Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein Todestimer wurde §cgestoppt.");
        }
        if (headhash.containsKey(player.getName())) {
            if (Bukkit.getEntity(headhash.get(player.getName())) != null)
                Bukkit.getEntity(headhash.get(player.getName())).remove();
        }
    }

    public void generateArmorStand(Player player) {
        Location location = player.getLocation();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (uPlayer.isFFA()) return;

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setCustomName(player.getName());
        armorStand.setCustomNameVisible(false);
        armorStand.setVisible(false);
        armorHash.put(player, armorStand);
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(armorStand);
    }

    public static void deleteArmorStand(Player player) {
        ArmorStand armorStand = armorHash.get(player);
        if (armorStand != null) {
            armorStand.remove();
            armorHash.remove(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.getDrops().clear();
    }
}
