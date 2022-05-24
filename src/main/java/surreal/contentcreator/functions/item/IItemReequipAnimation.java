package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IReequipAnimation")
public interface IItemReequipAnimation {
    boolean shouldCauseReequipAnimation(IItemStack oldStack, IItemStack newStack, boolean slotChanged);
}
