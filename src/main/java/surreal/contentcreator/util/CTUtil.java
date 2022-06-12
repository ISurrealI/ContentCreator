package surreal.contentcreator.util;

import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IRandom;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.mc1120.enchantments.MCEnchantmentDefinition;
import crafttweaker.mc1120.entity.MCEntityEquipmentSlot;
import crafttweaker.mc1120.entity.MCEntityItem;
import crafttweaker.mc1120.util.MCRandom;
import crafttweaker.mc1120.world.MCBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.SubItem;
import surreal.contentcreator.proxy.CommonProxy;

import java.util.*;

public class CTUtil {
    public static List<ItemStack> getStacks(Item item) {
        NonNullList<ItemStack> list = NonNullList.create();

        item.getSubItems(CreativeTabs.SEARCH, list);
        return list;
    }

    public static Map<String, ItemStack> getMap() {
        Map<String, ItemStack> map = new HashMap<>();

        for (ItemBase item : CommonProxy.ITEMS) {
            String itemName = Objects.requireNonNull(item.getRegistryName()).getResourcePath();

            if (item.SUBITEMS.size() > 1) {
                for (int meta = 0; meta < Math.min(item.SUBITEMS.size(), Short.MAX_VALUE-1); meta++) {
                    SubItem subItem = item.SUBITEMS.get(meta);
                    String name = subItem.UNLOCNAME != null ? subItem.UNLOCNAME.getUnlocalizedName(CraftTweakerMC.getIItemStack(new ItemStack(item, 1, meta))) : "" + meta;
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

    public static EnumAction getAction(String name) {
        for (EnumAction action : EnumAction.values()) {
            if (action.name().equalsIgnoreCase(name)) return action;
        }

        return EnumAction.NONE;
    }

    public static IBlockAccess getIBlockAccess(net.minecraft.world.IBlockAccess world) {
        return new MCBlockAccess(world);
    }

    public static IRandom getRandom(Random random) {
        return new MCRandom(random);
    }

    public static IEnchantmentDefinition getEnchantment(Enchantment enchantment) {
        return new MCEnchantmentDefinition(enchantment);
    }

    public static EnumHand getHand(IEntityEquipmentSlot equipmentSlot) {
        switch (equipmentSlot.getIndex()) {
            case 0: return EnumHand.MAIN_HAND;
            case 1: return EnumHand.OFF_HAND;
        }

        return EnumHand.MAIN_HAND;
    }

    public static IEntityEquipmentSlot getHandEquipment(EnumHand hand) {
        return new MCEntityEquipmentSlot(EntityEquipmentSlot.values()[hand.ordinal()]);
    }

    public static class MCEntityItemMutable extends MCEntityItem {
        EntityItem entityItem;

        public MCEntityItemMutable(EntityItem entityItem) {
            super(entityItem);
            this.entityItem = entityItem;
        }

        @Override
        public IItemStack getItem() {
            return CraftTweakerMC.getIItemStackMutable(entityItem.getItem());
        }
    }
}
