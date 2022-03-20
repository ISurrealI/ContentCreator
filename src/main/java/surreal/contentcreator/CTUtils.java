package surreal.contentcreator;

import crafttweaker.api.enchantments.IEnchantmentDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class CTUtils {
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
}
