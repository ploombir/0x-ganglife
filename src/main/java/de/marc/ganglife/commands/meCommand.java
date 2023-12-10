package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class meCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String msg = "";

            int BlockDistance = 10;
            Location playerLocation = player.getLocation();

            if (!playerLocation.getWorld().getName().equals("0xMain")) {
                player.sendMessage(main.pre_error + "§cDu kannst diesen Befehl nicht in dieser Welt ausführen.");
                main.playErrorSound(player);
                return true;
            }
            if (args.length <= 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /me <Aktion>");
                main.playErrorSound(player);
                return true;
            }

            for (int i = 0; i < args.length; i++) {
                msg = msg + " " + args[i];
            }
            for (Player pl : Bukkit.getOnlinePlayers()) {
                Location plLocation = pl.getLocation();
                if (plLocation.getWorld().equals(playerLocation.getWorld()) && plLocation.distance(playerLocation) <= BlockDistance) {
                    pl.sendMessage(" §a▹ " + player.getName() + "" + msg);
                }
            }
        }

        return false;
    }
}
