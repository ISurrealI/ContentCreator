package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IDestroyed")
public interface IBlockDestroyedPlayerFunc {
    void onBlockDestroyed(IWorld world, IBlockPos pos, IBlockState state);
}
