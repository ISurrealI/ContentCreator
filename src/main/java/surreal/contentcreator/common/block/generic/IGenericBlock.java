package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBlockBase;
import surreal.contentcreator.types.CTSoundType;

import java.util.List;

public interface IGenericBlock {
    void setSoundType(CTSoundType soundType);
    default void create(List<Block> listToAdd) {}

    default Item createItem(Block block) {
        return new ItemBlockBase(block);
    }

    default String getTextureName(Block block) {
        return ModValues.MODID + ":blocks/" + block.getRegistryName().getResourcePath();
    }

    default String getModelLocation() {
        return "cube_all";
    }

    default void setTextures(Block block, JsonObject textures) {
        textures.addProperty("all", getTextureName(block));
    }

    default void setVariants(Block block, JsonObject variants) {
        JsonArray mongus = new JsonArray();
        mongus.add(new JsonObject());
        variants.add("normal", mongus);
        variants.add("inventory", mongus);
    }
}
