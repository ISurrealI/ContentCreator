package surreal.contentcreator.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IHaloItem {
    ResourceLocation getLocation(World world, EntityPlayer player, ItemStack stack);
    int getSpread(World world, EntityPlayer player, ItemStack stack);
    int getColor(World world, EntityPlayer player, ItemStack stack);
    boolean shouldPulse(World world, EntityPlayer player, ItemStack stack);

    static String getHaloTexture(int halo) {
        switch (halo) {
            default: return "avaritia:items/halo";
            case 1: return "avaritia:items/halo128";
            case 2: return "avaritia:items/halonoise";
        }
    }
}
