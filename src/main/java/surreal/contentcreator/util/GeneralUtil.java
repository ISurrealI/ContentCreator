package surreal.contentcreator.util;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.text.WordUtils;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void generateModelFileItem(ItemMaterial item) {
        JsonObject object = new JsonObject();
        object.addProperty("parent", "item/generated");

        JsonObject texObject = new JsonObject();
        texObject.addProperty("layer0", ModValues.MODID + ":items/" + item.getRegistryName().getResourcePath());

        object.add("textures", texObject);

        try {
            File f = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item");
            boolean check = f.exists() || f.mkdirs();

            if (check) {
                f = new File(f, item.getRegistryName().getResourcePath() + ".json");
                FileWriter file = new FileWriter(f);
                file.write(object.toString());
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateModelFileItem(ItemBase item) {
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
                    FileWriter file = new FileWriter(f);
                    file.write(object.toString());
                    file.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
