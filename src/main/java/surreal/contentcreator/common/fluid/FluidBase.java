package surreal.contentcreator.common.fluid;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.ClientProxy;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.util.CTUtil;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("mods.contentcreator.Fluid")
public class FluidBase extends Fluid {
    private static final List<ResourceLocation> TEXTURES = ClientProxy.fluidTextures;

    public boolean bucket = false;
    public Material blockMaterial = null;

    public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing, @Nullable ResourceLocation overlay) {
        super(fluidName, still, flowing, overlay);
        CommonProxy.FLUIDS.add(this);
        this.setDensity(3000).setViscosity(6000);
    }

    @ZenMethod
    public static FluidBase create(String name, @Optional String stillLocation, @Optional String flowingLocation, @Optional String overlay) {
        ResourceLocation ol = overlay != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + overlay) : null;
        ResourceLocation still = stillLocation != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + stillLocation) : new ResourceLocation(ModValues.MODID, "blocks/fluids/base_still");
        ResourceLocation flow = flowingLocation != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + flowingLocation) : new ResourceLocation(ModValues.MODID, "blocks/fluids/base_flow");

        if (ol != null && !TEXTURES.contains(ol)) TEXTURES.add(ol);
        if (!TEXTURES.contains(still)) TEXTURES.add(still);
        if (!TEXTURES.contains(flow)) TEXTURES.add(flow);

        return new FluidBase(name, still, flow, ol);
    }

    @ZenMethod
    public FluidBase addBlock(IMaterial material) {
        this.blockMaterial = CraftTweakerMC.getMaterial(material);
        return this;
    }

    @ZenMethod("setUnlocalizedName")
    public FluidBase setUName(String name) {
        return (FluidBase) super.setUnlocalizedName(name);
    }

    @ZenMethod("setLuminosity")
    public FluidBase setLum(int luminosity) {
        return (FluidBase) super.setLuminosity(luminosity);
    }

    @ZenMethod("setDensity")
    public FluidBase setDen(int density) {
        return (FluidBase) super.setDensity(density);
    }

    @ZenMethod("setTemperature")
    public FluidBase setTemp(int temperature) {
        return (FluidBase) super.setTemperature(temperature);
    }

    @ZenMethod("setViscosity")
    public FluidBase setVis(int viscosity) {
        return (FluidBase) super.setViscosity(viscosity);
    }

    @ZenMethod("setGaseous")
    public FluidBase setGaseous() {
        return (FluidBase) super.setGaseous(true);
    }

    @ZenMethod
    public FluidBase addBucket() {
        this.bucket = true;
        return this;
    }

    @ZenMethod("setRarity")
    public Fluid setRare(String rarity) {
        return super.setRarity(CTUtil.getRarity(rarity));
    }

    @ZenMethod("setFillSound")
    public FluidBase setFill(String soundevent) {
        SoundEvent event = CTUtil.getSound(soundevent);

        if (event != null) return (FluidBase) super.setFillSound(event);
        return this;
    }

    @ZenMethod("setEmptySound")
    public FluidBase setEmpty(String soundevent) {
        SoundEvent event = CTUtil.getSound(soundevent);

        if (event != null) return (FluidBase) super.setEmptySound(event);
        return this;
    }

    @ZenMethod("setColor")
    public FluidBase setCol(int color) {
        return (FluidBase) super.setColor(color);
    }
}
