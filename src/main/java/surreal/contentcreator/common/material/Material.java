package surreal.contentcreator.common.material;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenClass("mods.contentcreator.Material")
public class Material {
    public static List<Material> MATERIALS = new ArrayList<>();

    private final String name;
    private int color;

    public Material(String name) {
        this.name = name;
        this.color = 0xFFFFFF;

        MATERIALS.add(this);
    }

    public String getName() {
        return name;
    }

    @ZenMethod
    public static Material get(String name) {
        return MATERIALS.stream().noneMatch(a -> a.getName().equalsIgnoreCase(name)) ? new Material(name) : getMaterial(name);
    }

    @ZenMethod
    public Material setColor(int color) {
        this.color = color;
        return this;
    }

    public int getColor() {
        return color;
    }

    public static Material getMaterial(String mat) {
        for (Material material : MATERIALS) {
            if (material.getName().equalsIgnoreCase(mat)) return material;
        }

        return null;
    }
}
