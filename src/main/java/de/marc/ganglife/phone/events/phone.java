package de.marc.ganglife.phone.events;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.faction.methods.sendFactionMessage;
import de.marc.ganglife.methods.navigation;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class phone implements Listener {

    public static Map<Player, Integer> emergencyTimer = new HashMap<>();
    public static ArrayList<Player> blockEmergencyMedics = new ArrayList<>();
    public static ArrayList<Player> blockEmergencyPolice = new ArrayList<>();

    public static final List<String> emergencyListMedicss = new ArrayList<>();

    public static final List<String> emergencyListPolice = new ArrayList<>();


    @EventHandler
    public void openPhone(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        Gui phoneInventory = Gui.gui()
                .rows(6)
                .title(Component.text(main.prefix + "§7Handy"))
                .disableAllInteractions()
                .create();

        GuiItem closePhoneGUI = ItemBuilder.from(Material.BARRIER).name(Component.text("§cAusschalten"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem contactApp = ItemBuilder.from(Material.BOOK).name(Component.text("§aKontakte"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem bankingApp = ItemBuilder.from(Material.GOLD_BLOCK).name(Component.text("§eBank"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);

                    player.sendMessage(main.prefix + "§7Aktueller Kontostand: §e" + uPlayer.getBank() + "$");
                });

        GuiItem emergencyApp = ItemBuilder.from(Material.TRIPWIRE_HOOK).name(Component.text("§9Notruf"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);

                    Gui emergencyInventory = Gui.gui()
                            .rows(3)
                            .title(Component.text(main.prefix + "§7Notruf wählen"))
                            .disableAllInteractions()
                            .create();

                    GuiItem policeEmergency = ItemBuilder.from(Material.MAGMA_CREAM).name(Component.text("§9Polizei rufen"))
                            .asGuiItem(clickPolice -> {
                                player.closeInventory();

                                if (blockEmergencyPolice.contains(player)) {
                                    player.sendMessage(main.pre_error + "§cDu kannst nur jede Minute einen Notruf absenden.");
                                    main.playErrorSound(player);
                                    return;
                                }
                                main.playProccessSound(player);
                                blockEmergencyPolice.add(player);
                                emergencyListPolice.add(player.getName());
                                player.sendMessage(main.prefix + "§7Du hast einen Notruf an die §9Polizei §7abgesendet.");
                                sendFactionMessage.sendStringFactionMessage("Polizei", "Es ist ein Notruf von " + player.getName() + " eingegangen.");

                                emergencyTimer.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                                    blockEmergencyPolice.remove(player);
                                }, 60 * 20L));

                            });
                    GuiItem medicEmergenency = ItemBuilder.from(Material.GHAST_TEAR).name(Component.text("§cSanitäter rufen"))
                            .asGuiItem(clickMedics -> {
                                player.closeInventory();

                                if (blockEmergencyMedics.contains(player)) {
                                    main.playErrorSound(player);
                                    player.sendMessage(main.pre_error + "§cDu kannst nur jede Minute einen Notruf absenden.");
                                    return;
                                }
                                main.playProccessSound(player);
                                blockEmergencyMedics.add(player);
                                emergencyListMedicss.add(player.getName());
                                player.sendMessage(main.prefix + "§7Du hast einen Notruf an die §cMedics §7abgesendet.");
                                sendFactionMessage.sendStringFactionMessage("Medics", "Es ist ein Notruf von " + player.getName() + " eingegangen.");

                                emergencyTimer.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                                    blockEmergencyMedics.remove(player);
                                }, 60 * 20L));
                            });

                    emergencyInventory.setItem(11, medicEmergenency);
                    emergencyInventory.setItem(15, policeEmergency);
                    emergencyInventory.open(player);
                });
        GuiItem settingsApp = ItemBuilder.from(Material.REDSTONE).name(Component.text("§7Einstellungen"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);

                    Gui settingsInventory = Gui.gui()
                            .rows(3)
                            .title(Component.text(main.prefix + "§7Einstellungen"))
                            .disableAllInteractions()
                            .create();

                    GuiItem hasIdentitySetting = ItemBuilder.from(Material.NAME_TAG)
                            .name(Component.text("§7Deine Identität:"))
                            .lore(Component.text(" §f▹ §e" + uPlayer.getFirstName() + " " + uPlayer.getLastName()))
                            .asGuiItem(settingsClickEvent -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                player.sendMessage(main.prefix + "§7Deine Identität: §e" + uPlayer.getFirstName() + " " + uPlayer.getLastName());
                            });

                    GuiItem noIdentitySetting = ItemBuilder.from(Material.NAME_TAG)
                            .name(Component.text("§7Deine Identität:"))
                            .lore(Component.text(" §f▹ §cDu hast keine Identität."))
                            .asGuiItem(settingsClickEvent -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                player.sendMessage(main.prefix + "§cDu hast keine Identität.");
                            });

                    if (uPlayer.getFirstName().equals("")) {
                        settingsInventory.setItem(10, noIdentitySetting);
                    } else {
                        settingsInventory.setItem(10, hasIdentitySetting);
                    }


                    GuiItem numberSetting = ItemBuilder.from(Material.NAME_TAG)
                            .name(Component.text("§7Deine Telefonnummer:"))
                            .lore(Component.text(" §f▹ §e" + uPlayer.getPhoneNumber()))
                            .asGuiItem(settingsClickEvent -> {
                                player.closeInventory();
                                main.playProccessSound(player);
                                player.sendMessage(main.prefix + "§7Deine Telefonnummer: §e" + uPlayer.getPhoneNumber());
                            });

                    settingsInventory.setItem(11, numberSetting);


                    GuiItem flightModeTrueSetting = ItemBuilder.from(Material.NAME_TAG)
                            .name(Component.text("§7Flugmodus:"))
                            .lore(Component.text(" §f▹ §aAngeschaltet"))
                            .asGuiItem(settingsClickEvent -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                player.sendMessage(main.prefix + "§7Du hast den Flugmodus nun §causgeschaltet.");
                                uPlayer.setPhoneFlightMode(false);
                            });

                    GuiItem flightModeFalseSetting = ItemBuilder.from(Material.NAME_TAG)
                            .name(Component.text("§7Flugmodus:"))
                            .lore(Component.text(" §f▹ §cAusgeschaltet"))
                            .asGuiItem(settingsClickEvent -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                player.sendMessage(main.prefix + "§7Du hast den Flugmodus nun §aangeschaltet.");
                                uPlayer.setPhoneFlightMode(true);
                            });

                    if (uPlayer.isPhoneFlightMode()) {
                        settingsInventory.setItem(12, flightModeTrueSetting);
                    } else {
                        settingsInventory.setItem(12, flightModeFalseSetting);
                    }

                    settingsInventory.open(player);
                    main.playProccessSound(player);
                });

        GuiItem navigationApp = ItemBuilder.from(Material.COMPASS).name(Component.text("§aNavigation"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                    Bukkit.dispatchCommand(player, "navi");
                });

        GuiItem factionApp = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR).name(Component.text("§6Fraktion"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem businessApp = ItemBuilder.from(Material.CLAY_BALL).name(Component.text("§eBusiness"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem notrufListApp = ItemBuilder.from(Material.WRITABLE_BOOK).name(Component.text("§cAktuelle Notrufe"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    Gui notruflistInventory = Gui.gui()
                            .rows(3)
                            .title(Component.text(main.prefix + "§7Aktuelle Notrufe"))
                            .disableAllInteractions()
                            .create();

                    if (uPlayer.getFaction().equals("Polizei")) {
                        for (String playerName : emergencyListPolice) {
                            Player target = Bukkit.getPlayerExact(playerName);
                            if (target != null) {
                                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                                meta.setOwningPlayer(target);
                                playerHead.setItemMeta(meta);

                                notruflistInventory.addItem(ItemBuilder.from(playerHead)
                                        .name(Component.text("§7Notruf von: §e" + playerName))
                                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                                        .asGuiItem(click -> {

                                            Gui manageEmergency = Gui.gui()
                                                    .rows(3)
                                                    .title(Component.text(main.prefix + "§7Notruf von: §e" + playerName))
                                                    .disableAllInteractions()
                                                    .create();

                                            GuiItem acceptEmergency = ItemBuilder.from(Material.GUNPOWDER)
                                                    .name(Component.text("§aNotruf annehmen"))
                                                    .lore(Component.text(" §f▹ §7§oKlicke um den Notruf anzunehmen."))
                                                    .asGuiItem(accept -> {
                                                        player.closeInventory();
                                                        main.playProccessSound(player);
                                                        player.sendMessage(main.prefix + "§7Du hast den Notruf von §e" + target.getName() + " §aangenommen.");
                                                        sendFactionMessage.sendPlayerFactionMessage(player, player.getName() + " hat den Notruf von " + target.getName() + " angenommen.");
                                                        target.sendMessage(main.prefix + "§7Dein Notruf wurde §aangenommen.");
                                                        target.sendMessage(" §f▹ §7§oBitte warte an deinem aktuellem Standort.");
                                                        main.playProccessSound(target);
                                                        navigation.navigateTo(player, target.getLocation());
                                                    });
                                            GuiItem cancelEmergency = ItemBuilder.from(Material.BARRIER)
                                                    .name(Component.text("§cNotruf schließen"))
                                                    .lore(Component.text(" §f▹ §7§oKlicke um den Notruf zu schließen."))
                                                    .asGuiItem(cancel -> {
                                                        player.closeInventory();
                                                        main.playProccessSound(player);
                                                        player.sendMessage(main.prefix + "§7Du hast den Notruf von §e" + target.getName() + " §cgeschlossen.");
                                                        emergencyListPolice.remove(target.getName());
                                                    });

                                            manageEmergency.setItem(11, acceptEmergency);
                                            manageEmergency.setItem(15, cancelEmergency);
                                            player.closeInventory();
                                            manageEmergency.open(player);
                                            main.playProccessSound(player);
                                        }));
                            }
                        }
                        notruflistInventory.open(player);
                        main.playProccessSound(player);

                    } else if (uPlayer.getFaction().equals("Medics")) {
                        for (String playerName : emergencyListMedicss) {
                            Player target = Bukkit.getPlayerExact(playerName);
                            if (target != null) {
                                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                                meta.setOwningPlayer(target);
                                playerHead.setItemMeta(meta);

                                notruflistInventory.addItem(ItemBuilder.from(playerHead)
                                        .name(Component.text("§7Notruf von: §e" + playerName))
                                        .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                                        .asGuiItem(click -> {

                                            Gui manageEmergency = Gui.gui()
                                                    .rows(3)
                                                    .title(Component.text(main.prefix + "§7Notruf von: §e" + playerName))
                                                    .disableAllInteractions()
                                                    .create();

                                            GuiItem acceptEmergency = ItemBuilder.from(Material.GUNPOWDER)
                                                    .name(Component.text("§aNotruf annehmen"))
                                                    .lore(Component.text(" §f▹ §7§oKlicke um den Notruf anzunehmen."))
                                                    .asGuiItem(accept -> {
                                                        player.closeInventory();
                                                        main.playProccessSound(player);
                                                        player.sendMessage(main.prefix + "§7Du hast den Notruf von §e" + target.getName() + " §aangenommen.");
                                                        sendFactionMessage.sendPlayerFactionMessage(player, player.getName() + " hat den Notruf von " + target.getName() + " angenommen.");
                                                        target.sendMessage(main.prefix + "§7Dein Notruf wurde §aangenommen.");
                                                        target.sendMessage(" §f▹ §7§oBitte warte an deinem aktuellem Standort.");
                                                        main.playProccessSound(target);
                                                        navigation.navigateTo(player, target.getLocation());
                                                    });
                                            GuiItem cancelEmergency = ItemBuilder.from(Material.BARRIER)
                                                    .name(Component.text("§cNotruf schließen"))
                                                    .lore(Component.text(" §f▹ §7§oKlicke um den Notruf zu schließen."))
                                                    .asGuiItem(cancel -> {
                                                        player.closeInventory();
                                                        main.playProccessSound(player);
                                                        player.sendMessage(main.prefix + "§7Du hast den Notruf von §e" + target.getName() + " §cgeschlossen.");
                                                        emergencyListMedicss.remove(target.getName());
                                                    });

                                            manageEmergency.setItem(11, acceptEmergency);
                                            manageEmergency.setItem(15, cancelEmergency);
                                            player.closeInventory();
                                            manageEmergency.open(player);
                                            main.playProccessSound(player);
                                        }));
                            }
                        }
                        notruflistInventory.open(player);
                        main.playProccessSound(player);
                    }
                });

        GuiItem actsApp = ItemBuilder.from(Material.MAGMA_CREAM).name(Component.text("§cAkten"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem placeHolder = ItemBuilder.from(Material.BLACK_STAINED_GLASS).name(Component.text(""))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        if (uPlayer.getFaction().equals("Polizei") || uPlayer.getFaction().equals("Medics")) {
            phoneInventory.setItem(12, notrufListApp);
        }

        phoneInventory.setItem(13, navigationApp);

        if (uPlayer.getFaction().equals("Polizei")) {
            phoneInventory.setItem(14, actsApp);
        }
        phoneInventory.setItem(21, emergencyApp);
        phoneInventory.setItem(22, bankingApp);
        phoneInventory.setItem(23, settingsApp);
        phoneInventory.setItem(39, businessApp);
        phoneInventory.setItem(40, contactApp);
        phoneInventory.setItem(41, factionApp);
        phoneInventory.setItem(49, closePhoneGUI);

        phoneInventory.setItem(2, placeHolder);
        phoneInventory.setItem(11, placeHolder);
        phoneInventory.setItem(20, placeHolder);
        phoneInventory.setItem(29, placeHolder);
        phoneInventory.setItem(38, placeHolder);
        phoneInventory.setItem(47, placeHolder);

        phoneInventory.setItem(6, placeHolder);
        phoneInventory.setItem(15, placeHolder);
        phoneInventory.setItem(24, placeHolder);
        phoneInventory.setItem(33, placeHolder);
        phoneInventory.setItem(42, placeHolder);
        phoneInventory.setItem(51, placeHolder);


        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getMaterial() == Material.MUSIC_DISC_CAT) {
                phoneInventory.open(player);
                main.playProccessSound(player);
            }
        }

    }
}
