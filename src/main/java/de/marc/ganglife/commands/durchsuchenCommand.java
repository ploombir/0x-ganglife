package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.playerEvents.interactionmenu;
import de.marc.ganglife.playerdatas.UPlayer;
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

    private final Map<Player, Integer> playerScheduler = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            int BlockDistance = 3;
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /durchsuchen <Name>");
                main.playErrorSound(player);
                return true;
            }
            if(uPlayer.getDeathTime() >= 1) return true;

            if(uPlayer.isFFA()) {
                player.sendMessage(main.pre_error + "§cDu kannst diesen Befehl nicht in FFA verwenden.");
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

            player.sendMessage(main.prefix + "§7Du durchsucht nun §e" + target.getName() + "..");
            player.sendMessage(main.prefix + "§c§l! §cBewege dich nicht");
            target.sendMessage(main.prefix + "§e" + player.getName() + " §7fängt an dich zu durchsuchen..");
            target.sendMessage(main.prefix + "§c§l! §cBewege dich nicht");
            player.setWalkSpeed(0);
            main.playProccessSound(target);

            playerScheduler.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                if(target == null) {
                    player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht mehr Online.");
                    main.playErrorSound(player);
                    return;
                }

                Gui searchInventory = Gui.gui()
                        .rows(5)
                        .disableAllInteractions()
                        .title(Component.text(main.prefix + "§7Inventar von: " + target.getName()))
                        .create();

                searchInventory.open(player);
                searchInventory.getInventory().setContents(target.getInventory().getContents());

                player.sendMessage(main.prefix + "§7Du hast erfolgreich §e" + target.getName() + " §7durchsucht!");
                target.sendMessage(main.prefix + "§e" + player.getName() + " §7hat dich erfolgreich durchsucht!");
                player.setWalkSpeed((float) 0.2);
                main.playProccessSound(player);

                UPlayer uPlayerTarget = UPlayer.getUPlayer(target.getUniqueId());

                player.sendMessage(" ");
                player.sendMessage(main.prefix + "§aInventar von §e" + target.getName());
                player.sendMessage(" §f▹ §7Bargeld: §e" + uPlayerTarget.getCash());
                player.sendMessage(" §f▹ §7Pulver: §e" + uPlayerTarget.getCocaineAmount() + "g");
                player.sendMessage(" §f▹ §7Sportzigaretten: §e" + uPlayerTarget.getWeedAmount() + "g");
                player.sendMessage(" §f▹ §7Kristalle: §e" + uPlayerTarget.getMethAmount() + "g");
                player.sendMessage(" §f▹ §7Verbandskasten: §e" + uPlayerTarget.getMedicineAmount() + "g");
                player.sendMessage(" §f▹ §7Schutzwesten: §e" + uPlayerTarget.getBulletproofAmount() + "g");

            }, 3 * 20L));
        }

        return false;
    }
}
