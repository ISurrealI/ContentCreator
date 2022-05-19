package surreal.contentcreator.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.IBlockColorCheck")
public interface IBlockColorCheck {
    int colorMultiplier(IBlockState state, IWorld world, IBlockPos pos, int tintIndex);
}
