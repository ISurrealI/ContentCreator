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
import net.minecraftforge.registries.IForgeRegistry;
import surreal.contentcreator.common.fluid.FluidBase;
import surreal.contentcreator.common.fluid.FluidBlockBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.SubItem;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.brackets.ItemBracketHandler;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class CommonProxy {
    public static List<ItemBase> ITEMS = new ArrayList<>();
    public static List<ItemBlock> ITEMBLOCKS = new ArrayList<>();

    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<FluidBase> FLUIDS = new ArrayList<>();

    public static List<SoundEvent> SOUNDS = new ArrayList<>();

    private static void init() {
    }

    public void preInit(FMLPreInitializationEvent event) {
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
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        SOUNDS.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
        ITEMBLOCKS.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        BLOCKS.forEach(registry::register);
    }
    
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (ItemBase item : ITEMS) {
            for (int i = 0; i < item.SUBITEMS.size(); i++) {
                SubItem value = item.SUBITEMS.get(i);

                if (value.oreList != null) {
                    for (String ore : value.oreList) {
                        OreDictionary.registerOre(ore, new ItemStack(item, 1, i));
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        if (ItemBracketHandler.itemMap == null) ItemBracketHandler.itemMap = CTUtil.getMap();
    }
}
