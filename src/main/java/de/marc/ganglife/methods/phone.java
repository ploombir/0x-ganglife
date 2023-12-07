package de.marc.ganglife.methods;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.setMobile;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class phone {

    public static String findContactNameByNumber(Player player, String numberToFind) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
        String contactsJSON = uPlayer.getPhoneContacts();
        Map<String, Object>[] contacts = formatFromString(contactsJSON);

        if (contacts.length == 0) {
            return "Unbekannt";
        } else {
            for (Map<String, Object> contact : contacts) {
                String name = (String) contact.get("name");
                String number = (String) contact.get("number");

                if (number.equals(numberToFind)) {
                    return name;
                }
            }
            return "Unbekannt";
        }
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
