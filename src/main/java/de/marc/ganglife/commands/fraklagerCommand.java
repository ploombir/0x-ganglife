package de.marc.ganglife.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class fraklagerCommand implements CommandExecutor {

    static Inventory police_inv = null;
    static Inventory mafia_inv = null;
    static Inventory gang_inv = null;
    static Inventory medic_inv = null;

    static boolean isInPoliceLager = false;
    static boolean isInGangLager = false;
    static boolean isInMafiaLager = false;
    static boolean isInMedicLager = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            Location police_hq = new Location(player.getWorld(), 94, 67, -11);
            Location medic_hq = new Location(player.getWorld(), 9, 71, -162);
            Location mafia_hq = new Location(player.getWorld(), -303, 70, 139);
            Location gang_hq = new Location(player.getWorld(), 190, 66, -284);


        }

        return false;
    }
}
