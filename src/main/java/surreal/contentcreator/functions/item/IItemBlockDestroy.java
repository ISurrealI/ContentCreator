package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.BlockDestroyed")
public interface IItemBlockDestroy {
    boolean onBlockDestroyed(IWorld world, IItemStack stack, IBlockState state, IBlockPos pos, IEntityLivingBase entity);
}
