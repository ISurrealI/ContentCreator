package surreal.contentcreator.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
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

    public void preInit(FMLPreInitializationEvent event) {
        MaterialPart.init();
        GeneralUtil.generateFiles();
    }

    public void init(FMLInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));

        for (MaterialPart type : MaterialPart.TYPES) {
            if (type.getMaterials() == null) continue;

            ItemMaterial item = new ItemMaterial(type);
            event.getRegistry().register(item);
        }
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
