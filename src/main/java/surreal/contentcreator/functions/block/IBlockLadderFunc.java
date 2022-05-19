package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.ILadder")
public interface IBlockLadderFunc {
    boolean isLadder(IBlockState state, IBlockAccess world, IBlockPos pos, IEntityLivingBase entity);
}
