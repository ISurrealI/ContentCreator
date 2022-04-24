package surreal.contentcreator.util;

import crafttweaker.api.enchantments.IEnchantmentDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import surreal.contentcreator.common.item.ItemBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTUtil {
    public static Enchantment[] getEnchantments(IEnchantmentDefinition[] enchantments) {
        Enchantment[] enchs = new Enchantment[enchantments.length];

        for (int i = 0; i < enchantments.length; i++) {
            enchs[i] = (Enchantment) enchantments[i].getInternal();
        }

        return enchs;
    }

    public static List<ItemStack> getStacks(Item item) {
        NonNullList<ItemStack> list = NonNullList.create();

        item.getSubItems(CreativeTabs.SEARCH, list);
        return list;
    }

    public static Map<String, ItemStack> getMap() {
        Map<String, ItemStack> map = new HashMap<>();

        for (ItemBase item : ItemBase.ITEMS) {
            String itemName = item.getRegistryName().getResourcePath();

            if (item.METAITEMS.size() > 1) {
                for (int meta = 0; meta < Math.min(item.METAITEMS.size(), Short.MAX_VALUE-1); meta++) {
                    String name = item.METAITEMS.get(meta).unlocName;

                    if (name == null) name = "" + meta;

                    map.put(itemName + ":" + name, new ItemStack(item, 1, meta));
                }
            } else {
                map.put(itemName, new ItemStack(item));
            }
        }

        return map;
    }

    public static EnumRarity getRarity(String name) {
        for (EnumRarity rarity : EnumRarity.values()) {
            if (rarity.name().equalsIgnoreCase(name)) return rarity;
        }

        return EnumRarity.COMMON;
    }

    public static SoundEvent getSound(String name) {
        for (SoundEvent event : SoundEvent.REGISTRY) {
            if (event.getSoundName().toString().equalsIgnoreCase(name)) return event;
        }

        return null;
    }
}
