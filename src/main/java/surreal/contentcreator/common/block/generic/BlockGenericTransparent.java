package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockGenericTransparent extends BlockGlass implements IGenericBlock {
    private final boolean silkTouch;

    public BlockGenericTransparent(Material materialIn, boolean ignoreSimilarity, boolean silkTouch) {
        super(materialIn, ignoreSimilarity);
        this.silkTouch = silkTouch;
    }

    @Override
    public int quantityDropped(@Nonnull IBlockState state, int fortune, @Nonnull Random random) {
        return silkTouch ? super.quantityDropped(state, fortune, random) : 1;
    }

    @Override
    protected boolean canSilkHarvest() {
        return silkTouch;
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
