package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contenttweaker.item.functions.AttributeModifier")
public interface IItemAttributeModifier {
    double getValue(IEntityEquipmentSlot slot);
}
