package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IBlockBreakReset")
public interface IItemBlockBreakResetFunc {
    boolean shouldCauseBlockBreakReset(IItemStack oldStack, IItemStack newStack);
}
