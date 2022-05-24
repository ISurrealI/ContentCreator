package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IContainerItem")
public interface IItemContainerItemFunc {
    IItemStack getContainerItem(IItemStack stack);
}
