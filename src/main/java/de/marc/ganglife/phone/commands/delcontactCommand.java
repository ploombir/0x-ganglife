package de.marc.ganglife.phone.commands;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class delcontactCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if (args.length == 2) {
                String nameToRemove = args[0];
                String numberToRemove = args[1];
                UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                String existingContacts = uPlayer.getPhoneContacts();

                List<Map<String, Object>> formattedContacts = formatFromString(existingContacts);

                boolean removed = false;
                for (int i = 0; i < formattedContacts.size(); i++) {
                    Map<String, Object> contact = formattedContacts.get(i);
                    String name = contact.get("name").toString();
                    String number = contact.get("number").toString();
                    if (name.equalsIgnoreCase(nameToRemove) && number.equalsIgnoreCase(numberToRemove)) {
                        formattedContacts.remove(i);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    String updatedContacts = new Gson().toJson(formattedContacts);
                    uPlayer.setPhoneContacts(updatedContacts);
                    player.sendMessage(main.prefix + "§aDu hast einen Kontakt entfernt.");
                    main.playSuccessSound(player);
                } else {
                    player.sendMessage(main.pre_error + "§cDieser Kontakt wurde nicht gefunden.");
                    main.playErrorSound(player);
                }
            } else {
                player.sendMessage(main.pre_error + "§cBitte benutze /delcontact <Name> <Nummer>");
                main.playErrorSound(player);
            }
        }

        return false;
    }
    public static List<Map<String, Object>> formatFromString(String input) {
        try {
            Gson gson = new Gson();
            List<Map<String, Object>> formattedList = gson.fromJson(input, List.class);

            if (formattedList == null) {
                return new ArrayList<>();
            }

            return formattedList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
