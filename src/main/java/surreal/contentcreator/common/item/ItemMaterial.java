package surreal.contentcreator.common.item;

import com.google.common.collect.Maps;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.types.parts.PartItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemMaterial extends Item {
    public PartItem part;

    public Map<Integer, CTMaterial> MATERIALS;

    public ItemMaterial(PartItem type) {
        this.part = type;
        this.MATERIALS = Maps.newHashMap();
        for (CTMaterial material : type.materials) {
            MATERIALS.put(material.id, material);
        }

        this.setRegistryName(ModValues.MODID, type.name);
        this.setUnlocalizedName(ModValues.MODID + "." + type.name);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        CTMaterial material = MATERIALS.get(stack.getMetadata());
        if (material != null) return I18n.format("part." + this.part.name, material.getLocalizedName());
        else return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (CTMaterial material : this.part.materials) {
                items.add(new ItemStack(this, 1, material.id));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        tooltip.addAll(material.tooltips);
    }

    public String getModelLocation(CTMaterial material) {
        return ModValues.MODID + ":part/" + material.textureType + "/" + this.part.name;
    }
}
