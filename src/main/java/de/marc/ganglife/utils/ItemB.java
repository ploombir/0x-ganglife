package de.marc.ganglife.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemB {
    private ItemStack istack;

    private ItemMeta imeta;

    public ItemB(Material material, short subID) {
        this.istack = new ItemStack(material, 1, subID);
        this.imeta = this.istack.getItemMeta();
    }

    public ItemB(Material material) {
        this(material, (short)0);
    }

    public ItemB setName(String name) {
        this.imeta.setDisplayName(name);
        return this;
    }

    public ItemB setDurability(short durability) {
        this.istack.setDurability(durability);
        return this;
    }

    public ItemB setLore(String lore) {
        this.imeta.setLore(Arrays.asList(new String[]{lore}));
        return this;
    }

    public ItemB setAmount(int amount) {
        this.istack.setAmount(amount);
        return this;
    }

    public ItemB setUnbreakable(boolean unbreakable) {
        this.imeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemB addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        this.istack.getItemMeta().addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemStack build() {
        this.istack.setItemMeta(this.imeta);
        return this.istack;
    }
}