package surreal.contentcreator.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;

import static net.minecraft.block.material.Material.*;

@ZenRegister
@BracketHandler
public class BlockMaterialBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public BlockMaterialBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(BlockMaterialBracketHandler.class, "get", String.class);
    }

    public static IMaterial get(String name) {
        switch (name) {
            case "grass": return CraftTweakerMC.getIMaterial(GRASS);
            case "ground": return CraftTweakerMC.getIMaterial(GROUND);
            case "wood": return CraftTweakerMC.getIMaterial(WOOD);
            case "rock": return CraftTweakerMC.getIMaterial(ROCK);
            case "iron": return CraftTweakerMC.getIMaterial(IRON);
            case "anvil": return CraftTweakerMC.getIMaterial(ANVIL);
            case "water": return CraftTweakerMC.getIMaterial(WATER);
            case "lava": return CraftTweakerMC.getIMaterial(LAVA);
            case "leaves": return CraftTweakerMC.getIMaterial(LEAVES);
            case "plants": return CraftTweakerMC.getIMaterial(PLANTS);
            case "vine": return CraftTweakerMC.getIMaterial(VINE);
            case "sponge": return CraftTweakerMC.getIMaterial(SPONGE);
            case "cloth": return CraftTweakerMC.getIMaterial(CLOTH);
            case "fire": return CraftTweakerMC.getIMaterial(FIRE);
            case "sand": return CraftTweakerMC.getIMaterial(SAND);
            case "circuits": return CraftTweakerMC.getIMaterial(CIRCUITS);
            case "carpet": return CraftTweakerMC.getIMaterial(CARPET);
            case "glass": return CraftTweakerMC.getIMaterial(GLASS);
            case "redstone_light": return CraftTweakerMC.getIMaterial(REDSTONE_LIGHT);
            case "tnt": return CraftTweakerMC.getIMaterial(TNT);
            case "coral": return CraftTweakerMC.getIMaterial(CORAL);
            case "ice": return CraftTweakerMC.getIMaterial(ICE);
            case "packed_ice": return CraftTweakerMC.getIMaterial(PACKED_ICE);
            case "snow": return CraftTweakerMC.getIMaterial(SNOW);
            case "crafted_snow": return CraftTweakerMC.getIMaterial(CRAFTED_SNOW);
            case "cactus": return CraftTweakerMC.getIMaterial(CACTUS);
            case "clay": return CraftTweakerMC.getIMaterial(CLAY);
            case "gourd": return CraftTweakerMC.getIMaterial(GOURD);
            case "dragon_egg": return CraftTweakerMC.getIMaterial(DRAGON_EGG);
            case "portal": return CraftTweakerMC.getIMaterial(PORTAL);
            case "cake": return CraftTweakerMC.getIMaterial(CAKE);
            case "web": return CraftTweakerMC.getIMaterial(WEB);
            case "piston": return CraftTweakerMC.getIMaterial(PISTON);
            case "barrier": return CraftTweakerMC.getIMaterial(BARRIER);
            case "structure_void": return CraftTweakerMC.getIMaterial(STRUCTURE_VOID);
            default: return CraftTweakerMC.getIMaterial(AIR);
        }
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("blockmaterial")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue().toLowerCase());
        }

        return position -> new ExpressionCallStatic(position, environment, method,
                new ExpressionString(position, nameBuilder.toString()));
    }
}
