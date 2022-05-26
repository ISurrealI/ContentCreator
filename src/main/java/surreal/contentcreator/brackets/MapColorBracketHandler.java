package surreal.contentcreator.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.material.MapColor;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import surreal.contentcreator.types.CTMapColor;

import java.util.List;

public class MapColorBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public MapColorBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(MapColorBracketHandler.class, "getMapColor", String.class);
    }

    public static MapColor getMapColor(String name) {
        return CTMapColor.fromName(name);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("mapcolor")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue());
        }

        return position -> new ExpressionCallStatic(position, environment, method,
                new ExpressionString(position, nameBuilder.toString()));
    }
}
