package de.marc.ganglife.faction.commands;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.faction.methods.factionPrefixes;
import de.marc.ganglife.faction.methods.sendFactionMessage;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public class inviteCommand implements CommandExecutor {

    public static HashMap<Player, String> invitedFaction = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

            boolean found = Arrays.stream(player.getInventory().getContents())
                    .filter(item -> item != null && item.getType() != Material.AIR)
                    .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                    .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

            if(!found) {
                player.sendMessage(main.pre_error + "§cDu benötigst ein Handy, um Fraktionen zu verwalten.");
                main.playErrorSound(player);
                return true;
            }

            if(args.length != 1) {
                player.sendMessage(main.pre_error + "§cVerwendung: /invite <Spieler>");
                main.playErrorSound(player);
                return true;
            }

            if(uPlayer.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDu bist in keiner Fraktion.");
                main.playErrorSound(player);
                return true;
            }
            if(uPlayer.getFactionRank() < 5) {
                player.sendMessage(main.noperms);
                main.playErrorSound(player);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage(main.notonline);
                main.playErrorSound(player);
                return true;
            }
            if(player == target) {
                player.sendMessage(main.pre_error + "§cDu kannst dich nicht selber in deine Fraktion einladen.");
                main.playErrorSound(player);
                return true;
            }
            UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());

            if(!uTarget.getFaction().equals("Zivilist")) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist bereits in einer Fraktion.");
                main.playErrorSound(player);
                return true;
            }

            if(player.getLocation().distance(target.getLocation()) >= 3) {
                player.sendMessage(main.pre_error + "§cDieser Spieler ist nicht in deiner nähe.");
                main.playErrorSound(player);
                return true;
            }


            invitedFaction.put(target, uPlayer.getFaction());

            Gui inviteInventory = Gui.gui()
                    .rows(3)
                    .title(Component.text(main.prefix + factionPrefixes.getPrefix(player) + invitedFaction.get(target)))
                    .disableAllInteractions()
                    .create();


            player.sendMessage(main.prefix + "§7Du hast §6" + target.getName() + " §7in die Fraktion " + factionPrefixes.getPrefix(player)
                    + uPlayer.getFaction() + " §7eingeladen.");
            main.playProccessSound(player);
            main.playProccessSound(target);


            GuiItem inviteGUI = ItemBuilder.from(Material.GREEN_CONCRETE)
                    .name(Component.text("§aFraktion beitreten.."))
                    .lore(Component.text(" §f▹ §7§oKlicke um die Einladung anzunehmen."))
                    .asGuiItem(clickEvent -> {
                        uTarget.setFaction(invitedFaction.get(target));
                        uTarget.setFactionRank(1);
                        target.sendMessage(main.prefix + "§7Du bist der Fraktion " + factionPrefixes.getPrefix(player) + invitedFaction.get(target) + " §7beigetreten.");
                        sendFactionMessage.sendPlayerFactionMessage(player, target.getName() + " ist der Fraktion beigetreten.");
                        main.playSuccessSound(player);
                        main.playSuccessSound(target);
                        target.closeInventory();
                    });

            inviteInventory.setCloseGuiAction(close -> {
                if(!uTarget.getFaction().equals("Zivilist")) {
                    return;
                }
                player.sendMessage(main.prefix + "§6" + target.getName() + " §7hat die Einladung §cabgelehnt.");
                target.sendMessage(main.prefix + "§7Du hast die Einladung §cabgelehnt.");
                main.playErrorSound(player);
                main.playErrorSound(target);
                invitedFaction.remove(target);
            });

            inviteInventory.setItem(13, inviteGUI);

            inviteInventory.open(target);
         }
        return false;
    }
}
