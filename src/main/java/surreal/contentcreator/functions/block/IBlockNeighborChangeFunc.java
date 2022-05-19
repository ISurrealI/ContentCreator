package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.NeighborChanged")
public interface IBlockNeighborChangeFunc {
    void onNeighborChanged(IBlockState state, IWorld world, IBlockPos pos, IBlockDefinition block, IBlockPos fromPos);
}
