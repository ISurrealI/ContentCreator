package surreal.contentcreator.common.fluid;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTMaterial;

import javax.annotation.Nullable;

public class FluidMaterial extends FluidBase {
    private final String typeName;
    private final CTMaterial material;

    public FluidMaterial(String typeName, CTMaterial material, ResourceLocation still, ResourceLocation flowing, @Nullable ResourceLocation overlay) {
        super(typeName + "_" + material.name, still, flowing, overlay);
        this.typeName = typeName;
        this.material = material;
    }

    public static FluidMaterial create(String type, CTMaterial material, String still, String flowing, String overlay) {
        ResourceLocation ol = overlay != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + overlay) : null;
        ResourceLocation st = still != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + still) : STILL_DEFAULT;
        ResourceLocation fl = flowing != null ? new ResourceLocation(ModValues.MODID, "blocks/fluids/" + flowing) : FLOW_DEFAULT;

        if (ol != null && !TEXTURES.contains(ol)) TEXTURES.add(ol);
        if (!TEXTURES.contains(st)) TEXTURES.add(st);
        if (!TEXTURES.contains(fl)) TEXTURES.add(fl);

        return new FluidMaterial(type, material, st, fl, ol);
    }

    @Override
    public String getLocalizedName(FluidStack stack) {
        if (material != null) return I18n.format("part." + this.typeName, material.getLocalizedName());
        else return super.getLocalizedName(stack);
    }
}
