package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.methods.navigation;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static de.marc.ganglife.methods.isInteger.isInt;
import static de.marc.ganglife.methods.navigation.navigateTo;
import static de.marc.ganglife.methods.navigation.navigationTimers;

public class naviCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cs instanceof Player player) {

            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um die Navigation zu öffnen.");
                main.playErrorSound(player);
                return true;
            }

            if (args.length == 0) {
                GuiItem navi_oeffentliche_orte = ItemBuilder.from(Material.SANDSTONE).name(Component.text("§aÖffentliche Orte"))
                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                        .asGuiItem(event -> {
                            main.playProccessSound(player);
                            Gui orteInventory = Gui.gui()
                                    .rows(3)
                                    .title(Component.text(main.prefix + "§7Öffentliche Orte:"))
                                    .disableAllInteractions()
                                    .create();

                            GuiItem stadthalle = ItemBuilder.from(Material.SANDSTONE).name(Component.text("§aStadthalle"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 6, 66, -34));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zur §fStadthalle §7gestartet.");
                                        main.playProccessSound(player);
                                    });
                            GuiItem flughafen = ItemBuilder.from(Material.QUARTZ_BLOCK).name(Component.text("§aFlughafen"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), -89, 68, -234));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zum §fFlughafen §7gestartet.");
                                        main.playProccessSound(player);
                                    });
                            GuiItem lagerhalle = ItemBuilder.from(Material.CHEST).name(Component.text("§aLagerhalle"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), -204, 69, -67));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zur §fLagerhalle §7gestartet.");
                                        main.playProccessSound(player);
                                    });
                            GuiItem krankehaus = ItemBuilder.from(Material.GHAST_TEAR).name(Component.text("§cKrankenhaus"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 39, 71, -149));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zum §cKrankenhaus §7gestartet.");
                                        main.playProccessSound(player);
                                    });
                            GuiItem polizeistation = ItemBuilder.from(Material.MAGMA_CREAM).name(Component.text("§9Polizeistation"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 83, 67, -19));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zur §9Polizeistation §7gestartet.");
                                        main.playProccessSound(player);
                                    });
                            GuiItem baumarkt = ItemBuilder.from(Material.IRON_PICKAXE).name(Component.text("§aBaumarkt"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 335, 67, -28.5));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zum §fBaumarkt §7gestartet.");
                                        main.playProccessSound(player);
                                    });

                            orteInventory.setItem(10, stadthalle);
                            orteInventory.setItem(11, flughafen);
                            orteInventory.setItem(12, lagerhalle);
                            orteInventory.setItem(13, krankehaus);
                            orteInventory.setItem(14, polizeistation);
                            orteInventory.setItem(15, baumarkt);

                            orteInventory.open(player);
                        });


                GuiItem navi_fraktionen = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§aFraktionen"))
                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                        .asGuiItem(event -> {
                            main.playProccessSound(player);
                            Gui fraktionenInventory = Gui.gui()
                                    .rows(3)
                                    .title(Component.text(main.prefix + "§7Fraktionen:"))
                                    .disableAllInteractions()
                                    .create();

                            GuiItem mafia = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§8Mafia"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), -260, 69, 137));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zum §8Mafia-Gebiet §7gestartet.");
                                        main.playProccessSound(player);
                                    });

                            GuiItem ballas = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§dBallas"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 170, 69, -170));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zum §dBallas-Gebiet §7gestartet.");
                                        main.playProccessSound(player);
                                    });

                            fraktionenInventory.setItem(11, mafia);
                            fraktionenInventory.setItem(15, ballas);
                            fraktionenInventory.open(player);
                        });
                GuiItem navi_freizeit = ItemBuilder.from(Material.OAK_BUTTON).name(Component.text("§aFreizeit"))
                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                        .asGuiItem(event -> {
                            main.playProccessSound(player);
                            Gui freizeitInventory = Gui.gui()
                                    .rows(3)
                                    .title(Component.text(main.prefix + "§7Freizeit:"))
                                    .disableAllInteractions()
                                    .create();

                            GuiItem ffa = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§aFFA"))
                                    .lore(Component.text(" §f▹ §7§oKlicke um Route zu starten."))
                                    .asGuiItem(clickRoute -> {
                                        player.closeInventory();
                                        navigateTo(player, new Location(player.getWorld(), 1, 68, -299));
                                        player.sendMessage(main.prefix + "§7Du hast eine Route zu §fFFA §7gestartet.");
                                        main.playProccessSound(player);
                                    });

                            freizeitInventory.setItem(13, ffa);
                            freizeitInventory.open(player);
                        });

                GuiItem navi_jobs = ItemBuilder.from(Material.IRON_PICKAXE).name(Component.text("§aJobs"))
                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .asGuiItem(event -> {
                            player.closeInventory();
                            main.playProccessSound(player);
                            Bukkit.dispatchCommand(player, "jobs");
                        });

                GuiItem navi_cancel = ItemBuilder.from(Material.BARRIER).name(Component.text("§cRoute abbrechen"))
                        .lore(Component.text(" §f▹ §7§oKlicke um Route abzubrechen."))
                        .asGuiItem(event -> {
                            player.closeInventory();
                            main.playErrorSound(player);
                            if(navigationTimers.get(player) == null) return;
                            Bukkit.getScheduler().cancelTask(navigationTimers.get(player));
                            navigationTimers.remove(player);
                            player.sendMessage(main.prefix + "§7Du hast die Route abgebrochen.");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§f▹ §7Du hast die Route abgebrochen."));
                        });

                Gui naviInventory = Gui.gui()
                        .rows(5)
                        .title(Component.text(main.prefix + "§7Navigation:"))
                        .disableAllInteractions()
                        .create();
                naviInventory.setItem(10, navi_oeffentliche_orte);
                naviInventory.setItem(12, navi_fraktionen);
                naviInventory.setItem(14, navi_freizeit);
                naviInventory.setItem(16, navi_jobs);
                naviInventory.setItem(31, navi_cancel);

                main.playSuccessSound(player);
                naviInventory.open(player);
            } else if (args.length == 3) {
                String x = args[0];
                String y = args[1];
                String z = args[2];

                if (isInt(x) && isInt(y) && isInt(z)) {
                    Location loc = new Location(player.getWorld(), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
                    navigateTo(player, loc);
                    main.playSuccessSound(player);
                } else {
                    player.sendMessage(main.pre_error + "§cDie angegeben Koordinaten sind keine ganzen Zahlen.");
                    main.playErrorSound(player);
                }

            } else {
                player.sendMessage(main.pre_error + "§cBitte benutze /navi oder /navi <x> <y> <z>");
                main.playErrorSound(player);
            }
        }
        return false;
    }
}
