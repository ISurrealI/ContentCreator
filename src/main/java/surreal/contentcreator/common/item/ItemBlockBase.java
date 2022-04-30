package surreal.contentcreator.common.item;

import net.minecraft.item.ItemBlock;
import surreal.contentcreator.common.block.BlockBase;

public class ItemBlockBase extends ItemBlock {
    public ItemBlockBase(BlockBase block) {
        super(block);
        this.setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
        this.setHasSubtypes(block.itemCount > 1);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
