package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.block.material.MapColor;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IMapColor")
public interface IBlockMapColorFunc {
    MapColor getMapColor(IBlockState state, IBlockAccess world, IBlockPos pos);
}
