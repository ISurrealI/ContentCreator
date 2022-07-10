package surreal.contentcreator.types;

import com.google.common.collect.Maps;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@ZenRegister
@ZenClass("contentcreator.material.Material")
public class CTMaterial {
    public static final Map<String, CTMaterial> MATERIALS = Maps.newHashMap();

    public String name;
    public int color;

    public String textureType = "default";

    private CTMaterial(String name, int color) {
        this.name = name;
        this.color = color;

        MATERIALS.put(name, this);
    }

    @ZenMethod
    public CTMaterial type(String type, @Optional String texName) {
        CTPart part = CTPart.PARTS.containsKey(type) ? CTPart.PARTS.get(type) : new CTPart(type, (texName == null ? type : texName));
        part.materials.add(this);
        return this;
    }

    @ZenMethod
    public CTMaterial types(String... type) {
        for (String str : type) {
            CTPart part = CTPart.PARTS.containsKey(str) ? CTPart.PARTS.get(str) : new CTPart(str, str);
            part.materials.add(this);
        }

        return this;
    }

    @ZenMethod
    public CTMaterial setTexture(String type) {
        this.textureType = type;
        return this;
    }

    @ZenMethod
    public static CTMaterial get(String name, @Optional int color) {
        CTMaterial material = MATERIALS.get(name);
        return material == null ? new CTMaterial(name, (color == 0 ? 0xFFFFFF : color)) : material;
    }
}
