package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IActivated")
public interface IBlockActivatedFunc {
    boolean onBlockActivated(IWorld world, IBlockPos pos, IBlockState state, IPlayer player, IItemStack stack, IFacing facing, float hitX, float hitY, float hitZ);
}
