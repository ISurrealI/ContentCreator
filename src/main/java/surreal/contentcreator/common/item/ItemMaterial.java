package surreal.contentcreator.common.item;

import com.google.common.base.CaseFormat;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.material.MaterialPart;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.util.GeneralUtil;

public class ItemMaterial extends Item {
    private final MaterialPart type;

    public ItemMaterial(MaterialPart type) {
        this.type = type;

        this.setRegistryName(ModValues.MODID, type.getName()).setUnlocalizedName(ModValues.MODID + "." + type.getName());
        this.setCreativeTab(CreativeTabs.SEARCH);
        this.setHasSubtypes(type.getMaterials() != null && type.getMaterials().size() > 1);

        CommonProxy.MATERIAL_ITEMS.add(this);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (type.getMaterials() == null) return super.getItemStackDisplayName(stack);

        return I18n.format(getUnlocalizedName() + ".name", GeneralUtil.toUppercase(type.getMaterials().get(stack.getMetadata()).getName()));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (type.getMaterials() == null) super.getSubItems(tab, items);

        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < type.getMaterials().size(); i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    public static ItemMaterial getItemFromPart(MaterialPart part) {
        for (ItemMaterial item : CommonProxy.MATERIAL_ITEMS) {
            if (item.getType() == part) return item;
        }

        return null;
    }

    public String getOre(int i) {
        if (type.getMaterials() == null) return null;

        return type.getOreName() + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, type.getMaterials().get(i).getName());
    }

    public MaterialPart getType() {
        return type;
    }
}
