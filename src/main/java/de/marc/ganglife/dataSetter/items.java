package de.marc.ganglife.dataSetter;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class items {

    public class ItemSetup {

        public static GuiItem m4 = ItemBuilder.from(Material.DIAMOND_HORSE_ARMOR)
                .unbreakable(true)
                .name(Component.text("§cM4A1"))
                .asGuiItem();

        public static GuiItem ak47 = ItemBuilder.from(Material.IRON_HORSE_ARMOR)
                .unbreakable(true)
                .name(Component.text("§cAK-47"))
                .asGuiItem();

        public static GuiItem pdw = ItemBuilder.from(Material.STONE_HOE)
                .unbreakable(true)
                .name(Component.text("§9Einsatz-PDW"))
                .asGuiItem();

        public static GuiItem smg = ItemBuilder.from(Material.GOLDEN_HORSE_ARMOR)
                .unbreakable(true)
                .name(Component.text("§cUzi"))
                .asGuiItem();

        public static GuiItem pistole = ItemBuilder.from(Material.WOODEN_SHOVEL)
                .unbreakable(true)
                .name(Component.text("§aPistole"))
                .asGuiItem();

        public static GuiItem jagdflinte = ItemBuilder.from(Material.STONE_PICKAXE)
                .unbreakable(true)
                .name(Component.text("§7Jagdflinte"))
                .asGuiItem();

        public static GuiItem sniper = ItemBuilder.from(Material.IRON_HOE)
                .unbreakable(true)
                .name(Component.text("§9AWP"))
                .asGuiItem();

        public static GuiItem lasergun = ItemBuilder.from(Material.DIAMOND_HOE)
                .unbreakable(true)
                .name(Component.text("§bL§aA§cS§6E§4R§dG§eU§9N"))
                .asGuiItem();

        public static GuiItem cocain = ItemBuilder.from(Material.SUGAR)
                .name(Component.text("§6Kokain"))
                .lore(Component.text("§7Übersicht: /inv"))
                .asGuiItem();

        public static GuiItem weed = ItemBuilder.from(Material.BLAZE_POWDER)
                .name(Component.text("§6Weed"))
                .lore(Component.text("§7Übersicht: /inv"))
                .asGuiItem();

        public static GuiItem meth = ItemBuilder.from(Material.ROTTEN_FLESH)
                .name(Component.text("§6Meth"))
                .lore(Component.text("§7Übersicht: /inv"))
                .asGuiItem();

        public static GuiItem medizin = ItemBuilder.from(Material.GLOWSTONE_DUST)
                .name(Component.text("§aVerbandskasten"))
                .lore(Component.text("§7Übersicht: /inv"))
                .asGuiItem();

        public static GuiItem FesselnInInv = ItemBuilder.from(Material.LEAD)
                .name(Component.text("§fSeil"))
                .asGuiItem();

        public static GuiItem phone = ItemBuilder.from(Material.MUSIC_DISC_CAT)
                .name(Component.text("§fHandy"))
                .asGuiItem();

        public static GuiItem chestplate = ItemBuilder.from(Material.IRON_CHESTPLATE)
                .name(Component.text("§fSchutzweste"))
                .asGuiItem();

        public static GuiItem police_chestplate = ItemBuilder.from(Material.GOLDEN_CHESTPLATE)
                .name(Component.text("§9Polizei-Schutzweste"))
                .asGuiItem();

        public static GuiItem police_helmet = ItemBuilder.from(Material.GOLDEN_HELMET)
                .name(Component.text("§9Polizei Helm"))
                .asGuiItem();

        public static GuiItem chestplatechest = ItemBuilder.from(Material.SCUTE)
                .name(Component.text("§fWestenkiste"))
                .asGuiItem();

        // Weitere Gegenstände folgen hier in ähnlichem Format

        public static GuiItem fangkäfig = ItemBuilder.from(Material.BOWL)
                .name(Component.text("§fFangkäfig"))
                .asGuiItem();

        public static GuiItem spitzhacke = ItemBuilder.from(Material.IRON_PICKAXE)
                .name(Component.text("§fSpitzhacke"))
                .asGuiItem();

        public static GuiItem axt = ItemBuilder.from(Material.IRON_AXE)
                .name(Component.text("§fAxt"))
                .asGuiItem();

        public static GuiItem kröten = ItemBuilder.from(Material.PUMPKIN_SEEDS)
                .name(Component.text("§fKröten"))
                .asGuiItem();

        public static GuiItem methchest = ItemBuilder.from(Material.FLINT)
                .name(Component.text("§fKristallkiste"))
                .asGuiItem();

        public static GuiItem cocainchest = ItemBuilder.from(Material.FEATHER)
                .name(Component.text("§fPulverkiste"))
                .asGuiItem();

        public static GuiItem cocainpap = ItemBuilder.from(Material.MELON_SEEDS)
                .name(Component.text("§fPulverblätter"))
                .asGuiItem();

        // Weitere Gegenstände folgen hier in ähnlichem Format

        public static GuiItem weedchest = ItemBuilder.from(Material.NETHER_BRICK)
                .name(Component.text("§fTabakkiste"))
                .asGuiItem();

        public static GuiItem weedpap = ItemBuilder.from(Material.WHEAT_SEEDS)
                .name(Component.text("§fTabakblätter"))
                .asGuiItem();

        public static GuiItem sichel = ItemBuilder.from(Material.WOODEN_HOE)
                .unbreakable(true)
                .name(Component.text("§fSichel"))
                .asGuiItem();

        public static GuiItem wood = ItemBuilder.from(Material.OAK_LOG)
                .name(Component.text("§fHolz"))
                .asGuiItem();

        public static GuiItem iron = ItemBuilder.from(Material.IRON_NUGGET)
                .name(Component.text("§fEisenklumpen"))
                .asGuiItem();

        public static GuiItem wood_finish = ItemBuilder.from(Material.OAK_PLANKS)
                .name(Component.text("§fHolzpaletten"))
                .asGuiItem();

        public static GuiItem iron_finish = ItemBuilder.from(Material.IRON_INGOT)
                .name(Component.text("§fEisenbarren"))
                .asGuiItem();

        public static GuiItem police_cuff = ItemBuilder.from(Material.BRICK)
                .name(Component.text("§fHandschelle"))
                .asGuiItem();

        public static GuiItem aramid = ItemBuilder.from(Material.STRING)
                .name(Component.text("§fAramid"))
                .asGuiItem();

        public static GuiItem hausvertrag = ItemBuilder.from(Material.SLIME_BALL)
                .name(Component.text("§fHausvertrag"))
                .asGuiItem();

        public static GuiItem box = ItemBuilder.from(Material.PRISMARINE_SHARD)
                .name(Component.text("§fBox"))
                .asGuiItem();

        public static GuiItem bohrer = ItemBuilder.from(Material.INK_SAC)
                .name(Component.text("§fBohrer"))
                .asGuiItem();

        public static GuiItem sprengstoff = ItemBuilder.from(Material.GLOW_INK_SAC)
                .name(Component.text("§fSprengstoff"))
                .asGuiItem();

        public static GuiItem gold = ItemBuilder.from(Material.GOLD_INGOT)
                .name(Component.text("§fGoldbarren"))
                .asGuiItem();
        }
    }
