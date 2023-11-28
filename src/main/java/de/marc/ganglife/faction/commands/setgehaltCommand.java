package de.marc.ganglife.faction.commands;

import com.google.gson.Gson;
import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.setFBank;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class setgehaltCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length != 2) {
                player.sendMessage(main.pre_error + "§cVerwendung: /setgehalt <Rang> <Gehalt>");
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

            String rank = args[0];
            String gehalt = args[1];

            if(!isInteger.isInt(rank)) {
                player.sendMessage(main.pre_error + "§cDer Rang muss eine Zahl sein.");
                main.playErrorSound(player);
                return true;
            }
            if(!isInteger.isInt(gehalt)) {
                player.sendMessage(main.pre_error + "§cDer Rang muss eine Zahl sein.");
                main.playErrorSound(player);
                return true;
            }

            if(Integer.parseInt(rank) > 6 || Integer.parseInt(rank) < 1) {
                player.sendMessage(main.pre_error + "§cBitte gebe einen Rang von 1 bis 6 an.");
                main.playErrorSound(player);
                return true;
            }

            setGehalt(uPlayer.getFaction(), rank, gehalt);
            player.sendMessage(main.prefix + "§aDu hast das Gehalt von Rang §6" + rank + " §aauf §6" + gehalt + " §agesetzt.");
            main.playProccessSound(player);

        } else if(sender instanceof ConsoleCommandSender console) {
            setGehalt(args[0], args[1], args[2]);
        }

        return false;
    }

    public static void setGehalt(String faction, String rank, String gehalt) {
        setFBank setFBank = new setFBank(main.getPlugin().getDatabaseAsync().getDataSource());

        setFBank.getFBankSalary(faction).thenAccept(salary -> {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                String existingGehalt = salary.get();

                Map<String, Object>[] formattedGehalt = formatFromString(existingGehalt);

                boolean entryExists = false;
                for (Map<String, Object> entry : formattedGehalt) {
                    if (rank.equals(String.valueOf(entry.get("rank")))) {
                        entry.put("money", gehalt);
                        entryExists = true;
                        break;
                    }
                }

                if (!entryExists) {
                    Map<String, Object> contact = new HashMap<>();
                    contact.put("money", gehalt);
                    contact.put("rank", rank);

                    formattedGehalt = Arrays.copyOf(formattedGehalt, formattedGehalt.length + 1);
                    formattedGehalt[formattedGehalt.length - 1] = contact;
                }

                String updatedGehalt = new Gson().toJson(formattedGehalt);

                setFBank.setFBankSalary(faction, updatedGehalt);
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
