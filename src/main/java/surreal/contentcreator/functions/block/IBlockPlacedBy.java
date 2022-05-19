package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IPlacedBy")
public interface IBlockPlacedBy {
    void onBlockPlacedBy(IWorld world, IBlockPos pos, IBlockState state, IEntityLivingBase placer, IItemStack stack);
}
