package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockFalling;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericFalling extends BlockFalling implements IGenericBlock {
    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
