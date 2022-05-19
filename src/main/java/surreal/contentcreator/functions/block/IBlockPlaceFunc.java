package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IPlaceBlockAt")
public interface IBlockPlaceFunc {
    boolean canPlaceBlockAt(IWorld world, IBlockPos pos);
}
