package de.marc.ganglife.playerEvents;

import de.marc.ganglife.commands.adutyCommand;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class cancelInteracts implements Listener {
    @EventHandler
    public void onBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player) {
            Player player = (Player) event.getRemover();
            if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting || event.getEntity() instanceof ArmorStand) {
                if (!adutyCommand.aduty.contains(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onInter(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!adutyCommand.aduty.contains(p) && shouldCancelInteraction(b.getType())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            if (!adutyCommand.aduty.contains(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Material clickedBlockType = event.getClickedBlock().getType();
            if (clickedBlockType == Material.ITEM_FRAME) {
                if (!adutyCommand.aduty.contains(event.getPlayer())) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof ItemFrame) {
            if (!adutyCommand.aduty.contains(event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
    private boolean shouldCancelInteraction(Material blockType) {
        Material[] blockedTypes = {
                Material.ANVIL,
                Material.CRAFTING_TABLE,
                Material.FURNACE,
                Material.BEACON,
                Material.ENCHANTING_TABLE,
                Material.HOPPER,
                Material.OAK_BUTTON,
                Material.STONE_BUTTON,
                Material.CHEST,
                Material.ARMOR_STAND,
                Material.CHISELED_BOOKSHELF,
                Material.SHULKER_BOX,
                Material.LECTERN,
                Material.BARREL,
                Material.ITEM_FRAME,
                Material.SHULKER_BOX
        };

        for (Material type : blockedTypes) {
            if (blockType == type) {
                return true;
            }
        }

        return false;
    }
}
