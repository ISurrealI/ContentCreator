package surreal.contentcreator.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;
import surreal.contentcreator.ContentCreator;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.fluid.FluidBlockBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.common.item.SubItem;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.types.parts.PartBlock;
import surreal.contentcreator.types.parts.PartItem;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.brackets.ItemBracketHandler;

import java.util.*;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class CommonProxy {
    public static List<ItemBase> ITEMS = new ArrayList<>();
    public static Map<String, ItemMaterial> MAT_ITEMS = new HashMap<>();
    public static List<Block> MAT_BLOCKS = new ArrayList<>();
    public static List<Item> ITEMBLOCKS = new ArrayList<>();

    public static List<Block> FLUID_BLOCKS = new ArrayList<>();
    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<FluidBase> FLUIDS = new ArrayList<>();

    public static List<SoundEvent> SOUNDS = new ArrayList<>();

    public void preInit(FMLPreInitializationEvent event) {
        registerFluids();
        registerMatItems();
        registerMatBlocks();
    }

    public void postInit(FMLPostInitializationEvent event) {
        registerMatOres();
        if (ItemBracketHandler.itemMap == null) ItemBracketHandler.itemMap = CTUtil.getMap();
    }

    private static void registerFluids() {
        for (FluidBase fluid : FLUIDS) {
            FluidRegistry.registerFluid(fluid);
            if (fluid.bucket) FluidRegistry.addBucketForFluid(fluid);
            if (fluid.blockMaterial != null) {
                FluidBlockBase fluidBlock = new FluidBlockBase(fluid, fluid.blockMaterial);

                FLUID_BLOCKS.add(fluidBlock);
                fluid.setBlock(fluidBlock);
            }
        }
    }

    private static void registerMatItems() {
        for (PartItem part : PartItem.PARTS.values()) {
            ItemMaterial item = new ItemMaterial(part);
            MAT_ITEMS.put(part.name, item);
        }
    }

    private static void registerMatBlocks() {
        for (PartBlock part : PartBlock.PARTS.values()) {
            int matSize = part.materials.size();
            int blockCount = matSize / 16;
            int lastBlock = matSize % 16;

            for (int i = 0; i < blockCount; i++) {
                CTMaterial[] mats = new CTMaterial[16];
                for (int g = 0; g < 16; g++) {
                    int v = i * 16;
                    mats[g + v] = part.materials.get(g + v);
                }
                BlockMaterial.setProperty(mats.length);
                BlockMaterial block = new BlockMaterial(i, part, mats);
                MAT_BLOCKS.add(block);
            }

            CTMaterial[] mats = new CTMaterial[lastBlock];
            for (int i = (matSize - lastBlock); i < matSize; i++) {
                mats[i - (matSize - lastBlock)] = part.materials.get(i);
            }
            BlockMaterial.setProperty(mats.length);
            BlockMaterial block = new BlockMaterial(blockCount, part, mats);
            MAT_BLOCKS.add(block);
        }
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        SOUNDS.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
        ITEMBLOCKS.forEach(registry::register);
        MAT_ITEMS.values().forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        FLUID_BLOCKS.forEach(registry::register);
        BLOCKS.forEach(registry::register);
        MAT_BLOCKS.forEach(registry::register);
    }
    
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (ItemBase item : ITEMS) {
            for (int i : item.SUBITEMS.keySet()) {
                SubItem value = item.SUBITEMS.get(i);

                if (value.oreList != null) {
                    for (String ore : value.oreList) {
                        OreDictionary.registerOre(ore, new ItemStack(item, 1, i));
                    }
                }
            }
        }
    }

    private static void registerMatOres() {
        for (ItemMaterial item : MAT_ITEMS.values()) {
            for (CTMaterial material : item.part.getMaterials()) {
                ItemStack stack = new ItemStack(item, 1, material.id);
                for (String ore : material.ores) {
                    OreDictionary.registerOre(item.part.oreName + ore, stack);
                }
            }
        }
    }

    public static Pair<Block, Integer> getBlockAndMetaFromMaterial(String partName, CTMaterial material) {
        PartBlock part = PartBlock.PARTS.get(partName);

        if (part != null && material != null) {
            for (int i = 0; i < part.materials.size(); i++) {
                if (part.materials.get(i) == material) {
                    Block block = MAT_BLOCKS.get(i/16);
                    return Pair.of(block, i%16);
                }
            }
        }

        return null;
    }
}
