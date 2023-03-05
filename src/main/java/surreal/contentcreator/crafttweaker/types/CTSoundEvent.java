package surreal.contentcreator.crafttweaker.types;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.crafttweaker.RegistryManager;

@ZenClass("contentcreator.sound.SoundEvent")
public class CTSoundEvent {

    private final SoundEvent event;

    public CTSoundEvent(SoundEvent event) {
        this.event = event;
    }

    @ZenMethod
    public String getName() {
        return event.getSoundName().toString();
    }

    @ZenMethod
    public static CTSoundEvent get(String name) {
        return new CTSoundEvent(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(name)));
    }

    @ZenMethod
    public static CTSoundEvent register(String name) {
        return new CTSoundEvent(RegistryManager.INSTANCE.add(new SoundEvent(new ResourceLocation(name))));
    }
}
