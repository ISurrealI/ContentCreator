package surreal.contentcreator.crafttweaker.types;

import net.minecraft.block.SoundType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

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
    public CTSoundEvent getBreakSound() {
        return new CTSoundEvent(this.type.getBreakSound());
    }

    @ZenMethod
    public CTSoundEvent getStepSound() {
        return new CTSoundEvent(this.type.getStepSound());
    }

    @ZenMethod
    public CTSoundEvent getPlaceSound() {
        return new CTSoundEvent(this.type.getPlaceSound());
    }

    @ZenMethod
    public CTSoundEvent getHitSound() {
        return new CTSoundEvent(this.type.getHitSound());
    }

    @ZenMethod
    public CTSoundEvent getFallSound() {
        return new CTSoundEvent(this.type.getFallSound());
    }
}
