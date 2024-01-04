package de.marc.ganglife.housing;

import de.marc.ganglife.Main.main;
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

public class createhouseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /registerhouse <Hausnummer> <Preis>");
                main.playErrorSound(player);
                return true;
            }
            if(!player.hasPermission("system.createhouse")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }


            String nbtTag = args[0];
            String nbtPrice = args[1];

            ItemStack signItem = new ItemStack(Material.OAK_SIGN);
            BlockStateMeta blockStateMeta = (BlockStateMeta) signItem.getItemMeta();
            Sign sign = (Sign) blockStateMeta.getBlockState();

            sign.getPersistentDataContainer().set(getKey("CustomTag"), PersistentDataType.STRING, nbtTag);
            sign.getPersistentDataContainer().set(getKey("CustomPrice"), PersistentDataType.STRING, nbtPrice);
            sign.getPersistentDataContainer().set(getKey("CustomOwner"), PersistentDataType.STRING, "none");
            sign.getPersistentDataContainer().set(getKey("CustomUUID"), PersistentDataType.STRING, "none");

            String customOwner = sign.getPersistentDataContainer().get(getKey("CustomOwner"), PersistentDataType.STRING);
            String customUUID = sign.getPersistentDataContainer().get(getKey("CustomUUID"), PersistentDataType.STRING);

            sign.setLine(0, "-x-");
            sign.setLine(1, "Haus " + nbtTag);
            sign.setLine(2, "Preis: " + nbtPrice);
            sign.setLine(3, "-x-");

            blockStateMeta.setBlockState(sign);
            signItem.setItemMeta(blockStateMeta);

            player.getInventory().addItem(signItem);

            player.sendMessage(main.prefix + "§7Hausnummer: §6" + nbtTag);
            player.sendMessage(main.prefix + "§7Preis: §6" + nbtPrice);
            player.sendMessage(main.prefix + "§7Besitzer: §6" + customOwner);
            player.sendMessage(main.prefix + "§7UUID: §6" + customUUID);
            main.playSuccessSound(player);
        }

        return false;
    }
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(main.getPlugin(), key);
    }
}
