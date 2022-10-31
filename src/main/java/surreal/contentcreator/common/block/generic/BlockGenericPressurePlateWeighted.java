package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTSoundType;
import surreal.contentcreator.util.GeneralUtil;

public class BlockGenericPressurePlateWeighted extends BlockPressurePlateWeighted implements IGenericBlock {
    public BlockGenericPressurePlateWeighted(Material materialIn, int p_i46380_2_, MapColor color) {
        super(materialIn, p_i46380_2_, color);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        textures.addProperty("texture", ModValues.MODID + ":blocks/" + block.getRegistryName().getResourcePath());
    }

    @Override
    public String getModelLocation() {
        return "pressure_plate_up";
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonObject powered = new JsonObject();
        powered.add("false", GeneralUtil.EMPTY_ARRAY);

        JsonObject model = new JsonObject();
        model.addProperty("model", "pressure_plate_down");
        powered.add("true", model);

        variants.add("powered", powered);
        variants.add("inventory", GeneralUtil.EMPTY_ARRAY);
    }
}
