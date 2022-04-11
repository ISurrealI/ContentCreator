package surreal.contentcreator.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import surreal.contentcreator.ContentCreator;

import java.util.List;
import java.util.Map;

@BracketHandler
@ZenRegister
public class ItemBracketHandler implements IBracketHandler {
    public static Map<String, ItemStack> itemMap = null;

    private final IJavaMethod method;

    public ItemBracketHandler() {
        this.method = CraftTweakerAPI.getJavaMethod(ItemBracketHandler.class, "getItem", String.class);
    }

    @SuppressWarnings("unused")
    public static IItemStack getItem(String name) {
        ItemStack stack = itemMap.getOrDefault(name, ItemStack.EMPTY);

        if (stack.isEmpty()) {
            ContentCreator.getLogger().error("There's no such item called " + name + "!");
            return null;
        }

        return new MCItemStack(stack);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if ((tokens.size() < 3)) return null;
        if (!tokens.get(0).getValue().equalsIgnoreCase("ccitem")) return null;
        if (!tokens.get(1).getValue().equals(":")) return null;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < tokens.size(); i++) {
            nameBuilder.append(tokens.get(i).getValue());
        }
        return position -> new ExpressionCallStatic(position, environment, method,
                new ExpressionString(position, nameBuilder.toString()));
    }
}
