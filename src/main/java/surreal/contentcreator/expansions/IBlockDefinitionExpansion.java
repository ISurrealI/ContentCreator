package surreal.contentcreator.expansions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings({"unused", "deprecation"})
@ZenRegister
@ZenExpansion("crafttweaker.block.IBlockDefinition")
public class IBlockDefinitionExpansion {
    @ZenMethod
    public static String getFaceShape(IBlockDefinition block, IBlockAccess world, IBlockState state, IBlockPos pos, IFacing facing) {
        return ((Block) block.getInternal()).getBlockFaceShape(((net.minecraft.world.IBlockAccess) world.getInternal()), ((net.minecraft.block.state.IBlockState) state.getInternal()), ((BlockPos) pos.getInternal()), ((EnumFacing) facing.getInternal())).name().toLowerCase();
    }
}
