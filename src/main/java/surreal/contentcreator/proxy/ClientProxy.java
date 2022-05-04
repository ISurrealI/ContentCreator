package surreal.contentcreator.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.ModConfig;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.client.fluid.CustomFluidStateMapper;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.TintColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class ClientProxy extends CommonProxy {
    public static final List<ResourceLocation> fluidTextures = new ArrayList<>();
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

    @Override
    public void init(FMLInitializationEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }
    }

    private static void registerSprites(TextureMap map) {
        for (ResourceLocation location : fluidTextures) {
            map.registerSprite(location);
        }
    }

    private static void registerMappers() {
        for (Fluid fluid : CommonProxy.FLUIDS) {
            registerFluidMapper(fluid);
        }
    }

    private static void registerFluidMapper(Fluid fluid) {
        CustomFluidStateMapper mapper = new CustomFluidStateMapper(fluid.getName());
        ModelLoader.setCustomStateMapper(fluid.getBlock(), mapper);
    }

    @SubscribeEvent
    public static void registerTextures(TextureStitchEvent.Pre event) {
        registerSprites(event.getMap());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerMappers();

        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, item.getModel(i));
            }

            if (ModConfig.CONFIG.generateFiles) GeneralUtil.generateModelFileItem(item);
        }

        for (ItemBlock item : CommonProxy.ITEMBLOCKS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
            if (ModConfig.CONFIG.generateFiles) GeneralUtil.generateModelFileItem(item);
        }

        if (ModConfig.CONFIG.generateFiles) {
            GeneralUtil.generateFiles();
            GeneralUtil.generateFluidFiles(FLUIDS);
        }
    }
}
