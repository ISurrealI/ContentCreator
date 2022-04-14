package surreal.contentcreator.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.TintColor;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class ClientProxy extends CommonProxy {
    private static final List<TintColor> DEFAULTITEMCOLOR = Collections.singletonList(new TintColor(0, 0xFFFFFF));

    public static final IItemColor ITEMCOLOR = (ItemStack stack, int index) -> {
        if (stack.getItem() instanceof ItemBase) {
            ItemBase item = (ItemBase) stack.getItem();
            if (item.COLOR == null) return 0xFFFFFF;

            for (TintColor tintColor : item.COLOR.getOrDefault(stack.getMetadata(), DEFAULTITEMCOLOR)) {
                if (tintColor.getTintIndex() == index) return tintColor.getValue();
            }
        }

        return 0xFFFFFF;
    };

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, item.getModel(i));
            }

            GeneralUtil.generateModelFileItem(item);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }
    }
}
