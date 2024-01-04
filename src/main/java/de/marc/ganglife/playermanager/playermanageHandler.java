package de.marc.ganglife.playermanager;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.playerdatas.UPlayer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.http.WebSocket;

public class playermanageHandler implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        event.setCancelled(true);

        if (playermanageCommand.ACTIONS.containsKey(player)) {
            switch (playermanageCommand.ACTIONS.get(player)) {
                case DEATH_TIME -> {
                    if (isInt(message)) {
                        uPlayer.setDeathTime(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Todeszeit von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case JAIL_TIME -> {
                    if (isInt(message)) {
                        uPlayer.setJailTime(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Gefängniszeit von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");


                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case CASH -> {
                    if (isInt(message)) {
                        uPlayer.setCash(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Geld von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case PHONE_NUMBER -> {
                    if (isInt(message)) {
                        uPlayer.setPhoneNumber(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Telefonnummer von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case PHONE_FLIGHT_MODE -> {
                    if (isBoolean(message)) {
                        uPlayer.setPhoneFlightMode(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den Flugmodus von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case PHONE_CONTACTS -> {
                    // TODO: NAME:NUMMER
                }
                case BANK -> {
                    if (isInt(message)) {
                        uPlayer.setBank(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Bankgeld von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case LEVEL -> {
                    if (isInt(message)) {
                        uPlayer.setLevel(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Level von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case LEVEL_EXP -> {
                    if (isInt(message)) {
                        uPlayer.setLevelExp(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Level-Exp von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FACTION -> {
                    uPlayer.setFaction(message);
                    player.sendMessage(main.prefix + "§aDu hast die Fraktion von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);

                    player.playSound(player, "block.anvil.land", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FACTION_RANK -> {
                    if (isInt(message)) {
                        uPlayer.setFactionRank(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast den Fraktionsrang von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case DRINK -> {
                    if (isInt(message)) {
                        uPlayer.setDrink(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast den Durst von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case HOUSE_NUMBER -> {
                    if (isInt(message)) {
                        uPlayer.setHouseNumber(message);
                        player.sendMessage(main.prefix + "§aDu hast die Hausnummer von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case HOUSE_INVENTORY -> {
                    // TODO: (keine ahnung bruder)
                }
                case PAYDAY -> {
                    if (isInt(message)) {
                        uPlayer.setPaydayTime(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast den Payday von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case PLAYTIME -> {
                    if (isInt(message)) {
                        uPlayer.setPlayTime(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Spielzeit von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib eine Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                }
                case WEAPON_LICENCE -> {
                    if (isBoolean(message)) {
                        uPlayer.setWeaponLicence(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast die Waffenlizenz von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case DRIVE_LICENCE -> {
                    if (isBoolean(message)) {
                        uPlayer.setDriveLicence(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den Führerschein von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FIRST_AID_LICENCE -> {
                    if (isBoolean(message)) {
                        uPlayer.setFirstaidLicence(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den Erste-Hilfe-Schein von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case ACTS -> {
                    // TODO: GRUND:HAFTEINHEITEN
                }
                case GARBAGE_LEVEL -> {
                    if (isInt(message)) {
                        uPlayer.setGarbageLevel(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast den Mülllevel von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case GARBAGE_EXP -> {
                    if (isInt(message)) {
                        uPlayer.setGarbageExp(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Müll-Exp von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case COCAINE -> {
                    if (isInt(message)) {
                        uPlayer.setCocaineAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Kokain von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case WEED -> {
                    if (isInt(message)) {
                        uPlayer.setWeedAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Weed von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case METH -> {
                    if (isInt(message)) {
                        uPlayer.setMethAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast das Meth von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case MEDICINE -> {
                    if (isInt(message)) {
                        uPlayer.setMedicineAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Medizin von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case BULLETPROOF -> {
                    if (isInt(message)) {
                        uPlayer.setBulletproofAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die kugelsichere Weste von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§cBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                }
                case POLICE_BULLETPROOF -> {
                    if (isInt(message)) {
                        uPlayer.setPoliceBulletproofAmount(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Polizei-Kugelsichere Weste von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "§aBitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case STORAGE_INVENTORY -> {
                    // TODO: keine ahnung bruder
                }
                case RENT_STORAGE -> {
                    if (isBoolean(message)) {
                        uPlayer.setRentStorage(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den Gemietet Status in der Lagerhalle von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FIRSTNAME -> {
                    uPlayer.setFirstName(message);
                    player.sendMessage(main.prefix + "§aDu hast den Vornamen von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case LASTNAME -> {
                    uPlayer.setLastName(message);
                    player.sendMessage(main.prefix + "§aDu hast den Nachnamen von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case BIRTHDATE -> {
                    uPlayer.setBirthDate(message);
                    player.sendMessage(main.prefix + "§aDu hast den Geburtstag von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case GENDER -> {
                    uPlayer.setGender(message);
                    player.sendMessage(main.prefix + "§aDu hast das Geschlecht von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case DISCORD_VERIFY -> {
                    if (isInt(message)) {
                        uPlayer.setDiscordVerify(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast den Discord-Verifizierungs-Code von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib eine gültige Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case DISCORD_ID -> {
                    uPlayer.setDiscordId(message);
                    player.sendMessage(main.prefix + "§aDu hast die Discord-ID von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                    player.playSound(player, "entity.player.levelup", 1, 1);
                    playermanageCommand.ACTIONS.remove(player);
                }
                case PREMIUM -> {
                    if (isBoolean(message)) {
                        uPlayer.setPremiumAccount(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den Premium-Status von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case IS_FFA -> {
                    if (isBoolean(message)) {
                        uPlayer.setFFA(Boolean.parseBoolean(message));
                        player.sendMessage(main.prefix + "§aDu hast den FFA-Status von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib true oder false ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FFA_INVENTORY -> {
                    // TODO: keine ahnung bruder
                }
                case FFA_KILLS -> {
                    if (isInt(message)) {
                        uPlayer.setFfaKills(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die FFA-Kills von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib eine gültige Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case FFA_DEATHS -> {
                    if (isInt(message)) {
                        uPlayer.setFfaDeaths(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die FFA-Tode von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib eine gültige Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
                case VOTES -> {
                    if (isInt(message)) {
                        uPlayer.setVotes(Integer.parseInt(message));
                        player.sendMessage(main.prefix + "§aDu hast die Votes von §e" + player.getName() + "§a auf §e" + message + "§a gesetzt.");

                        player.playSound(player, "entity.player.levelup", 1, 1);
                    } else {
                        player.sendMessage(main.prefix + "Bitte gib eine gültige Zahl ein.");

                        player.playSound(player, "block.anvil.land", 1, 1);
                    }
                    playermanageCommand.ACTIONS.remove(player);
                }
            }
        }
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String string) {
        try {
            Boolean.parseBoolean(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
