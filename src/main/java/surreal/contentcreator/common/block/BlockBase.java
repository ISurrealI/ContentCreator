package surreal.contentcreator.common.block;

import com.google.common.collect.ImmutableList;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IFacing;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBlockBase;
import surreal.contentcreator.functions.block.*;
import surreal.contentcreator.functions.item.IItemUnlocalizedNameFunc;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.util.CTUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"deprecation", "unused"})

// DO DROPS AND SILKTOUCH STUFF
@ZenRegister
@ZenClass("contentcreator.block.Block")
public class BlockBase extends Block {
    // Values
    private BlockRenderLayer layer = BlockRenderLayer.SOLID;
    private EnumOffsetType offset = EnumOffsetType.NONE;
    private List<AxisAlignedBB> COLLISION_BOXES = null;
    private AxisAlignedBB SELECTED_BOX = null;

    // Functions
    public IBlockColorFunc COLOR = null;
    private IBlockMaterialFunc MATERIAL = null;
    private IBlockMapColorFunc MAPCOLOR = null;
    private IBlockHardnessFunc HARDNESS = null;
    private IBlockResistanceFunc RESISTANCE = null;
    private IBlockDestroyExplosionFunc EXPLOSION = null;
    private IBlockStateBooleanFunc OPAQUE = null;
    private IBlockStateBooleanFunc SUFFOCATION = null;
    private IBlockPassableFunc PASSABLE = null;
    private IBlockFaceShapeFunc FACESHAPE = null;
    private IBlockUpdateTickFunc UPDATETICK = null;
    private IBlockUpdateTickFunc DISPLAYTICK = null;
    private IBlockDestroyedPlayerFunc DESTROYPLAYER = null;
    private IBlockNeighborChangeFunc NEIGHBORCHANGED = null;
    private IBlockTickRateFunc TICKRATE = null;
    private IBlockAddFunc ADDED = null;
    private IBlockBreakFunc BREAK = null;
    private IBlockPlaceSideFunc PLACESIDE = null;
    private IBlockPlaceFunc PLACE = null;
    private IBlockActivatedFunc ACTIVATED = null;
    private IBlockEntityWalkFunc ENTITYWALK = null;
    private IBlockStateForPlacementFunc STATEPLACEMENT = null;
    private IBlockPowerFunc WEAKPOWER = null;
    private IBlockPowerFunc STRONGPOWER = null;
    private IBlockEntityCollideFunc ENTITYCOLLIDE = null;
    private IBlockPlacedBy PLACEDBY = null;
    private IBlockHarvestFunc HARVEST = null;
    private IBlockOffsetFunc OFFSET = null;
    private IBlockSlipperinessFunc SLIPPERINESS = null;
    private IBlockLightFunc LIGHTVALUE = null;
    private IBlockLadderFunc LADDER = null;
    private IBlockRemovedPlayerFunc REMOVEDPLAYER = null;
    private IBlockBurnFunc FLAMMABILITY = null;
    private IBlockBurnFunc FIRESPREAD = null;
    private IBlockFireSourceFunc FIRESOURCE = null;
    private IBlockConnectRedstoneFunc CONNECTREDSTONE = null;
    private IBlockPlaceTorchTopFunc TORCHTOP = null;
    private IBlockLightFunc LIGHTOPACITY = null;
    private IBlockEntityDestroyFunc ENTITYDESTROY = null;
    private IBlockBeaconBaseFunc BEACONBASE = null;
    private IBlockEnchantPowerFunc ENCHANTPOWER = null;
    private IBlockSoundFunc SOUND = null;
    private IBlockBeaconMultiplierFunc BEACONMULTIPLIER = null;
    private IBlockStateViewFunc VIEW = null;
    private IBlockStateBooleanFunc STICKY = null;

    // Item Functions
    public IItemUnlocalizedNameFunc ITEMUNLOCNAME = null;
    private IBlockSubItemFunc SUBITEM = null;

    // Properties
    private static PropertyDirection FACING = null;
    private static PropertyInteger INT = null;

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

    public BlockBase(Material materialIn) {
        super(materialIn);
        if (FACING != null) this.setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
        if (INT != null) this.setDefaultState(getDefaultState().withProperty(INT, intProperty()[0]));
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @ZenMethod
    public static BlockBase create(IMaterial material, String name) {
        BlockBase base = new BlockBase(CraftTweakerMC.getMaterial(material));
        base.setRegistryName(ModValues.MODID, name).setUnlocalizedName(ModValues.MODID + "." + name);
        CommonProxy.BLOCKS.add(base);
        CommonProxy.ITEMBLOCKS.add(new ItemBlockBase(base));
        if (FACING != null || INT != null) clear();

        return base;
    }

    @Nonnull
    @Override
    public Material getMaterial(@Nonnull IBlockState state) {
        return MATERIAL != null ? CraftTweakerMC.getMaterial(MATERIAL.getMaterial(CraftTweakerMC.getBlockState(state))) : super.getMaterial(state);
    }

    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return MAPCOLOR != null ? MAPCOLOR.getMapColor(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(worldIn), CraftTweakerMC.getIBlockPos(pos)) : super.getMapColor(state, worldIn, pos);
    }

    @Override
    public float getBlockHardness(@Nonnull IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (HARDNESS != null) return HARDNESS.getBlockHardness(CraftTweakerMC.getBlockState(blockState), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos));
        else return super.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(@Nonnull World world, @Nonnull BlockPos pos, @Nullable Entity exploder, @Nonnull Explosion explosion) {
        if (RESISTANCE != null) return RESISTANCE.getExplosionResistance(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntity(exploder), CraftTweakerMC.getIExplosion(explosion));
        else return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public void onBlockExploded(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull Explosion explosion) {
        if (EXPLOSION != null) EXPLOSION.onBlockExploded(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIExplosion(explosion));
        else super.onBlockExploded(world, pos, explosion);
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return OPAQUE != null ? OPAQUE.check(CraftTweakerMC.getBlockState(state)) : super.isOpaqueCube(state);
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return OPAQUE != null ? OPAQUE.check(CraftTweakerMC.getBlockState(state)) : super.isFullCube(state);
    }

    @Override
    public boolean causesSuffocation(@Nonnull IBlockState state) {
        return SUFFOCATION != null ? SUFFOCATION.check(CraftTweakerMC.getBlockState(state)) : super.causesSuffocation(state);
    }

    @Override
    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return PASSABLE != null ? PASSABLE.isPassable(CTUtil.getIBlockAccess(worldIn), CraftTweakerMC.getIBlockPos(pos)) : super.isPassable(worldIn, pos);
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        return FACESHAPE != null ? BlockFaceShape.valueOf(FACESHAPE.getBlockFaceShape(CTUtil.getIBlockAccess(worldIn), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(face)).toUpperCase()) : super.getBlockFaceShape(worldIn, state, pos, face);
    }

    @Override
    public void addCollisionBoxToList(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (COLLISION_BOXES == null) {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        } else COLLISION_BOXES.forEach(aabb -> addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getSelectedBoundingBox(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        return SELECTED_BOX != null ? SELECTED_BOX : super.getSelectedBoundingBox(state, worldIn, pos);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (UPDATETICK != null) UPDATETICK.updateTick(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state), CTUtil.getRandom(rand));
        else super.updateTick(worldIn, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (DISPLAYTICK != null) DISPLAYTICK.updateTick(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(stateIn), CTUtil.getRandom(rand));
        else super.randomDisplayTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public void onBlockDestroyedByPlayer(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (DESTROYPLAYER != null) DESTROYPLAYER.onBlockDestroyed(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state));
        else super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if (NEIGHBORCHANGED != null) NEIGHBORCHANGED.onNeighborChanged(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockDefinition(blockIn), CraftTweakerMC.getIBlockPos(fromPos));
        else super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public int tickRate(@Nonnull World worldIn) {
        return TICKRATE != null ? TICKRATE.tickRate(CraftTweakerMC.getIWorld(worldIn)) : super.tickRate(worldIn);
    }

    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (ADDED != null) ADDED.onBlockAdded(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state));
        else super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        if (BREAK != null) BREAK.breakBlock(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state));
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return this.layer;
    }

    @Override
    public boolean canPlaceBlockOnSide(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return PLACESIDE != null ? PLACESIDE.canPlaceBlockOnSide(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side)) : super.canPlaceBlockOnSide(worldIn, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        return PLACE != null ? PLACE.canPlaceBlockAt(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos)) : super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        return ACTIVATED != null ? ACTIVATED.onBlockActivated(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIPlayer(playerIn), CraftTweakerMC.getIItemStackMutable(playerIn.getHeldItem(hand)), CraftTweakerMC.getIFacing(facing), hitX, hitY, hitZ) : super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if (ENTITYWALK != null) ENTITYWALK.onEntityWalk(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntity(entityIn));
        else super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, @Nonnull EnumHand hand) {
        return STATEPLACEMENT != null ? CraftTweakerMC.getBlockState(STATEPLACEMENT.getStateForPlacement(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(facing), hitX, hitY, hitZ, meta, CraftTweakerMC.getIEntityLivingBase(placer))) : super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public int getWeakPower(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return WEAKPOWER != null ? WEAKPOWER.getPower(CraftTweakerMC.getBlockState(blockState), CTUtil.getIBlockAccess(blockAccess), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side)) : super.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return STRONGPOWER != null ? STRONGPOWER.getPower(CraftTweakerMC.getBlockState(blockState), CTUtil.getIBlockAccess(blockAccess), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side)) : WEAKPOWER != null ? WEAKPOWER.getPower(CraftTweakerMC.getBlockState(blockState), CTUtil.getIBlockAccess(blockAccess), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side)) : super.getStrongPower(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean canProvidePower(@Nonnull IBlockState state) {
        return WEAKPOWER != null || STRONGPOWER != null || super.canProvidePower(state);
    }

    @Override
    public void onEntityCollidedWithBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entityIn) {
        if (ENTITYCOLLIDE != null) ENTITYCOLLIDE.onEntityCollidedWithBlock(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIEntity(entityIn));
        else super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase placer, @Nonnull ItemStack stack) {
        if (PLACEDBY != null) PLACEDBY.onBlockPlacedBy(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIEntityLivingBase(placer), CraftTweakerMC.getIItemStackMutable(stack));
        else super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        if (SUBITEM != null) {
            ImmutableList<IBlockState> states = this.blockState.getValidStates();

            for (int i = 0; i < states.size(); i++) {
                if (SUBITEM.check(CraftTweakerMC.getBlockState(states.get(i)))) items.add(new ItemStack(this, 1, i));
            }
        }
        else super.getSubBlocks(itemIn, items);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if (SUBITEM != null && SUBITEM.check(CraftTweakerMC.getBlockState(state))) return new ItemStack(this, 1, this.getMetaFromState(state));
        else return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void onBlockHarvested(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player) {
        if (HARVEST != null) HARVEST.onBlockHarvested(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIPlayer(player));
        else super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Nonnull
    @Override
    public EnumOffsetType getOffsetType() {
        return offset;
    }

    @Nonnull
    @Override
    public Vec3d getOffset(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return OFFSET != null ? CraftTweakerMC.getVec3d(OFFSET.getOffset(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(worldIn), CraftTweakerMC.getIBlockPos(pos))) : super.getOffset(state, worldIn, pos);
    }

    @Override
    public float getSlipperiness(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable Entity entity) {
        return SLIPPERINESS != null ? SLIPPERINESS.getSlipperiness(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntity(entity)) : super.getSlipperiness(state, world, pos, entity);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return LIGHTVALUE != null ? LIGHTVALUE.getLightValue(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos)) : super.getLightValue(state, world, pos);
    }

    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockPos beaconPos) {
        if (BEACONMULTIPLIER != null) return BEACONMULTIPLIER.getBeaconColorMultiplier(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIBlockPos(beaconPos));
        else return super.getBeaconColorMultiplier(state, world, pos, beaconPos);
    }

    @Override
    public boolean isLadder(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
        return LADDER != null ? LADDER.isLadder(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntityLivingBase(entity)) : super.isLadder(state, world, pos, entity);
    }

    @Override
    public boolean removedByPlayer(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
        if (REMOVEDPLAYER != null) return REMOVEDPLAYER.removedByPlayer(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIPlayer(player), willHarvest);
        else return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public int getFlammability(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        if (FLAMMABILITY != null) return FLAMMABILITY.getBurn(CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(face));
        else return super.getFlammability(world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        if (FIRESPREAD != null) return FIRESPREAD.getBurn(CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(face));
        else return super.getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public boolean isFireSource(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        if (FIRESOURCE != null) return FIRESOURCE.getBurn(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side));
        else return super.isFireSource(world, pos, side);
    }

    @Override
    public boolean canPlaceTorchOnTop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if (TORCHTOP != null) return TORCHTOP.canPlaceTorchOnTop(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos));
        else return super.canPlaceTorchOnTop(state, world, pos);
    }

    @Override
    public int getLightOpacity(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if (LIGHTOPACITY != null) return LIGHTOPACITY.getLightValue(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos));
        else return super.getLightOpacity(state, world, pos);
    }

    @Override
    public boolean canEntityDestroy(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        if (ENTITYDESTROY != null) return ENTITYDESTROY.canEntityDestroy(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntity(entity));
        else return super.canEntityDestroy(state, world, pos, entity);
    }

    @Nonnull
    @Override
    public IBlockState getStateAtViewpoint(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull Vec3d viewpoint) {
        if (VIEW != null) return CraftTweakerMC.getBlockState(VIEW.getStateAtViewpoint(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIVector3d(viewpoint)));
        else return super.getStateAtViewpoint(state, world, pos, viewpoint);
    }

    @Override
    public boolean isStickyBlock(@Nonnull IBlockState state) {
        if (STICKY != null) return STICKY.check(CraftTweakerMC.getBlockState(state));
        else return super.isStickyBlock(state);
    }

    @Nonnull
    @Override
    public SoundType getSoundType(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nullable Entity entity) {
        if (SOUND != null) return SOUND.getSoundType(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntity(entity)).getInternal();
        else return super.getSoundType(state, world, pos, entity);
    }

    @Override
    public float getEnchantPowerBonus(@Nonnull World world, @Nonnull BlockPos pos) {
        if (ENCHANTPOWER != null) return ENCHANTPOWER.getEnchantPowerBonus(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos));
        else return super.getEnchantPowerBonus(world, pos);
    }

    @Override
    public boolean isBeaconBase(@Nonnull IBlockAccess worldObj, @Nonnull BlockPos pos, @Nonnull BlockPos beacon) {
        if (BEACONBASE != null) return BEACONBASE.isBeaconBase(CTUtil.getIBlockAccess(worldObj), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIBlockPos(beacon));
        else return super.isBeaconBase(worldObj, pos, beacon);
    }

    @Override
    public boolean canConnectRedstone(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing side) {
        if (CONNECTREDSTONE != null) return CONNECTREDSTONE.canConnectRedstone(CraftTweakerMC.getBlockState(state), CTUtil.getIBlockAccess(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIFacing(side));
        else return super.canConnectRedstone(state, world, pos, side);
    }

    // MAYBE...
    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return super.getActualState(state, worldIn, pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasCustomBreakingProgress(@Nonnull IBlockState state) {
        return super.hasCustomBreakingProgress(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean canCollideCheck(@Nonnull IBlockState state, boolean hitIfLiquid) {
        return super.canCollideCheck(state, hitIfLiquid);
    }

    @Override
    public boolean addLandingEffects(@Nonnull IBlockState state, @Nonnull WorldServer worldObj, @Nonnull BlockPos blockPosition, @Nonnull IBlockState iblockstate, @Nonnull EntityLivingBase entity, int numberOfParticles) {
        return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
    }

    @Override
    public boolean addRunningEffects(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        return super.addRunningEffects(state, world, pos, entity);
    }

    @Override
    public boolean addHitEffects(@Nonnull IBlockState state, @Nonnull World worldObj, @Nonnull RayTraceResult target, @Nonnull ParticleManager manager) {
        return super.addHitEffects(state, worldObj, target, manager);
    }

    @Override
    public boolean addDestroyEffects(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ParticleManager manager) {
        return super.addDestroyEffects(world, pos, manager);
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

    @Nonnull
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

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        List<IProperty<?>> properties = new ArrayList<>();

        if (FACING != null) properties.add(FACING);
        if (INT != null) properties.add(INT);
        return properties.size() > 0 ? new BlockStateContainer(this, properties.toArray(new IProperty[properties.size()])) : super.createBlockState();
    }

    // Setters
    @ZenMethod
    public BlockBase setMaterial(IBlockMaterialFunc func) {
        this.MATERIAL = func;
        return this;
    }

    @ZenMethod
    public BlockBase setMapColor(IBlockMapColorFunc func) {
        this.MAPCOLOR = func;
        return this;
    }

    @ZenMethod("setHardness")
    public BlockBase setHard(float hardness) {
        this.HARDNESS = (state, world, pos) -> hardness;
        return this;
    }

    @ZenMethod
    public BlockBase setHardness(IBlockHardnessFunc func) {
        this.HARDNESS = func;
        return this;
    }

    @ZenMethod("setResistance")
    public BlockBase setRes(float resistance) {
        this.RESISTANCE = (world, pos, entity, explosion) -> resistance;
        return this;
    }

    @ZenMethod
    public BlockBase setResistance(IBlockResistanceFunc func) {
        this.RESISTANCE = func;
        return this;
    }

    @ZenMethod
    public BlockBase onBlockExploded(IBlockDestroyExplosionFunc func) {
        this.EXPLOSION = func;
        return this;
    }

    @ZenMethod
    public BlockBase setOpaque() {
        this.OPAQUE = state -> true;
        return this;
    }

    @ZenMethod
    public BlockBase setOpaque(IBlockStateBooleanFunc func) {
        this.OPAQUE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setSuffocation() {
        this.SUFFOCATION = state -> true;
        return this;
    }

    @ZenMethod
    public BlockBase setSuffocation(IBlockStateBooleanFunc func) {
        this.SUFFOCATION = func;
        return this;
    }

    @ZenMethod
    public BlockBase setPassable() {
        this.PASSABLE = (world, pos) -> true;
        return this;
    }

    @ZenMethod
    public BlockBase setPassable(IBlockPassableFunc func) {
        this.PASSABLE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setFaceShape(String faceShape) {
        this.FACESHAPE = (world, state, pos, facing) -> faceShape.toUpperCase();
        return this;
    }

    @ZenMethod
    public BlockBase setFaceShape(IBlockFaceShapeFunc func) {
        this.FACESHAPE = func;
        return this;
    }

    @ZenMethod
    public BlockBase addCollisionBox(IAxisAlignedBB aabb) {
        if (COLLISION_BOXES == null) COLLISION_BOXES = new ArrayList<>();
        if (aabb != null) COLLISION_BOXES.add(CraftTweakerMC.getAxisAlignedBB(aabb));
        return this;
    }

    @ZenMethod
    public BlockBase addCollisionBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (COLLISION_BOXES == null) COLLISION_BOXES = new ArrayList<>();
        COLLISION_BOXES.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        return this;
    }

    @ZenMethod
    public BlockBase setSelectedBox(IAxisAlignedBB aabb) {
        if (aabb != null) SELECTED_BOX = CraftTweakerMC.getAxisAlignedBB(aabb);
        return this;
    }

    @ZenMethod
    public BlockBase setSelectedBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        SELECTED_BOX = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        return this;
    }

    @ZenMethod
    public BlockBase setUpdateTick(IBlockUpdateTickFunc func) {
        UPDATETICK = func;
        return this;
    }

    @ZenMethod
    public BlockBase setDisplayTick(IBlockUpdateTickFunc func) {
        DISPLAYTICK = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBlockDestroyedByPlayer(IBlockDestroyedPlayerFunc func) {
        DESTROYPLAYER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setNeighborChange(IBlockNeighborChangeFunc func) {
        NEIGHBORCHANGED = func;
        return this;
    }

    @ZenMethod
    public BlockBase setTickRate(IBlockTickRateFunc func) {
        this.setTickRandomly(true);
        TICKRATE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBlockAdded(IBlockAddFunc func) {
        ADDED = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBlockBreak(IBlockBreakFunc func) {
        BREAK = func;
        return this;
    }

    @ZenMethod
    public BlockBase setRenderLayer(String layer) {
        this.layer = BlockRenderLayer.valueOf(layer.toUpperCase());
        return this;
    }

    @ZenMethod
    public BlockBase canPlaceSide(IBlockPlaceSideFunc func) {
        this.PLACESIDE = func;
        return this;
    }

    @ZenMethod
    public BlockBase canPlace(IBlockPlaceFunc func) {
        this.PLACE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBlockActivated(IBlockActivatedFunc func) {
        this.ACTIVATED = func;
        return this;
    }

    @ZenMethod
    public BlockBase setEntityWalk(IBlockEntityWalkFunc func) {
        this.ENTITYWALK = func;
        return this;
    }

    @ZenMethod
    public BlockBase setStateForPlacement(IBlockStateForPlacementFunc func) {
        this.STATEPLACEMENT = func;
        return this;
    }

    @ZenMethod
    public BlockBase setWeakPower(int power) {
        this.WEAKPOWER = (state, world, pos, facing) -> power;
        return this;
    }

    @ZenMethod
    public BlockBase setWeakPower(IBlockPowerFunc func) {
        this.WEAKPOWER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setStrongPower(int power) {
        this.STRONGPOWER = (state, world, pos, facing) -> power;
        return this;
    }

    @ZenMethod
    public BlockBase setStrongPower(IBlockPowerFunc func) {
        this.STRONGPOWER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setEntityCollide(IBlockEntityCollideFunc func) {
        this.ENTITYCOLLIDE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setPlacedBy(IBlockPlacedBy func) {
        this.PLACEDBY = func;
        return this;
    }

    @ZenMethod
    public BlockBase setUnlocalizedName(IItemUnlocalizedNameFunc func) {
        this.ITEMUNLOCNAME = func;
        return this;
    }

    @ZenMethod
    public BlockBase setSubItems() {
        this.SUBITEM = state -> true;
        return this;
    }

    @ZenMethod
    public BlockBase setSubItems(IBlockSubItemFunc func) {
        this.SUBITEM = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBlockHarvest(IBlockHarvestFunc func) {
        this.HARVEST = func;
        return this;
    }

    @ZenMethod
    public BlockBase setOffset(String offset) {
        this.offset = EnumOffsetType.valueOf(offset);
        return this;
    }

    @ZenMethod
    public BlockBase setOffset(IBlockOffsetFunc func) {
        this.OFFSET = func;
        return this;
    }

    @ZenMethod
    public BlockBase setSlipperiness(float slipperiness) {
        this.SLIPPERINESS = (state, world, pos, entity) -> slipperiness;
        return this;
    }

    @ZenMethod
    public BlockBase setSlipperiness(IBlockSlipperinessFunc func) {
        this.SLIPPERINESS = func;
        return this;
    }

    @ZenMethod
    public BlockBase setLightValue(int lightValue) {
        this.LIGHTVALUE = (state, world, pos) -> lightValue;
        return this;
    }

    @ZenMethod
    public BlockBase setLightValue(IBlockLightFunc func) {
        this.LIGHTVALUE = func;
        return this;
    }

    @ZenMethod
    public BlockBase isLadder() {
        this.LADDER = (state, world, pos, entity) -> true;
        return this;
    }

    @ZenMethod
    public BlockBase isLadder(IBlockLadderFunc func) {
        this.LADDER = func;
        return this;
    }

    @ZenMethod
    public BlockBase onRemovedByPlayer(IBlockRemovedPlayerFunc func) {
        this.REMOVEDPLAYER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setFlammability(int flammability) {
        this.FLAMMABILITY = (world, pos, facing) -> flammability;
        return this;
    }

    @ZenMethod
    public BlockBase setFlammability(IBlockBurnFunc func) {
        this.FLAMMABILITY = func;
        return this;
    }

    @ZenMethod
    public BlockBase setFireSpread(int fireSpread) {
        this.FIRESPREAD = (world, pos, facing) -> fireSpread;
        return this;
    }

    @ZenMethod
    public BlockBase setFireSpread(IBlockBurnFunc func) {
        this.FIRESPREAD = func;
        return this;
    }

    @ZenMethod
    public BlockBase isFireSource() {
        this.FIRESOURCE = (world, pos, facing) -> true;
        return this;
    }

    @ZenMethod
    public BlockBase isFireSource(IBlockFireSourceFunc func) {
        this.FIRESOURCE = func;
        return this;
    }

    @ZenMethod
    public BlockBase canConnectRedstone() {
        this.CONNECTREDSTONE = (state, world, pos, facing) -> true;
        return this;
    }

    @ZenMethod
    public BlockBase canConnectRedstone(IBlockConnectRedstoneFunc func) {
        this.CONNECTREDSTONE = func;
        return this;
    }

    @ZenMethod
    public BlockBase canPlaceTorchOnTop(IBlockPlaceTorchTopFunc func) {
        this.TORCHTOP = func;
        return this;
    }

    @ZenMethod("setLightOpacity")
    public BlockBase setLO(int lightOpacity) {
        this.LIGHTOPACITY = (state, world, pos) -> lightOpacity;
        return this;
    }

    @ZenMethod
    public BlockBase setLightOpacity(IBlockLightFunc func) {
        this.LIGHTOPACITY = func;
        return this;
    }

    @ZenMethod
    public BlockBase canEntityDestroy(IBlockEntityDestroyFunc func) {
        this.ENTITYDESTROY = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBeaconBase(IBlockBeaconBaseFunc func) {
        this.BEACONBASE = func;
        return this;
    }

    @ZenMethod
    public BlockBase setEnchantPower(IBlockEnchantPowerFunc func) {
        this.ENCHANTPOWER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setSoundType(IBlockSoundFunc func) {
        this.SOUND = func;
        return this;
    }

    @ZenMethod
    public BlockBase setBeaconMultiplier(IBlockBeaconMultiplierFunc func) {
        this.BEACONMULTIPLIER = func;
        return this;
    }

    @ZenMethod
    public BlockBase setStateAtViewpoint(IBlockStateViewFunc func) {
        this.VIEW = func;
        return this;
    }

    @ZenMethod
    public BlockBase isSticky(IBlockStateBooleanFunc func) {
        this.STICKY = func;
        return this;
    }

    @ZenMethod
    public BlockBase setColor(IBlockColorFunc func) {
        this.COLOR = func;
        return this;
    }

    private static Integer[] intProperty() {
        if (INT == null) return null;

        return INT.getAllowedValues().toArray(new Integer[INT.getAllowedValues().size()]);
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

    public static void clear() {
        FACING = null;
        INT = null;
    }
}
