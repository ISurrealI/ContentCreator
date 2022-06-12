package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IPlayerDropped")
public interface IItemPlayerDrop {
    boolean onDroppedByPlayer(IItemStack stack, IPlayer player);
}
