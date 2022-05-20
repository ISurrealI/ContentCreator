package surreal.contentcreator.common.item;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import surreal.contentcreator.common.block.BlockBase;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ItemBlockBase extends ItemBlock {
    public BlockBase blockBase;

    public ItemBlockBase(BlockBase block) {
        super(block);
        this.blockBase = block;
        this.setRegistryName(Objects.requireNonNull(block.getRegistryName())).setUnlocalizedName(block.getUnlocalizedName());
        this.setHasSubtypes(true);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        return blockBase.ITEMUNLOCNAME != null ? blockBase.ITEMUNLOCNAME.getUnlocalizedName(CraftTweakerMC.getIItemStack(stack)) : super.getUnlocalizedName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
