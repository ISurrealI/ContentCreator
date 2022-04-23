package surreal.contentcreator.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.util.CTUtil;
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

    private static final IItemColor MATERIALITEMCOLOR = (ItemStack stack, int index) -> {
        if (stack.getItem() instanceof ItemMaterial) {
            ItemMaterial item = (ItemMaterial) stack.getItem();

            return item.getType().getMaterials().get(stack.getMetadata()).getColor();
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

        for (ItemMaterial item : CommonProxy.MATERIAL_ITEMS) {
            ModelBakery.registerItemVariants(item, item.getRegistryName());
            ModelLoader.setCustomMeshDefinition(item, (stack -> new ModelResourceLocation(item.getRegistryName(), "inventory")));
            GeneralUtil.generateModelFileItem(item);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }

        for (ItemMaterial item : CommonProxy.MATERIAL_ITEMS) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(MATERIALITEMCOLOR, item);
        }
    }
}
