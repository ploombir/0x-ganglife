package de.marc.ganglife.commands;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setFrakLager;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class fraklagerCommand implements CommandExecutor {
    static boolean isInPoliceLager = false;
    static boolean isInGangLager = false;
    static boolean isInMafiaLager = false;
    static boolean isInMedicLager = false;

    setFrakLager setFrakLager = new setFrakLager(main.getPlugin().getDatabaseAsync().getDataSource());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Location police_hq = new Location(player.getWorld(), 94, 67, -11);
            Location medic_hq = new Location(player.getWorld(), 9, 71, -162);
            Location mafia_hq = new Location(player.getWorld(), -303, 70, 139);
            Location gang_hq = new Location(player.getWorld(), 190, 66, -284);

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
            if (args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /fraklager");
                main.playErrorSound(player);
                return true;
            }
            if (uPlayer.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }
            if (uPlayer.getFactionRank() <= 4) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            switch (uPlayer.getFaction()) {
                case "Polizei" -> {
                    if (isInPoliceLager) {
                        player.sendMessage(main.prefix + "§cEs ist bereits jemand im Lager.");
                        main.playErrorSound(player);
                        return true;
                    }

                    if (player.getLocation().distance(police_hq) >= 3) {
                        player.sendMessage(main.prefix + "§cDu befindest dich nicht am Fraklager.");
                        main.playErrorSound(player);
                        return true;
                    }

                    isInPoliceLager = true;

                    Gui policeInventory = Gui.gui()
                            .rows(4)
                            .title(Component.text(main.prefix + "§7Fraklager"))
                            .create();

                    setFrakLager.getFrakLager(1).thenAccept(fraklager -> {
                        Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                            String inventoryString = fraklager.get();
                            Map<String, Object>[] inventoryFormat = formatFromString(inventoryString);
                            main.playProccessSound(player);
                            policeInventory.open(player);
                            policeInventory.getInventory().setContents(formatToInventory(inventoryFormat));
                        });
                    });

                    policeInventory.setCloseGuiAction(close -> {
                        isInPoliceLager = false;
                        Inventory closedInventory = close.getInventory();

                        Map[] formattedInventory = inventoryToFormat(closedInventory.getContents()).toArray(new Map[0]);
                        setFrakLager.setFrakLagerFrak(Arrays.toString(formattedInventory), 1);
                    });
                }
                case "test" -> {

                }
                default -> {
                    player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                    main.playErrorSound(player);
                }
            }

        }

        return false;
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
        ItemStack[] inventory = new ItemStack[36];

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
