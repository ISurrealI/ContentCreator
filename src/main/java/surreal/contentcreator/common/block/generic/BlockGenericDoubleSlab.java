package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.util.GeneralUtil;

import javax.annotation.Nonnull;

public class BlockGenericDoubleSlab extends BlockGenericSlab {
    private final Block halfSlab;

    public BlockGenericDoubleSlab(BlockGenericHalfSlab halfSlab, Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
        this.halfSlab = halfSlab;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType soundType) {
        return super.setSoundType(soundType);
    }

    @Override
    public Item createItem(Block block) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(halfSlab);
    }

    @Override
    public String getModelLocation() {
        return "cube_column";
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        String name = getTextureName(block);
        textures.addProperty("end", name);
        textures.addProperty("side", name);
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonObject half = new JsonObject();
        half.add("bottom", GeneralUtil.EMPTY_OBJECT);
        half.add("top", GeneralUtil.EMPTY_OBJECT);

        variants.add("half", half);

        JsonObject variant = new JsonObject();
        variant.add("normal", GeneralUtil.EMPTY_OBJECT);
        variants.add("variant", variant);

        variants.add("inventory", GeneralUtil.EMPTY_ARRAY);
    }
}
