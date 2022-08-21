package surreal.contentcreator.common.fluid;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.ClientProxy;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.CTSoundEvent;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.fluid.Fluid")
public class FluidBase extends Fluid {
    private static final List<ResourceLocation> TEXTURES = ClientProxy.fluidTextures;

    public static final ResourceLocation STILL_DEFAULT = new ResourceLocation(ModValues.MODID, "blocks/fluids/base_still");
    public static final ResourceLocation FLOW_DEFAULT = new ResourceLocation(ModValues.MODID, "blocks/fluids/base_flow");

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
        ResourceLocation still = stillLocation != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + stillLocation) : STILL_DEFAULT;
        ResourceLocation flow = flowingLocation != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + flowingLocation) : FLOW_DEFAULT;

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
    public FluidBase setRare(String rarity) {
        return (FluidBase) super.setRarity(CTUtil.getRarity(rarity));
    }

    @ZenMethod("setFillSound")
    public FluidBase setFill(CTSoundEvent soundevent) {
        return (FluidBase) super.setFillSound(soundevent.getInternal());
    }

    @ZenMethod("setEmptySound")
    public FluidBase setEmpty(CTSoundEvent soundevent) {
        return (FluidBase) super.setEmptySound(soundevent.getInternal());
    }

    @ZenMethod("setColor")
    public FluidBase setCol(int color) {
        return (FluidBase) super.setColor(GeneralUtil.getColorFromInt(color));
    }
}
