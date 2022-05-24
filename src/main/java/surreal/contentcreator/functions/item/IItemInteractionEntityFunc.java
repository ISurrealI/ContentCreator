package surreal.contentcreator.functions.item;

import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;

public interface IItemInteractionEntityFunc {
    boolean itemInteractionForEntity(IItemStack stack, IPlayer player, IEntityLivingBase entity, IEntityEquipmentSlot hand);
}
