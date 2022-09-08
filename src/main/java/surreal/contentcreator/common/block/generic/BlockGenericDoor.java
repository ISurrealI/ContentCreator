package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericDoor extends BlockDoor implements IGenericBlock {
    protected BlockGenericDoor(Material materialIn) {
        super(materialIn);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
