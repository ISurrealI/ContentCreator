package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IPlaceBlockOnSide")
public interface IBlockPlaceSideFunc {
    boolean canPlaceBlockOnSide(IWorld world, IBlockPos pos, IFacing facing);
}
