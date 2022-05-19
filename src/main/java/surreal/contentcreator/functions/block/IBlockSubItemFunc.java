package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("contentcreator.block.ISubItem")
public interface IBlockSubItemFunc {
    @ZenMethod
    boolean check(IBlockState state);
}
