package surreal.contentcreator.types;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.block.MapColor")
public class CTMapColor {
    @ZenMethod
    public static MapColor fromIndex(int index) {
        return MapColor.COLORS[Math.min(index, 63)];
    }

    @ZenMethod
    public static MapColor fromColor(String name) {
        return MapColor.getBlockColor(EnumDyeColor.valueOf(name));
    }

    @ZenMethod
    public static MapColor fromName(String name) {
        switch (name) {
            case "grass": return MapColor.GRASS;
            case "sand": return MapColor.SAND;
            case "cloth": return MapColor.CLOTH;
            case "tnt": return MapColor.TNT;
            case "ice": return MapColor.ICE;
            case "iron": return MapColor.IRON;
            case "foliage": return MapColor.FOLIAGE;
            case "snow": return MapColor.SNOW;
            case "clay": return MapColor.CLAY;
            case "dirt": return MapColor.DIRT;
            case "stone": return MapColor.STONE;
            case "water": return MapColor.WATER;
            case "wood": return MapColor.WOOD;
            case "quartz": return MapColor.QUARTZ;
            case "adobe": return MapColor.ADOBE;
            case "magenta": return MapColor.MAGENTA;
            case "light_blue": return MapColor.LIGHT_BLUE;
            case "yellow": return MapColor.YELLOW;
            case "lime": return MapColor.LIME;
            case "pink": return MapColor.PINK;
            case "gray": return MapColor.GRAY;
            case "silver":
            case "light_gray": return MapColor.SILVER;
            case "cyan": return MapColor.CYAN;
            case "purple": return MapColor.PURPLE;
            case "blue": return MapColor.BLUE;
            case "brown": return MapColor.BROWN;
            case "green": return MapColor.GREEN;
            case "red": return MapColor.RED;
            case "black": return MapColor.BLACK;
            case "gold": return MapColor.GOLD;
            case "diamond": return MapColor.DIAMOND;
            case "lapis": return MapColor.LAPIS;
            case "emerald": return MapColor.EMERALD;
            case "obsidian": return MapColor.OBSIDIAN;
            case "netherrack": return MapColor.NETHERRACK;
            case "white_stained_hardened_clay": return MapColor.WHITE_STAINED_HARDENED_CLAY;
            case "orange_stained_hardened_clay": return MapColor.ORANGE_STAINED_HARDENED_CLAY;
            case "MAGENTA_STAINED_HARDENED_CLAY": return MapColor.MAGENTA_STAINED_HARDENED_CLAY;
            case "light_blue_stained_hardened_clay": return MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY;
            case "yellow_stained_hardened_clay": return MapColor.YELLOW_STAINED_HARDENED_CLAY;
            case "lime_stained_hardened_clay": return MapColor.LIME_STAINED_HARDENED_CLAY;
            case "pink_stained_hardened_clay": return MapColor.PINK_STAINED_HARDENED_CLAY;
            case "gray_stained_hardened_clay": return MapColor.GRAY_STAINED_HARDENED_CLAY;
            case "silver_stained_hardened_clay":
            case "light_gray_stained_hardened_clay": return MapColor.SILVER_STAINED_HARDENED_CLAY;
            case "cyan_gray_stained_hardened_clay": return MapColor.CYAN_STAINED_HARDENED_CLAY;
            case "purple_gray_stained_hardened_clay": return MapColor.PURPLE_STAINED_HARDENED_CLAY;
            case "blue_gray_stained_hardened_clay": return MapColor.BLUE_STAINED_HARDENED_CLAY;
            case "brown_gray_stained_hardened_clay": return MapColor.BROWN_STAINED_HARDENED_CLAY;
            case "green_gray_stained_hardened_clay": return MapColor.GREEN_STAINED_HARDENED_CLAY;
            case "red_gray_stained_hardened_clay": return MapColor.RED_STAINED_HARDENED_CLAY;
            case "black_gray_stained_hardened_clay": return MapColor.BLACK_STAINED_HARDENED_CLAY;
            default: return MapColor.AIR;
        }
    }
}
