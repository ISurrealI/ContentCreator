package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IConnectRedstone")
public interface IBlockConnectRedstoneFunc {
    boolean canConnectRedstone(IBlockState state, IBlockAccess world, IBlockPos pos, IFacing facing);
}
