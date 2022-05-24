package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IBlockStartBreak")
public interface IItemBlockStartBreakFunc {
    boolean onBlockStartBreak(IItemStack stack, IBlockPos pos, IPlayer player);
}
