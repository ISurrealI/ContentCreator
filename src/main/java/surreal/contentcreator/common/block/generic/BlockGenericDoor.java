package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericDoor extends BlockDoor implements IGenericBlock {
    protected BlockGenericDoor(Material materialIn) {
        super(materialIn);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    @Override
    public Item createItem(Block block) {
        return new ItemDoor(block).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
    }
}
