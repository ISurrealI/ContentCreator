package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IExplosion;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IDestroyedExplosion")
public interface IBlockDestroyExplosionFunc {
    void onBlockExploded(IWorld world, IBlockPos pos, IExplosion explosion);
}
