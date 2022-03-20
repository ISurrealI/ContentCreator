package surreal.contentcreator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import surreal.contentcreator.proxy.CommonProxy;

import static surreal.contentcreator.ModValues.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = "required-after:crafttweaker")
public class ContentCreator {
    private static final Logger LOGGER = LogManager.getLogger(MODID);

    @SidedProxy(serverSide = COMMON, clientSide = CLIENT)
    private static CommonProxy PROXY;

    public static Logger getLogger() {
        return LOGGER;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
    }
}
