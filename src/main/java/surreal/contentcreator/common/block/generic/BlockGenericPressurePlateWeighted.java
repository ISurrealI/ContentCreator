package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericPressurePlateWeighted extends BlockPressurePlateWeighted implements IGenericBlock {
    public BlockGenericPressurePlateWeighted(Material materialIn, int p_i46380_2_, MapColor color) {
        super(materialIn, p_i46380_2_, color);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
