package surreal.contentcreator.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.common.block.generic.IGenericBlock;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.common.item.SubItem;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.CTMaterial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class FileGenUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void generateFiles() {
        generateResourcesFile();
        generateItemFiles(CommonProxy.ITEMS);
        generateMatItems(CommonProxy.MAT_ITEMS.values());
        generateBlocks(CommonProxy.BLOCKS);
        generateBlocks(CommonProxy.MAT_BLOCKS);
        generateFluidFiles(CommonProxy.FLUIDS);
    }

    private static void generateResourcesFile() {
        File resources = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID);
        if (!resources.exists()) resources.mkdirs();

        File textures = new File(resources, "textures/items");
        if (!textures.exists()) textures.mkdirs();

        textures = new File(textures.getParentFile(), "blocks");
        if (!textures.exists()) textures.mkdirs();

        File lang = new File(resources, "lang");
        if (!lang.exists()) lang.mkdirs();

        lang = new File(lang, "en_us.lang");
        if (!lang.exists()) {
            try { lang.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }

        File models = new File(resources, "models/block");
        if (!models.exists()) models.mkdirs();

        models = new File(models.getParentFile(), "item");
        if (!models.exists()) models.mkdirs();

        File blockstates = new File(resources, "blockstates");
        if (!blockstates.exists()) blockstates.mkdirs();
    }

    private static void generateItemFiles(Collection<ItemBase> list) {
        File blockstates = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/blockstates/item");
        File models = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item");

        for (ItemBase item : list) {
            if (item.modelBlockState) {
                JsonObject object = new JsonObject();
                object.addProperty("forge_marker", 1);

                JsonObject defaults = new JsonObject();
                defaults.addProperty("model", "builtin/generated");
                defaults.addProperty("transform", "forge:default-item");
                object.add("defaults", defaults);

                JsonObject variants = new JsonObject();
                JsonObject type = new JsonObject();
                for (SubItem sub : item.SUBITEMS.values()) {
                    String name = item.getSubName(sub);
                    if (name == null) name = "" + sub.meta;

                    JsonObject obj = new JsonObject();
                    JsonObject textures = new JsonObject();
                    textures.addProperty("layer0", ModValues.MODID + ":items/" + item.getRegistryName().getResourcePath() + "/" + name);
                    obj.add("textures", textures);
                    type.add(name, obj);
                }
                variants.add("type", type);
                object.add("variants", variants);

                try {
                    File f = new File(blockstates, item.getRegistryName().getResourcePath() + ".json");

                    if (!f.exists()) {
                        FileWriter file = new FileWriter(f);
                        file.write(GSON.toJson(object));
                        file.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                for (SubItem sub : item.SUBITEMS.values()) {
                    String name = item.getSubName(sub);
                    if (name == null) {
                        if (sub.meta != 0) name = item.getRegistryName().getResourcePath() + "_" + sub.meta;
                        else name = item.getRegistryName().getResourcePath();
                    }

                    JsonObject object = new JsonObject();
                    object.addProperty("parent", "item/generated");

                    JsonObject textures = new JsonObject();
                    textures.addProperty("layer0", ModValues.MODID + ":items/" + name);
                    object.add("textures", textures);

                    try {
                        File f = new File(models, name + ".json");

                        if (!f.exists()) {
                            FileWriter file = new FileWriter(f);
                            file.write(GSON.toJson(object));
                            file.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void generateMatItems(Collection<ItemMaterial> items) {
        for (ItemMaterial item : items) {
            for (CTMaterial material : item.MATERIALS.values()) {
                String model = item.getModelLocation(material).split(":")[1];
                String texture = "items/" + model;

                File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item/" + model + ".json");

                JsonObject object = new JsonObject();
                object.addProperty("parent", "item/generated");

                JsonObject textures = new JsonObject();
                textures.addProperty("layer0", ModValues.MODID + ":" + texture);
                object.add("textures", textures);

                try {
                    if (!f.exists()) {
                        FileWriter file = new FileWriter(f);
                        file.write(GSON.toJson(object));
                        file.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void generateBlocks(Collection<Block> blocks) {
        for (Block block : blocks) {
            JsonObject object = new JsonObject();
            object.addProperty("forge_marker", 1);

            JsonObject defaults = new JsonObject();
            defaults.addProperty("transform", "forge:default-block");
            defaults.addProperty("model", getModelLocation(block));

            JsonObject textures = getTextures(block);
            if (!(block instanceof BlockMaterial)) defaults.add("textures", textures);

            object.add("defaults", defaults);

            JsonObject variants = getVariants(block, textures);
            object.add("variants", variants);

            try {
                File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/blockstates/" + block.getRegistryName().getResourcePath() + ".json");

                if (!f.exists()) {
                    FileWriter file = new FileWriter(f);
                    file.write(GSON.toJson(object));
                    file.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getModelLocation(Block block) {
        if (block instanceof BlockMaterial) return "contentcreator:tinted_cube_all";
        else if (block instanceof IGenericBlock) return ((IGenericBlock) block).getModelLocation();
        else return "cube_all";
    }

    private static JsonObject getTextures(Block block) {
        JsonObject textures = new JsonObject();
        if (block instanceof BlockMaterial) textures.addProperty("all", ModValues.MODID + ":blocks/part/" + ((BlockMaterial) block).part.name);
        else if (block instanceof IGenericBlock) ((IGenericBlock) block).setTextures(block, textures);
        else textures.addProperty("all", ModValues.MODID + ":blocks/" + block.getRegistryName().getResourcePath());

        return textures;
    }

    private static JsonObject getVariants(Block block, JsonObject textures) {
        JsonObject variants = new JsonObject();
        if (block instanceof BlockMaterial) {
            BlockMaterial b = (BlockMaterial) block;
            JsonObject material = new JsonObject();
            for (int i = 0; i < b.materials.length; i++) {
                JsonObject mat = new JsonObject();
                mat.add("textures", textures);
                material.add("" + i, mat);
            }
            variants.add("material", material);

            JsonArray mongus = new JsonArray();
            mongus.add(new JsonObject());
            variants.add("inventory", mongus);
        } else if (block instanceof IGenericBlock) {
            ((IGenericBlock) block).setVariants(block, variants);
        } else {
            JsonArray mongus = new JsonArray();
            mongus.add(new JsonObject());
            variants.add("normal", mongus);
            variants.add("inventory", mongus);
        }

        return variants;
    }

    private static void generateFluidFiles(Collection<FluidBase> list) {
        if (list.size() > 0) {
            JsonObject object = new JsonObject();
            object.addProperty("forge_marker", 1);
            JsonObject variants = new JsonObject();

            for (Fluid fluid : list) {
                if (variants.get(fluid.getName()) != null) continue;

                JsonObject fluidObj = new JsonObject();
                JsonObject customFluid = new JsonObject();

                customFluid.addProperty("fluid", fluid.getName());

                fluidObj.addProperty("model", "forge:fluid");
                fluidObj.add("custom", customFluid);

                variants.add(fluid.getName(), fluidObj);
            }

            object.add("variants", variants);

            try {
                File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/blockstates");

                f = new File(f, "fluids.json");

                FileWriter file = new FileWriter(f);
                file.write(GSON.toJson(object));
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
