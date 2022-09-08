package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericStairs extends BlockStairs implements IGenericBlock {
    protected BlockGenericStairs(IBlockState modelState) {
        super(modelState);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
