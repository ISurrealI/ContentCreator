package surreal.contentcreator.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import surreal.contentcreator.types.CTMaterial;

import java.util.List;

@ZenRegister
@BracketHandler
public class MaterialBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public MaterialBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(MaterialBracketHandler.class, "get", String.class);
    }

    public static CTMaterial get(String name) {
        if (!CTMaterial.MATERIALS.containsKey(name)) {
            CraftTweakerAPI.logError("Couldn't find material " + name + "! Returning null!");
            return null;
        }

        return CTMaterial.MATERIALS.get(name);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("ccmaterial")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue().toLowerCase());
        }

        return position -> new ExpressionCallStatic(position, environment, method,
                new ExpressionString(position, nameBuilder.toString()));
    }
}
