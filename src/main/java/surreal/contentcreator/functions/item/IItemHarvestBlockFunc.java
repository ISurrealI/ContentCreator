package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IHarvestBlock")
public interface IItemHarvestBlockFunc {
    boolean canHarvestBlock(IBlockState state, IItemStack stack);
}
