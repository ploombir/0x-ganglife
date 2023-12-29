package de.marc.ganglife.farm;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.commands.adutyCommand;
import de.marc.ganglife.commands.blockfarmingCommand;
import de.marc.ganglife.dataSetter.items;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class farmAramid implements Listener {

    public static Map<Player, Integer> farmTimers = new HashMap<>();
    Material farmItem = Material.DEAD_BUSH;
    items farmTool = items.HOE;
    ItemStack farmResult = new ItemStack(items.ARAMID.getItem());

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Location farmLocation = new Location(player.getWorld(), -127, 68, 308);

        int BlockDistance = 50;

        Block block = event.getBlock();

        if(block.getType() != farmItem) return;
        if(player.getLocation().distance(farmLocation) >= BlockDistance) return;
        if(adutyCommand.aduty.contains(player)) return;


        if(blockfarmingCommand.allowFarming == false) {
            player.sendMessage(main.pre_error + "§cFarming ist zurzeit deaktiviert. Grund: Serverrestart.");
            main.playErrorSound(player);
            return;
        }

        if(player.getInventory().getItemInMainHand().getType() != farmTool.getItem().getType()) {
            player.sendMessage(main.prefix + "§cUm Aramid zu sammeln, benötigst du einen Sichel, diesen findest du im Baumarkt!");
            player.sendMessage(" §f▹ §7Um zum Baumarkt zu gelangen starte eine Navigation unter der Navi-App auf deinem Handy.");
            main.playErrorSound(player);
            return;
        }

        main.playProccessSound(player);

        if(player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            int randomValue = new Random().nextInt(100);

            if (randomValue < 70) {
                player.getInventory().addItem(farmResult);
                player.getInventory().addItem(farmResult);
            } else {
                player.getInventory().addItem(farmResult);
            }

        } else {
            player.getInventory().addItem(farmResult);
        }
        block.setType(Material.AIR);

        farmTimers.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
            block.setType(farmItem);
        }, 60 * 20L));
    }
}
