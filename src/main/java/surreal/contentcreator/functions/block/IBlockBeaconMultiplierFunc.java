package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IBeaconMultiplier")
public interface IBlockBeaconMultiplierFunc {
    float[] getBeaconColorMultiplier(IBlockState state, IWorld world, IBlockPos pos, IBlockPos beaconPos);
}
