package de.marc.ganglife.phone.events;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.items;
import de.marc.ganglife.phone.commands.callCommand;
import de.marc.ganglife.playerdatas.UPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

public class callManager implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Player target = callCommand.phoneCaller.get(player);

        String[] args = e.getMessage().split(" ");
        args = Arrays.copyOfRange(args, Math.min(1, args.length), args.length);

        boolean foundPlayer = Arrays.stream(player.getInventory().getContents())
                .filter(item -> item != null && item.getType() != Material.AIR)
                .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

        boolean foundTarget = Arrays.stream(target.getInventory().getContents())
                .filter(item -> item != null && item.getType() != Material.AIR)
                .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.PHONE.getItem().getItemMeta().getDisplayName()))
                .anyMatch(item -> item.getType() == items.PHONE.getItem().getType());

        if(target == null) return;

        if (callCommand.phoneIsInCall.contains(player) && callCommand.phoneIsInCall.contains(target)) {
            if(target.isOnline()) {
                if(foundTarget && foundPlayer) {
                    Bukkit.getScheduler().runTask(main.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());
                            UPlayer uTarget = UPlayer.getUPlayer(target.getUniqueId());

                            player.sendMessage("§bTELEFONAT mit §e" + uTarget.getPhoneNumber() + " §7(" +
                                    de.marc.ganglife.methods.phone.findContactNameByNumber(player, String.valueOf(uTarget.getPhoneNumber())) +
                                    ") §bDU §f▹ §6" + e.getMessage());

                            target.sendMessage("§bTELEFONAT mit §e" + uPlayer.getPhoneNumber() + " §7(" +
                                    de.marc.ganglife.methods.phone.findContactNameByNumber(target, String.valueOf(uPlayer.getPhoneNumber())) +
                                    ") §f▹ §e" + e.getMessage());
                        }
                    });
                } else {
                    player.sendMessage(main.pre_error + "§cDiese Nummer ist nicht mehr erreichbar. Der Anruf wurde abgebrochen.");
                    target.sendMessage(main.pre_error + "§cDer Anruf wurde abebrochen, da du kein Handy mehr dabei hast.");
                    main.playErrorSound(player);
                    main.playErrorSound(target);
                    callCommand.phoneIsInCall.remove(player);
                    callCommand.phoneIsInCall.remove(target);
                }
            } else {
                player.sendMessage(main.pre_error + "§cDiese Nummer ist nicht mehr erreichbar. Der Anruf wurde abgebrochen.");
                main.playErrorSound(player);
                callCommand.phoneIsInCall.remove(player);
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Player target = callCommand.phoneCaller.get(player);

        if(callCommand.phoneIsInCall.contains(player)) {
            target.sendMessage(main.pre_error + "§cDiese Nummer ist nicht mehr erreichbar. Der Anruf wurde abgebrochen.");
            main.playErrorSound(target);
            callCommand.phoneIsInCall.remove(target);
        }
    }
}
