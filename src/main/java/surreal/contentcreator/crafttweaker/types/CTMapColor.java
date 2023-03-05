package surreal.contentcreator.crafttweaker.types;

import net.minecraft.block.material.MapColor;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.crafttweaker.brackets.MapColorBracket;

@ZenClass("contentcreator.block.MapColor")
public class CTMapColor {

    private final MapColor color;

    public CTMapColor(MapColor color) {
        this.color = color;
    }

    @ZenGetter
    public int index() {
        return color.colorIndex;
    }

    @ZenGetter
    public int value() {
        return color.colorValue;
    }

    @ZenMethod
    public int getColor(int index) {
        return color.getMapColor(index);
    }

    @ZenMethod
    public static CTMapColor get(String name) {
        return MapColorBracket.get(name);
    }
}
