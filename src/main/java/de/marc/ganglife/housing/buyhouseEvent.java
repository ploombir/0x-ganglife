package de.marc.ganglife.housing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.commands.adutyCommand;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setHousing;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.awt.event.ComponentEvent;
import java.util.*;

public class buyhouseEvent implements Listener {

    static String customTag;
    static String customPrice;
    static String customOwner;

    static String customUUID;

    static Sign sign;

    setHousing housing = new setHousing(main.getPlugin().getDatabaseAsync().getDataSource());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;
        if (!clickedBlock.getType().toString().contains("DOOR")) return;
        if (adutyCommand.aduty.contains(player)) return;

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Block relativeBlock = clickedBlock.getRelative(x, y, z);

                    if (relativeBlock.getState() instanceof Sign) {
                        sign = (Sign) relativeBlock.getState();

                        if (!player.getInventory().getItemInMainHand().isSimilar(items.HOUSE_CONTRACT.getItem()))
                            return;

                        customTag = sign.getPersistentDataContainer().get(getKey("CustomTag"), PersistentDataType.STRING);
                        customPrice = sign.getPersistentDataContainer().get(getKey("CustomPrice"), PersistentDataType.STRING);
                        customOwner = sign.getPersistentDataContainer().get(getKey("CustomOwner"), PersistentDataType.STRING);
                        customUUID = sign.getPersistentDataContainer().get(getKey("CustomUUID"), PersistentDataType.STRING);

                        if (!customUUID.equals("none")) {
                            player.sendMessage(main.pre_error + "§cDiese Immobilie ist nicht mehr verfügbar.");
                            main.playErrorSound(player);
                            return;
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

                        Gui buyHouseInventory = Gui.gui()
                                .rows(3)
                                .title(Component.text(main.prefix + "§7Immobile: " + customTag))
                                .disableAllInteractions()
                                .create();

                        GuiItem buyHouse = ItemBuilder.from(Material.BLUE_DYE)
                                .name(Component.text("§7Immobilie " + customTag + " kaufen.."))
                                .lore(Component.text(" §f▹ §eImmobilienpreis: " + customPrice + "$"))
                                .asGuiItem(clickEvent -> {
                                    if (uPlayer.getCash() < Integer.parseInt(customPrice)) {
                                        player.sendMessage(main.pre_error + "§cDu hast nicht genügend Geld.");
                                        player.sendMessage(" §f▹ §7§oDie Kosten für diese Immobilie beträgt: §e" + customPrice + "$");
                                        main.playErrorSound(player);
                                        return;
                                    }
                                    String locationXYZ = clickedBlock.getX() + ", " + clickedBlock.getY() + ", " + clickedBlock.getZ();

                                    housing.buyHouse(player.getUniqueId().toString(), Integer.parseInt(customTag), "[]", locationXYZ);

                                    String existingHouses = uPlayer.getHouseNumber();
                                    List<Integer> customTags = new ArrayList<>();

                                    if (existingHouses != null && !existingHouses.isEmpty()) {
                                        List<Integer> existingNumbers = new Gson().fromJson(existingHouses, new TypeToken<List<Integer>>() {}.getType());
                                        customTags.addAll(existingNumbers);

                                        if(player.hasPermission("system.premium")) {
                                            if(customTags.size() >= 5) {
                                                player.sendMessage(main.pre_error + "§cDu besitzt bereits 5 Immobilien.");
                                                main.playErrorSound(player);
                                                return;
                                            }
                                        } else {
                                            if(customTags.size() >= 1) {
                                                player.sendMessage(main.pre_error + "§cDu besitzt bereits eine Immobilie.");
                                                main.playErrorSound(player);
                                                return;
                                            }
                                        }

                                    }

                                    customTags.add(Integer.parseInt(customTag));

                                    String updatedHouses = new Gson().toJson(customTags);
                                    uPlayer.setHouseNumber(updatedHouses);

                                    uPlayer.setCash(uPlayer.getCash() - Integer.parseInt(customPrice));
                                    player.sendMessage(main.prefix + "§aDu hast erfolgreich Immobilie " + customTag + " erworben.");
                                    main.playSuccessSound(player);

                                    sign.getPersistentDataContainer().set(getKey("CustomOwner"), PersistentDataType.STRING, player.getName());
                                    sign.getPersistentDataContainer().set(getKey("CustomUUID"), PersistentDataType.STRING, player.getUniqueId().toString());
                                    player.getInventory().removeItem(items.HOUSE_CONTRACT.getItem());

                                    sign.setLine(0, "-x-");
                                    sign.setLine(1, "Haus " + customTag);
                                    sign.setLine(2, player.getName());
                                    sign.setLine(3, "-x-");
                                    sign.update();

                                    player.closeInventory();
                                });

                        buyHouseInventory.setItem(13, buyHouse);
                        main.playProccessSound(player);
                        buyHouseInventory.open(player);
                    }
                }
            }
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

    private NamespacedKey getKey(String key) {
        return new NamespacedKey(main.getPlugin(), key);
    }

}
