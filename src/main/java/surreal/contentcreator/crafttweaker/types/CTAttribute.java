package surreal.contentcreator.crafttweaker.types;

import net.minecraft.entity.ai.attributes.IAttribute;
import stanhebben.zenscript.annotations.ZenMethod;

import static net.minecraft.entity.SharedMonsterAttributes.*;

public class CTAttribute {

    private final IAttribute attribute;

    public CTAttribute(IAttribute attribute) {
        this.attribute = attribute;
    }

    @ZenMethod
    public String getName() {
        return attribute.getName();
    }

    @ZenMethod
    public double clampValue(double value) {
        return attribute.clampValue(value);
    }

    @ZenMethod
    public double getDefaultValue() {
        return attribute.getDefaultValue();
    }

    @ZenMethod
    public boolean shouldWatch() {
        return attribute.getShouldWatch();
    }

    @ZenMethod
    public CTAttribute getParent() {
        IAttribute at = attribute.getParent();
        return at != null ? new CTAttribute(at) : null;
    }

    @ZenMethod
    public static CTAttribute get(String name) {
        switch (name) {
            case "max_health": return new CTAttribute(MAX_HEALTH);
            case "follow_range": return new CTAttribute(FOLLOW_RANGE);
            case "knockback_resistance": return new CTAttribute(KNOCKBACK_RESISTANCE);
            case "movement_speed": return new CTAttribute(MOVEMENT_SPEED);
            case "flying_speed": return new CTAttribute(FLYING_SPEED);
            case "attack_damage": return new CTAttribute(ATTACK_DAMAGE);
            case "attack_speed": return new CTAttribute(ATTACK_SPEED);
            case "armor": return new CTAttribute(ARMOR);
            case "armor_toughness": return new CTAttribute(ARMOR_TOUGHNESS);
            case "luck": return new CTAttribute(LUCK);
            default: return null;
        }
    }
}
