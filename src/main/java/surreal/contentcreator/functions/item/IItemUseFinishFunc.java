package surreal.contentcreator.functions.item;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;

public interface IItemUseFinishFunc {
    IItemStack onItemUseFinish(IItemStack stack, IWorld world, IEntityLivingBase entity);
}
