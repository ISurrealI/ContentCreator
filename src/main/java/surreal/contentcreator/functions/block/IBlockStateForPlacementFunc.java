package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IStateForPlacement")
public interface IBlockStateForPlacementFunc {
    IBlockState getStateForPlacement(IWorld world, IBlockPos pos, IFacing facing, float hitX, float hitY, float hitZ, int meta, IEntityLivingBase player);
}
