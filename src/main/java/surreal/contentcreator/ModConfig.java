package surreal.contentcreator;

import net.minecraftforge.common.config.Config;

@Config(modid = ModValues.MODID)
public class ModConfig {
    public static ModConfig CONFIG = new ModConfig();

    @Config.Comment("generates resources file")
    public boolean generateFiles = true;
}
