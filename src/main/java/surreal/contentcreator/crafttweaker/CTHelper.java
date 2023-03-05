package surreal.contentcreator.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.util.ResourceLocation;
import surreal.contentcreator.ContentCreator;
import surreal.contentcreator.crafttweaker.brackets.BlockMaterialBracket;
import surreal.contentcreator.crafttweaker.brackets.MapColorBracket;
import surreal.contentcreator.crafttweaker.brackets.SoundEventBracket;
import surreal.contentcreator.crafttweaker.brackets.SoundTypeBracket;
import surreal.contentcreator.crafttweaker.types.CTMapColor;
import surreal.contentcreator.crafttweaker.types.CTSoundEvent;
import surreal.contentcreator.crafttweaker.types.CTSoundType;

public class CTHelper {

    public static ResourceLocation getResource(String name) {

        if (name.contains(":")) {
            StringBuilder builder = new StringBuilder();
            String[] splittedStr = name.split(":");

            for (int i = 1; i < splittedStr.length; i++) {
                builder.append(splittedStr[i]);
                if (i + 1 != splittedStr.length) builder.append('_');
            }

            name = builder.toString();
        }

        return new ResourceLocation(ContentCreator.MODID, name);
    }

    public static void registerCT() {

        RegistryManager.INSTANCE = new RegistryManager();

        // Types
        CraftTweakerAPI.registerClass(CTMapColor.class);
        CraftTweakerAPI.registerClass(CTSoundEvent.class);
        CraftTweakerAPI.registerClass(CTSoundType.class);

        // Brackets
        CraftTweakerAPI.registerBracketHandler(new BlockMaterialBracket());
        CraftTweakerAPI.registerBracketHandler(new MapColorBracket());
        CraftTweakerAPI.registerBracketHandler(new SoundEventBracket());
        CraftTweakerAPI.registerBracketHandler(new SoundTypeBracket());
    }
}
