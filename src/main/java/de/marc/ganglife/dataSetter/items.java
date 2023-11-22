package de.marc.ganglife.dataSetter;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum items {
    M4(Material.DIAMOND_HORSE_ARMOR, "§cM4A1"),
    AK47(Material.IRON_HORSE_ARMOR, "§cAK-47"),
    PDW(Material.STONE_HOE, "§9Einsatz-PDW"),
    UZI(Material.GOLDEN_HORSE_ARMOR, "§cUzi"),
    PISTOL(Material.WOODEN_SHOVEL, "§aPistole"),
    RIFLE(Material.STONE_PICKAXE, "§7Jagdflinte"),
    AWP(Material.IRON_HOE, "§9AWP"),
    LASERGUN(Material.DIAMOND_HOE, "§bL§aA§cS§6E§4R§dG§eU§9N"),
    COCAIN(Material.SUGAR, "§6Kokain"),
    WEED(Material.BLAZE_POWDER, "§6Weed"),
    METH(Material.ROTTEN_FLESH, "§6Meth"),
    MEDIZIN(Material.GLOWSTONE_DUST, "§aVerbandskasten"),
    CUFFS(Material.LEAD, "§fSeil"),
    PHONE(Material.MUSIC_DISC_CAT, "§fHandy"),
    BULLETPROOF(Material.IRON_CHESTPLATE, "§fSchutzweste"),
    POLICE_BULLETPROOF(Material.GOLDEN_CHESTPLATE, "§9Polizei-Schutzweste"),
    POLICE_HELMET(Material.GOLDEN_HELMET, "§9Polizei Helm"),
    BULLETPROOF_CHEST(Material.SCUTE, "§fWestenkiste"),
    CAGE(Material.BOWL, "§fFangkäfig"),
    PICKAXE(Material.IRON_PICKAXE, "§fSpitzhacke"),
    AXE(Material.IRON_AXE, "§fAxt"),
    FROG(Material.PUMPKIN_SEEDS, "§fKröten"),
    METH_CHEST(Material.FLINT, "§fKristallkiste"),
    COCAINE_CHEST(Material.FEATHER, "§fPulverkiste"),
    COCAINE_LEAVES(Material.MELON_SEEDS, "§fPulverblätter"),
    WEED_CHEST(Material.NETHER_BRICK, "§fTabakkiste"),
    WEED_LEAVES(Material.WHEAT_SEEDS, "§fTabakblätter"),
    HOE(Material.WOODEN_HOE, "§fSichel"),
    WOOD_LOG(Material.OAK_LOG, "§fHolz"),
    IRON_NUGGET(Material.IRON_NUGGET, "§fEisenklumpen"),
    WOOD_PLANKS(Material.OAK_PLANKS, "§fHolzpaletten"),
    IRON_INGOT(Material.IRON_INGOT, "§fEisenbarren"),
    POLICE_CUFF(Material.BRICK, "§fHandschelle"),
    ARAMID(Material.STRING, "§fAramid"),
    HOUSE_CONTRACT(Material.SLIME_BALL, "§fHausvertrag"),
    BOX(Material.PRISMARINE_SHARD, "§fBox"),
    DRILL(Material.INK_SAC, "§fBohrer"),
    C4(Material.GLOW_INK_SAC, "§fSprengstoff"),
    GOLD_INGOT(Material.GOLD_INGOT, "§fGoldbarren");

    private final ItemStack item;

    items(Material material, String displayName) {
        this.item = ItemBuilder.from(material)
                .name(Component.text(displayName))
                .unbreakable(true)
                .build();
    }
    public ItemStack getItem() {
        return this.item.clone();
    }
    public static items getItem(ItemStack itemstack) {
        return Arrays.stream(items.values()).filter(item -> item.getItem().getType() == itemstack.getType()).filter(item -> item.getItem().getItemMeta().getDisplayName().equals(itemstack.getItemMeta().getDisplayName())).findFirst().orElse(null);
    }
}
