package surreal.contentcreator.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IHaloItem {
    ResourceLocation getLocation(World world, EntityPlayer player, ItemStack stack);
    int getSpread(World world, EntityPlayer player, ItemStack stack);
    int getColor(World world, EntityPlayer player, ItemStack stack);
}
