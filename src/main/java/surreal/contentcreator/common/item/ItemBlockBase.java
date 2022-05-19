package surreal.contentcreator.common.item;

import net.minecraft.item.ItemBlock;
import surreal.contentcreator.common.block.BlockBase;

public class ItemBlockBase extends ItemBlock {
    public BlockBase blockBase;

    public ItemBlockBase(BlockBase block) {
        super(block);
        this.blockBase = block;
        this.setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
