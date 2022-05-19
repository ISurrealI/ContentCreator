package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import stanhebben.zenscript.annotations.ZenClass;

// Do fire spread and fire source too
@ZenRegister
@ZenClass("contentcreator.block.functions.IFlame")
public interface IBlockBurnFunc {
    int getBurn(IBlockAccess world, IBlockPos pos, IFacing facing);
}
