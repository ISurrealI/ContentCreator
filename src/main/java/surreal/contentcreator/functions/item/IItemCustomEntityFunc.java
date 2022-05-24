package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

/*
    I think we can see interesting things can be done with this
    but in like after 5 years if people still continue to make packs in 1.12.2 and CT didn't die lol
*/
@ZenRegister
@ZenClass("contentcreator.item.functions.ICustomEntity")
public interface IItemCustomEntityFunc {
    IEntity createEntity(IWorld world, IEntityItem entity, IItemStack stack);
}
