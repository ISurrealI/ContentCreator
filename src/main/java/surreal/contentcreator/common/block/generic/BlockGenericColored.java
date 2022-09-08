package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericColored extends BlockColored implements IGenericBlock {
    public BlockGenericColored(Material materialIn) {
        super(materialIn);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
