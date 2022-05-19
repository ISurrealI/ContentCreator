package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import surreal.contentcreator.types.CTSoundType;

@ZenRegister
@ZenClass("contentcreator.block.functions.IBlockSound")
public interface IBlockSoundFunc {
    CTSoundType getSoundType(IBlockState state, IWorld world, IBlockPos pos, IEntity entity);
}
