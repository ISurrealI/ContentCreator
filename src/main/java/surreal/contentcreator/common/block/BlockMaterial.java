package surreal.contentcreator.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBlockMaterial;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.types.parts.PartBlock;

import javax.annotation.Nonnull;

public class BlockMaterial extends Block {
    private static int lastInt = -1;
    public static PropertyInteger INT = null;

    public final CTMaterial[] materials;
    public final PartBlock part;
    
    public BlockMaterial(int level, PartBlock part, CTMaterial... materials) {
        super(Material.IRON);
        this.setRegistryName(ModValues.MODID, part.name + "_material_" + level);
        this.setCreativeTab(CreativeTabs.SEARCH);
        this.materials = materials;
        this.part = part;
        if (INT != null) this.setDefaultState(getDefaultState().withProperty(INT, 0));
        CommonProxy.ITEMBLOCKS.add(new ItemBlockMaterial(this));
    }

    @Nonnull
    @Override
    public String getLocalizedName() {
        return I18n.format("part." + part.name, I18n.format("material.generic"));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public Material getMaterial(@Nonnull IBlockState state) {
        CTMaterial material = materials[this.getMetaFromState(state)];
        return material.blockMaterial == null ? super.getMaterial(state) : material.blockMaterial;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state.getPropertyKeys().size() > 0) {
            PropertyInteger property = getInteger(state);

            if (property != null) {
                return state.getValue(property);
            }
        }

        return super.getMetaFromState(state);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (this.getDefaultState().getPropertyKeys().size() > 0) {
            IBlockState state = getDefaultState();
            PropertyInteger integer = getInteger(state);
            if (integer != null) return state.withProperty(integer, meta);
        }

        return super.getStateFromMeta(meta);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        if (INT != null) return new BlockStateContainer(this, INT);
        return super.createBlockState();
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < materials.length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public static void setProperty(int max) {
        if (lastInt != max) {
            lastInt = max;
            if (max-1 > 0) {
                INT = PropertyInteger.create("material", 0, max - 1);
            } else INT = null;
        }
    }

    private static PropertyInteger getInteger(IBlockState state) {
        for (IProperty<?> property : state.getPropertyKeys()) {
            if (property instanceof PropertyInteger) return (PropertyInteger) property;
        }

        return null;
    }
}
