package de.marc.ganglife.phone.events;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.faction.methods.sendFactionMessage;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.methods.navigation;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
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

import java.util.*;

public class phone implements Listener {

    public static Map<Player, Integer> emergencyTimer = new HashMap<>();
    public static ArrayList<Player> blockEmergencyMedics = new ArrayList<>();
    public static ArrayList<Player> blockEmergencyPolice = new ArrayList<>();

    public static final List<String> emergencyListMedicss = new ArrayList<>();

    public static final List<String> emergencyListPolice = new ArrayList<>();

    public static HashMap<Player, String> safeContactNumber = new HashMap<>();
    public static HashMap<Player, String> safeContactName = new HashMap<>();


    @EventHandler
    public void openPhone(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        Gui phoneInventory = Gui.gui()
                .rows(6)
                .title(Component.text(main.prefix + "§7Handy"))
                .disableAllInteractions()
                .create();

        GuiItem closePhoneGUI = ItemBuilder.from(Material.CYAN_DYE).name(Component.text("§cAusschalten"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem contactApp = ItemBuilder.from(Material.LIGHT_GRAY_DYE).name(Component.text("§aKontakte"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);

                    Gui contactInventory = Gui.gui()
                            .rows(3)
                            .title(Component.text(main.prefix + "§7Kontakte verwalten"))
                            .disableAllInteractions()
                            .create();

                    GuiItem addContacts = ItemBuilder.from(Material.HONEYCOMB)
                            .name(Component.text("§aKontakte hinzufügen"))
                            .lore(Component.text(" §7▹ §7§oKlicke um ein Kontakt hinzuzufügen."))
                            .asGuiItem(eventContact -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                ItemStack safe = ItemBuilder.from(Material.HONEYCOMB)
                                        .name(Component.text("§aBestätigen"))
                                        .lore(Component.text(" §7▹ §7§oKlicke zum bestätigen."))
                                        .build();

                                ItemStack infoNumber = ItemBuilder.from(Material.BLAZE_ROD)
                                        .name(Component.text("§7Info"))
                                        .lore(Component.text(" §7▹ §7§oBitte gebe eine Telefonnummer ein."))
                                        .build();

                                ItemStack infoName = ItemBuilder.from(Material.BLAZE_ROD)
                                        .name(Component.text("§7Info"))
                                        .lore(Component.text(" §7▹ §7§oBitte gebe einen Namen ein."))
                                        .build();

                                new AnvilGUI.Builder()
                                        .onClick((slot, stateSnapshot) -> {
                                            if (slot != AnvilGUI.Slot.OUTPUT) {
                                                return Collections.emptyList();
                                            }

                                            if (isInteger.isInt(stateSnapshot.getText())) {
                                                main.playProccessSound(player);
                                                player.closeInventory();
                                                safeContactNumber.put(player, stateSnapshot.getText());

                                                new AnvilGUI.Builder()
                                                        .onClick((slot2, stateSnapshot2) -> {
                                                            if (slot2 != AnvilGUI.Slot.OUTPUT) {
                                                                return Collections.emptyList();
                                                            }
                                                            player.closeInventory();
                                                            safeContactName.put(player, stateSnapshot2.getText());
                                                            Bukkit.dispatchCommand(player, "addcontact " + safeContactName.get(player) + " " + safeContactNumber.get(player));
                                                            return Collections.emptyList();
                                                        })
                                                        .title(main.prefix + "§7Kontaktname:")
                                                        .plugin(main.getPlugin())
                                                        .text("§7Kontaktname")
                                                        .itemLeft(infoName)
                                                        .itemOutput(safe)
                                                        .open(player);

                                                return Collections.emptyList();

                                            } else {
                                                main.playErrorSound(player);
                                                return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("§cBitte gebe eine Zahl an."));
                                            }
                                        })

                                        .title(main.prefix + "§7Telefonnummer:")
                                        .plugin(main.getPlugin())
                                        .text("§7Telefonnummer")
                                        .itemLeft(infoNumber)
                                        .itemOutput(safe)
                                        .open(player);

                            });

                    GuiItem manageContacts = ItemBuilder.from(Material.LIGHT_GRAY_DYE)
                            .name(Component.text("§eKontakte verwalten"))
                            .lore(Component.text(" §7▹ §7§oKlicke um deine Kontakte zu verwalten."))
                            .asGuiItem(eventContact -> {
                                player.closeInventory();
                                main.playProccessSound(player);

                                String contactsJSON = uPlayer.getPhoneContacts();
                                Map<String, Object>[] contacts = de.marc.ganglife.methods.phone.formatFromString(contactsJSON);

                                if (contacts.length == 0) {
                                    player.sendMessage(main.pre_error + "§cEs wurden keine Kontakte gefunden.");
                                    main.playErrorSound(player);
                                } else {
                                    Gui playercontactsInventory = Gui.gui()
                                            .rows(6)
                                            .title(Component.text(main.prefix + "§7Kontakte"))
                                            .disableAllInteractions()
                                            .create();

                                    for (Map<String, Object> contact : contacts) {
                                        String name = (String) contact.get("name");
                                        String number = (String) contact.get("number");

                                        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

                                        playercontactsInventory.addItem(ItemBuilder.from(playerHead)
                                                .name(Component.text("§e" + name + " §7- §6" + number))
                                                .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen."))
                                                .asGuiItem(click -> {

                                                    Gui contactinfoInventory = Gui.gui()
                                                            .rows(3)
                                                            .title(Component.text(main.prefix + "§e" + name))
                                                            .disableAllInteractions()
                                                            .create();

                                                    GuiItem callContact = ItemBuilder.from(Material.BOOK)
                                                            .name(Component.text("§e" + name + " §aanrufen"))
                                                            .lore(Component.text(" §f▹ §7§oKlicke um den Kontakt anzurufen."))
                                                            .asGuiItem(accept -> {
                                                                player.closeInventory();
                                                                main.playProccessSound(player);
                                                                // Call functionality
                                                            });

                                                    GuiItem smsContact = ItemBuilder.from(Material.WHITE_DYE)
                                                            .name(Component.text("§e" + name + " §6eine SMS schreiben"))
                                                            .lore(Component.text(" §f▹ §7§oKlicke um dem Kontakt eine Nachricht zu schreiben."))
                                                            .asGuiItem(cancel -> {
                                                                player.closeInventory();
                                                                main.playProccessSound(player);
                                                                // SMS functionality
                                                            });

                                                    GuiItem deleteContact = ItemBuilder.from(Material.BARRIER)
                                                            .name(Component.text("§e" + name + " §clöschen"))
                                                            .lore(Component.text(" §f▹ §7§oKlicke um den Kontakt zu löschen."))
                                                            .asGuiItem(cancel -> {
                                                                player.closeInventory();
                                                                main.playProccessSound(player);
                                                                // Delete functionality
                                                            });

                                                    contactinfoInventory.setItem(11, callContact);
                                                    contactinfoInventory.setItem(13, smsContact);
                                                    contactinfoInventory.setItem(15, deleteContact);

                                                    player.closeInventory();
                                                    contactinfoInventory.open(player);
                                                    main.playProccessSound(player);
                                                }));
                                    }

                                    playercontactsInventory.open(player);
                                }
                            });

                    contactInventory.setItem(11, addContacts);
                    contactInventory.setItem(15, manageContacts);
                    contactInventory.open(player);
                });

        GuiItem bankingApp = ItemBuilder.from(Material.YELLOW_DYE).name(Component.text("§eBank"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);

                    player.sendMessage(main.prefix + "§7Aktueller Kontostand: §e" + uPlayer.getBank() + "$");
                });

        GuiItem emergencyApp = ItemBuilder.from(Material.BROWN_DYE).name(Component.text("§9Notruf"))
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
        GuiItem settingsApp = ItemBuilder.from(Material.RED_DYE).name(Component.text("§7Einstellungen"))
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

        GuiItem navigationApp = ItemBuilder.from(Material.LIME_DYE).name(Component.text("§aNavigation"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                    Bukkit.dispatchCommand(player, "navi");
                });

        GuiItem factionApp = ItemBuilder.from(Material.BLACK_DYE).name(Component.text("§6Fraktion"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem businessApp = ItemBuilder.from(Material.GRAY_DYE).name(Component.text("§eBusiness"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem notrufListApp = ItemBuilder.from(Material.ORANGE_DYE).name(Component.text("§cAktuelle Notrufe"))
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
                                                        main.playProccessSound(target);
                                                        target.sendMessage(main.prefix + "§7Dein Notruf wurde §cgeschlossen.");
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

        GuiItem actsApp = ItemBuilder.from(Material.GREEN_DYE).name(Component.text("§cAkten"))
                .asGuiItem(clickEvent -> {
                    player.closeInventory();
                    main.playProccessSound(player);
                });

        GuiItem placeHolder = ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(""))
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
