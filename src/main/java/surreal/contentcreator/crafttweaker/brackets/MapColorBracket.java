package surreal.contentcreator.crafttweaker.brackets;

import crafttweaker.annotations.BracketHandler;
import surreal.contentcreator.crafttweaker.types.CTMapColor;

import static net.minecraft.block.material.MapColor.*;

@BracketHandler
public class MapColorBracket extends BasicBracket {

    public MapColorBracket() {
        super("mapcolor");
    }

    public static CTMapColor get(String name) {
        switch (name) {
            case "grass": return new CTMapColor(GRASS);
            case "sand": return new CTMapColor(SAND);
            case "cloth": return new CTMapColor(CLOTH);
            case "tnt": return new CTMapColor(TNT);
            case "ice": return new CTMapColor(ICE);
            case "iron": return new CTMapColor(IRON);
            case "foliage": return new CTMapColor(FOLIAGE);
            case "snow": return new CTMapColor(SNOW);
            case "clay": return new CTMapColor(CLAY);
            case "dirt": return new CTMapColor(DIRT);
            case "stone": return new CTMapColor(STONE);
            case "water": return new CTMapColor(WATER);
            case "wood": return new CTMapColor(WOOD);
            case "quartz": return new CTMapColor(QUARTZ);
            case "adobe": return new CTMapColor(ADOBE);
            case "magenta": return new CTMapColor(MAGENTA);
            case "light_blue": return new CTMapColor(LIGHT_BLUE);
            case "yellow": return new CTMapColor(YELLOW);
            case "lime": return new CTMapColor(LIME);
            case "pink": return new CTMapColor(PINK);
            case "gray": return new CTMapColor(GRAY);
            case "silver":
            case "light_gray": return new CTMapColor(SILVER);
            case "cyan": return new CTMapColor(CYAN);
            case "purple": return new CTMapColor(PURPLE);
            case "blue": return new CTMapColor(BLUE);
            case "brown": return new CTMapColor(BROWN);
            case "green": return new CTMapColor(GREEN);
            case "red": return new CTMapColor(RED);
            case "black": return new CTMapColor(BLACK);
            case "gold": return new CTMapColor(GOLD);
            case "diamond": return new CTMapColor(DIAMOND);
            case "lapis": return new CTMapColor(LAPIS);
            case "emerald": return new CTMapColor(EMERALD);
            case "obsidian": return new CTMapColor(OBSIDIAN);
            case "netherrack": return new CTMapColor(NETHERRACK);
            case "white_clay": return new CTMapColor(WHITE_STAINED_HARDENED_CLAY);
            case "orange_clay": return new CTMapColor(ORANGE_STAINED_HARDENED_CLAY);
            case "magenta_clay": return new CTMapColor(MAGENTA_STAINED_HARDENED_CLAY);
            case "light_blue_clay": return new CTMapColor(LIGHT_BLUE_STAINED_HARDENED_CLAY);
            case "yellow_clay": return new CTMapColor(YELLOW_STAINED_HARDENED_CLAY);
            case "lime_clay": return new CTMapColor(LIME_STAINED_HARDENED_CLAY);
            case "pink_clay": return new CTMapColor(PINK_STAINED_HARDENED_CLAY);
            case "gray_clay": return new CTMapColor(GRAY_STAINED_HARDENED_CLAY);
            case "light_gray_clay":
            case "silver_clay": return new CTMapColor(SILVER_STAINED_HARDENED_CLAY);
            case "cyan_clay": return new CTMapColor(CYAN_STAINED_HARDENED_CLAY);
            case "purple_clay": return new CTMapColor(PURPLE_STAINED_HARDENED_CLAY);
            case "blue_clay": return new CTMapColor(BLUE_STAINED_HARDENED_CLAY);
            case "brown_Clay": return new CTMapColor(BROWN_STAINED_HARDENED_CLAY);
            case "green_clay": return new CTMapColor(GRAY_STAINED_HARDENED_CLAY);
            case "red_clay": return new CTMapColor(RED_STAINED_HARDENED_CLAY);
            case "black_clay": return new CTMapColor(BLACK_STAINED_HARDENED_CLAY);
            default: return new CTMapColor(AIR);
        }
    }
}
