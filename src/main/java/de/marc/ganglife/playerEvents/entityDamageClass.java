package de.marc.ganglife.playerEvents;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.commands.adutyCommand;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

    public static Map<Player, Integer> playerTimers = new HashMap<>();
    public static final HashMap<String, UUID> headhash = new HashMap<>();
    public static Map<Player, Integer> deadTimer = new HashMap<>();
    public static Map<Player, Location> playerLocations = new HashMap<>();
    public static Map<Player, ArmorStand> armorHash = new HashMap<>();

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
                uPlayer.setDeathTime(4);
                startDeathTimer(player);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerTimers.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if (uPlayer.getDeathTime() <= 0) return;
            if (headhash.containsKey(player.getName())) return;

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

            playerLocations.put(player, player.getLocation());
            headhash.put(player.getName(), leiche.getUniqueId());

            startDeathTimer(player);
        }, 1*20L));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (headhash.containsKey(p.getName())) {
            if (Bukkit.getEntity(headhash.get(p.getName())) != null)
                Bukkit.getEntity(headhash.get(p.getName())).remove();
                headhash.remove(p.getName());
                deleteArmorStand(p);
                stopDeathTimer(p);
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
                player.sendMessage(main.prefix + "§7Du hast dich wiederbelebt.");
                stopDeathTimer(player);
                player.setGameMode(GameMode.SURVIVAL);
                uPlayer.setDeathTime(0);
                player.setFlySpeed((float) 0.2);
                main.playSuccessSound(player);

            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    player.sendMessage(main.notonline);
                    main.playErrorSound(player);
                    return true;
                }

                UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());

                if (uTarget.getDeathTime() <= 0) {
                    player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht tot.");
                    main.playErrorSound(player);
                    return true;
                }

                deleteArmorStand(target);
                player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7wiederbelebt.");
                target.sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7wiederbelebt.");
                stopDeathTimer(target);
                target.setGameMode(GameMode.SURVIVAL);
                uTarget.setDeathTime(0);
                target.setFlySpeed((float) 0.2);

            } else {
                player.sendMessage(main.pre_error + "§cVerwendung: /arevive <Spieler>");
                main.playErrorSound(player);
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

        player.sendMessage(main.prefix + "§7Du bist nun für 5 Minuten bewusstlos..");
        main.playErrorSound(player);

        final int[] totalTime = {60};

        Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein DeathTimer wurde §agestartet.");

        deadTimer.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
            if (!player.isOnline()) {
                stopDeathTimer(player);
                return;
            }
            totalTime[0]--;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.prefix + "§7Noch §6" + uPlayer.getDeathTime() + ":" + totalTime[0] + " §7Minuten bewusstlos.."));

            if (totalTime[0] <= 0) {
                uPlayer.setDeathTime(uPlayer.getDeathTime() - 1);

                totalTime[0] = 60;

                if (uPlayer.getDeathTime() <= 0) {
                    Location locationrespawn = new Location(player.getWorld(), 39, 71, -149);

                    player.sendMessage(main.prefix + "§aDu lebst nun wieder.");
                    deleteArmorStand(player);
                    stopDeathTimer(player);
                    player.teleport(locationrespawn);
                    player.setGameMode(GameMode.SURVIVAL);
                    uPlayer.setDeathTime(0);
                    player.setFlySpeed((float) 0.2);
                    main.playSuccessSound(player);

                    if(headhash.containsKey(player.getName())) {
                        if(Bukkit.getEntity(headhash.get(player.getName())) != null)
                            Bukkit.getEntity(headhash.get(player.getName())).remove();
                    }

                }
            }
        }, 0, 20));
    }

    public static void stopDeathTimer(Player player) {
        if (deadTimer.get(player) != null) {
            Bukkit.getScheduler().cancelTask(deadTimer.get(player));
            deadTimer.remove(player);
            Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " §9sein DeathTimer wurde §cgestoppt.");
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

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Entity entity = event.getHitEntity();

        if (projectile instanceof Arrow) {
            projectile.remove();
        }

        if (entity != null) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!adutyCommand.aduty.contains(player)) {
            if (!(entity instanceof ArmorStand armorStand)) return;
            if (armorStand.getCustomName() != null) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!adutyCommand.aduty.contains(damager)) {
            if (!(entity instanceof ArmorStand armorStand)) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (uPlayer.getDeathTime() > 0) {
            Location to = event.getTo();
            Location from = event.getFrom();
            if (to.getX() != from.getX() || to.getZ() != from.getZ()) {
                event.setTo(from);
            }
        }
    }

    @EventHandler
    public void spectate(PlayerStartSpectatingEntityEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (uPlayer.getDeathTime() > 0) {
            event.setCancelled(true);
        }
    }
}
