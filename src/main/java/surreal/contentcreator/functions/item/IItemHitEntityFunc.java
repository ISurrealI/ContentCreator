package surreal.contentcreator.functions.item;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;

public interface IItemHitEntityFunc {
    boolean hitEntity(IItemStack stack, IEntityLivingBase entity, IEntityLivingBase attacker);
}
