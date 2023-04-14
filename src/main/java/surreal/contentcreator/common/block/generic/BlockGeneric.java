package surreal.contentcreator.common.block.generic;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IMaterial;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.types.CTSoundEvent;
import surreal.contentcreator.types.CTSoundType;

@ZenRegister
@ZenClass("contentcreator.block.GenericBlock")
public class BlockGeneric {
    private final Block block;

    private BlockGeneric(Block block) {
        this.block = block;
    }

    @ZenMethod
    public static BlockGeneric createPillar(IMaterial material, MapColor mapColor, String name) {
        return new BlockGeneric(createBlock(new BlockGenericPillar(CraftTweakerMC.getMaterial(material), mapColor), name));
    }

    @ZenMethod
    public static BlockGeneric createPillar(IMaterial material, String name) {
        return createPillar(material, CraftTweakerMC.getMaterial(material).getMaterialMapColor(), name);
    }

    @ZenMethod
    public static BlockGeneric createBlockFalling(String name) {
        return new BlockGeneric(createBlock(new BlockGenericFalling(), name));
    }

    @ZenMethod
    public static BlockGeneric createBlockColored(IMaterial material, String name, @Optional boolean color) {
        return new BlockGeneric(createBlock(new BlockGenericColored(CraftTweakerMC.getMaterial(material), color), name));
    }

    @ZenMethod
    public static BlockGeneric createFence(IMaterial material, MapColor mapColor, String name) {
        return new BlockGeneric(createBlock(new BlockGenericFence(CraftTweakerMC.getMaterial(material), mapColor), name));
    }

    @ZenMethod
    public static BlockGeneric createFence(IMaterial material, String name) {
        return createFence(material, CraftTweakerMC.getMaterial(material).getMaterialMapColor(), name);
    }

    @ZenMethod
    public static BlockGeneric createFenceGate(IMaterial material, MapColor mapColor, String name) {
        return new BlockGeneric(createBlock(new BlockGenericFenceGate(CraftTweakerMC.getMaterial(material), mapColor), name));
    }

    @ZenMethod
    public static BlockGeneric createFenceGate(IMaterial material, String name) {
        return createFenceGate(material, CraftTweakerMC.getMaterial(material).getMaterialMapColor(), name);
    }

    @ZenMethod
    public static BlockGeneric createDoor(IMaterial material, String name) {
        return new BlockGeneric(createBlock(new BlockGenericDoor(CraftTweakerMC.getMaterial(material)), name));
    }

    // 0 = all | 1 = Only entities
    @ZenMethod
    public static BlockGeneric createPressurePlate(IMaterial material, int sensitivity, String name) {
        return new BlockGeneric(createBlock(new BlockGenericPressurePlate(CraftTweakerMC.getMaterial(material), sensitivity), name));
    }

    @ZenMethod
    public static BlockGeneric createPressurePlateWeighted(IMaterial material, MapColor mapColor, int maxWeight, String name) {
        return new BlockGeneric(createBlock(new BlockGenericPressurePlateWeighted(CraftTweakerMC.getMaterial(material), maxWeight, mapColor), name));
    }

    @ZenMethod
    public static BlockGeneric createPressurePlateWeighted(IMaterial material, int maxWeight, String name) {
        return createPressurePlateWeighted(material, CraftTweakerMC.getMaterial(material).getMaterialMapColor(), maxWeight, name);
    }

    @ZenMethod
    public static BlockGeneric createButton(String name, boolean wooden, @Optional int tickrate, @Optional CTSoundEvent click, @Optional CTSoundEvent release) {
        return new BlockGeneric(createBlock(new BlockGenericButton(wooden, tickrate).setClick(click).setRelease(release), name));
    }

    @ZenMethod
    public static BlockGeneric createCake(String name, @Optional int foodLevel, @Optional float saturation) {
        return new BlockGeneric(createBlock(new BlockGenericCake(foodLevel, saturation), name));
    }

    @ZenMethod
    public static BlockGeneric createCrop(String name, String crop, @Optional int meta, @Optional int cropMinDrop) {
        return new BlockGeneric(createBlock(new BlockGenericCrop(crop, meta, cropMinDrop), name));
    }

    @ZenMethod
    public static BlockGeneric createCocoaCrop(String name, String crop, @Optional int meta, @Optional int minDrop) {
        return new BlockGeneric(createBlock(new BlockGenericCocoa(new ItemStack(Item.getByNameOrId(crop), 1, meta), minDrop), name));
    }

    @ZenMethod
    public static BlockGeneric createSlab(IMaterial material, MapColor mapColor, String name) {
        return new BlockGeneric(new BlockGenericHalfSlab(CraftTweakerMC.getMaterial(material), mapColor, name));
    }

    @ZenMethod
    public static BlockGeneric createSlab(IMaterial material, String name) {
        return createSlab(material, CraftTweakerMC.getMaterial(material).getMaterialMapColor(), name);
    }

    @ZenMethod
    public static BlockGeneric createStairs(String name, IBlockState state) {
        return new BlockGeneric(createBlock(new BlockGenericStairs(CraftTweakerMC.getBlockState(state)), name));
    }

    @ZenMethod
    public static BlockGeneric createStairs(String name) {
        return createStairs(name, CraftTweakerMC.getBlockState(Blocks.STONE.getDefaultState()));
    }

    @ZenMethod
    public static BlockGeneric createGlass(IMaterial material, String name, @Optional boolean ignoreSimilarity, @Optional boolean silkTouch) {
        return new BlockGeneric(createBlock(new BlockGenericTransparent(CraftTweakerMC.getMaterial(material), ignoreSimilarity, silkTouch), name));
    }

    @ZenMethod
    public BlockGeneric setHardness(float hardness) {
        this.block.setHardness(hardness);
        return this;
    }

    @ZenMethod
    public BlockGeneric setUnbreakable() {
        this.block.setBlockUnbreakable();
        return this;
    }

    @ZenMethod
    public BlockGeneric setStrength(float hardness, float resistance) {
        this.block.setHardness(hardness).setResistance(resistance);
        return this;
    }

    @ZenMethod
    public BlockGeneric setLightOpacity(int opacity) {
        this.block.setLightOpacity(opacity);
        return this;
    }

    @ZenMethod
    public BlockGeneric setLightLevel(int value) {
        this.block.setLightLevel(value);
        return this;
    }

    @ZenMethod
    public BlockGeneric setSlipperiness(float slipperiness) {
        this.block.setDefaultSlipperiness(slipperiness);
        return this;
    }

    @ZenMethod
    public BlockGeneric setHarvestLevel(String tool, int level) {
        this.block.setHarvestLevel(tool, level);
        return this;
    }

    @ZenMethod
    public BlockGeneric setSoundType(CTSoundType soundType) {
        ((IGenericBlock) this.block).setSoundType(soundType);
        return this;
    }

    @ZenMethod
    public void register() {
        ((IGenericBlock) this.block).create(CommonProxy.BLOCKS);
        CommonProxy.BLOCKS.add(this.block);

        Item item = ((IGenericBlock) block).createItem(block);
        if (item != null) CommonProxy.ITEMBLOCKS.add(item);
    }

    private static Block createBlock(Block block, String name) {
        return block.setRegistryName(ModValues.MODID, name).setUnlocalizedName(ModValues.MODID + "." + name);
    }
}
