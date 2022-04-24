package surreal.contentcreator.common.fluid;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import surreal.contentcreator.ModValues;

public class FluidBlockBase extends BlockFluidClassic {
    public FluidBlockBase(Fluid fluid, Material material) {
        super(fluid, material);
        this.setRegistryName(ModValues.MODID, fluid.getName());
        this.setUnlocalizedName(fluid.getName());
    }
}
