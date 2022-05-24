package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IEntityItemLifespan")
public interface IItemEntityLifespanFunc {
    int getEntityLifespane(IItemStack stack, IWorld world);
}
