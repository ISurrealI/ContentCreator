package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericPressurePlate extends BlockPressurePlate implements IGenericBlock {
    public BlockGenericPressurePlate(Material materialIn, int sensitivityIn) {
        super(materialIn, Sensitivity.values()[Math.min(1, sensitivityIn)]);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
