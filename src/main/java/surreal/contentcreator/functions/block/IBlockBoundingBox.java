package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IBoundingBox")
public interface IBlockBoundingBox {
    IAxisAlignedBB getBoundingBox(IBlockAccess world, IBlockPos pos, IBlockState state);
}
