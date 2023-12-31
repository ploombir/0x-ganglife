package de.marc.ganglife.methods;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class process {

    private static final Map<Player, Integer> proccessTimer = new HashMap<>();

    public static void startProcess(Player player, items itemRaw, Integer raw_amount, items itemFinish, Integer finish_amoumt, Location locNPC) {
        if (proccessTimer.containsKey(player)) {
            player.sendMessage(main.pre_error + "§cDu bist bereits am verarbeiten!");
            main.playErrorSound(player);
            return;
        }

        ItemStack item_raw = new ItemStack(ItemBuilder.from(itemRaw.getItem()).build());
        ItemStack item_finish = new ItemStack(ItemBuilder.from(itemFinish.getItem()).build());

        player.closeInventory();

        boolean found = Arrays.stream(player.getInventory().getContents())
                .filter(item -> item != null && item.getType() != Material.AIR)
                .filter(item -> item.getAmount() >= raw_amount)
                .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(item_raw.getItemMeta().getDisplayName()))
                .anyMatch(item -> item.getType() == item_raw.getType());

        if (!found) {
            player.sendMessage(main.pre_error + "§cDu hast nicht genügend " + item_raw.getItemMeta().getDisplayName() + "§c dabei. (Du benötigst " + raw_amount + " " + item_raw.getItemMeta().getDisplayName() + "§c)");
            main.playErrorSound(player);
            return;
        }

        final int[] totalTime = {60};
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (player.hasPermission("system.premium")) {
            totalTime[0] = 30;
        } else {
            totalTime[0] = 60;
        }

        player.sendMessage(main.prefix + "§7Du verarbeitest nun " + item_raw.getItemMeta().getDisplayName() + "§7.. (Dauer: " + totalTime[0] + " Sekunden)");
        main.playProccessSound(player);

        ItemStack is = ItemBuilder.from(item_raw).build();
        is.setAmount(raw_amount);
        player.getInventory().removeItem(is);

        proccessTimer.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), () -> {
            if (player.getLocation().distance(locNPC) >= 5) {
                player.sendMessage(main.pre_error + "§cDas Verarbeiten wurde abgebrochen, da du dich zu weit entfernt hast.");
                player.getInventory().addItem(item_raw);
                main.playErrorSound(player);

                Bukkit.getScheduler().cancelTask(proccessTimer.get(player));
                proccessTimer.remove(player);
                return;
            }
            if (!player.isOnline()) {
                Bukkit.getScheduler().cancelTask(proccessTimer.get(player));
                proccessTimer.remove(player);
                return;
            }

            totalTime[0]--;

            if (totalTime[0] <= 0) {
                Bukkit.getScheduler().cancelTask(proccessTimer.get(player));
                proccessTimer.remove(player);

                player.sendMessage(main.prefix + "§aDu hast nun " + raw_amount + " §7" + item_raw.getItemMeta().getDisplayName() + " §averarbeitet und erhältst " + finish_amoumt + " " + item_finish.getItemMeta().getDisplayName() + ".");
                main.playSuccessSound(player);
                item_finish.setAmount(finish_amoumt);
                player.getInventory().addItem(item_finish);

                player.sendMessage(main.prefix + "§a+ 25 EXP");

                uPlayer.setLevelExp(uPlayer.getLevelExp() + 25);
                systems.updateLevel(player, uPlayer);
                Bukkit.getConsoleSender().sendMessage(main.log + player.getName() + " hat " + item_raw.getItemMeta().getDisplayName() + " §9verarbeitet.");
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(main.prefix + "§7Noch §6" + totalTime[0] + " §7Sekunden zum verarbeiten.."));
            }
        }, 0, 20));
    }
}
