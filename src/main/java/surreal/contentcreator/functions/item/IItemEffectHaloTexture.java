package surreal.contentcreator.functions.item;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("contenttweaker.item.functions.BGTexture")
public interface IItemEffectHaloTexture {
    @ZenMethod
    String getTexture(IWorld world, IPlayer player, IItemStack stack);
}
