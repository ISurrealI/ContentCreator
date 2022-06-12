package surreal.contentcreator.expansions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.EnumHand;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.util.CTUtil;

@SuppressWarnings("unused")
@ZenRegister
@ZenExpansion("crafttweaker.entity.IEntityLivingBase")
public class IEntityLivingBaseExpansion {
    @ZenMethod
    public static IItemStack getItemInHand(IEntityLivingBase player, IEntityEquipmentSlot slot) {
        EnumHand hand = CTUtil.getHand(slot);
        return CraftTweakerMC.getIItemStackMutable(CraftTweakerMC.getEntityLivingBase(player).getHeldItem(hand));
    }

    @ZenMethod
    public static IItemStack getItemOffHand(IEntityLivingBase player) {
        return CraftTweakerMC.getIItemStackMutable(CraftTweakerMC.getEntityLivingBase(player).getHeldItemOffhand());
    }
}
