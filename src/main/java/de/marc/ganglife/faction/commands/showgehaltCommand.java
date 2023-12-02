package de.marc.ganglife.faction.commands;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.dataSetter.setFBank;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class showgehaltCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {

            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Gehälter zu einzusehen.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length != 0) {
                player.sendMessage(main.pre_error + "§cVerwendung: /showgehalt");
                main.playErrorSound(player);
                return true;
            }

            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
            if(uPlayer.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }
            if(uPlayer.getFactionRank() != 6) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            main.playProccessSound(player);
            sendGehaltToChat(player);


        } else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = Bukkit.getConsoleSender();

            if(args.length == 1) {
                sendGehaltToConsole(args[0]);
            } else {
                console.sendMessage(main.pre_error + "§cVerwendung: /showgehalt <Fraktion>");
            }
        }
        return false;
    }
    public static void sendGehaltToChat(Player player) {
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
        setFBank setFBank = new setFBank(main.getPlugin().getDatabaseAsync().getDataSource());

        setFBank.getFBankSalary(uPlayer.getFaction()).thenAccept(salary -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                String gehaltJSON = salary.get();
                Map<String, Object>[] gehalte = formatFromString(gehaltJSON);

                if (gehalte.length == 0) {
                    player.sendMessage(main.pre_error + "§cEs wurden keine Gehälter gefunden.");
                    main.playErrorSound(player);
                } else {
                    player.sendMessage(main.prefix + "§aAktuelle Gehälter:");

                    for (Map<String, Object> gehalt : gehalte) {
                        String name = (String) gehalt.get("rank");
                        String money = (String) gehalt.get("money");

                        String message = " §f▹ §7Rang: " + name + " §7- §6Gehalt: " + money;
                        player.sendMessage(" ");
                        player.sendMessage(message);
                    }
                }
            });
        });
    }

    public static void sendGehaltToConsole(String faction) {
        setFBank setFBank = new setFBank(main.getPlugin().getDatabaseAsync().getDataSource());
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        setFBank.getFBankSalary(faction).thenAccept(salary -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                String getGehalt = salary.get();

                String gehaltJSON = getGehalt;
                Map<String, Object>[] gehalte = formatFromString(gehaltJSON);

                if (gehalte.length == 0) {
                    console.sendMessage(main.pre_error + "§cEs wurden keine Gehälter gefunden.");
                } else {
                    console.sendMessage(main.prefix + "§aAktuelle Gehälter:");

                    for (Map<String, Object> gehalt : gehalte) {
                        String name = (String) gehalt.get("rank");
                        String money = (String) gehalt.get("money");

                        String message = " §f▹ §7Rang: " + name + " §7- §6Gehalt: " + money;
                        console.sendMessage(" ");
                        console.sendMessage(message);
                    }
                }
            });
        });
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
