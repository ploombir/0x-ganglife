package de.marc.ganglife.housing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.commands.adutyCommand;
import de.marc.ganglife.dataSetter.setHousing;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class houseInfo implements Listener {

    Sign sign;
    String customTag;
    String customPrice;
    String customOwner;
    String customUUID;
    Integer sellPrice;

    setHousing setHousing = new setHousing(main.getPlugin().getDatabaseAsync().getDataSource());

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

                        if (!sign.getPersistentDataContainer().has(getKey("CustomTag"), PersistentDataType.STRING))
                            return;

                        customTag = sign.getPersistentDataContainer().get(getKey("CustomTag"), PersistentDataType.STRING);
                        customPrice = sign.getPersistentDataContainer().get(getKey("CustomPrice"), PersistentDataType.STRING);

                        if (customPrice != null) {
                            customOwner = sign.getPersistentDataContainer().get(getKey("CustomOwner"), PersistentDataType.STRING);
                            customUUID = sign.getPersistentDataContainer().get(getKey("CustomUUID"), PersistentDataType.STRING);
                            sellPrice = (int) ((Integer.parseInt(customPrice)) * 0.75);
                        }

                        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
                        String existingHouses = uPlayer.getHouseNumber();
                        List<Integer> customTags = new ArrayList<>();
                        List<Integer> existingNumbers = new Gson().fromJson(existingHouses, new TypeToken<List<Integer>>() {
                        }.getType());
                        customTags.addAll(existingNumbers);

                        if (!customTags.contains(Integer.parseInt(customTag))) return;
                        event.setCancelled(false);

                        if (!customUUID.equals(player.getUniqueId().toString())) return;
                        if (!player.isSneaking()) return;

                        Gui houseInfoInventory = Gui.gui()
                                .rows(3)
                                .title(Component.text(main.prefix + "§7Immobile: " + customTag))
                                .disableAllInteractions()
                                .create();

                        GuiItem houseInfo = ItemBuilder.from(Material.NAME_TAG)
                                .name(Component.text("§7Informationen"))
                                .lore(Component.text(" §f▹ §7§oKlicke um mehr Informationen zu erhalten."))
                                .asGuiItem(clickEvent -> {
                                    player.closeInventory();
                                    main.playProccessSound(player);
                                    player.sendMessage(main.prefix + "§aImmobilie " + customTag);
                                    player.sendMessage(" §f▹ §7Besitzer: §6" + customOwner);
                                    player.sendMessage(" §f▹ §7Immobilienpreis: §6" + customPrice + "$");
                                });

                        GuiItem houseInventory = ItemBuilder.from(Material.CHEST)
                                .name(Component.text("§aHauslager"))
                                .lore(Component.text(" §f▹ §7§oKlicke um Hauslager zu öffnen."))
                                .asGuiItem(clickEvent -> {

                                    Gui houseLagerInventory = Gui.gui()
                                            .rows(3)
                                            .title(Component.text(main.prefix + "§7Hauslager"))
                                            .create();

                                    setHousing.getHouseInventory(Integer.parseInt(customTag)).thenAccept(inventory -> {
                                        Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                                            player.closeInventory();

                                            Map<String, Object>[] houseInventoryFormat = formatFromString(inventory.get());

                                            houseLagerInventory.open(player);
                                            houseLagerInventory.getInventory().setContents(formatToInventory(houseInventoryFormat));
                                            main.playProccessSound(player);
                                        });
                                    });

                                    houseLagerInventory.setCloseGuiAction(close -> {
                                        Map[] formattedInventory = inventoryToFormat(houseLagerInventory.getInventory().getContents()).toArray(new Map[0]);
                                        setHousing.setHouseInventory(Integer.parseInt(customTag), Arrays.toString(formattedInventory));
                                        main.playProccessSound(player);
                                    });
                                });

                        GuiItem houseSell = ItemBuilder.from(Material.BARRIER)
                                .name(Component.text("§cHaus verkaufen"))
                                .lore(Component.text(" §f▹ §7§oKlicke um dein Haus zum Verkauf anzubieten."))
                                .asGuiItem(clickEvent -> {

                                    Gui houseSellInventory = Gui.gui()
                                            .rows(3)
                                            .title(Component.text(main.prefix + "§7Bestätigung"))
                                            .disableAllInteractions()
                                            .create();

                                    GuiItem info = ItemBuilder.from(Material.NAME_TAG)
                                            .name(Component.text("§7Bist du dir sicher?"))
                                            .lore(Component.text(" §f▹ §7§oMöchtest du wirklich deine Immobilie für §e" + sellPrice + "$ §7verkaufen?"))
                                            .asGuiItem(clickk -> {});

                                    GuiItem accept = ItemBuilder.from(Material.BLUE_DYE)
                                            .name(Component.text("§aJa"))
                                            .lore(Component.text(" §f▹ §7§oJa, ich möchte mein Haus verkaufen."))
                                            .asGuiItem(clickk -> {
                                                player.closeInventory();
                                                main.playSuccessSound(player);
                                                player.sendMessage(main.prefix + "§aDu hast Immoblie " + customTag +" für §e" + sellPrice + "$ §aerfolgreich verkauft.");
                                                uPlayer.setCash(uPlayer.getCash() + sellPrice);

                                                sign.getPersistentDataContainer().set(getKey("CustomTag"), PersistentDataType.STRING, customTag);
                                                sign.getPersistentDataContainer().set(getKey("CustimPrice"), PersistentDataType.STRING, customPrice);
                                                sign.getPersistentDataContainer().set(getKey("CustomOwner"), PersistentDataType.STRING, "none");
                                                sign.getPersistentDataContainer().set(getKey("CustomUUID"), PersistentDataType.STRING, "none");

                                                sign.setLine(0, "-x-");
                                                sign.setLine(1, "Haus " + customTag);
                                                sign.setLine(2, "Preis: " + customPrice);
                                                sign.setLine(3, "-x-");
                                                sign.update();

                                                if (customTags.contains(Integer.parseInt(customTag))) {
                                                    customTags.removeIf(number -> number.equals(Integer.parseInt(customTag)));
                                                    String updatedHouses = new Gson().toJson(customTags);
                                                    uPlayer.setHouseNumber(updatedHouses);
                                                    setHousing.deleteHouse(player.getUniqueId().toString(), Integer.parseInt(customTag));
                                                } else {
                                                    player.sendMessage(main.pre_error + "§cEs ist ein Fehler aufgetreten, bitte kontaktiere einen Administrator.");
                                                    main.playErrorSound(player);
                                                }

                                            });

                                    GuiItem deny = ItemBuilder.from(Material.BARRIER)
                                            .name(Component.text("§cNein"))
                                            .lore(Component.text(" §f▹ §7§oNein, Hausverkauf abbrechen."))
                                            .asGuiItem(clickk -> {
                                                player.closeInventory();
                                                main.playErrorSound(player);
                                            });

                                    player.closeInventory();
                                    houseSellInventory.setItem(11, accept);
                                    houseSellInventory.setItem(13, info);
                                    houseSellInventory.setItem(15, deny);
                                    houseSellInventory.open(player);
                                    main.playProccessSound(player);
                                });

                        houseInfoInventory.setItem(11, houseInfo);
                        houseInfoInventory.setItem(13, houseInventory);
                        houseInfoInventory.setItem(15, houseSell);
                        houseInfoInventory.open(player);
                        main.playProccessSound(player);
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

    public static List<Map<String, Object>> inventoryToFormat(ItemStack[] inventory) {
        List<Map<String, Object>> formatList = new ArrayList<>();

        for (int slot = 0; slot < inventory.length; slot++) {
            ItemStack itemStack = inventory[slot];
            if (itemStack != null) {
                Map<String, Object> itemInfo = new HashMap<>();
                itemInfo.put("item", itemStack.getType().toString());
                itemInfo.put("amount", itemStack.getAmount());
                itemInfo.put("slot", slot);
                itemInfo.put("unbreakable", itemStack.getItemMeta().isUnbreakable());
                itemInfo.put("durability", itemStack.getDurability());

                String displayName = itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()
                        ? itemStack.getItemMeta().getDisplayName()
                        : itemStack.getType().name();

                itemInfo.put("displayName", displayName);

                formatList.add(itemInfo);
            }
        }

        return formatList;
    }

    public static ItemStack[] formatToInventory(Map<String, Object>[] formatArray) {
        ItemStack[] inventory = new ItemStack[27];

        for (int slot = 0; slot < formatArray.length && slot < inventory.length; slot++) {
            Map<String, Object> itemInfo = formatArray[slot];
            if (itemInfo != null) {
                String itemName = (String) itemInfo.get("item");
                int amount = 0;
                int slotIndex = 0;
                String displayName = null;
                boolean unbreakable = false;
                short durability = 0;

                try {
                    amount = ((Number) itemInfo.get("amount")).intValue();
                    slotIndex = ((Number) itemInfo.get("slot")).intValue();
                    displayName = (String) itemInfo.get("displayName");
                    unbreakable = (boolean) itemInfo.get("unbreakable");
                    durability = ((Number) itemInfo.get("durability")).shortValue();
                } catch (ClassCastException | NullPointerException | NumberFormatException e) {
                    displayName = null;
                }

                if (slotIndex >= 0 && slotIndex < inventory.length) {
                    ItemStack itemStack = new ItemStack(Material.matchMaterial(itemName), amount);
                    itemStack.setDurability(durability);
                    if (unbreakable) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setUnbreakable(true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    if (displayName != null) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(displayName);
                        itemStack.setItemMeta(itemMeta);
                    }

                    inventory[slotIndex] = itemStack;
                }
            }
        }

        return inventory;
    }
}
