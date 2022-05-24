package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IItemUse")
public interface IItemUseFunc {
    String onItemUse(IPlayer player, IWorld world, IBlockPos pos, IEntityEquipmentSlot hand, IFacing facing, float hitX, float hitY, float hitZ);
}
