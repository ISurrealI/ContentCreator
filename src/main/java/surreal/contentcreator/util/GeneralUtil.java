package surreal.contentcreator.util;

import com.google.gson.JsonObject;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.text.WordUtils;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static net.minecraft.block.SoundType.*;
import static net.minecraft.block.SoundType.SLIME;

public class GeneralUtil {
    public static String toUppercase(String name) {
        return WordUtils.capitalizeFully(name, '_').replaceAll("_", " ");
    }

    public static void generateFiles() {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/textures/items");
        if (!file.exists()) file.mkdirs();

        file = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/lang");
        if (!file.exists()) file.mkdirs();

        file = new File(file, "en_us.lang");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateModelFileItem(Item it) {
        if (it instanceof ItemBase) {
            ItemBase item = (ItemBase) it;

            for (int i = 0; i < item.METAITEMS.size(); i++) {
                JsonObject object = new JsonObject();

                object.addProperty("parent", "item/generated");

                JsonObject texObject = new JsonObject();
                texObject.addProperty("layer0", ModValues.MODID + ":items/" + item.getModel(i).getResourcePath());

                object.add("textures", texObject);

                try {
                    File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item");
                    boolean check = f.exists() || f.mkdirs();

                    if (check) {
                        f = new File(f, item.getModel(i).getResourcePath() + ".json");

                        if (!f.exists()) {
                            FileWriter file = new FileWriter(f);
                            file.write(object.toString());
                            file.close();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("parent", "item/generated");

            JsonObject texObject = new JsonObject();
            texObject.addProperty("layer0", ModValues.MODID + ":items/" + it.getRegistryName().getResourcePath());

            object.add("textures", texObject);

            try {
                File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item");
                boolean check = f.exists() || f.mkdirs();

                if (check) {
                    f = new File(f, it.getRegistryName().getResourcePath() + ".json");

                    if (!f.exists()) {
                        FileWriter file = new FileWriter(f);
                        file.write(object.toString());
                        file.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateFluidFiles(List<FluidBase> list) {
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
                boolean check = f.exists() || f.mkdirs();

                if (check) {
                    f = new File(f, "fluids.json");

                    FileWriter file = new FileWriter(f);
                    file.write(object.toString());
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SoundType get(String name) {
        switch (name) {
            case "wood": return WOOD;
            case "ground": return GROUND;
            case "plant": return PLANT;
            case "stone": return STONE;
            case "metal": return METAL;
            case "glass": return GLASS;
            case "cloth": return CLOTH;
            case "sand": return SAND;
            case "snow": return SNOW;
            case "ladder": return LADDER;
            case "anvil": return ANVIL;
            case "slime": return SLIME;
            default: return null;
        }
    }
}
