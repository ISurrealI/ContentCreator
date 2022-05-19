package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

import javax.annotation.Nullable;

@ZenRegister
@ZenClass("contentcreator.block.functions.ISlipperiness")
public interface IBlockSlipperinessFunc {
    float getSlipperiness(IBlockState state, IBlockAccess world, IBlockPos pos, @Nullable IEntity entity);
}
