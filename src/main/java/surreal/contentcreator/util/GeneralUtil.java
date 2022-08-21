package surreal.contentcreator.util;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonObject;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.text.WordUtils;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.SubItem;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static net.minecraft.block.SoundType.*;
import static net.minecraft.block.SoundType.SLIME;

public class GeneralUtil {
    public static String toUppercase(CaseFormat from, CaseFormat to, String str) {
        return from.to(to, str);
    }

    public static void generateFiles() {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/textures/items");
        if (!file.exists()) file.mkdirs();

        file = new File(file.getParentFile(), "blocks");
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

    public static void generateItemFiles(List<ItemBase> list) {
        File blockstates = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/blockstates/item");
        File models = new File(Minecraft.getMinecraft().mcDataDir, "resources/" + ModValues.MODID + "/models/item");

        for (ItemBase item : list) {
            if (item.modelBlockState) {
                if (!blockstates.exists()) blockstates.mkdirs();

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
                        file.write(object.toString());
                        file.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (!models.exists()) models.mkdirs();

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
                            file.write(object.toString());
                            file.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

    public static ItemStack getStackFromString(String str) {
        boolean hasNBT = str.contains("#");

        String a = hasNBT ? str.split("#")[1] : null;
        if (hasNBT) str = str.split("#")[0];

        String[] stackSplit = str.split(":");

        Item item = Item.getByNameOrId(stackSplit[0] + ":" + stackSplit[1]);
        if (item == null || item == Items.AIR) return ItemStack.EMPTY;

        int meta = stackSplit.length > 2 ? Integer.parseInt(stackSplit[2]) : 0;

        ItemStack stack = new ItemStack(item, 1, meta);

        if (a != null) {
            try {
                NBTTagCompound tag = JsonToNBT.getTagFromJson(a);
                stack.setTagCompound(tag);
            } catch (NBTException ignored) {}
        }

        return stack;
    }

    public static Color getColorFromInt(int i) {
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }
}
