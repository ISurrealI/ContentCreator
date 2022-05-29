package surreal.contentcreator.functions.block;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("contentcreator.block.functions.IDrop")
public interface IBlockDropsFunc {
    IItemStack[] getDrops(IBlockAccess world, IBlockPos pos, IBlockState state, int fortune);
}
