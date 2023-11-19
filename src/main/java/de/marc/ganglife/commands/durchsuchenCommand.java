package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.setDrugs;
import de.marc.ganglife.dataSetter.setFFA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class durchsuchenCommand implements CommandExecutor {

    private final setFFA setFFA = new setFFA(main.getPlugin().getDatabaseAsync().getDataSource());
    private final Map<Player, Integer> playerScheduler = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            Player target = Bukkit.getPlayer(args[0]);
            if(setFFA.isInFFA(player.getUniqueId(), "true")) return true;
            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /durchsuchen <Name>");
                main.playErrorSound(player);
                return true;
            }
            if(target == null) {
                player.sendMessage(main.notonline);
                main.playErrorSound(player);
                return true;
            }
            if(target == player) {
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber durchsuchen.");
                main.playErrorSound(player);
                return true;
            }

        }

        return false;
    }
}
