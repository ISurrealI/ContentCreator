package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericFence extends BlockFence implements IGenericBlock {
    public BlockGenericFence(Material materialIn, MapColor mapColorIn) {
        super(materialIn, mapColorIn);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
