package surreal.contentcreator.util;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneralUtil {

    public static void generateModelFileItem(ItemBase item) {
        if (!Loader.isModLoaded("resourceloader")) return;

        if (item.METAITEMS.size() > 0) {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "textures/items");
            if (!file.exists()) file.mkdirs();
        }

        for (int i = 0; i < item.METAITEMS.size(); i++) {
            JsonObject object = new JsonObject();

            object.addProperty("parent", "item/generated");

            JsonObject texObject = new JsonObject();

            String[] textures = item.METAITEMS.get(i).textures;

            if (textures != null) {
                for (int g = 0; g < textures.length; g++) {
                    texObject.addProperty("layer" + g, ModValues.MODID + ":items/" + textures[g]);
                }
            }

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
