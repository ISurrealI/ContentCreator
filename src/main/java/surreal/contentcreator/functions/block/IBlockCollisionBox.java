package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.ICollisionBox")
public interface IBlockCollisionBox {
    IAxisAlignedBB[] getCollisionBoxList(IWorld world, IBlockPos pos, IBlockState state, IAxisAlignedBB entityBox, IEntity entity, boolean isActualState);
}
