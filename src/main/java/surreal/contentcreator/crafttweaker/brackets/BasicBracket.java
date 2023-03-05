package surreal.contentcreator.crafttweaker.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BasicBracket implements IBracketHandler {

    protected final Set<String> ids;

    protected final IJavaMethod methodGet;

    public BasicBracket(String... ids) {
        Set<String> idSet = new HashSet<>();
        for (String id : ids) idSet.add(id);

        this.ids = idSet;

        this.methodGet = CraftTweakerAPI.getJavaMethod(this.getClass(), "get", String.class);
    }


    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2 && ids.contains(tokens.get(0).getValue()) && tokens.get(1).getValue().equals(":")) {
            return position -> new ExpressionCallStatic(position, environment, methodGet, new ExpressionString(position, tokens.get(2).getValue()));
        }

        return null;
    }

    @Override
    public String getRegexMatchingString() {
        StringBuilder builder = new StringBuilder();

        builder.append('(');

        int i = 0;
        for (String str : ids) {
            i++;

            builder.append(str);
            if (i < ids.size()) builder.append('|');
        }

        builder.append(").*");

        return builder.toString();
    }
}
