package surreal.contentcreator.types;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.util.GeneralUtil;

import static net.minecraft.block.SoundType.*;
import static net.minecraft.block.SoundType.SLIME;

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
        SoundType type = get(name);
        return type != null ? new CTSoundType(type) : null;
    }

    public static SoundType get(String name) {
        switch (name) {
            case "wood": return WOOD;
            case "ground": return GROUND;
            case "plant": return PLANT;
            case "stone": return STONE;
            case "metal": return METAL;
            case "glass": return GLASS;
            case "cloth": return CLOTH;
            case "sand": return SAND;
            case "snow": return SNOW;
            case "ladder": return LADDER;
            case "anvil": return ANVIL;
            case "slime": return SLIME;
            default: return null;
        }
    }

    public SoundType getInternal() {
        return type;
    }
}
