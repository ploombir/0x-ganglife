package de.marc.ganglife.phone.commands;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class addcontactCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Kontakte hinzuzufügen.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /addcontact <Name> <Nummer>");
                main.playErrorSound(player);
                return true;
            }

            String name = args[0];
            String number = args[1];

            if(!isInteger.isInt(number)) {
                player.sendMessage(main.pre_error + "§cBitte gebe eine Nummer an.");
                main.playErrorSound(player);
                return true;
            }

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            String existingContacts = uPlayer.getPhoneContacts();

            Map<String, Object>[] formattedContacts = formatFromString(existingContacts);

            Map<String, Object> contact = new HashMap<>();
            contact.put("name", name);
            contact.put("number", number);

            formattedContacts = Arrays.copyOf(formattedContacts, formattedContacts.length + 1);
            formattedContacts[formattedContacts.length - 1] = contact;

            String updatedContacts = new Gson().toJson(formattedContacts);

            uPlayer.setPhoneContacts(updatedContacts);

            player.sendMessage(main.prefix + "§aNeuen Kontakt hinzugefügt.");
            main.playProccessSound(player);

        }

        return false;
    }

    public static Map<String, Object>[] formatFromString(String input) {
        try {
            Gson gson = new Gson();
            Map<String, Object>[] formattedArray = gson.fromJson(input, Map[].class);

            if (formattedArray == null) {
                return new HashMap[0];
            }

            return formattedArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap[0];
        }
    }
}
