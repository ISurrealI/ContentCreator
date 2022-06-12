package surreal.contentcreator.types;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.CommonProxy;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.sound.SoundEvent")
public class CTSoundEvent {
    private final SoundEvent event;

    public CTSoundEvent(SoundEvent event) {
        this.event = event;
    }

    @ZenMethod
    public String getName() {
        return this.event.getSoundName().toString();
    }

    @ZenMethod
    public static CTSoundEvent get(String name) {
        for (SoundEvent event : SoundEvent.REGISTRY) {
            if (event.getSoundName().toString().equalsIgnoreCase(name)) return new CTSoundEvent(event);
        }

        return null;
    }

    @ZenMethod
    public static SoundEvent create(String name) {
        SoundEvent event = new SoundEvent(new ResourceLocation(ModValues.MODID, name));
        CommonProxy.SOUNDS.add(event);
        return event;
    }

    public SoundEvent getInternal() {
        return event;
    }
}
