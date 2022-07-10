package surreal.contentcreator.client.fluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import surreal.contentcreator.ModValues;

public class CustomFluidStateMapper extends StateMapperBase {
    public final ModelResourceLocation location;

    public CustomFluidStateMapper(String name) {
        location = new ModelResourceLocation(ModValues.MODID + ":fluids", name);
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return location;
    }
}
