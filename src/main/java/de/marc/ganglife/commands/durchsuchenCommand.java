package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.playerEvents.interactionmenu;
import de.marc.ganglife.utils.inventoryCancel;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class durchsuchenCommand implements CommandExecutor {

    setFFA setFFA = new setFFA(main.getPlugin().getDatabaseAsync().getDataSource());
    setDrugs setDrugs = new setDrugs(main.getPlugin().getDatabaseAsync().getDataSource());
    setEconomy setEconomy = new setEconomy(main.getPlugin().getDatabaseAsync().getDataSource());
    private final Map<Player, Integer> playerScheduler = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            int BlockDistance = 3;
            if(setFFA.isInFFA(player.getUniqueId(), "true")) return true;
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /durchsuchen <Name>");
                main.playErrorSound(player);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(main.notonline);
                main.playErrorSound(player);
                return true;
            }
            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber durchsuchen.");
                main.playErrorSound(player);
                return true;
            }
            if(interactionmenu.cuff.contains(player)) {
                player.sendMessage(main.pre_error + "§cDu kannst nicht gefesselt Personen durchsuchen.");
                main.playErrorSound(player);
                return true;
            }
            if(!interactionmenu.cuff.contains(target)) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht gefesselt.");
                main.playErrorSound(player);
                return true;
            }
            if(player.getLocation().distance(target.getLocation()) >= BlockDistance) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner nähe.");
                main.playErrorSound(player);
                return true;
            }

            player.sendMessage(main.prefix + "§7Du durchsucht nun §6" + target.getName() + "..");
            player.sendMessage(main.prefix + "§c§l! §cBewege dich nicht");
            target.sendMessage(main.prefix + "§6" + player.getName() + " §7fängt an dich zu durchsuchen..");
            target.sendMessage(main.prefix + "§c§l! §cBewege dich nicht");
            player.setWalkSpeed(0);
            main.playProccessSound(target);

            playerScheduler.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                Player open_inv = Bukkit.getPlayerExact(args[0]);

                if(open_inv == null) {
                    player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht mehr Online.");
                    main.playErrorSound(player);
                    return;
                }
                inventoryCancel.inventoryFreeze.add(player); // türken fix
                player.openInventory(open_inv.getInventory());
                player.sendMessage(main.prefix + "§7Du hast erfolgreich §6" + target.getName() + " §7durchsucht!");
                target.sendMessage(main.prefix + "§6" + player.getName() + " §7hat dich erfolgreich durchsucht!");
                player.setWalkSpeed((float) 0.2);
                main.playProccessSound(player);

                player.sendMessage(" ");
                player.sendMessage(main.prefix + "§aInventar von §6" + target.getName());
                setDrugs.getCocain(target.getUniqueId()).thenAccept(amount -> {
                    player.sendMessage(" §f▹ §7Pulver: §6" + amount.get() + "g");
                    setDrugs.getWeed(target.getUniqueId()).thenAccept(amountw -> {
                        player.sendMessage(" §f▹ §7Sportzigaretten: §6" + amountw.get() + "g");
                        setDrugs.getMeth(target.getUniqueId()).thenAccept(amountm -> {
                            player.sendMessage(" §f▹ §7Kristalle: §6" + amountm.get() + "g");
                            setDrugs.getMedizin(target.getUniqueId()).thenAccept(amountmed -> {
                                player.sendMessage(" §f▹ §7Verbandskasten: §6" + amountmed.get() + "g");
                                    setDrugs.getSchutzweste(target.getUniqueId()).thenAccept(s -> {
                                    player.sendMessage(" §f▹ §7Schutzwesten: §6" + s.get() + "g");

                                    player.sendMessage(" ");

                                    setEconomy.getMoney(target.getUniqueId()).thenAccept(money -> {
                                        player.sendMessage(" §f▹ §7Bargeld: §6" + money.get());
                                    });
                                });
                            });
                        });
                    });
                });
            }, 3 * 20L));
        }

        return false;
    }
}
