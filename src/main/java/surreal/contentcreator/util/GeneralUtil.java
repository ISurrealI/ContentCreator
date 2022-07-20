package surreal.contentcreator.util;

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

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static net.minecraft.block.SoundType.*;
import static net.minecraft.block.SoundType.SLIME;

public class GeneralUtil {
    public static String toUppercase(String name, String replacement) {
        return WordUtils.capitalizeFully(name, '_').replaceAll("_", replacement);
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
