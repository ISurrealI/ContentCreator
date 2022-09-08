package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

public class BlockGenericDoubleSlab extends BlockGenericSlab {
    public BlockGenericDoubleSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
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
}
