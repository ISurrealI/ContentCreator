package surreal.contentcreator.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.types.CTMaterial;

import javax.annotation.Nonnull;

public class ItemBlockMaterial extends ItemBlock {
    private final BlockMaterial block;

    public ItemBlockMaterial(BlockMaterial block) {
        super(block);
        this.block = block;
        if (block.materials.length > 1) this.setHasSubtypes(true);
        this.setRegistryName(block.getRegistryName());
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        CTMaterial material = block.materials[stack.getMetadata()];
        if (material != null) return I18n.format("part." + block.part.name, material.getLocalizedName());
        else return super.getItemStackDisplayName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    public CTMaterial getMaterial(ItemStack stack) {
        return block.materials[stack.getMetadata()];
    }
}
