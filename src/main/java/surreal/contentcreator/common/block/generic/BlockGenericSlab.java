package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import surreal.contentcreator.types.CTSoundType;

@SuppressWarnings("all")
public abstract class BlockGenericSlab extends BlockSlab implements IGenericBlock {
    protected static PropertyEnum<Type> VARIANT = PropertyEnum.create("variant", Type.class);

    public BlockGenericSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
        if (!this.isDouble())
            this.setDefaultState(getDefaultState().withProperty(HALF, EnumBlockHalf.BOTTOM).withProperty(VARIANT, Type.normal));
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Type.normal;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    private enum Type implements IStringSerializable {
        normal;

        @Override
        public String getName() {
            return this.name();
        }
    }
}
