package surreal.contentcreator.functions.item;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;

public interface IItemInformation {
    String[] addInformation(IItemStack stack, IWorld world, boolean isAdvanced);
}
