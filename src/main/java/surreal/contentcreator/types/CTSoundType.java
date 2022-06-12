package surreal.contentcreator.types;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.util.GeneralUtil;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.block.SoundType")
public class CTSoundType {
    private final SoundType type;

    public CTSoundType(SoundType type) {
        this.type = type;
    }

    @ZenMethod
    public float getVolume() {
        return type.volume;
    }

    @ZenMethod
    public float getPitch() {
        return type.pitch;
    }

    @ZenMethod
    public SoundEvent getBreakSound() {
        return this.type.getBreakSound();
    }

    @ZenMethod
    public SoundEvent getStepSound() {
        return this.type.getStepSound();
    }

    @ZenMethod
    public SoundEvent getPlaceSound() {
        return this.type.getPlaceSound();
    }

    @ZenMethod
    public SoundEvent getHitSound() {
        return this.type.getHitSound();
    }

    @ZenMethod
    public SoundEvent getFallSound() {
        return this.type.getFallSound();
    }

    @ZenMethod("get")
    public static CTSoundType getCT(String name) {
        return GeneralUtil.get(name) != null ? new CTSoundType(GeneralUtil.get(name)) : null;
    }

    public SoundType getInternal() {
        return type;
    }
}
