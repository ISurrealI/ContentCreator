package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IRightClick")
public interface IItemRightClick {
    String onItemRightClick(IWorld world, IPlayer player, IEntityEquipmentSlot hand);
}
