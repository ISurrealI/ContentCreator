package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IDestroyCreative")
public interface IItemDestroyCreative {
    boolean canDestroyBlockInCreative(IWorld world, IBlockPos pos, IItemStack stack, IPlayer player);
}
