package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nonnull;

public class BlockGenericPillar extends BlockRotatedPillar implements IGenericBlock {
    protected BlockGenericPillar(Material materialIn, MapColor color) {
        super(materialIn, color);
        this.setDefaultState(getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return getMeta(state.getValue(AXIS));
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing.Axis axis = getAxis(meta);
        return getDefaultState().withProperty(AXIS, axis);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    private int getMeta(EnumFacing.Axis axis) {
        switch (axis) {
            default: return 0;
            case X: return 1;
            case Z: return 2;
        }
    }

    private EnumFacing.Axis getAxis(int meta) {
        switch (meta) {
            default: return EnumFacing.Axis.Y;
            case 1: return EnumFacing.Axis.X;
            case 2: return EnumFacing.Axis.Z;
        }
    }
}
