package surreal.contentcreator.types;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.fluid.FluidMaterial;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.functions.item.IItemEffectHaloColor;
import surreal.contentcreator.functions.item.IItemEffectHaloSpread;
import surreal.contentcreator.functions.item.IItemEffectHaloTexture;
import surreal.contentcreator.functions.item.IItemEffectPulse;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.parts.PartBlock;
import surreal.contentcreator.types.parts.PartItem;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.IHaloItem;

import java.util.*;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.material.Material")
public class CTMaterial {
    public static final Map<String, CTMaterial> MATERIALS = Maps.newHashMap();
    public static int ID = 0;

    public int id;
    public String name;
    public int color;
    public String[] ores;

    public String textureType = "default";

    public String rarity = "common";

    public List<String> tooltips;
    public Map<String, Object> properties;

    public Map<String, FluidBase> fluids = new HashMap<>();

    public boolean enchantedEffect;

    public Material blockMaterial = null;

    // Background
    public IItemEffectHaloTexture HALOTEXTURE = null;
    public IItemEffectHaloSpread HALOSPREAD = null;
    public IItemEffectHaloColor HALOCOLOR = null;

    public IItemEffectPulse EFFECTPULSE = null;

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
    public CTMaterial setRarity(String rarity) {
        this.rarity = rarity;
        return this;
    }

    @ZenMethod
    public CTMaterial addTooltip(String... tooltips) {
        if (this.tooltips == null) this.tooltips = new ArrayList<>();
        Collections.addAll(this.tooltips, tooltips);
        return this;
    }

    @ZenMethod
    public CTMaterial addItem(String type, @Optional String oreName) {
        PartItem part = PartItem.PARTS.containsKey(type) ? PartItem.PARTS.get(type) : new PartItem(type, (oreName == null ? type : oreName));
        part.materials.add(this);
        return this;
    }

    @ZenMethod
    public CTMaterial addBlock(String type, @Optional String oreName) {
        PartBlock part = PartBlock.PARTS.containsKey(type) ? PartBlock.PARTS.get(type) : new PartBlock(type, (oreName == null ? type : oreName));
        part.materials.add(this);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, boolean prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, byte prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, short prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, int prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, long prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, float prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, double prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
        return this;
    }

    @ZenMethod
    public CTMaterial addProperty(String name, String prop) {
        if (properties == null) properties = new HashMap<>();
        if (!properties.containsKey(name)) properties.put(name, prop);
        else properties.replace(name, prop);
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

    @ZenMethod
    public CTMaterial addBlocks(String... type) {
        for (String str : type) {
            PartBlock part = PartBlock.PARTS.containsKey(str) ? PartBlock.PARTS.get(str) : new PartBlock(str, str);
            part.materials.add(this);
        }

        return this;
    }

    @ZenMethod
    public CTMaterial addFluid(String name, int temperature, @Optional int density, @Optional int viscosity, @Optional boolean block, @Optional String overlay) {
        FluidBase fluid = FluidMaterial.create(name, this, name + "_still", name + "_flow", overlay).setCol(this.color).setRare(this.rarity).addBucket();

        this.fluids.put(name, fluid);
        fluid.setDen(density).setVis(viscosity);
        if (block) {
            IMaterial material = temperature >= FluidRegistry.LAVA.getTemperature() ? CraftTweakerMC.getIMaterial(Material.LAVA) : CraftTweakerMC.getIMaterial(Material.WATER);
            fluid.addBlock(material);
        }
        addProperty("temperature." + name, temperature);

        return this;
    }

    @ZenMethod
    public CTMaterial addPropertyInfo() {
        if (tooltips == null) tooltips = new ArrayList<>();
        if (properties.size() > 0) {
            for (Map.Entry<String, Object> prop : properties.entrySet()) {
                tooltips.add(I18n.format(prop.getKey()) + ": " + prop.getValue());
            }
        } else CraftTweakerAPI.logWarning("There's no property to add to info. Skipping!");
        return this;
    }

    @ZenGetter("localizedName")
    public String getLocalizedName() {
        return I18n.hasKey("material." + this.name) ? I18n.format("material." + this.name) : WordUtils.capitalizeFully(this.name, '_').replace("_", " ");
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
    public IItemStack getItem(String part) {
        ItemMaterial item = CommonProxy.MAT_ITEMS.get(part);
        if (item == null) {
            CraftTweakerAPI.logError("The part " + part + " doesn't exist. Returning null!");
            return null;
        }

        return new MCItemStack(new ItemStack(item, 1, this.id));
    }

    @ZenMethod
    public IItemStack getBlock(String part) {
        Pair<Block, Integer> pair = CommonProxy.getBlockAndMetaFromMaterial(part, this);
        if (pair != null) return CraftTweakerMC.getIItemStack(new ItemStack(pair.getKey(), 1, pair.getValue()));
        return null;
    }

    @ZenMethod
    public IBlockState getBlockState(String part) {
        Pair<Block, Integer> pair = CommonProxy.getBlockAndMetaFromMaterial(part, this);
        if (pair != null) return CraftTweakerMC.getBlockState(pair.getKey().getStateFromMeta(pair.getValue()));
        return null;
    }

    @ZenMethod
    public ILiquidStack getFluid(String part) {
        Fluid fluid = this.fluids.get(part);
        if (fluid != null) return CraftTweakerMC.getILiquidStack(new FluidStack(fluid, 1));
        return null;
    }

    @ZenMethod
    public boolean getBool(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Boolean) {
            return (boolean) properties.get(name);
        }

        return false;
    }

    @ZenMethod
    public byte getByte(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Byte) {
            return (byte) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public short getShort(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Short) {
            return (short) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public int getInt(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Integer) {
            return (int) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public float getFloat(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Float) {
            return (float) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public double getDouble(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Double) {
            return (double) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public long getLong(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof Long) {
            return (long) properties.get(name);
        }

        return 0;
    }

    @ZenMethod
    public String getString(String name) {
        if (properties != null && properties.containsKey(name) && properties.get(name) instanceof String) {
            return (String) properties.get(name);
        }

        return null;
    }

    @ZenMethod
    public CTMaterial setBackgroundTexture(IItemEffectHaloTexture func) {
        this.HALOTEXTURE = func;
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundTexture(String texture) {
        this.HALOTEXTURE = (world, player, stack) -> texture;
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundTexture(int halo) {
        this.HALOTEXTURE = (world, player, stack) -> IHaloItem.getHaloTexture(halo);
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundSize(IItemEffectHaloSpread func) {
        this.HALOSPREAD = func;
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundSize(int size) {
        this.HALOSPREAD = (world, player, stack) -> size;
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundColor(IItemEffectHaloColor func) {
        this.HALOCOLOR = func;
        return this;
    }

    @ZenMethod
    public CTMaterial setBackgroundColor(int color) {
        this.HALOCOLOR = (world, player, stack) -> color;
        return this;
    }

    @ZenMethod
    public CTMaterial setPulseEffect(IItemEffectPulse func) {
        this.EFFECTPULSE = func;
        return this;
    }

    @ZenMethod
    public CTMaterial setPulseEffect() {
        this.EFFECTPULSE = (world, player, stack) -> true;
        return this;
    }

    @ZenMethod
    public CTMaterial setEnchanted() {
        this.enchantedEffect = true;
        return this;
    }

    @ZenMethod
    public CTMaterial setMaterial(IMaterial material) {
        this.blockMaterial = CraftTweakerMC.getMaterial(material);
        return this;
    }

    @ZenMethod
    public static CTMaterial get(String name, @Optional int color) {
        CTMaterial material = MATERIALS.get(name);
        if (material == null) {
            material = new CTMaterial(ID, name, (color == 0 ? 0xFFFFFF : color));
            ID++;
        }

        return material;
    }

    @ZenMethod
    public static CTMaterial get(int id, String name, @Optional int color) {
        CTMaterial material = MATERIALS.get(name);
        if (material != null) material.id = id;
        else material = new CTMaterial(id, name, (color == 0 ? 0xFFFFFF : color));
        return material;
    }
}
