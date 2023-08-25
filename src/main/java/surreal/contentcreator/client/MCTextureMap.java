package surreal.contentcreator.client;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.ClientProxy;

@SuppressWarnings("unused")

@SideOnly(Side.CLIENT)
@ZenRegister
@ZenClass("contentcreator.textures.TextureMap")
public class MCTextureMap {
    @ZenMethod
    public static void registerSprite(String path) {
        ClientProxy.texturesToRegister.add(ModValues.MODID + ":" + path);
    }
}
