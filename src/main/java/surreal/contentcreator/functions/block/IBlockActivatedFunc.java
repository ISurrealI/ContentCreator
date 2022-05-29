package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IActivated")
public interface IBlockActivatedFunc {
    boolean onBlockActivated(IWorld world, IBlockPos pos, IBlockState state, IPlayer player, IEntityEquipmentSlot hand, IFacing facing, float hitX, float hitY, float hitZ);
}
