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
    SMG(Material.GOLDEN_HORSE_ARMOR, "§cUzi"),
    PISTOLE(Material.WOODEN_SHOVEL, "§aPistole"),
    JAGDFLINTE(Material.STONE_PICKAXE, "§7Jagdflinte"),
    SNIPER(Material.IRON_HOE, "§9AWP"),
    LASERGUN(Material.DIAMOND_HOE, "§bL§aA§cS§6E§4R§dG§eU§9N"),
    COCAIN(Material.SUGAR, "§6Kokain"),
    WEED(Material.BLAZE_POWDER, "§6Weed"),
    METH(Material.ROTTEN_FLESH, "§6Meth"),
    MEDIZIN(Material.GLOWSTONE_DUST, "§aVerbandskasten"),
    FESSELN_IN_INV(Material.LEAD, "§fSeil"),
    PHONE(Material.MUSIC_DISC_CAT, "§fHandy"),
    CHESTPLATE(Material.IRON_CHESTPLATE, "§fSchutzweste"),
    POLICE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, "§9Polizei-Schutzweste"),
    POLICE_HELMET(Material.GOLDEN_HELMET, "§9Polizei Helm"),
    CHESTPLATECHEST(Material.SCUTE, "§fWestenkiste"),
    FANGKÄFIG(Material.BOWL, "§fFangkäfig"),
    SPITZHACKE(Material.IRON_PICKAXE, "§fSpitzhacke"),
    AXT(Material.IRON_AXE, "§fAxt"),
    KRÖTEN(Material.PUMPKIN_SEEDS, "§fKröten"),
    METHCHEST(Material.FLINT, "§fKristallkiste"),
    COCAINCHEST(Material.FEATHER, "§fPulverkiste"),
    COCAINPAP(Material.MELON_SEEDS, "§fPulverblätter"),
    WEEDCHEST(Material.NETHER_BRICK, "§fTabakkiste"),
    WEEDPAP(Material.WHEAT_SEEDS, "§fTabakblätter"),
    SICHEL(Material.WOODEN_HOE, "§fSichel"),
    WOOD(Material.OAK_LOG, "§fHolz"),
    IRON(Material.IRON_NUGGET, "§fEisenklumpen"),
    WOOD_FINISH(Material.OAK_PLANKS, "§fHolzpaletten"),
    IRON_FINISH(Material.IRON_INGOT, "§fEisenbarren"),
    POLICE_CUFF(Material.BRICK, "§fHandschelle"),
    ARAMID(Material.STRING, "§fAramid"),
    HAUSVERTRAG(Material.SLIME_BALL, "§fHausvertrag"),
    BOX(Material.PRISMARINE_SHARD, "§fBox"),
    BOHRER(Material.INK_SAC, "§fBohrer"),
    SPRENGSTOFF(Material.GLOW_INK_SAC, "§fSprengstoff"),
    GOLD(Material.GOLD_INGOT, "§fGoldbarren");

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
