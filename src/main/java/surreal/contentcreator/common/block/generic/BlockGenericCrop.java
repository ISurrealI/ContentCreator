package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import surreal.contentcreator.types.CTSoundType;

import java.util.Random;

@SuppressWarnings("all")
public class BlockGenericCrop extends BlockCrops implements IGenericBlock {
    private ItemStack s, c;
    private int cMin;

    public BlockGenericCrop(ItemStack s, ItemStack c, int cMin) {
        this.s = s;
        this.c = c;
        this.cMin = Math.max(cMin, 0);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, 0);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (age >= getMaxAge())
        {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    drops.add(s);
                }
            }
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

        if (false && !worldIn.isRemote) // Forge: NOP all this.
        {
            int i = this.getAge(state);

            if (i >= this.getMaxAge())
            {
                int j = 3 + fortune;

                for (int k = 0; k < j; ++k)
                {
                    if (worldIn.rand.nextInt(2 * this.getMaxAge()) <= i)
                    {
                        spawnAsEntity(worldIn, pos, s);
                    }
                }
            }
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return this.isMaxAge(state) ? cMin + random.nextInt(c.getCount()) : 1;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.isMaxAge(state) ? c.getMetadata() : s.getMetadata();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? c.getItem() : s.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return s;
    }

    @Override
    protected Item getCrop() {
        return Items.AIR;
    }

    @Override
    protected Item getSeed() {
        return Items.AIR;
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
