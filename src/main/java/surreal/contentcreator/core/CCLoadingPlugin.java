package surreal.contentcreator.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(CCLoadingPlugin.NAME)
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(8792)
public class CCLoadingPlugin implements IFMLLoadingPlugin {
    public static final String NAME = "Content Creator";
    public static Logger LOGGER = LogManager.getLogger(NAME);
    public static final boolean DEOBF = FMLLaunchHandler.isDeobfuscatedEnvironment();

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { CCClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
