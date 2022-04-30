package surreal.contentcreator.common.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IFacing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBlockBase;
import surreal.contentcreator.functions.IItemBlockCheck;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.CTSoundType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ZenRegister
@ZenClass("contentcreator.block.BlockBase")
public class BlockBase extends Block {
    private static PropertyDirection FACING = null;
    private static PropertyInteger INT = null;

    private AxisAlignedBB AABB = FULL_BLOCK_AABB;

    private boolean PASSABLE;
    private boolean FULLCUBE = true;
    private boolean OPAQUE = true;

    private IItemBlockCheck ITEM_CHECK = null;
    private boolean added = false;
    public int itemCount = 0;

    public BlockBase(Material materialIn) {
        super(materialIn);
        if (FACING != null) this.setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
        if (INT != null) this.setDefaultState(getDefaultState().withProperty(INT, intProperty()[0]));
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (ITEM_CHECK == null) {
            super.getSubBlocks(itemIn, items);
            return;
        }

        for (IBlockState state : this.blockState.getValidStates()) {
            if (ITEM_CHECK.check(CraftTweakerMC.getBlockState(state))) {
                items.add(new ItemStack(this, 1, getMetaFromState(state)));
            }

            itemCount++;
        }
    }

    @ZenMethod
    public BlockBase addItem(IItemBlockCheck check) {
        if (!added) {
            this.ITEM_CHECK = check;
            added = true;
            CommonProxy.ITEMBLOCKS.add(new ItemBlockBase(this));
        }

        return this;
    }

    @ZenMethod
    public BlockBase addItem() {
        if (!added) {
            added = true;
            CommonProxy.ITEMBLOCKS.add(new ItemBlockBase(this));
        }

        return this;
    }

    @ZenMethod
    public static BlockBase create(IMaterial material, String name) {
        BlockBase base = new BlockBase(CraftTweakerMC.getMaterial(material));
        base.setRegistryName(ModValues.MODID, name).setUnlocalizedName(ModValues.MODID + "." + name);
        CommonProxy.BLOCKS.add(base);
        if (FACING != null || INT != null) clear();

        return base;
    }

    @ZenMethod
    public static void setFacing(String name) {
        FACING = PropertyDirection.create(name);
    }

    @ZenMethod
    public static void setFacing(String name, IFacing... facings) {
        List<EnumFacing> faces = new ArrayList<>();

        for (IFacing facing : facings) {
            faces.add(CraftTweakerMC.getFacing(facing));
        }

        FACING = PropertyDirection.create(name, faces);
    }

    @ZenMethod
    public static void setInteger(String name, int min, int max) {
        max = Math.min(max, 15);

        INT = PropertyInteger.create(name, Math.max(min, 0), max);
    }

    @ZenMethod
    public static void setInteger(String name, int max) {
        INT = PropertyInteger.create(name, 0, Math.min(max, 15));
    }

    @ZenMethod("setSoundType")
    public BlockBase setSound(CTSoundType sound) {
        return (BlockBase) super.setSoundType(sound.getInternal());
    }

    @ZenMethod("setLightOpacity")
    public BlockBase setLightOp(int opacity) {
        return (BlockBase) super.setLightOpacity(opacity);
    }

    @ZenMethod("setLightLevel")
    public BlockBase setLightLvl(float value) {
        return (BlockBase) super.setLightLevel(value);
    }

    @ZenMethod("setResistance")
    public BlockBase setRes(float resistance) {
        return (BlockBase) super.setResistance(resistance);
    }

    @ZenMethod("setHardness")
    public BlockBase setHard(float hardness) {
        return (BlockBase) super.setHardness(hardness);
    }

    @ZenMethod("setHardnessAndResistance")
    public BlockBase setHardNRes(float fl) {
        this.setHardness(fl);
        return setRes(fl);
    }

    @ZenMethod
    public BlockBase setPassable() {
        this.PASSABLE = true;
        return this;
    }

    @ZenMethod
    public BlockBase setFullCube() {
        this.FULLCUBE = false;
        return this;
    }

    @ZenMethod("setUnbreakable")
    public BlockBase setUnbreak() {
        return (BlockBase) super.setBlockUnbreakable();
    }

    @ZenMethod("setBoundingBox")
    public BlockBase setAABB(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        AABB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        return this;
    }

    @ZenMethod
    public BlockBase setSlipperiness(float slipperiness) {
        super.setDefaultSlipperiness(slipperiness);
        return this;
    }

    @ZenMethod("setHarvestLevel")
    public BlockBase setHarLevel(String name, int level) {
        super.setHarvestLevel(name, level);
        return this;
    }

    @ZenMethod
    public BlockBase setOpaque() {
        this.OPAQUE = false;
        return this;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (state.getPropertyKeys().size() > 0) {
            PropertyDirection facing = getFacing(state);
            PropertyInteger integer = getInteger(state);

            int meta = 0;
            int increment = 0;
            if (integer != null) {
                List<Integer> allowedVal = new ArrayList<>(integer.getAllowedValues());
                meta = allowedVal.indexOf(state.getValue(integer));
            }
            if (facing != null) {
                increment = Arrays.asList(EnumFacing.values()).indexOf(state.getValue(facing));
                if (integer != null) increment *= integer.getAllowedValues().size();
            }

            meta += increment;
            return meta;
        }

        return super.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (this.getDefaultState().getPropertyKeys().size() > 0) {
            PropertyDirection facing = getFacing(this.getDefaultState());
            PropertyInteger integer = getInteger(this.getDefaultState());

            IBlockState state = getDefaultState();

            int intg = -1;
            int face = -1;
            if (integer != null) {
                intg = meta % integer.getAllowedValues().size();
            }

            if (facing != null) {
                face = meta;
                if (intg >= 0) face = (int) meta / integer.getAllowedValues().size();
            }

            if (facing != null) state = state.withProperty(facing, EnumFacing.values()[face]);
            if (integer != null) state = state.withProperty(integer, intg);

            return state;
        }

        return super.getStateFromMeta(meta);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        List<IProperty<?>> properties = new ArrayList<>();

        if (FACING != null) properties.add(FACING);
        if (INT != null) properties.add(INT);
        return properties.size() > 0 ? new BlockStateContainer(this, properties.toArray(new IProperty[properties.size()])) : super.createBlockState();
    }

    public static void clear() {
        FACING = null;
        INT = null;
    }

    private static PropertyDirection getFacing(IBlockState state) {
        for (IProperty<?> property : state.getPropertyKeys()) {
            if (property instanceof PropertyDirection) return (PropertyDirection) property;
        }

        return null;
    }

    private static PropertyInteger getInteger(IBlockState state) {
        for (IProperty<?> property : state.getPropertyKeys()) {
            if (property instanceof PropertyInteger) return (PropertyInteger) property;
        }

        return null;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        PropertyDirection FACING = getFacing(this.getDefaultState());

        if (FACING != null) {
            EnumFacing face = EnumFacing.getDirectionFromEntityLiving(pos, placer);
            if (!FACING.getAllowedValues().contains(face)) face = FACING.getAllowedValues().toArray(new EnumFacing[FACING.getAllowedValues().size()])[0];

            return this.getDefaultState().withProperty(FACING, face);
        }
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return this.PASSABLE;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return this.FULLCUBE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return this.OPAQUE;
    }

    private static Integer[] intProperty() {
        if (INT == null) return null;

        return INT.getAllowedValues().toArray(new Integer[INT.getAllowedValues().size()]);
    }

    @Override
    public int damageDropped(IBlockState state) {
        if (ITEM_CHECK != null && ITEM_CHECK.check(CraftTweakerMC.getBlockState(state))) return getMetaFromState(state);
        return super.damageDropped(state);
    }
}
