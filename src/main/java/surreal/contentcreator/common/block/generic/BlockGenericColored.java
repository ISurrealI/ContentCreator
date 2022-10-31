package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import surreal.contentcreator.types.CTSoundType;
import surreal.contentcreator.util.GeneralUtil;

public class BlockGenericColored extends BlockColored implements IGenericBlock {
    public final boolean color;

    public BlockGenericColored(Material materialIn, boolean color) {
        super(materialIn);
        this.color = color;
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    @Override
    public String getModelLocation() {
        return color ? "contentcreator:tinted_cube_all" : "cube_all";
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonObject color = new JsonObject();

        for (EnumDyeColor c : BlockColored.COLOR.getAllowedValues()) {
            color.add(c.getDyeColorName(), GeneralUtil.EMPTY_ARRAY);
        }

        variants.add("color", color);
        variants.add("inventory", GeneralUtil.EMPTY_ARRAY);
    }
}
