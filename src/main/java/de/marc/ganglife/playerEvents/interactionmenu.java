package de.marc.ganglife.playerEvents;

import de.marc.ganglife.Main.main;
import de.marc.ganglife.dataSetter.*;
import de.marc.ganglife.methods.isInteger;
import de.marc.ganglife.playerdatas.UPlayer;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class interactionmenu implements Listener {

    public static ArrayList<Player> cuff = new ArrayList<>();
    public static ArrayList<Player> writeMoneyGive = new ArrayList<>();
    public static ArrayList<Player> writeCocain = new ArrayList<>();
    public static ArrayList<Player> writeWeed = new ArrayList<>();
    public static ArrayList<Player> writeMeth = new ArrayList<>();
    public static ArrayList<Player> writeMedizin = new ArrayList<>();
    public static ArrayList<Player> writeBulletproof = new ArrayList<>();

    public static HashMap<Player, String> getHashTarget = new HashMap<>();
    private static final Map<Player, Integer> playerScheduler = new HashMap<>();

    @EventHandler
    public void onInt(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UPlayer uPlayer = UPlayer.getUPlayer(player.getUniqueId());

        if (!player.isSneaking()) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getRightClicked().getType().equals(EntityType.PLAYER)) return;
        if (uPlayer.isFFA()) return;
        if(uPlayer.getDeathTime() >= 1) return;
        if(cuff.contains(player)) return;
        Player target = (Player) event.getRightClicked();
        getHashTarget.put(player, target.getName());

        Gui interactionInventory = Gui.gui()
                .rows(5)
                .title(Component.text(main.prefix + "§7" + target.getName()))
                .disableAllInteractions()
                .create();

        GuiItem exit = ItemBuilder.from(Material.BARRIER).name(Component.text("§cExit"))
                .lore(Component.text(" §f▹ §7§oDas Interaktionsmenü verlassen.."))
                .asGuiItem(clickEvent -> {
                    main.playErrorSound(player);
                    player.closeInventory();
                });
        GuiItem giveMoney = ItemBuilder.from(Material.GOLD_NUGGET).name(Component.text("§aGeld übergeben"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " Geld geben.."))
                .asGuiItem(clickEvent -> {
                    player.sendMessage(main.prefix + "§7Gebe nun den Betrag an:");
                    player.closeInventory();
                    writeMoneyGive.add(player);
                    main.playProccessSound(player);
                });
        GuiItem cuffTarget = ItemBuilder.from(Material.LEGACY_LEASH).name(Component.text("§aPerson fesseln"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " fesseln anlegen.."))
                .asGuiItem(clickEvent -> {
                    boolean found = Arrays.stream(player.getInventory().getContents())
                            .filter(item -> item != null && item.getType() != Material.AIR)
                            .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.CUFFS.getItem().getItemMeta().getDisplayName()))
                            .anyMatch(item -> item.getType() == items.CUFFS.getItem().getType());
                    if(!found) {
                        player.sendMessage(main.pre_error + "§cDu hast keine Seile dabei.");
                        main.playErrorSound(player);
                        return;
                    }
                    cuffPlayers(player, target, items.CUFFS.getItem());
                    main.playProccessSound(player);
                    player.closeInventory();
                });
        GuiItem searchTarget = ItemBuilder.from(Material.SPIDER_EYE).name(Component.text("§aPerson durchsuchen"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " durchsuchen.."))
                .asGuiItem(clickEvent -> {
                    Bukkit.dispatchCommand(player, "durchsuchen " + target.getName());
                    player.closeInventory();
                });
        GuiItem showPersoOrLicence = ItemBuilder.from(Material.CLAY_BALL).name(Component.text("§aPersonalien oder Lizenzen zeigen"))
                .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen"))
                .asGuiItem(clickEvent -> {
                    main.playProccessSound(player);
                    player.closeInventory();
                });
        GuiItem giveItem = ItemBuilder.from(Material.GUNPOWDER).name(Component.text("§aItems geben"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " Items geben.."))
                .asGuiItem(clickEvent -> {
                    main.playProccessSound(player);
                    player.closeInventory();
                });
        GuiItem uncuffTarget = ItemBuilder.from(Material.LEGACY_LEASH).name(Component.text("§aPerson entfesseln"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " entfesseln.."))
                .asGuiItem(clickEvent -> {
                    Bukkit.dispatchCommand(player, "uncuff " + target.getName());
                    player.closeInventory();
                });
        GuiItem policeActions = ItemBuilder.from(Material.MAGMA_CREAM).name(Component.text("§aPolizei Aktionen"))
                .lore(Component.text(" §f▹ §7§oKlicke für mehr Informationen"))
                .asGuiItem(clickEvent -> {
                    main.playProccessSound(player);
                    player.closeInventory();
                });
        GuiItem policeCuffTarget = ItemBuilder.from(Material.BRICK).name(Component.text("§aPerson festnehmen"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " Handschellen anlegen.."))
                .asGuiItem(clickEvent -> {
                    boolean found = Arrays.stream(player.getInventory().getContents())
                            .filter(item -> item != null && item.getType() != Material.AIR)
                            .filter(item -> item.getItemMeta().getDisplayName().equalsIgnoreCase(items.POLICE_CUFF.getItem().getItemMeta().getDisplayName()))
                            .anyMatch(item -> item.getType() == items.POLICE_CUFF.getItem().getType());
                    if(!found) {
                        player.sendMessage(main.pre_error + "§cDu hast keine Handschellen dabei.");
                        main.playErrorSound(player);
                        return;
                    }
                    cuffPlayers(player, target, items.POLICE_CUFF.getItem());
                    player.closeInventory();
                    main.playProccessSound(player);
                });
        GuiItem policeUncuffTarget = ItemBuilder.from(Material.BRICK).name(Component.text("§aPerson freilassen"))
                .lore(Component.text(" §f▹ §7§o" + target.getName() + " freilassen.."))
                .asGuiItem(clickEvent -> {
                    Bukkit.dispatchCommand(player, "uncuff " + target.getName());
                    player.closeInventory();
                });

        if (cuff.contains(target)) {
            if (uPlayer.getFaction().equals("Polizei")) {
                interactionInventory.setItem(11, policeUncuffTarget);
            } else {
                interactionInventory.setItem(11, uncuffTarget);
            }
        } else {
            if (uPlayer.getFaction().equals("Polizei")) {
                interactionInventory.setItem(11, policeCuffTarget);
            } else {
                interactionInventory.setItem(11, cuffTarget);
            }
        }
        if (uPlayer.getFaction().equals("Polizei")) {
            interactionInventory.setItem(22, policeActions);
        }

        interactionInventory.setItem(4, exit);
        interactionInventory.setItem(15, giveMoney);
        interactionInventory.setItem(29, searchTarget);
        interactionInventory.setItem(33, showPersoOrLicence);
        interactionInventory.setItem(40, giveItem);
        interactionInventory.open(player);
    }

    public static void cuffPlayers(Player player, Player p2, ItemStack FesselnInInv) {
        if (!cuff.contains(p2)) {
            cuff.remove(p2);

            playerScheduler.put(player, Bukkit.getScheduler().scheduleSyncDelayedTask(main.getPlugin(), () -> {
                player.sendMessage(main.prefix + "§7Du hast erfolgreich §6" + p2.getName() + " §7gefesselt!");
                player.setWalkSpeed((float)0.2);
                ((CommandSender) p2).sendMessage(main.prefix + "§7Du wurdest von §6" + player.getName() + " §7gefesselt!");
                main.playProccessSound(player);
                main.playProccessSound(player);
                p2.setWalkSpeed(0);
                if (!cuff.contains(p2)) {
                    cuff.add((Player) p2);
                }
            }, 4 * 20L));

            player.sendMessage(main.prefix + "§7Du fesselt nun §6" + p2.getName() + "..");
            player.sendMessage(main.prefix + "§c§l! §cBewege dich nicht");

            ((CommandSender) p2).sendMessage(main.prefix + "§7Du wirst von §6" + player.getName() + " §7gefesselt..");
            ((CommandSender) p2).sendMessage(main.prefix + "§c§l! §cBewege dich nicht");

            player.setWalkSpeed(0);
            p2.setWalkSpeed(0);

            player.getInventory().removeItem(FesselnInInv);
            cuff.add((Player) p2);
            player.closeInventory();
        } else {
            player.sendMessage(main.pre_error + "§cDieser Spieler ist bereits oder wird gerade gefesselt!");
            main.playErrorSound(player);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String[] args = event.getMessage().split(" ");
        args = Arrays.copyOfRange(args, Math.min(1, args.length), args.length);

        if(!writeMoneyGive.contains(player)) return;

        if (writeMoneyGive.contains(player)) {
            Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                Bukkit.dispatchCommand(player, "pay " + getHashTarget.get(player) + " " + event.getMessage());
                getHashTarget.remove(player);
                writeMoneyGive.remove(player);
            });
        }
    }

}
