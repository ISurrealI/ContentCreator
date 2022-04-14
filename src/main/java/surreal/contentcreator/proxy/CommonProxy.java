package surreal.contentcreator.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.brackets.ItemBracketHandler;
import surreal.contentcreator.common.item.ItemBase;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class CommonProxy {
    public static List<ItemBase> ITEMS = new ArrayList<>();

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        if (ItemBracketHandler.itemMap == null) ItemBracketHandler.itemMap = CTUtil.getMap();
    }
}
