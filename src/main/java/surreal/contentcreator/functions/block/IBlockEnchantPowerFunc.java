package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IEnchantPowerBonus")
public interface IBlockEnchantPowerFunc {
    float getEnchantPowerBonus(IWorld world, IBlockPos pos);
}
