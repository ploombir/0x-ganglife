package de.marc.ganglife.commands;

import de.marc.ganglife.Main.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static de.marc.ganglife.commands.buildCommand.allowed;

public class adutyCommand implements CommandExecutor {
    public static ArrayList<Player> aduty = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (!player.hasPermission("system.aduty")) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }

            if (aduty.contains(player)) {
                aduty.remove(player);
                main.playProccessSound(player);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setGameMode(GameMode.SURVIVAL);
                allowed.remove(player);

                Bukkit.broadcast(Component.text(main.prefix + "§c" + player.getName() + " hat den Admin Modus verlassen."));
            } else {
                aduty.add(player);
                main.playProccessSound(player);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setGameMode(GameMode.CREATIVE);
                allowed.add(player);
                Bukkit.broadcast(Component.text(main.prefix + "§c" + player.getName() + " hat den Admin Modus betreten."));
                player.playEffect(player.getLocation().add(0.0D, 1.0D, 0.0D), Effect.ENDER_SIGNAL, 1);
            }
        }
        return false;
    }
}
