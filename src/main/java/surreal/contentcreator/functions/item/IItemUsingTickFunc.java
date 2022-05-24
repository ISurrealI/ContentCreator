package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IUsingTick")
public interface IItemUsingTickFunc {
    void onUsingTick(IItemStack stack, IEntityLivingBase entity, int tick);
}
