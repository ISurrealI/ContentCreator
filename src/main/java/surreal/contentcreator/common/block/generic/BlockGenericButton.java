package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import surreal.contentcreator.types.CTSoundEvent;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockGenericButton extends BlockButton implements IGenericBlock {
    private final boolean wooden;
    private final int tickRate;
    private CTSoundEvent click, release;

    protected BlockGenericButton(boolean wooden, int tickRate) {
        super(wooden);
        this.wooden = wooden;
        this.tickRate = tickRate;
    }

    @Override
    public int tickRate(@Nonnull World worldIn) {
        return this.tickRate > 0 ? tickRate : super.tickRate(worldIn);
    }

    public BlockGenericButton setClick(CTSoundEvent click) {
        this.click = click;
        return this;
    }

    public BlockGenericButton setRelease(CTSoundEvent release) {
        this.release = release;
        return this;
    }

    @Override
    protected void playClickSound(@Nullable EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (click != null) {
            worldIn.playSound(player, pos, click.getInternal(), SoundCategory.BLOCKS, 0.3F, 0.6F);
        } else {
            if (this.wooden) worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
            else worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

    @Override
    protected void playReleaseSound(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (release != null) {
            worldIn.playSound(null, pos, release.getInternal(), SoundCategory.BLOCKS, 0.3F, 0.5F);
        } else {
            if (this.wooden) worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
            else worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
