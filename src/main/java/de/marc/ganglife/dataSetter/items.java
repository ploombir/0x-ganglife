package de.marc.ganglife.dataSetter;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class items {

    public static ItemStack m4 = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR)
            .unbreakable(true)
            .name(Component.text("§cM4A1"))
            .build();

    public static ItemStack ak47 = ItemBuilder.from(Material.IRON_HORSE_ARMOR)
            .unbreakable(true)
            .name(Component.text("§cAK-47"))
            .build();

    public static ItemStack pdw = ItemBuilder.from(Material.STONE_HOE)
            .unbreakable(true)
            .name(Component.text("§9Einsatz-PDW"))
            .build();

    public static ItemStack smg = ItemBuilder.from(Material.GOLDEN_HORSE_ARMOR)
            .unbreakable(true)
            .name(Component.text("§cUzi"))
            .build();

    public static ItemStack pistole = ItemBuilder.from(Material.WOODEN_SHOVEL)
            .unbreakable(true)
            .name(Component.text("§aPistole"))
            .build();

    public static ItemStack jagdflinte = ItemBuilder.from(Material.STONE_PICKAXE)
            .unbreakable(true)
            .name(Component.text("§7Jagdflinte"))
            .build();

    public static ItemStack sniper = ItemBuilder.from(Material.IRON_HOE)
            .unbreakable(true)
            .name(Component.text("§9AWP"))
            .build();

    public static ItemStack lasergun = ItemBuilder.from(Material.DIAMOND_HOE)
            .unbreakable(true)
            .name(Component.text("§bL§aA§cS§6E§4R§dG§eU§9N"))
            .build();

    public static ItemStack cocain = ItemBuilder.from(Material.SUGAR)
            .name(Component.text("§6Kokain"))
            .lore(Component.text("§7Übersicht: /inv"))
            .build();

    public static ItemStack weed = ItemBuilder.from(Material.BLAZE_POWDER)
            .name(Component.text("§6Weed"))
            .lore(Component.text("§7Übersicht: /inv"))
            .build();

    public static ItemStack meth = ItemBuilder.from(Material.ROTTEN_FLESH)
            .name(Component.text("§6Meth"))
            .lore(Component.text("§7Übersicht: /inv"))
            .build();

    public static ItemStack medizin = ItemBuilder.from(Material.GLOWSTONE_DUST)
            .name(Component.text("§aVerbandskasten"))
            .lore(Component.text("§7Übersicht: /inv"))
            .build();

    public static ItemStack FesselnInInv = ItemBuilder.from(Material.LEAD)
            .name(Component.text("§fSeil"))
            .build();

    public static ItemStack phone = ItemBuilder.from(Material.MUSIC_DISC_CAT)
            .name(Component.text("§fHandy"))
            .build();

    public static ItemStack chestplate = ItemBuilder.from(Material.IRON_CHESTPLATE)
            .name(Component.text("§fSchutzweste"))
            .build();

    public static ItemStack police_chestplate = ItemBuilder.from(Material.GOLDEN_CHESTPLATE)
            .name(Component.text("§9Polizei-Schutzweste"))
            .build();

    public static ItemStack police_helmet = ItemBuilder.from(Material.GOLDEN_HELMET)
            .name(Component.text("§9Polizei Helm"))
            .build();

    public static ItemStack chestplatechest = ItemBuilder.from(Material.SCUTE)
            .name(Component.text("§fWestenkiste"))
            .build();

    public static ItemStack fangkäfig = ItemBuilder.from(Material.BOWL)
            .name(Component.text("§fFangkäfig"))
            .build();

    public static ItemStack spitzhacke = ItemBuilder.from(Material.IRON_PICKAXE)
            .name(Component.text("§fSpitzhacke"))
            .build();

    public static ItemStack axt = ItemBuilder.from(Material.IRON_AXE)
            .name(Component.text("§fAxt"))
            .build();

    public static ItemStack kröten = ItemBuilder.from(Material.PUMPKIN_SEEDS)
            .name(Component.text("§fKröten"))
            .build();

    public static ItemStack methchest = ItemBuilder.from(Material.FLINT)
            .name(Component.text("§fKristallkiste"))
            .build();

    public static ItemStack cocainchest = ItemBuilder.from(Material.FEATHER)
            .name(Component.text("§fPulverkiste"))
            .build();

    public static ItemStack cocainpap = ItemBuilder.from(Material.MELON_SEEDS)
            .name(Component.text("§fPulverblätter"))
            .build();

    // Weitere Gegenstände folgen hier in ähnlichem Format

    public static ItemStack weedchest = ItemBuilder.from(Material.NETHER_BRICK)
            .name(Component.text("§fTabakkiste"))
            .build();

    public static ItemStack weedpap = ItemBuilder.from(Material.WHEAT_SEEDS)
            .name(Component.text("§fTabakblätter"))
            .build();

    public static ItemStack sichel = ItemBuilder.from(Material.WOODEN_HOE)
            .unbreakable(true)
            .name(Component.text("§fSichel"))
            .build();

    public static ItemStack wood = ItemBuilder.from(Material.OAK_LOG)
            .name(Component.text("§fHolz"))
            .build();

    public static ItemStack iron = ItemBuilder.from(Material.IRON_NUGGET)
            .name(Component.text("§fEisenklumpen"))
            .build();

    public static ItemStack wood_finish = ItemBuilder.from(Material.OAK_PLANKS)
            .name(Component.text("§fHolzpaletten"))
            .build();

    public static ItemStack iron_finish = ItemBuilder.from(Material.IRON_INGOT)
            .name(Component.text("§fEisenbarren"))
            .build();

    public static ItemStack police_cuff = ItemBuilder.from(Material.BRICK)
            .name(Component.text("§fHandschelle"))
            .build();

    public static ItemStack aramid = ItemBuilder.from(Material.STRING)
            .name(Component.text("§fAramid"))
            .build();

    public static ItemStack hausvertrag = ItemBuilder.from(Material.SLIME_BALL)
            .name(Component.text("§fHausvertrag"))
            .build();

    public static ItemStack box = ItemBuilder.from(Material.PRISMARINE_SHARD)
            .name(Component.text("§fBox"))
            .build();

    public static ItemStack bohrer = ItemBuilder.from(Material.INK_SAC)
            .name(Component.text("§fBohrer"))
            .build();

    public static ItemStack sprengstoff = ItemBuilder.from(Material.GLOW_INK_SAC)
            .name(Component.text("§fSprengstoff"))
            .build();

    public static ItemStack gold = ItemBuilder.from(Material.GOLD_INGOT)
            .name(Component.text("§fGoldbarren"))
            .build();
}
