package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IStateBoolean")
public interface IBlockStateBooleanFunc {
    boolean check(IBlockState state);
}
