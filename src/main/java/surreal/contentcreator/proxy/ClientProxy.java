package surreal.contentcreator.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.CTUtils;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBase;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i = 0; i < CTUtils.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, item.getModel(i));
            }
        }
    }
}
