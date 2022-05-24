package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.ILeftClickEntity")
public interface IItemLeftClickEntityFunc {
    boolean onLeftClickEntity(IItemStack stack, IPlayer player, IEntity entity);
}
