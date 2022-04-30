package surreal.contentcreator.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("contentcreator.block.IItemBlockCheck")
public interface IItemBlockCheck {
    @ZenMethod
    boolean check(IBlockState state);
}
