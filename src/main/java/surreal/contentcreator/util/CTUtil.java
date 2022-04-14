package surreal.contentcreator.util;

import crafttweaker.api.enchantments.IEnchantmentDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

            for (int meta = 0; meta < Math.min(item.METAITEMS.size(), Short.MAX_VALUE-1); meta++) {
                String name = item.METAITEMS.get(meta).unlocName;

                if (name == null) name = "" + meta;

                map.put(itemName + ":" + name, new ItemStack(item, 1, meta));
            }
        }

        return map;
    }
}
