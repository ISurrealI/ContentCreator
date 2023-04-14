package surreal.contentcreator.util;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.text.WordUtils;
import surreal.contentcreator.ContentCreator;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.block.BlockBase;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.common.block.generic.IGenericBlock;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.common.item.SubItem;
import surreal.contentcreator.types.CTMaterial;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class GeneralUtil {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String toUppercase(CaseFormat from, CaseFormat to, String str) {
        return from.to(to, str);
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

    public static ResourceLocation getTextureLocation(String location) {
        if (!location.contains(":")) return new ResourceLocation(ModValues.MODID, location);
        return new ResourceLocation(location);
    }
}
