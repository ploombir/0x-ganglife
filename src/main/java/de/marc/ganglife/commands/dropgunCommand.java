package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class dropgunCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /dropgun");
                main.playErrorSound(player);
            }
            items item = items.getItem(player.getInventory().getItemInMainHand());

            if(item == null) {
                player.sendMessage(main.pre_error + "§cDu kannst dieses Item nicht droppen.");
                main.playErrorSound(player);
                return true;
            }

            switch(item) {
                case M4 -> {
                    dropItem(player, items.M4.getItem());
                }
                case AK47 -> {
                    dropItem(player, items.AK47.getItem());
                }
                case PISTOLE -> {
                    dropItem(player, items.PISTOLE.getItem());
                }
                case SMG -> {
                    dropItem(player, items.SMG.getItem());
                }
                case JAGDFLINTE -> {
                    dropItem(player, items.JAGDFLINTE.getItem());
                }
                default -> {
                    player.sendMessage(main.pre_error + "§cDu kannst dieses Item nicht droppen.");
                    main.playErrorSound(player);
                }
            }
        }
        return false;
    }
    public static void dropItem(Player player, ItemStack gun) {
        Item droppedItem = player.getLocation().getWorld().dropItem(player.getLocation(), gun);

        droppedItem.setPickupDelay(10*10);
        droppedItem.setCustomNameVisible(true);
        droppedItem.setCustomName(gun.getItemMeta().getDisplayName());

        player.setItemInHand(null);
        player.sendMessage(main.prefix + "§7Du hast deine " + gun.getItemMeta().getDisplayName() + " §7gedroppt.");
        main.playProccessSound(player);
    }
}
