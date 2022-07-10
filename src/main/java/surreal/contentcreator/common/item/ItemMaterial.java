package surreal.contentcreator.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.types.CTPart;
import surreal.contentcreator.util.GeneralUtil;

import javax.annotation.Nonnull;

public class ItemMaterial extends Item {
    public CTPart part;
    public CTMaterial[] MATERIAL_ARRAY;

    public ItemMaterial(CTPart type) {
        this.part = type;
        this.MATERIAL_ARRAY = type.materials.toArray(new CTMaterial[type.materials.size()]);

        this.setRegistryName(ModValues.MODID, type.name);
        this.setUnlocalizedName(ModValues.MODID + "." + type.name);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        CTMaterial material = MATERIAL_ARRAY[stack.getMetadata()];
        return I18n.format(ModValues.MODID + "." + this.part.name, GeneralUtil.toUppercase(material.name));
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < this.part.materials.size(); i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }


    public String getModelLocation(CTMaterial material) {
        return ModValues.MODID + ":part/" + material.textureType + "/" + this.part.texName;
    }
}
