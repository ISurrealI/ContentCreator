package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IMaterial;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IMaterial")
public interface IBlockMaterialFunc {
    IMaterial getMaterial(IBlockState state);
}
