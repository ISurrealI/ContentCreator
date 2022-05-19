package surreal.contentcreator.functions.block;

// PUHAHAHAHAHA

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IVector3d;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IStateAtViewpoint")
public interface IBlockStateViewFunc {
    IBlockState getStateAtViewpoint(IBlockState state, IBlockAccess world, IBlockPos pos, IVector3d viewpoint);
}
