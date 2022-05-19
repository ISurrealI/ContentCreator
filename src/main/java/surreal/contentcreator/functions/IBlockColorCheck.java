package surreal.contentcreator.functions;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;

public interface IBlockColorCheck {
    int colorMultiplier(IBlockState state, IWorld world, IBlockPos pos, int tintIndex);
}
