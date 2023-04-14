package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTSoundType;
import surreal.contentcreator.util.GeneralUtil;

public class BlockGenericPressurePlate extends BlockPressurePlate implements IGenericBlock {
    public BlockGenericPressurePlate(Material materialIn, int sensitivityIn) {
        super(materialIn, Sensitivity.values()[Math.min(1, sensitivityIn)]);
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
        JsonObject emptyObject = new JsonObject();
        JsonArray emptyArray = new JsonArray();
        emptyArray.add(new JsonObject());

        JsonObject powered = new JsonObject();
        powered.add("false", emptyObject);

        JsonObject model = new JsonObject();
        model.addProperty("model", "pressure_plate_down");
        powered.add("true", model);

        variants.add("powered", powered);
        variants.add("inventory", emptyArray);
    }
}
