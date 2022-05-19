package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IBeaconBase")
public interface IBlockBeaconBaseFunc {
    boolean isBeaconBase(IBlockAccess world, IBlockPos pos, IBlockPos beaconPos);
}
