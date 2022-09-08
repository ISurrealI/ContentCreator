package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.util.List;

public class BlockGenericHalfSlab extends BlockGenericSlab {
    public BlockGenericHalfSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
    }

    @Override
    public void create(List<Block> listToAdd) {
        BlockGenericDoubleSlab slab = new BlockGenericDoubleSlab(this.blockMaterial, this.blockMapColor);
        slab.setRegistryName(this.getRegistryName().toString() + "_double").setUnlocalizedName(this.getUnlocalizedName());
        slab.setHardness(this.blockHardness).setResistance(blockResistance);
        slab.setDefaultSlipperiness(this.slipperiness);
        slab.setSoundType(this.blockSoundType);
        slab.setLightLevel(this.lightValue).setLightOpacity(this.lightOpacity);
        String tool = this.getHarvestTool(getDefaultState());
        if (tool != null) slab.setHarvestLevel(tool, this.getHarvestLevel(getDefaultState()));
        listToAdd.add(slab);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
