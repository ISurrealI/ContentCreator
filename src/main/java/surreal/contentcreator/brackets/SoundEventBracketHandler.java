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
import surreal.contentcreator.types.CTSoundEvent;

import java.util.List;

@BracketHandler
@ZenRegister
public class SoundEventBracketHandler implements IBracketHandler {
    private final IJavaMethod method;

    public SoundEventBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(SoundEventBracketHandler.class, "getSound", String.class);
    }

    public static CTSoundEvent getSound(String name) {
        return CTSoundEvent.get(name);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("soundevent")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue());
        }

        return position -> new ExpressionCallStatic(position, environment, method,
                new ExpressionString(position, nameBuilder.toString()));
    }
}
