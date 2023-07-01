package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericStairs extends BlockStairs implements IGenericBlock {
    protected BlockGenericStairs(IBlockState modelState) {
        super(modelState);
        this.useNeighborBrightness = true;
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    @Override
    public String getModelLocation() {
        return "stairs";
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        String name = getTextureName(block);
        textures.addProperty("top", name);
        textures.addProperty("bottom", name);
        textures.addProperty("side", name);
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonArray emptyArray = new JsonArray();
        emptyArray.add(new JsonObject());

        for (EnumFacing facing : BlockStairs.FACING.getAllowedValues()) {
            for (EnumHalf half : BlockStairs.EnumHalf.values()) {
                for (EnumShape shape : EnumShape.values()) {
                    JsonObject object = new JsonObject();
                    // x
                    if (half == EnumHalf.TOP) object.addProperty("x", 180);

                    // uvlock
                    if (half == EnumHalf.TOP || (!shape.getName().contains("left")
                            && facing != EnumFacing.SOUTH) || (facing != EnumFacing.EAST))
                        object.addProperty("uvlock", true);

                    // y
                    if (half != EnumHalf.TOP) {
                        if (facing != EnumFacing.EAST && !shape.getName().contains("left")) {
                            switch (facing) {
                                case WEST:
                                    object.addProperty("y", 180);
                                    break;
                                case SOUTH:
                                    object.addProperty("y", 90);
                                    break;
                                case NORTH:
                                    object.addProperty("y", 270);
                                    break;
                            }
                        } else if (facing != EnumFacing.SOUTH && shape.getName().contains("left")) {
                            switch (facing) {
                                case EAST:
                                    object.addProperty("y", 270);
                                    break;
                                case WEST:
                                    object.addProperty("y", 90);
                                    break;
                                case NORTH:
                                    object.addProperty("y", 180);
                                    break;
                            }
                        }
                    } else {
                        if (facing != EnumFacing.EAST && !shape.getName().contains("right")) {
                            switch (facing) {
                                case WEST:
                                    object.addProperty("y", 180);
                                    break;
                                case SOUTH:
                                    object.addProperty("y", 90);
                                    break;
                                case NORTH:
                                    object.addProperty("y", 270);
                                    break;
                            }
                        } else if (facing != EnumFacing.NORTH && shape.getName().contains("right")) {
                            switch (facing) {
                                case EAST:
                                    object.addProperty("y", 90);
                                    break;
                                case WEST:
                                    object.addProperty("y", 270);
                                    break;
                                case SOUTH:
                                    object.addProperty("y", 180);
                                    break;
                            }
                        }
                    }

                    // model
                    if (shape.getName().contains("inner")) object.addProperty("model", "inner_stairs");
                    else if (shape.getName().contains("outer")) object.addProperty("model", "outer_stairs");

                    variants.add("facing=" + facing.getName() + ",half=" + half.getName() + ",shape=" + shape.getName(), object);
                }
            }
        }

        variants.add("inventory", emptyArray);
    }
}
