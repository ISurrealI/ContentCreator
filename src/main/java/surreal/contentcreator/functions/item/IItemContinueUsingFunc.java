package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IContinueUsing")
public interface IItemContinueUsingFunc {
    boolean canContinueUsing(IItemStack oldStack, IItemStack newStack);
}
