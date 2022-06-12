package surreal.contentcreator.client;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.ClientProxy;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.textures.TextureMap")
public class MCTextureMap {
    @ZenMethod
    public static void registerSprite(String path) {
        ClientProxy.texturesToRegister.add(ModValues.MODID + ":" + path);
    }
}
