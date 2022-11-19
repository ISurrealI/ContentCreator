package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import surreal.contentcreator.ContentCreator;
import surreal.contentcreator.types.CTSoundType;

import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class BlockGenericCrop extends BlockCrops implements IGenericBlock {
    private ItemStack s, c;
    private int cMin;

    public BlockGenericCrop(String c, int meta, int cMin) {
        this.c = GameRegistry.makeItemStack(c, meta, 1, null);
        this.cMin = Math.max(cMin, 1);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (age >= getMaxAge()) {
            for (int i = 0; i < rand.nextInt(cMin) + 1; i++) {
                ContentCreator.getLogger().info("Adding " + c.getItem().getRegistryName() + " to drops.");
                drops.add(c);
            }
        }

        drops.add(s);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops) {
                if (worldIn.rand.nextFloat() <= chance)
                    spawnAsEntity(worldIn, pos, drop);
            }
        }
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

    @Override
    public Item createItem(Block block) {
        Item seeds = new ItemSeeds(block, Blocks.FARMLAND).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
        this.s = new ItemStack(seeds);
        return seeds;
    }
}
