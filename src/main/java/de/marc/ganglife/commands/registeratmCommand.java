package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.methods.isInteger;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class registeratmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(!player.hasPermission("system.admin")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "Verwendung: /createatmsign <Nummer>");
                main.playErrorSound(player);
                return true;
            }
            String nbtTag = args[0];

            if(!isInteger.isInt(args[0])) {
                player.sendMessage(main.pre_error + "Bitte gebe eine Zahl an.");
                main.playErrorSound(player);
                return true;
            }

            ItemStack signItem = new ItemStack(Material.OAK_SIGN);
            BlockStateMeta blockStateMeta = (BlockStateMeta) signItem.getItemMeta();
            Sign sign = (Sign) blockStateMeta.getBlockState();

            sign.getPersistentDataContainer().set(getKey("ATMTag"), PersistentDataType.STRING, nbtTag);

            blockStateMeta.setBlockState(sign);
            signItem.setItemMeta(blockStateMeta);

            player.getInventory().addItem(signItem);
            sign.update();

            player.sendMessage(main.prefix + "§7ATM mit Nummer: §6" + nbtTag + " §7erstellt");
            main.playSuccessSound(player);

        }
        return false;
    }
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(main.getPlugin(), key);
    }
}
