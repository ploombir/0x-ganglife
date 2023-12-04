package de.marc.ganglife.playermanager;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import  net.kyori.adventure.text.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class playermanageCommand implements CommandExecutor {

    public static final Map<Player, ActionType> ACTIONS = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(main.pre_error + "§cDu musst ein Spieler sein");
            return false;
        }

        if (!player.hasPermission("system.manager")) {
            player.sendMessage(main.noperms);

            main.playErrorSound(player);
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(main.pre_error + "§cVerwendung: /playermanage <Spieler>");

            main.playErrorSound(player);
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(main.pre_error + "§cSpieler nicht gefunden.");

            main.playErrorSound(player);
            return false;
        }
        UPlayer uPlayer = UPlayer.getUPlayer(target.getUniqueId());

        Gui playerGui = Gui.gui()
                .title(Component.text(main.prefix + "§7Spielerverwaltung | " + target.getName()))
                .rows(6)
                .disableAllInteractions()
                .create();

        GuiItem deadItem = ItemBuilder.from(Material.SKELETON_SKULL)
                .name(Component.text("§aTod verwalten"))
                .lore(Component.text("§f▹ §7Tod von " + target.getName() + " verwalten"), Component.text(""), Component.text("§7Linksklick §8» §7Informationen anzeigen"), Component.text("§7Rechtsklick §8» §7Todeszeit setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            // DEATH TIME
                            player.sendMessage(Component.text(main.prefix + "§7Todesstatus: " + (uPlayer.getDeathTime() <= 0 ? "§aLebendig" : "§cTot")));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            // SET DEATH TIME
                            ACTIONS.put(player, ActionType.DEATH_TIME);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Todeszeit in Minuten in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem jailItem = ItemBuilder.from(Material.IRON_BARS)
                .name(Component.text("§aGefängnis verwalten"))
                .lore(Component.text("§f▹ §7Gefängnis von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Gefängniszeit setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            // JAIL TIME
                            player.sendMessage(Component.text(main.prefix + "§7Gefängnisstatus: " + (uPlayer.isJail() ? "§cEingesperrt" : "§aFrei")));
                            player.sendMessage(Component.text(main.prefix + "§7Gefängniszeit: " + uPlayer.getJailTime() + " Hafteinheiten"));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            // SET JAIL TIME
                            ACTIONS.put(player, ActionType.JAIL_TIME);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Gefängniszeit in Hafteinheiten in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem cashItem = ItemBuilder.from(Material.GOLD_NUGGET)
                .name(Component.text("§aGeld verwalten"))
                .lore(Component.text("§f▹ §7Geld von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Geld setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Geld: " + uPlayer.getCash() + "$"));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.CASH);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Geld in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem phoneItem = ItemBuilder.from(Material.IRON_INGOT)
                .name(Component.text("§aHandy verwalten"))
                .lore(Component.text("§f▹ §7Handy von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Nummer setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Flugmodus setzen"), Component.text("§f▹ §7Shift + Rechtsklick §8» §7Kontakte setzen"))
                .asGuiItem(event -> {
                    // NUMBER
                    // FLIGHT MODE
                    // CONTACTS

                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Telefonnummer: " + uPlayer.getPhoneNumber()));
                            player.sendMessage(Component.text(main.prefix + "§7Flugmodus: " + uPlayer.isPhoneFlightMode()));
                            player.sendMessage(Component.text(main.prefix + "§7Kontakte: " + uPlayer.getPhoneContacts()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.PHONE_NUMBER);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Telefonnummer in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.PHONE_FLIGHT_MODE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Flugmodus in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                        case SHIFT_RIGHT -> {
                            ACTIONS.put(player, ActionType.PHONE_CONTACTS);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Kontakte in den Chat. (Name:Nummer)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem bankItem = ItemBuilder.from(Material.GOLD_NUGGET)
                .name(Component.text("§aBank verwalten"))
                .lore(Component.text("§f▹ §7Bank von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Bank setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Kontostand: " + uPlayer.getBank() + "$"));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.BANK);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Bankgeld in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem levelItem = ItemBuilder.from(Material.EXPERIENCE_BOTTLE)
                .name(Component.text("§aLevel verwalten"))
                .lore(Component.text("§f▹ §7Level von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Level setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Exp setzen"))
                .asGuiItem(event -> {
                    // LEVEL
                    // EXP
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Level: " + uPlayer.getLevel()));
                            player.sendMessage(Component.text(main.prefix + "§7Exp: " + uPlayer.getLevelExp()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.LEVEL);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Level in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.LEVEL_EXP);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Level EXP in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem factionItem = ItemBuilder.from(Material.GREEN_DYE)
                .name(Component.text("§aFraktion verwalten"))
                .lore(Component.text("§f▹ §7Fraktion von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Fraktion setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Fraktionsrang setzen"))
                .asGuiItem(event -> {
                    // FACTION
                    // FACTION RANK
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Fraktion: " + uPlayer.getFaction()));
                            player.sendMessage(Component.text(main.prefix + "§7Rang: " + uPlayer.getFactionRank()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.FACTION);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Fraktion in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.FACTION_RANK);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Fraktionsrang in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem drinkItem = ItemBuilder.from(Material.GLASS_BOTTLE)
                .name(Component.text("§aTrinken verwalten"))
                .lore(Component.text("§f▹ §7Trinken von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Trinken setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Trinken: " + uPlayer.getDrink()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.DRINK);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Trinken in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem houseItem = ItemBuilder.from(Material.LIGHT_GRAY_CONCRETE)
                .name(Component.text("§aHaus verwalten"))
                .lore(Component.text("§f▹ §7Haus von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Hausnummer setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Hausinventar setzen"))
                .asGuiItem(event -> {
                    // HOUSE NUMBER
                    // HOUSE INVENTORY
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Hausnummer: " + uPlayer.getHouseNumber()));
                            player.sendMessage(Component.text(main.prefix + "§7Hausinventar: " + uPlayer.getHouseInventory()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.HOUSE_NUMBER);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Hausnummer in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.HOUSE_INVENTORY);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Hausinventar in den Chat. (keine ahnung bruder)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem paydayItem = ItemBuilder.from(Material.GOLD_BLOCK)
                .name(Component.text("§aPayday verwalten"))
                .lore(Component.text("§f▹ §7Payday von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Payday setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Payday: " + uPlayer.getPaydayTime() + " Minuten"));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.PAYDAY);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Payday in Minuten in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem playtimeItem = ItemBuilder.from(Material.CLOCK)
                .name(Component.text("§aSpielzeit verwalten"))
                .lore(Component.text("§f▹ §7Spielzeit von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Spielzeit setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Spielzeit: " + uPlayer.getPlayTime() + " Minuten"));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.PLAYTIME);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Spielzeit in Minuten in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem licenceItem = ItemBuilder.from(Material.BOOK)
                .name(Component.text("§aLizenzen verwalten"))
                .lore(Component.text("§f▹ §7Lizenzen von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Waffenlizent setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Führerschein setzen"), Component.text("§f▹ §7Shift + Rechtsklick §8» §7Erste-Hilfe Schein setzen"))
                .asGuiItem(event -> {
                    // WEAPON
                    // DRIVE
                    // FIRST AID
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Waffenlizenz: " + uPlayer.isWeaponLicence()));
                            player.sendMessage(Component.text(main.prefix + "§7Führerschein: " + uPlayer.isDriveLicence()));
                            player.sendMessage(Component.text(main.prefix + "§7Erste-Hilfe Schein: " + uPlayer.isFirstaidLicence()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.WEAPON_LICENCE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Waffenlizenz in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.DRIVE_LICENCE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Führerschein in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                        case SHIFT_RIGHT -> {
                            ACTIONS.put(player, ActionType.FIRST_AID_LICENCE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Erste-Hilfe Schein in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem actsItem = ItemBuilder.from(Material.BOOK)
                .name(Component.text("§aAkten verwalten"))
                .lore(Component.text("§f▹ §7Akten von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Akten setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Akten: " + uPlayer.getActReasons()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.ACTS);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Akten in den Chat. (Grund:Hafteinheiten)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem garbageJobItem = ItemBuilder.from(Material.CAULDRON)
                .name(Component.text("§aMüllmannjob verwalten"))
                .lore(Component.text("§f▹ §7Müllmannjob von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Level setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Exp setzen"))
                .asGuiItem(event -> {
                    // LEVEL
                    // EXP
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Level: " + uPlayer.getGarbageLevel()));
                            player.sendMessage(Component.text(main.prefix + "§7Exp: " + uPlayer.getGarbageExp()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.LEVEL);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Müllmann Level in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.LEVEL_EXP);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Müllmann Level EXP in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem inventoryItem = ItemBuilder.from(Material.CHEST)
                .name(Component.text("§aInventar verwalten"))
                .lore(Component.text("§f▹ §7Inventar von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Cocaine setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Weed setzen"), Component.text("§f▹ §7Shift + Rechtsklick §8» §7Meth setzen"), Component.text("§f▹ §7Q §8» §7Medicine setzen"), Component.text("§f▹ §7F §8» §7Bulletproof setzen"), Component.text("§f▹ §7Mausrad §8» §7Police Bulletproof setzen"))
                .asGuiItem(event -> {
                    // COCAINE
                    // WEED
                    // METH
                    // MEDICINE
                    // BULLETPROOF
                    // POLICE BULLETPROOF
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Cocaine: " + uPlayer.getCocaineAmount()));
                            player.sendMessage(Component.text(main.prefix + "§7Weed: " + uPlayer.getWeedAmount()));
                            player.sendMessage(Component.text(main.prefix + "§7Meth: " + uPlayer.getMethAmount()));
                            player.sendMessage(Component.text(main.prefix + "§7Medicine: " + uPlayer.getMedicineAmount()));
                            player.sendMessage(Component.text(main.prefix + "§7Bulletproof: " + uPlayer.getBulletproofAmount()));
                            player.sendMessage(Component.text(main.prefix + "§7Police Bulletproof: " + uPlayer.getPoliceBulletproofAmount()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.COCAINE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Cocaine Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.WEED);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Weed Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_RIGHT -> {
                            ACTIONS.put(player, ActionType.METH);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Meth Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                        case DROP -> {
                            ACTIONS.put(player, ActionType.MEDICINE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Medicine Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                        case SWAP_OFFHAND -> {
                            ACTIONS.put(player, ActionType.BULLETPROOF);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Bulletproof Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                        case MIDDLE -> {
                            ACTIONS.put(player, ActionType.POLICE_BULLETPROOF);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Police Bulletproof Anzahl in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem storageItem = ItemBuilder.from(Material.CHEST)
                .name(Component.text("§aLagerhalle verwalten"))
                .lore(Component.text("§f▹ §7Lagerhalle von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Gemietet setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Inventar setzen"))
                .asGuiItem(event -> {
                    // INVENTORY
                    // IS RENTED
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Lagerhalleninventar: " + uPlayer.getStorageInventory()));
                            player.sendMessage(Component.text(main.prefix + "§7Gemietet: " + uPlayer.isRentStorage()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.STORAGE_INVENTORY);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Lagerhalleninventar in den Chat. (keine ahnung bruder)"));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.RENT_STORAGE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Gemietet Status in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem persoItem = ItemBuilder.from(Material.PAPER)
                .name(Component.text("§aPersonalausweis verwalten"))
                .lore(Component.text("§f▹ §7Personalausweis von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Vorname setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Nachname setzen"), Component.text("§f▹ §7Shift + Rechtsklick §8» §7Geburtsdatum setzen"), Component.text("§f▹ §7Q §8» §7Geschlecht setzen"))
                .asGuiItem(event -> {
                    // FIRSTNAME
                    // LASTNAME
                    // BIRTHDATE
                    // GENDER
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Vorname: " + uPlayer.getFirstName()));
                            player.sendMessage(Component.text(main.prefix + "§7Nachname: " + uPlayer.getLastName()));
                            player.sendMessage(Component.text(main.prefix + "§7Geburtsdatum: " + uPlayer.getBirthDate()));
                            player.sendMessage(Component.text(main.prefix + "§7Geschlecht: " + uPlayer.getGender()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.FIRSTNAME);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Vornamen in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.LASTNAME);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Nachnamen in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_RIGHT -> {
                            ACTIONS.put(player, ActionType.BIRTHDATE);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Geburtsdatum in den Chat."));

                            playerGui.close(player);
                        }
                        case DROP -> {
                            ACTIONS.put(player, ActionType.GENDER);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das Geschlecht in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem discordVerifyItem = ItemBuilder.from(Material.BLUE_BANNER)
                .name(Component.text("§aDiscord Verify verwalten"))
                .lore(Component.text("§f▹ §7Discord Verify von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7Verify setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Discord ID setzen"))
                .asGuiItem(event -> {
                    // VERIFY
                    // ID
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Verify: " + uPlayer.getDiscordVerify()));
                            player.sendMessage(Component.text(main.prefix + "§7ID: " + uPlayer.getDiscordId()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.DISCORD_VERIFY);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Discord Verify ID in den Chat."));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.DISCORD_ID);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Discord ID in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem premiumItem = ItemBuilder.from(Material.GOLD_NUGGET)
                .name(Component.text("§aPremium verwalten"))
                .lore(Component.text("§f▹ §7Premium von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Premium setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Premium: " + uPlayer.isPremiumAccount()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.PREMIUM);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den Premium Status in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem ffaItem = ItemBuilder.from(Material.IRON_SWORD)
                .name(Component.text("§aFFA verwalten"))
                .lore(Component.text("§f▹ §7FFA von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Rechtsklick §8» §7FFA setzen"), Component.text("§f▹ §7Shift + Linksklick §8» §7FFA Inventar setzen"), Component.text("§f▹ §7Shift + Rechtsklick §8» §7FFA Kills setzen"), Component.text("§f▹ §7Q §8» §7FFA Tode setzen"))
                .asGuiItem(event -> {
                    // IS FFA
                    // FFA INVENTORY
                    // FFA KILLS
                    // FFA DEATHS
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7FFA: " + uPlayer.isFFA()));
                            player.sendMessage(Component.text(main.prefix + "§7FFA Inventar: " + uPlayer.getFfaInventory()));
                            player.sendMessage(Component.text(main.prefix + "§7FFA Kills: " + uPlayer.getFfaKills()));
                            player.sendMessage(Component.text(main.prefix + "§7FFA Tode: " + uPlayer.getFfaDeaths()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.IS_FFA);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun den FFA Status in den Chat. (true/false)"));

                            playerGui.close(player);
                        }
                        case SHIFT_LEFT -> {
                            ACTIONS.put(player, ActionType.FFA_INVENTORY);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun das FFA Inventar in den Chat. (keine ahnung bruder)"));

                            playerGui.close(player);
                        }
                        case SHIFT_RIGHT -> {
                            ACTIONS.put(player, ActionType.FFA_KILLS);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die FFA Kills in den Chat."));

                            playerGui.close(player);
                        }
                        case DROP -> {
                            ACTIONS.put(player, ActionType.FFA_DEATHS);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die FFA Tode in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        GuiItem votesItem = ItemBuilder.from(Material.EXPERIENCE_BOTTLE)
                .name(Component.text("§aVotes verwalten"))
                .lore(Component.text("§f▹ §7Votes von " + target.getName() + " verwalten"), Component.text(""), Component.text("§f▹ §7Linksklick §8» §7Informationen anzeigen"), Component.text("§f▹ §7Shift + Linksklick §8» §7Votes setzen"))
                .asGuiItem(event -> {
                    switch (event.getClick()) {
                        case LEFT -> {
                            player.sendMessage(Component.text(main.prefix + "§7Akten: " + uPlayer.getVotes()));

                            playerGui.close(player);

                            main.playSuccessSound(player);
                        }
                        case RIGHT -> {
                            ACTIONS.put(player, ActionType.VOTES);

                            player.sendMessage(Component.text(main.prefix + "§7Schreibe nun die Votes in den Chat."));

                            playerGui.close(player);
                        }
                    }
                });

        playerGui.setItem(10, deadItem);
        playerGui.setItem(12, jailItem);
        playerGui.setItem(14, cashItem);
        playerGui.setItem(16, phoneItem);

        playerGui.setItem(18, bankItem);
        playerGui.setItem(20, levelItem);
        playerGui.setItem(22, factionItem);
        playerGui.setItem(24, drinkItem);
        playerGui.setItem(26, houseItem);

        playerGui.setItem(28, paydayItem);
        playerGui.setItem(30, playtimeItem);
        playerGui.setItem(32, licenceItem);
        playerGui.setItem(34, actsItem);

        playerGui.setItem(36, garbageJobItem);
        playerGui.setItem(38, inventoryItem);
        playerGui.setItem(40, storageItem);
        playerGui.setItem(42, persoItem);
        playerGui.setItem(44, discordVerifyItem);

        playerGui.setItem(47, premiumItem);
        playerGui.setItem(49, ffaItem);
        playerGui.setItem(51, votesItem);

        playerGui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());

        playerGui.open(player);
        return true;
    }

    public enum ActionType {
        DEATH_TIME, JAIL_TIME, CASH, PHONE_NUMBER, PHONE_FLIGHT_MODE, PHONE_CONTACTS, BANK, LEVEL, LEVEL_EXP, FACTION, FACTION_RANK, DRINK, HOUSE_NUMBER, HOUSE_INVENTORY, PAYDAY, PLAYTIME, WEAPON_LICENCE, DRIVE_LICENCE, FIRST_AID_LICENCE, ACTS, GARBAGE_LEVEL, GARBAGE_EXP, COCAINE, WEED, METH, MEDICINE, BULLETPROOF, POLICE_BULLETPROOF, STORAGE_INVENTORY, RENT_STORAGE, FIRSTNAME, LASTNAME, BIRTHDATE, GENDER, DISCORD_VERIFY, DISCORD_ID, PREMIUM, IS_FFA, FFA_INVENTORY, FFA_KILLS, FFA_DEATHS, VOTES
    }
}
