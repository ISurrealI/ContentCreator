package surreal.contentcreator.types;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.parts.PartItem;
import surreal.contentcreator.util.GeneralUtil;

import java.util.Map;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.material.Material")
public class CTMaterial {
    public static final Map<String, CTMaterial> MATERIALS = Maps.newHashMap();

    public int id;
    public String name;
    public int color;
    public String[] ores;

    public String textureType = "default";

    private CTMaterial(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.ores = new String[] { GeneralUtil.toUppercase(CaseFormat.LOWER_UNDERSCORE, CaseFormat.UPPER_CAMEL, name) };

        MATERIALS.put(name, this);
    }

    @ZenMethod
    public CTMaterial setColor(int color) {
        this.color = color;
        return this;
    }

    @ZenMethod
    public CTMaterial setTexture(String type) {
        this.textureType = type;
        return this;
    }

    @ZenMethod
    public CTMaterial setOres(String... ores) {
        this.ores = ores;
        return this;
    }

    @ZenMethod
    public CTMaterial addItem(String type, @Optional String oreName) {
        PartItem part = PartItem.PARTS.containsKey(type) ? PartItem.PARTS.get(type) : new PartItem(type, (oreName == null ? type : oreName));
        part.materials.add(this);
        return this;
    }

    @ZenMethod
    public CTMaterial addItems(String... type) {
        for (String str : type) {
            PartItem part = PartItem.PARTS.containsKey(str) ? PartItem.PARTS.get(str) : new PartItem(str, str);
            part.materials.add(this);
        }

        return this;
    }

    @ZenGetter("localizedName")
    public String getLocalizedName() {
        return I18n.hasKey(this.name) ? I18n.format(this.name) : WordUtils.capitalizeFully(this.name, '_').replace("_", " ");
    }

    @ZenGetter("name")
    public String getName() {
        return this.name;
    }

    @ZenGetter("color")
    public int getColor() {
        return this.color;
    }

    @ZenMethod
    public IItemStack getPart(String part) {
        ItemMaterial item = CommonProxy.MAT_ITEMS.get(part);
        if (item == null) {
            CraftTweakerAPI.logError("The part " + part + " doesn't exist. Returning null!");
            return null;
        }

        return new MCItemStack(new ItemStack(item, 1, this.id));
    }

    @ZenMethod
    public static CTMaterial get(String name, @Optional int color) {
        CTMaterial material = MATERIALS.get(name);
        return material == null ? new CTMaterial(MATERIALS.size(), name, (color == 0 ? 0xFFFFFF : color)) : material;
    }

    @ZenMethod
    public static CTMaterial get(int id, String name, @Optional int color) {
        CTMaterial material = MATERIALS.get(name);
        return material == null ? new CTMaterial(id, name, (color == 0 ? 0xFFFFFF : color)) : material;
    }
}
