package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import surreal.contentcreator.ModValues;

import java.util.List;

public class BlockGenericHalfSlab extends BlockGenericSlab {
    private final BlockGenericDoubleSlab slab;

    public BlockGenericHalfSlab(Material p_i47249_1_, MapColor p_i47249_2_, String name) {
        super(p_i47249_1_, p_i47249_2_);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setRegistryName(ModValues.MODID, name).setUnlocalizedName(ModValues.MODID + "." + name);
        this.slab = new BlockGenericDoubleSlab(this, this.blockMaterial, this.blockMapColor);
        slab.setRegistryName(this.getRegistryName().toString() + "_double").setUnlocalizedName(this.getUnlocalizedName());
        String tool = this.getHarvestTool(getDefaultState());
        if (tool != null) slab.setHarvestLevel(tool, this.getHarvestLevel(getDefaultState()));
    }

    @Override
    public void create(List<Block> listToAdd) {
        listToAdd.add(slab);
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public Item createItem(Block block) {
        return new ItemSlab(block, this, slab).setRegistryName(this.getRegistryName()).setUnlocalizedName(this.getUnlocalizedName());
    }

    @Override
    public String getModelLocation() {
        return "half_slab";
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        String texture = getTextureName(block);
        textures.addProperty("top", texture);
        textures.addProperty("bottom", texture);
        textures.addProperty("side", texture);
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonObject emptyObject = new JsonObject();
        JsonArray emptyArray = new JsonArray();
        emptyArray.add(new JsonObject());

        JsonObject topMal = new JsonObject();
        topMal.addProperty("model", "upper_slab");

        JsonObject half = new JsonObject();
        half.add("bottom", emptyObject);
        half.add("top", topMal);

        variants.add("half", half);

        JsonObject variant = new JsonObject();
        variant.add("normal", emptyObject);
        variants.add("variant", variant);

        variants.add("inventory", emptyArray);
    }
}
