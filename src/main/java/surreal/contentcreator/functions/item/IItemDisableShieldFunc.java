package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IDisableShield")
public interface IItemDisableShieldFunc {
    boolean canDisableShield(IItemStack stack, IItemStack shield, IEntityLivingBase entity, IEntityLivingBase attacker);
}
