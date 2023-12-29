package de.marc.ganglife.dataSetter;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum items {
    M4(Material.DIAMOND_HORSE_ARMOR, "§cM4A1", true),
    AK47(Material.IRON_HORSE_ARMOR, "§cAK-47", true),
    PDW(Material.STONE_HOE, "§9Einsatz-PDW", true),
    UZI(Material.GOLDEN_HORSE_ARMOR, "§cUzi", true),
    PISTOL(Material.WOODEN_SHOVEL, "§aPistole", true),
    RIFLE(Material.STONE_PICKAXE, "§7Jagdflinte", true),
    AWP(Material.IRON_HOE, "§9AWP", true),
    LASERGUN(Material.DIAMOND_HOE, "§bL§aA§cS§6E§4R§dG§eU§9N", true),
    COCAIN(Material.SUGAR, "§6Kokain", false),
    WEED(Material.BLAZE_POWDER, "§6Weed", false),
    METH(Material.ROTTEN_FLESH, "§6Meth", false),
    MEDIZIN(Material.GLOWSTONE_DUST, "§aVerbandskasten", false),
    CUFFS(Material.LEAD, "§fSeil", false),
    PHONE(Material.MUSIC_DISC_CAT, "§fHandy", false),
    BULLETPROOF(Material.IRON_CHESTPLATE, "§fSchutzweste", false),
    POLICE_BULLETPROOF(Material.GOLDEN_CHESTPLATE, "§9Polizei-Schutzweste", false),
    POLICE_HELMET(Material.GOLDEN_HELMET, "§9Polizei Helm", false),
    BULLETPROOF_CHEST(Material.SCUTE, "§fWestenkiste", false),
    CAGE(Material.BOWL, "§fFangkäfig", false),
    PICKAXE(Material.IRON_PICKAXE, "§fSpitzhacke", false),
    AXE(Material.IRON_AXE, "§fAxt", false),
    FROG(Material.PUMPKIN_SEEDS, "§fKröten", false),
    METH_CHEST(Material.FLINT, "§fKristallkiste", false),
    COCAINE_CHEST(Material.FEATHER, "§fPulverkiste", false),
    COCAINE_LEAVES(Material.MELON_SEEDS, "§fPulverblätter", false),
    WEED_CHEST(Material.NETHER_BRICK, "§fTabakkiste", false),
    WEED_LEAVES(Material.WHEAT_SEEDS, "§fTabakblätter", false),
    HOE(Material.WOODEN_HOE, "§fSichel", false),
    WOOD_LOG(Material.OAK_LOG, "§fHolz", false),
    IRON_NUGGET(Material.IRON_NUGGET, "§fEisenklumpen", false),
    WOOD_PLANKS(Material.OAK_PLANKS, "§fHolzpaletten", false),
    IRON_INGOT(Material.IRON_INGOT, "§fEisenbarren", false),
    POLICE_CUFF(Material.BRICK, "§fHandschelle", false),
    ARAMID(Material.STRING, "§fAramid", false),
    HOUSE_CONTRACT(Material.SLIME_BALL, "§fHausvertrag", false),
    BOX(Material.PRISMARINE_SHARD, "§fBox", false),
    DRILL(Material.INK_SAC, "§fBohrer", false),
    C4(Material.GLOW_INK_SAC, "§fSprengstoff", false),
    GOLD_INGOT(Material.GOLD_INGOT, "§fGoldbarren", false);

    private final ItemStack item;

    items(Material material, String displayName, boolean unbreakable) {
        this.item = ItemBuilder.from(material)
                .name(Component.text(displayName))
                .unbreakable(unbreakable)
                .build();
    }
    public ItemStack getItem() {
        return this.item.clone();
    }
    public static items getItem(ItemStack itemstack) {
        return Arrays.stream(items.values()).filter(item -> item.getItem().getType() == itemstack.getType()).filter(item -> item.getItem().getItemMeta().getDisplayName().equals(itemstack.getItemMeta().getDisplayName())).findFirst().orElse(null);
    }
}
