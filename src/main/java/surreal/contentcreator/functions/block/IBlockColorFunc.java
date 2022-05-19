package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.IColor")
public interface IBlockColorFunc {
    int colorMultiplier(IBlockState state, int tintIndex);
}
