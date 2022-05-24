package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IEntityItemUpdate")
public interface IItemEntityItemUpdateFunc {
    boolean onEntityItemUpdate(IEntityItem entityItem);
}
