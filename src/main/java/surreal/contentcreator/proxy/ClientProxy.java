package surreal.contentcreator.proxy;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import surreal.contentcreator.ModConfig;
import surreal.contentcreator.client.fluid.CustomFluidStateMapper;
import surreal.contentcreator.common.block.BlockBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemBlockBase;
import surreal.contentcreator.functions.item.IItemColorFunc;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static final List<String> texturesToRegister = new ArrayList<>();
    public static final List<ResourceLocation> fluidTextures = new ArrayList<>();

    public static final IItemColor ITEMCOLOR = (stack, tintIndex) -> {
        if (stack.getItem() instanceof ItemBase) {
            ItemBase item = (ItemBase) stack.getItem();
            IItemColorFunc color = item.SUBITEMS.get(stack.getMetadata()).COLOR;
            if (color != null) return color.colorMultiplier(CraftTweakerMC.getIItemStack(stack), tintIndex);
        }

        return 0xFFFFFF;
    };

    public static final IItemColor ITEMBLOCKCOLOR = (ItemStack stack, int index) -> {
        if (stack.getItem() instanceof ItemBlockBase) {
            ItemBlockBase item = (ItemBlockBase) stack.getItem();
            if (item.blockBase.COLOR != null) return item.blockBase.COLOR.colorMultiplier(CraftTweakerMC.getBlockState(item.blockBase.getStateFromMeta(stack.getMetadata())), index);
        }

        return 0xFFFFFF;
    };

    public static final IBlockColor BLOCKCOLOR = (state, worldIn, pos, tintIndex) -> {
        if (state.getBlock() instanceof BlockBase) {
            BlockBase block = (BlockBase) state.getBlock();
            if (block.COLOR != null) return block.COLOR.colorMultiplier(CraftTweakerMC.getBlockState(state), tintIndex);
        }

        return 0xFFFFFF;
    };

    private static void registerSprites(TextureMap map) {
        for (ResourceLocation location : fluidTextures) {
            map.registerSprite(location);
        }

        for (String location : texturesToRegister) {
            map.registerSprite(new ResourceLocation(location));
        }
    }

    private static void registerMappers() {
        for (Fluid fluid : CommonProxy.FLUIDS) {
            registerFluidMapper(fluid);
        }
    }

    private static void registerFluidMapper(Fluid fluid) {
        if (fluid.getBlock() != null) {
            CustomFluidStateMapper mapper = new CustomFluidStateMapper(fluid.getName());
            ModelLoader.setCustomStateMapper(fluid.getBlock(), mapper);
        }
    }

    @SubscribeEvent
    public static void registerTextures(TextureStitchEvent.Pre event) {
        registerSprites(event.getMap());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerMappers();

        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i = 0; i < item.SUBITEMS.size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getModelLocation(i)));
            }
        }

        for (ItemBlock item : CommonProxy.ITEMBLOCKS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }

        if (ModConfig.CONFIG.generateFiles) {
            GeneralUtil.generateFiles();
            GeneralUtil.generateFluidFiles(FLUIDS);
        }
    }

    @SubscribeEvent
    public static void regColorsBlock(ColorHandlerEvent.Block event) {
        for (Block block : CommonProxy.BLOCKS) {
            event.getBlockColors().registerBlockColorHandler(BLOCKCOLOR, block);
        }
    }

    @SubscribeEvent
    public static void regColorsItem(ColorHandlerEvent.Item event) {
        for (Item item : CommonProxy.ITEMS) {
            event.getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }

        for (Item item : CommonProxy.ITEMBLOCKS) {
            event.getItemColors().registerItemColorHandler(ITEMBLOCKCOLOR, item);
        }
    }
}
