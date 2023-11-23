package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.playerdatas.UPlayer;
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
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /dropgun");
                main.playErrorSound(player);
            }
            items item = items.getItem(player.getInventory().getItemInMainHand());
            
            if(uPlayer.isFFA()) return true;
            if(uPlayer.getDeathTime() >= 0) return true;

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
                case PISTOL -> {
                    dropItem(player, items.PISTOL.getItem());
                }
                case UZI -> {
                    dropItem(player, items.UZI.getItem());
                }
                case RIFLE -> {
                    dropItem(player, items.RIFLE.getItem());
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
