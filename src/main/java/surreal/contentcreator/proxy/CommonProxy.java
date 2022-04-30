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
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import surreal.contentcreator.common.block.BlockBase;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.fluid.FluidBlockBase;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.common.material.MaterialPart;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.brackets.ItemBracketHandler;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.util.GeneralUtil;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class CommonProxy {
    public static List<ItemBase> ITEMS = new ArrayList<>();
    public static List<ItemMaterial> MATERIAL_ITEMS = new ArrayList<>();
    public static List<ItemBlock> ITEMBLOCKS = new ArrayList<>();

    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<FluidBase> FLUIDS = new ArrayList<>();

    public static List<SoundEvent> SOUNDS = new ArrayList<>();

    private static void init() {
    }

    public void preInit(FMLPreInitializationEvent event) {
        MaterialPart.init();

        GeneralUtil.generateFiles();
        GeneralUtil.generateFluidFiles(FLUIDS);

        registerFluids();

        init();
    }

    public void init(FMLInitializationEvent event) {
    }

    private static void registerFluids() {
        for (FluidBase fluid : FLUIDS) {
            FluidRegistry.registerFluid(fluid);
            if (fluid.bucket) FluidRegistry.addBucketForFluid(fluid);
            if (fluid.blockMaterial != null) {
                FluidBlockBase fluidBlock = new FluidBlockBase(fluid, fluid.blockMaterial);

                BLOCKS.add(fluidBlock);
                fluid.setBlock(fluidBlock);
            }
        }
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(SOUNDS.toArray(new SoundEvent[SOUNDS.size()]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));
        event.getRegistry().registerAll(ITEMBLOCKS.toArray(new ItemBlock[ITEMBLOCKS.size()]));

        for (MaterialPart type : MaterialPart.TYPES) {
            if (type.getMaterials() == null) continue;

            ItemMaterial item = new ItemMaterial(type);
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[BLOCKS.size()]));
    }
    
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (ItemBase item : ITEMS) {
            if (item.METAITEMS.size() > 1) {
                for (int i = 0; i < item.METAITEMS.size(); i++) {
                    ItemBase.ValueItem value = item.METAITEMS.get(i);

                    if (value.ores != null) {
                        for (String ore : value.ores) {
                            OreDictionary.registerOre(ore, new ItemStack(item, 1, i));
                        }
                    }
                }
            } else {
                ItemBase.ValueItem value = item.METAITEMS.get(0);

                if (value.ores != null) {
                    for (String ore : value.ores) {
                        OreDictionary.registerOre(ore, new ItemStack(item));
                    }
                }
            }
        }

        for (MaterialPart part : MaterialPart.TYPES) {
            ItemMaterial item = ItemMaterial.getItemFromPart(part);
            if (item == null) continue;

            for (int i = 0; i < part.getMaterials().size(); i++) {
                OreDictionary.registerOre(item.getOre(i), new ItemStack(item, 1, i));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        if (ItemBracketHandler.itemMap == null) ItemBracketHandler.itemMap = CTUtil.getMap();
    }
}
