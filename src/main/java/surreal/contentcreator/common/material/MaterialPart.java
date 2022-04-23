package surreal.contentcreator.common.material;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenClass("mods.contentcreator.MaterialPart")
public class MaterialPart {
    public static List<MaterialPart> TYPES = new ArrayList<>();
    public static MaterialPart ingot, gem, plate, gear;

    private final String name;
    private String oreName;

    private List<Material> materials;

    public MaterialPart(String name) {
        this.name = name;
        this.oreName = name;

        TYPES.add(this);
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public String getName() {
        return name;
    }

    @ZenMethod
    public MaterialPart add(Material... material) {
        if (materials == null) materials = new ArrayList<>();
        materials.addAll(Arrays.asList(material));
        return this;
    }

    public static MaterialPart getPart(String name) {
        for (MaterialPart part : TYPES) {
            if (part.name.equalsIgnoreCase(name)) return part;
        }

        return null;
    }

    @ZenMethod
    public static MaterialPart get(String name) {
        return TYPES.stream().noneMatch(a -> a.name.equalsIgnoreCase(name)) ? new MaterialPart(name) : getPart(name);
    }

    @ZenMethod
    public MaterialPart setOre(String oreName) {
        this.oreName = oreName;
        return this;
    }

    public String getOreName() {
        return oreName;
    }

    public static void init() {
        ingot = new MaterialPart("ingot");
        gem = new MaterialPart("gem");
    }
}
