package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IPlaceTorchOnTop")
public interface IBlockPlaceTorchTopFunc {
    boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, IBlockPos pos);
}
