package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.ISetDamage")
public interface IItemSetDamageFunc {
    void setDamage(IItemStack stack, int damage);
}
