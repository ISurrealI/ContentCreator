package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import surreal.contentcreator.types.CTSoundType;

import java.util.Random;

// idk why i added this. We might see a funny pack using this lol
@SuppressWarnings("all")
public class BlockGenericCocoa extends BlockCocoa implements IGenericBlock {
    private final ItemStack drop;
    private final int minDrop;

    public BlockGenericCocoa(ItemStack stack, int minDrop) {
        this.drop = stack;
        this.minDrop = minDrop;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return drop;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(AGE) >= 2 ? minDrop + drop.getCount() : 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return drop.getItem();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return drop.getMetadata();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
